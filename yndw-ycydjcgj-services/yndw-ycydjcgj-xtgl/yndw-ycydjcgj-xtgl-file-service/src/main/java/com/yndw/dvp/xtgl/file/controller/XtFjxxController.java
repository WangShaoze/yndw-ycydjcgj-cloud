package com.yndw.dvp.xtgl.file.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.log.annotation.AuditLog;
import com.yndw.dvp.xtgl.file.model.XtFjxx;
import com.yndw.dvp.xtgl.file.queryParam.XtFjxxQueryParam;
import com.yndw.dvp.xtgl.file.service.IXtFjxxService;
import com.yndw.dvp.xtgl.file.storage.FileStorage;
import com.yndw.dvp.common.core.model.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文件系统类
 */
@RestController
@RequestMapping("/yndw/xtgl/file/yndw-ycydjcgj-xtgl-file-service/V1/fjxx")
@Api(tags = "文件系统")
public class XtFjxxController {
    private static final Logger logger = LoggerFactory.getLogger(XtFjxxController.class);

    @Autowired
    private IXtFjxxService fjxxService;

    @Autowired
    private FileStorage fileStorage;

    @ApiOperation(value = "获取文件分组编号")
    @GetMapping(value = "/getFJfzbh")
    @AuditLog(operation = "获取文件分组编号")
    public CommonResult getFJfzbh() {
        return CommonResult.succeed(IdUtil.fastSimpleUUID(),"获取文件分组编号成功");
    }


    @ApiOperation(value = "文件列表")
    @GetMapping(value = "/page")
    @AuditLog(operation = "'查询附件'+#queryParam")
    public PageResult<XtFjxx> page(XtFjxxQueryParam queryParam) {
        try {
            Page<XtFjxx> page = fjxxService.findPage(queryParam);
            return PageResult.succeed(page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询操作员异常：" + e);
            throw new BusinessException("查询操作员异常");
        }
    }

    @ApiOperation(value = "文件列表")
    @GetMapping(value = "/list")
    @AuditLog(operation = "'查询附件'+#queryParam")
    public CommonResult<List<XtFjxx>> list(XtFjxxQueryParam queryParam) {
        try {
            List<XtFjxx> list = fjxxService.findList(queryParam);
            return CommonResult.succeed(list);
        } catch (Exception e) {
            logger.debug("查询文件列表异常：" + e);
            throw new BusinessException("查询文件列表异常");
        }
    }

    @ApiOperation(value = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件流", dataType = "MultipartFile", required = true)
    })
    @PostMapping(value = "/upload")
    @AuditLog(operation = "文件上传")
    public CommonResult upload(@LoginUser SecurityUser loginUser, @RequestPart("file") MultipartFile file,@RequestParam(value = "fjfz") String fjfz) {
        try{
            fjxxService.upload(loginUser,file,fjfz);
            return CommonResult.succeed("上传成功");
        }catch (Exception e){
            logger.debug("上传文件异常：" + e.getMessage());
            throw new BusinessException("上传文件异常:"+e.getMessage());
        }
    }

    @ApiOperation("文件预览")
    @AuditLog(operation = "文件预览")
    @GetMapping(path = "/preview")
    public void preview(@RequestParam String bh, HttpServletResponse response) {

        XtFjxx fjxx = fjxxService.getFileinfo(bh);

        byte[] fileBytes = fileStorage.getFileBytes(fjxx.getFjcclj());

        //设置头信息Content-Type
        String fileContentType = fileStorage.getFileContentType(fjxx.getFjhz());
        response.setContentType(fileContentType);

        //返回文件流
        returnFileStream(response, fileBytes);
    }

    @ApiOperation("文件下载")
    @AuditLog(operation = "文件下载")
    @GetMapping("/download/{bh}")
    public void download(@PathVariable(value = "bh", required = true) String bh, HttpServletResponse response) {

        //获取文件信息
        XtFjxx fjxx = fjxxService.getFileinfo(bh);

        //获取文件内容
        byte[] fileBytes = fileStorage.getFileBytes(fjxx.getFjcclj());

        //设置头信息
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fjxx.getFjmc()+fjxx.getFjhz());

        //返回文件流
        returnFileStream(response, fileBytes);

    }

    /**
     * 下载文件
     */
    @ApiOperation("文件删除")
    @AuditLog(operation = "文件删除")
    @DeleteMapping("/delete/{bh}")
    public CommonResult delete(@PathVariable(value = "bh", required = true) String bh){
        try {
            return fjxxService.deleteById(bh) > 0 ? CommonResult.succeed("删除文件成功") : CommonResult.failed("删除文件失败");
        } catch (Exception e) {
            logger.debug("删除文件异常：" + e);
            throw new BusinessException("删除文件异常");
        }
    }

    /**
     * 返回前端文件流
     */
    private void returnFileStream(HttpServletResponse response, byte[] fileBytes) {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            stream.write(fileBytes);
            stream.flush();
        } catch (IOException e) {
            throw new BusinessException("获取文件流异常");
        } finally {
            IoUtil.close(stream);
        }
    }

    @ApiOperation(value = "查询文件")
    @GetMapping(value = "/queryByFjfz")
    @AuditLog(operation = "'查询附件'+#queryParam")
    public List<XtFjxx> queryByFjfz(@RequestParam("fjfz") String fjfz) {
        try {
            return fjxxService.queryByFjfz(fjfz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("查询文件异常：" + e);
            throw new BusinessException("查询文件异常");
        }
    }
}
