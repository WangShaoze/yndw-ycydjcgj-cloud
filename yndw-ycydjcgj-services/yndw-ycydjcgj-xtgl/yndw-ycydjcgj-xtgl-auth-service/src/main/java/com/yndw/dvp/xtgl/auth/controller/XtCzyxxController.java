package com.yndw.dvp.xtgl.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUserDetails;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.utils.ExcelUtil;
import com.yndw.dvp.common.core.utils.RsaUtils;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxCountDto;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxDto;
import com.yndw.dvp.xtgl.auth.dto.XtCzyxxExcelDto;
import com.yndw.dvp.xtgl.auth.dto.XtGwxxCountDto;
import com.yndw.dvp.xtgl.auth.factory.XtCzyxxFactory;
import com.yndw.dvp.xtgl.auth.queryParam.XtCzyxxQueryParam;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.service.IXtCzyxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Create By Carlos
 * 2020/6/12
 */
@RestController
@RequestMapping("/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyxx")
@Api(tags = "操作员管理")
public class XtCzyxxController {
    private static final Logger logger = LoggerFactory.getLogger(XtCzyxxController.class);
    @Autowired
    private IXtCzyxxService czyxxService;

    @Value("${dvp.rsa.privateKey}")
    private String rsaPrivateKey;

    @GetMapping(value = "/page")
    @AuditLog(operation = "'查询操作员:' + #queryParam")
    @ApiOperation(value = "查询操作员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "过滤条件", dataType = "XtCzyxxQueryParam", required = false)
    })
    public PageResult<XtCzyxxDto> page(XtCzyxxQueryParam queryParam) {
        try {
            Page<XtCzyxxDto> page = czyxxService.queryPage(queryParam);
            return PageResult.succeed(XtCzyxxFactory.buildDto(page));
        } catch (Exception e) {
            logger.debug("查询操作员异常：" + e);
            throw new BusinessException("查询操作员异常");
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    @AuditLog(operation = "添加或更新操作员")
    @ApiOperation(value = "添加或更新操作员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录操作员信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "操作员信息", dataType = "XtCzyxx", required = true)
    })
    public CommonResult saveOrUpdate(@LoginUser SecurityUser securityUser, @RequestBody @Valid XtCzyxx entity) {
        try {
            String msg = StrUtil.isBlank(entity.getBh()) ? "添加" : "修改";
            XtCzyxx result = czyxxService.saveOrUpdate(securityUser, entity);
            return result != null ? CommonResult.succeed(result, msg + "操作员成功") : CommonResult.failed(msg + "操作员失败");
        } catch (Exception e) {
            logger.debug("添加或更新操作员异常：" + e);
            throw new BusinessException("添加或更新操作员异常:" + e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{bh}")
    @AuditLog(operation = "删除操作员")
    @ApiOperation(value = "删除操作员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "操作员编号", dataType = "String", required = true)
    })
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh) {
        try {
            return czyxxService.deleteById(bh) > 0 ? CommonResult.succeed("删除操作员成功") : CommonResult.failed("删除操作员失败");
        } catch (Exception e) {
            logger.debug("删除操作员异常：" + e);
            throw new BusinessException("删除异常");
        }
    }

    @GetMapping(value = "/get/{bh}")
    @AuditLog(operation = "获取操作员")
    @ApiOperation(value = "获取操作员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "操作员编号", dataType = "String", required = true)
    })
    public CommonResult<XtCzyxx> get(@PathVariable(value = "bh", required = true) String bh) {
        try {
            XtCzyxx entity = czyxxService.getById(bh);
            return entity != null ? CommonResult.succeed(entity, "获取操作员成功") : CommonResult.failed("获取操作员失败");
        } catch (Exception e) {
            logger.debug("获取操作员异常：" + e);
            throw new BusinessException("获取操作员异常");
        }
    }

    @GetMapping(value = "/getMore/{bh}")
    @AuditLog(operation = "获取操作员")
    @ApiOperation(value = "获取操作员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bh", value = "操作员编号", dataType = "String", required = true)
    })
    public CommonResult<XtCzyxxDto> getMore(@PathVariable(value = "bh", required = true) String bh) {
        try {
            XtCzyxxDto entity = czyxxService.getMoreById(bh);
            return entity != null ? CommonResult.succeed(entity, "获取操作员成功") : CommonResult.failed("获取操作员失败");
        } catch (Exception e) {
            logger.debug("获取操作员异常：" + e);
            throw new BusinessException("获取操作员异常");
        }
    }

    @PutMapping(value = "/resetDlmm/{bh}")
    @AuditLog(operation = "重置操作员密码")
    @ApiOperation(value = "重置操作员密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录操作员信息，系统自动获取", dataType = "LoginUser", required = false)
    })
    public CommonResult<XtCzyxx> resetDlmm(@LoginUser SecurityUser securityUser, @PathVariable(value = "bh") String bh) {
        try {
            int result = czyxxService.updateDlmm(securityUser, bh, null, null);
            return result > 0 ? CommonResult.succeed("重置操作员密码成功") : CommonResult.failed("重置操作员密码失败");
        } catch (Exception e) {
            logger.debug("重置操作员密码异常：" + e);
            throw new BusinessException("重置操作员密码异常");
        }
    }

    @PutMapping(value = "/updateDlmm")
    @AuditLog(operation = "更新操作员密码")
    @ApiOperation(value = "更新操作员密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录操作员信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "操作员信息", dataType = "XtCzyxx", required = true)
    })
    public CommonResult<XtCzyxx> updateDlmm(@LoginUser SecurityUser securityUser, @RequestBody XtCzyxx entity) {
        try {
            String oldDlmm = RsaUtils.decryptByPrivateKey(rsaPrivateKey, entity.getOldDlmm());
            String newDlmm = RsaUtils.decryptByPrivateKey(rsaPrivateKey, entity.getNewDlmm());
            int result = czyxxService.updateDlmm(securityUser, securityUser.getBh(), oldDlmm, newDlmm);
            return result > 0 ? CommonResult.succeed("更新操作员密码成功") : CommonResult.failed("更新操作员密码失败");
        } catch (Exception e) {
            logger.debug("更新操作员密码异常：" + e);
            throw new BusinessException("更新操作员密码异常：" + e.getMessage());
        }
    }

    @ApiOperation(value = "解锁页面")
    @PostMapping("/unLock")
    public CommonResult unLock(@LoginUser SecurityUser loginUser, String passwd) {
        try {
            passwd = RsaUtils.decryptByPrivateKey(rsaPrivateKey, passwd);
            boolean flag = czyxxService.unlock(loginUser, passwd);
            return CommonResult.succeed(flag, flag ? "解锁成功" : "解锁失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("账户密码解密失败：" + e.getMessage());
        }

    }

    @GetMapping(value = "/checkDefaultDlmm")
    @AuditLog(operation = "检验当前用户是否使用默认密码")
    @ApiOperation(value = "检验当前用户是否使用默认密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录操作员信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "操作员信息", dataType = "XtCzyxx", required = true)
    })
    public CommonResult checkDefaultDlmm(@LoginUser SecurityUser securityUser) {
        try {
            boolean result = czyxxService.checkDefaultDlmm(securityUser);
            return CommonResult.succeed(result, "默认密码");
        } catch (Exception e) {
            logger.debug("检验当前用户是否使用默认密码异常：" + e);
            throw new BusinessException("检验当前用户是否使用默认密码异常：" + e.getMessage());
        }
    }

    @PutMapping(value = "/updateZtdm")
    @AuditLog(operation = "更新操作员状态")
    @ApiOperation(value = "更新操作员状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录操作员信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "操作员信息", dataType = "XtCzyxx", required = true)
    })
    public CommonResult<XtCzyxx> updateZtdm(@LoginUser SecurityUser securityUser, XtCzyxx entity) {
        try {
            return czyxxService.updateZtdm(securityUser, entity) > 0 ? CommonResult.succeed("更新操作员状态成功") : CommonResult.failed("更新操作员状态失败");
        } catch (Exception e) {
            logger.debug("更新操作员状态异常：" + e);
            throw new BusinessException("更新操作员状态异常");
        }
    }

    @PutMapping(value = "/updateAvatar")
    @AuditLog(operation = "更新头像")
    @ApiOperation(value = "更新头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUser", value = "当前登录操作员信息，系统自动获取", dataType = "LoginUser", required = false),
            @ApiImplicitParam(name = "entity", value = "操作员信息", dataType = "XtCzyxx", required = true)
    })
    public CommonResult<XtCzyxx> updateAvatar(@LoginUser SecurityUser securityUser, @RequestBody XtCzyxx entity) {
        try {
            return czyxxService.updateAvatar(securityUser, entity) > 0 ? CommonResult.succeed("更新头像成功") : CommonResult.failed("更新头像失败");
        } catch (Exception e) {
            logger.debug("更新操作员头像异常：" + e);
            throw new BusinessException("更新操作员头像异常");
        }
    }

    @PutMapping(value = "/lockCzy")
    @AuditLog(operation = "锁定/解锁操作员")
    @ApiOperation(value = "锁定/解锁操作员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "czybh", value = "操作员编码", dataType = "String", required = true)
    })
    public Boolean lockCzy(@RequestParam(value = "czybh") String czybh, @RequestParam(value = "isLock") String isLock) {
        try {
            return czyxxService.lockCzy(czybh, isLock) > 0;
        } catch (Exception e) {
            logger.debug("锁定/解锁操作员异常：" + e);
            throw new BusinessException("锁定/解锁操作员异常");
        }
    }

    @PutMapping(value = "/updateDlmmycwcs", params = "czybh")
    @AuditLog(operation = "更新账户登录错误计数")
    @ApiOperation(value = "更新账户登录错误计数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "czybh", value = "操作员编号", dataType = "String", required = true)
    })
    public Boolean updateDlmmycwcs(String czybh) {
        try {
            return czyxxService.updateDlmmycwcs(czybh) > 0;
        } catch (Exception e) {
            logger.debug("更新账户登录错误计数异常：" + e);
            throw new BusinessException("更新账户登录错误计数异常：" + e.getMessage());
        }
    }

    @PutMapping(value = "/updateDlmmycwcsToZero", params = "czybh")
    @AuditLog(operation = "更新账户登录错误归零")
    @ApiOperation(value = "更新账户登录错误归零")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "czybh", value = "操作员编号", dataType = "String", required = true)
    })
    public Boolean updateDlmmycwcsToZero(String czybh) {
        try {
            return czyxxService.updateDlmmycwcsToZero(czybh) > 0;
        } catch (Exception e) {
            logger.debug("更新账户登录错误计数归零：" + e);
            throw new BusinessException("更新账户登录错误计数归零：" + e.getMessage());
        }
    }

    @GetMapping(value = "/getUserByDlzh/{dlzh}")
    @ApiOperation(value = "根据登录账号查询")
