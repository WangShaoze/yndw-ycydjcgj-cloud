package com.yndw.dvp.xtgl.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.service.ISuperService;
import com.yndw.dvp.xtgl.file.model.XtFjxx;
import com.yndw.dvp.xtgl.file.queryParam.XtFjxxQueryParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用管理 服务类
 * </p>
 *
 */
public interface IXtFjxxService extends ISuperService<XtFjxx> {

    /**
     * 上传文件
     */
    void upload(SecurityUser loginUser, MultipartFile file,String fjfz);

    /**
     * 获取文件信息
     */
    XtFjxx getFileinfo(String bh);

    List<XtFjxx> findList(XtFjxxQueryParam queryParam);

    Page<XtFjxx> findPage(XtFjxxQueryParam queryParam);

    int deleteById(String bh);

    List<XtFjxx> queryByFjfz(String bh);

}
