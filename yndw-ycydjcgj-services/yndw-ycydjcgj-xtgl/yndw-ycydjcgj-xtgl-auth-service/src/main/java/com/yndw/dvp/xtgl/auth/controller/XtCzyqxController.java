package com.yndw.dvp.xtgl.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.constant.GnxxLxdmConstant;
import com.yndw.dvp.xtgl.auth.dto.CzyqxDto;
import com.yndw.dvp.xtgl.auth.dto.GnTopDto;
import com.yndw.dvp.xtgl.auth.dto.MyAppDto;
import com.yndw.dvp.xtgl.auth.dto.YyUITreeDto;
import com.yndw.dvp.xtgl.auth.factory.XtCzyqxFactory;
import com.yndw.dvp.xtgl.auth.feign.IMallYyxxService;
import com.yndw.dvp.xtgl.auth.model.XtCzyqx;
import com.yndw.dvp.xtgl.auth.model.XtGnxx;
import com.yndw.dvp.xtgl.auth.model.XtGwxx;
import com.yndw.dvp.xtgl.auth.model.XtYyxx;
import com.yndw.dvp.xtgl.auth.queryParam.XtCzyqxSaveParam;
import com.yndw.dvp.xtgl.auth.queryParam.XtGnxxQueryParam;
import com.yndw.dvp.xtgl.auth.queryParam.XtYyxxQueryParam;
import com.yndw.dvp.xtgl.auth.service.IXtCzyqxService;
import com.yndw.dvp.xtgl.auth.service.IXtCzyxxService;
import com.yndw.dvp.xtgl.auth.service.IXtYyxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyqx")
@Api(tags = "操作员权限管理")
public class XtCzyqxController {

    private static final Logger logger = LoggerFactory.getLogger(XtCzyqxController.class);
    @Autowired
    private IXtCzyqxService czyqxService;
    @Autowired
    private IXtCzyxxService czyxxService;
    @Autowired
    private IMallYyxxService yyxxService;

    @Autowired
    private IXtYyxxService xtYyxxService;

