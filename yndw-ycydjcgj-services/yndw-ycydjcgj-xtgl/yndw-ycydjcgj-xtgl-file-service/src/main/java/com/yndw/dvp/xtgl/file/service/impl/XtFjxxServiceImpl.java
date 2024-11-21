package com.yndw.dvp.xtgl.file.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.impl.SuperServiceImpl;
import com.yndw.dvp.xtgl.file.factory.FileFactory;
import com.yndw.dvp.xtgl.file.mapper.XtFjxxMapper;
import com.yndw.dvp.xtgl.file.model.XtFjxx;
import com.yndw.dvp.xtgl.file.queryParam.XtFjxxQueryParam;
import com.yndw.dvp.xtgl.file.service.IXtFjxxService;
import com.yndw.dvp.xtgl.file.storage.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件系统 服务实现类
 * </p>
 */
@Slf4j
@Service
public class XtFjxxServiceImpl extends SuperServiceImpl<XtFjxxMapper, XtFjxx> implements IXtFjxxService {

    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private XtFjxxMapper fjxxMapper;

    @Override
    public List<XtFjxx> findList(XtFjxxQueryParam queryParam) {
        QueryWrapper<XtFjxx> queryWrapper = buildQueryWapper(queryParam);
        return fjxxMapper.selectList(queryWrapper);
    }

    @Override
    public Page<XtFjxx> findPage(XtFjxxQueryParam queryParam) {
        QueryWrapper<XtFjxx> queryWrapper = buildQueryWapper(queryParam);
        return fjxxMapper.selectPage(new Page<>(queryParam.getPage(), queryParam.getLimit()), queryWrapper);
    }

    private QueryWrapper<XtFjxx> buildQueryWapper(XtFjxxQueryParam queryParam) {
        QueryWrapper query = new QueryWrapper<XtFjxx>();
        if (queryParam != null) {
            if (StrUtil.isNotEmpty(queryParam.getFjmc())) {
                query.like("fjmc", queryParam.getFjmc());
            }
            if (StrUtil.isNotEmpty(queryParam.getFjfz())) {
                query.eq("fjfz", queryParam.getFjfz());
            }
            query.orderByDesc(queryParam.getSort());
        }
        return query;
    }

    @Override
    public XtFjxx getFileinfo(String bh) {

        if (ObjectUtil.isEmpty(bh)) {
            throw new BusinessException("编号不能为空");
        }

        XtFjxx fjxx = this.getById(bh);

        if (ObjectUtil.isEmpty(fjxx)) {
            throw new BusinessException("文件不存在");
        }

        return fjxx;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(SecurityUser loginUser,MultipartFile file,String fjfz) {

        //获取文件名称
        String fileName = file.getOriginalFilename();

        if (ObjectUtil.isEmpty(fileName)) {
            fileName = "emptyFileName";
        }
        if (!fileName.contains(".")){
            throw new BusinessException("文件["+fileName+"]格式错误");
        }
        String fileType[] = {".txt",".doc",".docx",".xls",".xlsx",".pdf",".json",".pot",".pps","ppt","xlc","xlm","xlt","xlw","wdb","wps"};
        if(!Arrays.asList(fileType).contains(fileName.substring(fileName.indexOf(".")))){
            throw new BusinessException("不支持"+fileName.substring(fileName.indexOf("."))+"格式的文件");
        }

        //文件大小
        long size = file.getSize();

        //保存文件
        XtFjxx fileInfo = FileFactory.createFileInfo(loginUser,fileName, size);
        fileInfo.setFjfz(fjfz);
        this.save(fileInfo);

        //上传文件
        try {
            fileStorage.upload(fileInfo.getFjcclj(), file.getBytes());
        } catch (IOException e) {
            log.error("上传文件错误！", e);
            throw new BusinessException("上传文件错误！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String bh) {
        XtFjxx fjxx = fjxxMapper.selectById(bh);

        int result = fjxxMapper.deleteById(bh);
        if(result >0){
            fileStorage.delete(fjxx.getFjcclj());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<XtFjxx> queryByFjfz(String bh){
        return fjxxMapper.queryByFjfz(bh);
    }

}
