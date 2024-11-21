package com.yndw.dvp.xtgl.auth.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.*;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.constant.GnxxLxdmConstant;
import com.yndw.dvp.xtgl.auth.dto.*;
import com.yndw.dvp.xtgl.auth.factory.XtGnxxFactory;
import com.yndw.dvp.xtgl.auth.feign.IMallZdygnzService;
import com.yndw.dvp.xtgl.auth.mapper.XtGnxxMapper;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtGnxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtGnxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Create By Carlos
 * 2020/6/12
 */
@RestController
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/gnxx")
@Api(tags = "功能管理")
public class XtGnxxController {
    private static final Logger logger = LoggerFactory.getLogger(XtGnxxController.class);
    @Autowired
    private IXtGnxxService gnxxService;
    @Autowired
    private IMallZdygnzService zdygnzService;
    @Autowired
    private XtGnxxMapper gnxxMapper;

    @ApiOperation(value = "获取功能组")
    @GetMapping("/getGnzxx")
    public CommonResult<List<XtZdygnzDto>> getGnz(@LoginUser SecurityUser LoginUser) {
        String czybh = LoginUser.getBh();
        List<XtZdygnzDto> gnz = zdygnzService.getZdyGnzByYhbh(czybh);
        return CommonResult.succeed(gnz);
    }

    @ApiOperation(value = "获取功能组")
    @GetMapping("/searchGn")
    public Page<SearchDto> searchGn(int page, int limit, String searchWord) {
        Page searchDtoPage = new Page<>(page, limit);
        return gnxxMapper.searchGn(searchDtoPage, searchWord);

    }

    @ApiOperation(value = "获取功能组下的功能")
    @GetMapping("/getGnxxBygnzbh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gnzbh", value = "功能组编号", required = false)
    })
    public CommonResult<List<XtZdygnzDto>> getGnxxByczybh(@RequestParam("gnzbh") String gnzbh) {
        List<XtZdygnzDto> gnzxx = zdygnzService.getZdyGnzByYhbh(gnzbh);
        return CommonResult.succeed(gnzxx);
    }

    @GetMapping(value = "/list")
    @AuditLog(operation = "查询功能")
    @ApiOperation(value = "查询功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtGnxxQueryParam", required = false)
    })
    public CommonResult<List<XtGnxxDto>> list(XtGnxxQueryParam queryParam) {
        try {
            List<XtGnxx> entityList = gnxxService.findList(queryParam);
            return CommonResult.succeed(XtGnxxFactory.build(entityList));
        } catch (Exception e) {
            logger.debug("查询功能异常：" + e);
            throw new BusinessException("查询功能异常");
        }
    }


    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新功能")
    @ApiOperation(value = "添加或更新功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "功能信息", dataType = "XtGnxx", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtGnxx entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtGnxx result = gnxxService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "功能成功") : CommonResult.failed(msg + "功能失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("添加或更新功能异常：" + e);
            throw new BusinessException("添加或更新功能异常：" + e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除功能")
    @ApiOperation(value = "删除功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "功能编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return gnxxService.deleteById(bh) > 0 ? CommonResult.succeed("删除功能成功") : CommonResult.failed("删除功能失败");
        } catch (Exception e) {
            logger.debug("删除功能异常：" + e);
            throw new BusinessException("删除异常");
        }
    }

    @GetMapping(value = "/get/{bh}")
    @AuditLog(operation = "获取功能")
    @ApiOperation(value = "获取功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "功能编号", dataType = "String", required = true)
    })
    public CommonResult<XtGnxx> get(@PathVariable(value = "bh", required = true) String bh) {
        try {
            XtGnxx entity = gnxxService.getById(bh);
            return entity != null ? CommonResult.succeed(entity, "获取功能成功") : CommonResult.failed("获取功能失败");
        } catch (Exception e) {
            logger.debug("获取功能异常：" + e);
            throw new BusinessException("获取功能异常");
        }
    }

    @PutMapping(value = "/updateZtdm")
    @AuditLog(operation = "更新功能状态")
    @ApiOperation(value = "更新功能状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录信息，系统XtCzyxx自动获取", dataType = "", required = false),
            @ApiImplicitParam(name = "entity", value = "功能信息", dataType = "XtGnxx", required = true)
    })
    public CommonResult<XtGnxx> updateZtdm(@LoginUser SecurityUser securityUser, @RequestBody XtGnxx entity) {
        try {
            return gnxxService.updateZtdm(securityUser, entity) > 0 ? CommonResult.succeed("更新功能状态成功") : CommonResult.failed("更新功能状态失败");
        } catch (Exception e) {
            logger.debug("更新功能状态异常：" + e);
            throw new BusinessException("更新功能状态异常");
        }
    }

    @ApiOperation(value = "根据roleCodes获取对应的权限")
//    @Cacheable(value = "gnxx", key ="#jsbhs")
    @GetMapping("/findMenuByJsbhs/{jsbhs}")
    public List<SecurityMenu> findMenuByJsbhs(@PathVariable String jsbhs, @LoginUser SecurityUser czyxx) {
        try {
            List<SecurityMenu> result = null;
            if (StringUtils.isNotEmpty(jsbhs)) {
                Set<String> jsbhSet = (Set<String>) Convert.toCollection(HashSet.class, String.class, jsbhs);
                result = gnxxService.fingGnxxByJsbhs(czyxx, jsbhSet, GnxxLxdmConstant.PERMISSION.getValue());
            }
            return result;
        } catch (Exception e) {
            logger.debug("查询功能权限异常：" + e);
            throw new BusinessException("查询功能权限异常");
        }
    }

    @ApiOperation(value = "根据操作员权限获取对应的权限")
