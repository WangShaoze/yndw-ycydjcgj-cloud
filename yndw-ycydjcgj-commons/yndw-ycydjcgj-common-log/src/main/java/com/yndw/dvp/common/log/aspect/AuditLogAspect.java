package com.yndw.dvp.common.log.aspect;

import com.alibaba.fastjson.JSONObject;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.common.log.model.Audit;
import com.yndw.dvp.common.log.properties.AuditLogProperties;
import com.yndw.dvp.common.log.service.IAuditService;
import com.yndw.dvp.common.log.utils.WebToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 审计日志切面
 *
 *
 * @date 2020/2/3
 * <p>
 *
 *
 */
@Slf4j
@Aspect
@ConditionalOnClass({HttpServletRequest.class, RequestContextHolder.class})
public class AuditLogAspect {
    @Value("${spring.application.name}")
    private String applicationName;

    private AuditLogProperties auditLogProperties;
    private IAuditService auditService;

    public AuditLogAspect(AuditLogProperties auditLogProperties, IAuditService auditService) {
        this.auditLogProperties = auditLogProperties;
        this.auditService = auditService;
    }

    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Before("@within(auditLog) || @annotation(auditLog)")
    public void beforeMethod(JoinPoint joinPoint, AuditLog auditLog) {
        //判断功能是否开启
        if (auditLogProperties.getEnabled()) {
            if (auditService == null) {
                log.warn("AuditLogAspect - auditService is null");
                return;
            }
            if (auditLog == null) {
                // 获取类上的注解
                auditLog = joinPoint.getTarget().getClass().getDeclaredAnnotation(AuditLog.class);
            }
            Audit audit = getAudit(auditLog, joinPoint);
            auditService.save(audit);
        }
    }

    /**
     * 解析spEL表达式
     */
    private String getValBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEL);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for(int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            return expression.getValue(context).toString();
        }
        return null;
    }

    /**
     * 构建审计对象
     */
    private Audit getAudit(AuditLog auditLog, JoinPoint joinPoint) {
        Audit audit = new Audit();
        audit.setTimestamp(LocalDateTime.now());
        audit.setApplicationName(applicationName);

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        audit.setClassName(methodSignature.getDeclaringTypeName());
        audit.setMethodName(methodSignature.getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 响应
        HttpServletResponse response = attributes.getResponse();
        String userId = request.getHeader("x-userid-header");
        String userName = request.getHeader("x-user-header");
        String clientId = request.getHeader("x-tenant-header");
        // 获取 访问ip以及服务端ip
        String ipAddr = WebToolUtils.getIpAddr(request);
        String localIP = "";
        try {
            localIP = WebToolUtils.getLocalIP();
        } catch (Exception e) {
            log.error("获取服务端ip失败");
        }
        // 请求结果
        String result = response.getStatus() == 200 ? "1": "0";
        audit.setUserId(userId);
        audit.setUserName(userName);
        audit.setClientId(clientId);
        // 入参
        audit.setInParam(getAllParam(joinPoint, request));
        audit.setRequestIp(ipAddr);
        audit.setServerIp(localIP);
        audit.setMethodResult(result);
        String operation = auditLog.operation();
        if (operation.contains("#")) {
            //获取方法参数值
            Object[] args = joinPoint.getArgs();
            operation = getValBySpEL(operation, methodSignature, args);
        }
        audit.setOperation(operation);

        return audit;
    }

    private String getAllParam(JoinPoint joinPoint, HttpServletRequest request){
        String strParamsm = "";
        String methond = request.getMethod();
        // get 或 delete 请求
        if (methond.equals("GET") || methond.equals("DELETE")){
            // 获取url参数 request.getQueryString()
            strParamsm = " {";
            // request.getParameterMap();
            Enumeration enu=request.getParameterNames();
            while(enu.hasMoreElements()){
                String paraName=(String)enu.nextElement();
                strParamsm = strParamsm +"\""+ paraName+"\": \""+request.getParameter(paraName) + "\",";
            }
            if(strParamsm.indexOf(",") >0)
                strParamsm = strParamsm.substring(0,strParamsm.lastIndexOf(","))+"}";
            else
                strParamsm = strParamsm + "}";
        }else{
            Object[] argsParms = joinPoint.getArgs();
            Object[] arguments  = new Object[argsParms.length];
            for (int i = 0; i < argsParms.length; i++) {
                if (argsParms[i] instanceof ServletRequest || argsParms[i] instanceof ServletResponse || argsParms[i] instanceof MultipartFile) {
                    //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                    //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                    continue;
                }
                arguments[i] = argsParms[i];
            }
            strParamsm = JSONObject.toJSONString(arguments);
        }
        return strParamsm ;
    }

}