//    @Cacheable(value = "czyxx", key = "#dlzh")
    public SecurityUser getUserByDlzh(@PathVariable String dlzh) {
        return czyxxService.getUserByDlzh(dlzh);
    }

    @GetMapping(value = "/getUserDetailsByDlzh", params = "dlzh")
    @ApiOperation(value = "根据用户名查询用户")
    public SecurityUserDetails getUserDetailsByDlzh(String dlzh) {
        return czyxxService.getUserDetailsByDlzh(dlzh);
    }

    @GetMapping(value = "/getUserDetailsByCzybh", params = "bh")
    @ApiOperation(value = "根据用户编号查询用户")
    public SecurityUserDetails getUserDetailsByCzybh(String bh) {
        return czyxxService.getUserDetailsByCzybh(bh);
    }

    @GetMapping(value = "/getByCzybh", params = "bh")
    @ApiOperation(value = "根据用户编号查询用户")
    public XtCzyxx getByCzybh(String bh) {
        return czyxxService.getByCzybh(bh);
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询用户")
    public List getCzyxxList(){
        return czyxxService.list();
    }

    @GetMapping(value = "/getUserDetailsByCurrentLoginUser")
    @ApiOperation(value = "根据当前登录信息获取操作员认证信息")
    public CommonResult<SecurityUserDetails> getUserDetailsByCurrentLoginUser(@LoginUser SecurityUser securityUser) {
        try {
            return CommonResult.succeed(czyxxService.getUserDetailsByCzybh(securityUser.getBh()), "获取操作员成功");
        } catch (Exception e) {
            logger.debug("获取操作员认证信息异常：" + e);
            throw new BusinessException("获取操作员认证信息异常");
        }
    }

    @GetMapping(value = "/getCzyxxByCurrentLoginUser")
    @ApiOperation(value = "根据当前登录信息获取操作员信息")
    public CommonResult<XtCzyxx> getCzyxxByCurrentLoginUser(@LoginUser SecurityUser securityUser) {
        try {
            XtCzyxx entity = czyxxService.getById(securityUser.getBh());
            return entity != null ? CommonResult.succeed(entity, "获取操作员成功") : CommonResult.failed("获取操作员失败");
        } catch (Exception e) {
            logger.debug("获取操作员异常：" + e);
            throw new BusinessException("获取操作员异常");
        }
    }

    /**
     * 导出excel
     *
     * @return
     */
    @PostMapping("/export")
    public void exportUser(@LoginUser SecurityUser user, XtCzyxxQueryParam queryParam, HttpServletResponse response) throws IOException {
        List<XtCzyxx> czyxxList = czyxxService.findList(queryParam);

        //导出操作
        ExcelUtil.exportExcel(XtCzyxxFactory.buildExcelDto(czyxxList), "操作员", "操作员", XtCzyxxExcelDto.class, "操作员列表", response);
    }

    /**
     * 查询所以操作员
     *
     * @return
     */
    @GetMapping("/queryList")
    public List<XtCzyxx> queryList(XtCzyxxQueryParam queryParam, HttpServletResponse response) throws IOException {
        return czyxxService.findList(queryParam);
    }

    /**
     * 查询组织下所以操作员
     *
     * @return
     */
    @GetMapping("/queryList4Xm")
    public List<XtCzyxx> queryList4Xm(@RequestParam("bhStr") String bhStr) throws IOException {
        return czyxxService.queryList4Xm(bhStr);
    }

    @GetMapping(value = "/czyxxCount")
    @AuditLog(operation = "查询操作员信息总数、禁用数和停用数")
    @ApiOperation(value = "查询操作员信息总数、禁用数和停用数")
    public CommonResult<XtCzyxxCountDto> gwxxCount() {
        try {
            XtCzyxxCountDto xtCzyxxCountDto = czyxxService.czyxxCount();
            return (xtCzyxxCountDto != null)?CommonResult.succeed(xtCzyxxCountDto):CommonResult.failed("查询操作员信息总数、禁用数和停用数失败");
        } catch (Exception e) {
            logger.debug("查询操作员信息总数、禁用数和停用数异常：" + e);
            throw new BusinessException("查询操作员信息总数、禁用数和停用数异常");
        }
    }

    @GetMapping(value = "/getCzyxxBybhList")
    @AuditLog(operation = "查询操作员信息")
    @ApiOperation(value = "查询操作员信息")
    public List<XtCzyxx> getCzyxxBybhList(@RequestParam("bhList") List<String> bhList) {
        try {
            return czyxxService.getCzyxxBybhList(bhList);
        } catch (Exception e) {
            logger.debug("查询操作员信息异常：" + e);
            throw new BusinessException("查询操作员信息异常");
        }
    }
}