//    @Cacheable(value = "gnxx", key ="#jsbhs")
    @GetMapping("/findMenuByCzybhs/{czybhs}")
    public List<SecurityMenu> findMenuByCzybhs(@PathVariable String czybhs, @LoginUser SecurityUser czyxx) {
        try {
            List<SecurityMenu> result = null;
            if (StringUtils.isNotEmpty(czybhs)) {
                Set<String> czybhSet = (Set<String>) Convert.toCollection(HashSet.class, String.class, czybhs);
                result = gnxxService.fingGnxxByCzybhs(czyxx, czybhSet, GnxxLxdmConstant.PERMISSION.getValue());
            }
            return result;
        } catch (Exception e) {
            logger.debug("查询功能权限异常：" + e);
            throw new BusinessException("查询功能权限异常");
        }
    }

    @ApiOperation(value = "获取当前登陆人所拥有的菜单")
    @GetMapping("/getAuthGnxxMenuByLoginUser")
    public CommonResult<List<MenuNode>> getAuthGnxxByLoginUser(@LoginUser SecurityUser czyxx) {
        List<SecurityRole> jsxxList = czyxx.getSecurityRoleList();
        if (CollectionUtil.isEmpty(jsxxList)) {
            return CommonResult.succeed(Collections.emptyList());
        }

        List<SecurityMenu> gnxxList = gnxxService.fingGnxxByJsbhs(czyxx,
                jsxxList.parallelStream().map(SecurityRole::getBh).collect(Collectors.toSet()),  // 安全用户列表收集并去重
                GnxxLxdmConstant.MENU.getValue()
        );
        return CommonResult.succeed(XtGnxxFactory.buildGnxxMenuTree(gnxxList));
    }

    @ApiOperation(value = "获取当前登陆人自定义的菜单")
    @GetMapping("/getOwnGnxxByLoginUser")
    public CommonResult<List<MenuNode>> getOwnGnxxByLoginUser(@LoginUser SecurityUser czyxx) {
        List<SecurityRole> jsxxList = czyxx.getSecurityRoleList();
        if (CollectionUtil.isEmpty(jsxxList)) {
            return CommonResult.succeed(Collections.emptyList());
        }
        List<SecurityMenu> gnxxList = gnxxService.findOwnGnxx(czyxx,
                jsxxList.parallelStream().map(SecurityRole::getBh).collect(Collectors.toSet()),
                GnxxLxdmConstant.MENU.getValue()
        );
        return CommonResult.succeed(XtGnxxFactory.buildGnxxMenuTree(gnxxList));
    }

    @GetMapping(value = "/gnxxCount")
    @AuditLog(operation = "查询功能信息总数、启用数和停用数")
    @ApiOperation(value = "查询功能信息总数、启用数和停用数")
    public CommonResult<XtGnxxCountDto> gwxxCount() {
        try {
            XtGnxxCountDto xtGnxxCountDto = gnxxService.gnxxCount();
            return (xtGnxxCountDto != null) ? CommonResult.succeed(xtGnxxCountDto) : CommonResult.failed("查询功能信息总数、启用数和停用数失败");
        } catch (Exception e) {
            logger.debug("查询功能信息总数、启用数和停用数异常：" + e);
            throw new BusinessException("查询功能信息总数、启用数和停用数异常");
        }
    }

    @ApiOperation(value = "获取应用的功能")
    @GetMapping("/getGnxxByyybh/yybh")
    @ApiImplicitParam(name = "yybh", value = "应用编号")
    public CommonResult<XtSqGnxx> getGnxxByyybh(@PathVariable String yybh, @LoginUser SecurityUser czyxx) {
        String czybh = czyxx.getBh();
        XtSqGnxx gnxx = gnxxService.getByYybh(yybh, czybh);
        return CommonResult.succeed(gnxx);
    }

    @PostMapping("/myGnxx")
    @AuditLog(operation = "获取我的功能")
    @ApiOperation(value = "获取我的功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "securityUser", value = "当前登录信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtGnxxQueryParam", required = false)
    })
    public Page<XtGnxx> myGnxx(@LoginUser SecurityUser securityUser, @RequestBody XtGnxxQueryParam queryParam) {
        try {
            return gnxxService.myGnxx(securityUser, queryParam);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询我的功能异常" + e);
            throw new BusinessException("查询我的功能异常");
        }
    }


    @GetMapping("/gnxxByYybh/{yybh}")
    @AuditLog(operation = "获取应用功能")
    @ApiOperation(value = "获取应用功能")
    public CommonResult<List<XtGnxx>> gnxxByYybh(@PathVariable(value = "yybh", required = true) String yybh){
        try{
            return  CommonResult.succeed(gnxxService.gnxxByYybh(yybh));
        }catch (Exception e){
            logger.debug("查询应用功能");
            throw  new BusinessException("查询应用功能"+e.getMessage());
        }
    }

    @GetMapping("/gnxxByCzy/{czybh}")
    @AuditLog(operation = "获取用户已授权功能")
    @ApiOperation(value = "获取用户已授权功能")
    public List<SecurityMenu> authGnxxByCzy(@PathVariable("czybh") String czybh){
        try{
            return gnxxService.authGnxxByCzy(czybh);
        }catch (Exception e){
            logger.debug("获取当前用户已授权功能异常");
            e.printStackTrace();
            throw  new BusinessException("查询应用功能");
        }
    }
}
