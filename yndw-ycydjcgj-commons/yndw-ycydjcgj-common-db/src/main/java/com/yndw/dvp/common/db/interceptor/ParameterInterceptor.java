package com.yndw.dvp.common.db.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }) })

public class ParameterInterceptor implements Interceptor  {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // TODO Auto-generated method stub
        // 拦截sql
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        // 请求参数对象
        Object parameterObject = args[1];

        // 获取 SQL
        SqlCommandType sqlCommandType = statement.getSqlCommandType();
        if (SqlCommandType.SELECT.equals(sqlCommandType)) {
            if (parameterObject instanceof HashMap) {
                // 调用特殊字符处理方法
                HashMap hash = (HashMap) parameterObject;
                hash.forEach((k, v) -> {
                    // 仅拦截字符串类型且值不为空
                    if (v != null && v instanceof String) {
                        String value = (String) v;
                        value = value.replace("\\", "\\\\");
                        value = value.replace("_", "\\_");
                        value = value.replaceAll("%", "AB&_%");
                        // 请求参数对象HashMap重新赋值转义后的值
                        hash.put(k, value);
                    }else {
                        try {
                            printFieldMessage(v);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

            }
        }
        // 返回
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // TODO Auto-generated method stub
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }

    /**
     * 获取对象的成员变量的信息
     *
     * @param obj
     */
    public static void printFieldMessage(Object obj) throws IllegalAccessException {
        Class c = obj.getClass();
        /*
         * 成员变量也是对象 java.lang.reflect.Field Field类封装了关于成员变量的操作
         * getFields()方法获取的是所有的public的成员变量的信息
         * getDeclaredFields获取的是该类自己声明的成员变量的信息
         */
        // Field[] fs = c.getFields();
        Field[] fs = c.getDeclaredFields();
        for (Field field : fs) {
            // isAccessible为false时不可以通过反射访问该字段，设置成true
            field.setAccessible(true);
            Object v = field.get(obj);
            if (v != null && v instanceof String){
                //
                String firstChar = "\\";
                String value = (String) v;
                // 判断\后一位是否有_ 或 %
                if (value.length() > 1){
                    char resutChar = value.charAt(value.indexOf(firstChar)+1);
                    if (resutChar == '%' || resutChar == '_'){
                        value = value.replace("\\","");
                    }
                }
                // 转义 \
                value = value.replace("\\", "\\\\");
                value = value.replace("\\\\\\\\", "\\\\");
                System.out.println("##################" + value);
                // 转义 _
                value = value.replace("\\_","_");
                value = value.replace("_", "\\_");
                value = value.replace("\\\\_", "\\_");
                // 转义%
                value = value.replace("\\%", "%");
                value = value.replace("%", "\\%");
                value = value.replace("\\\\%", "\\%");
                field.set(obj, value);
            }
        }
    }

}