    @GetMapping(value = "/getCzyGnList/{czybh}")
    @AuditLog(operation = "获取操作员已分配功能")
    @ApiOperation(value = "获取操作员已分配功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "czybh", value = "操作员编号", dataType = "String", required = true)
    })
    public CommonResult<List<YyUITreeDto>> getGwJsList(@PathVariable(value = "czybh", required = true) String czybh) {
        try {
            String czyGwGns = czyqxService.getGnbhsByGwbh(czyxxService.getByCzybh(czybh).getGwbh());
            String czygns = czyqxService.getGnbhsByCzybh(czybh);
            String czyyys = czyqxService.getYybhsByCzybh(czybh);
            List<XtGnxx> gnxxList = czyqxService.findList(new XtGnxxQueryParam());
            List<XtYyxx> yyxxList = xtYyxxService.findList(new XtYyxxQueryParam());
            return CommonResult.succeed(XtCzyqxFactory.buildGnxxTree(czyGwGns, czygns, czyyys, gnxxList, yyxxList));
        } catch (Exception e) {
            logger.debug("获取操作员已分配功能异常：" + e);
            throw new BusinessException("获取操作员已分配功能异常");
        }
    }

    @PutMapping(value = "/setGnxxToCzy")
    @AuditLog(operation = "为操作员分配角色功能之外的功能")
    @ApiOperation(value = "为操作员分配角色功能之外的功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "czybh", value = "操作员编号", dataType = "String", required = true),
            @ApiImplicitParam(name = "gnxxList", value = "授权功能", dataType = "List<YyUITreeDto>", required = true),
    })
    public CommonResult<XtGwxx> setGnxxToCzy(@RequestBody XtCzyqxSaveParam saveParam) {
        try {
            czyqxService.setGnxxToCzyqxx(saveParam.getCzybh(), saveParam.getGnxxList());
            return CommonResult.succeed("操作员分配角色功能之外的功能成功");
        } catch (Exception e) {
            logger.debug("操作员分配角色功能之外的功能异常：" + e);
            throw new BusinessException("操作员分配角色功能之外的功能异常");
        }
    }

    @PostMapping(value = "/addGn")
    @AuditLog(operation = "操作菜单添加功能数")
    @ApiOperation(value = "操作菜单添加功能数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "czybh", value = "操作员编号", dataType = "String", required = true),
            @ApiImplicitParam(name = "gnbh", value = "功能名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "gwbh", value = "岗位编号", dataType = "String", required = true),
    })
    public CommonResult<XtCzyqx> addGn(@RequestParam String czybh, @RequestParam String gnbh, @RequestParam String gwbh) {
        try {
            czyqxService.addGn(czybh, gnbh, gwbh);

            return CommonResult.succeed("功能计数成功");
        } catch (Exception e) {
            logger.debug("计数异常：" + e);
            throw new BusinessException("功能计数异常");
        }
    }

    @GetMapping(value = "/gnxxCount")
    @AuditLog(operation = "查询排名前十的功能总数")
    @ApiOperation(value = "查询排名前十的功能总数")
    public CommonResult<List<GnTopDto>> gnxxCount() {
        try {
            List<GnTopDto> gnTopten = czyqxService.getGnTopten();
            return (gnTopten != null) ? CommonResult.succeed(gnTopten) : CommonResult.failed("查询失败");
        } catch (Exception e) {
            logger.debug("查询异常：" + e);
            throw new BusinessException("查询排名前10的功能计数异常");
        }
    }

    @ApiOperation(value = "根据czybh获取对应的权限功能")
    @GetMapping("/findGnxxByCzybh/{czybh}")
    public List<SecurityMenu> findMenuByJsbhs(@PathVariable String czybh) {
        try {
            List<SecurityMenu> result = null;
            result = czyqxService.findGnxxByCzybh(czybh, GnxxLxdmConstant.PERMISSION.getValue());
            return result;
        } catch (Exception e) {
            logger.debug("查询功能权限异常：" + e);
            throw new BusinessException("查询功能权限异常");
        }
    }

    @ApiOperation(value = "根据czybh获取常用应用")
    @PostMapping("/findCzyqxByCzybh")
    public List<CzyqxDto> findCzyqxByCzybh(@RequestParam("czybh") String czybh) {
        try {
            return czyqxService.getCzyqxbyCzybh(czybh);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询人气应用异常：" + e);
            throw new BusinessException("查询人气应用异常");
        }
    }

    @ApiOperation(value = "根据czybh获取我的应用")
    @GetMapping("/myApp")
    public Page<MyAppDto> getMyApp(@RequestParam("czybh") String czybh, @RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            return czyqxService.getMyApp(czybh, page, size);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("根据czybh获取我的应用异常：" + e);
            throw new BusinessException("根据czybh获取我的应用异常");
        }
    }

    @ApiOperation(value = "获取人气应用")
    @GetMapping("/findAllYy")
    public Page<CzyqxDto> findAllYy(@LoginUser SecurityUser securityUser, @RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            return czyqxService.getAllYyByUsecount(securityUser, page, size);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询人气应用异常：" + e);
            throw new BusinessException("查询人气应用异常");
        }
    }

    //    添加操作员权限
    @PostMapping("/saveCzyqx")
    @ApiOperation(value = "添加操作员权限")
    public int saveCzyqx(@RequestBody XtCzyqx czyqx) {
        return czyqxService.saveCzyqx(czyqx);
    }

    /**
     * 以下将移植到zdygnz相关类下
     */
    @GetMapping(value = "/getAuthGnTree")
    @AuditLog(operation = "获取操作员已授权功能树")
    @ApiOperation(value = "获取操作员已授权功能树")
    public CommonResult<List<YyUITreeDto>> getGwJsList(@LoginUser SecurityUser loginUser) {
        try {
            String czybh = loginUser.getBh();
            String czygns = czyqxService.getGnbhsByCzybh(czybh);
            String czyyys = czyqxService.getYybhsByCzybh(czybh);
            List<XtGnxx> gnxxList = czyqxService.findMenuList(new XtGnxxQueryParam());
            List<XtYyxx> yyxxList = yyxxService.findListByYhbh(czybh);
            return CommonResult.succeed(XtCzyqxFactory.buildGnzGnxxTree(czygns, czyyys, gnxxList, yyxxList));
        } catch (Exception e) {
            logger.debug("获取操作员已授权功能树：" + e);
            throw new BusinessException("获取操作员已授权功能树");
        }
    }

    /**
     * 上传第三方组件
     */
    @PostMapping(value = "/uploadComponent")
    @AuditLog(operation = "上传第三方组件")
    @ApiOperation(value = "上传第三方组件")
    public CommonResult uploadComponent(@LoginUser SecurityUser loginUser, @RequestPart("file") MultipartFile file, @RequestParam(value = "fjfz") String fjfz, @RequestParam(value = "hj") String hj, @RequestParam("groupId") String groupId, @RequestParam("artifactId") String artifactId, @RequestParam("version") String version) {
        try {
            czyqxService.uploadComponent(loginUser, file, fjfz, hj, groupId, artifactId, version);
            return CommonResult.succeed("上传第三方组件成功");
        } catch (Exception e) {
            logger.debug("上传第三方组件异常：" + e);
            throw new BusinessException("上传第三方组件异常");
        }
    }
}


