package com.yndw.dvp.xtgl.file.factory;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.utils.IdGenerator;
import com.yndw.dvp.xtgl.file.model.XtFjxx;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 文件信息创建工厂
 *
 */
public class FileFactory {

    public static XtFjxx createFileInfo(SecurityUser loginUser,String fileName, long size) {

        //文件后缀
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));

        //文件唯一id
        long fileId = IdGenerator.getId();

        //创建文件信息
        XtFjxx xtFjxx = new XtFjxx();
        xtFjxx.setFjmc(fileName);
        xtFjxx.setFjdx(size);
        xtFjxx.setFjcclj(fileId + fileSuffix);
        xtFjxx.setFjhz(fileSuffix);
        xtFjxx.setCjrbh(loginUser.getBh());
        xtFjxx.setCzrbh(loginUser.getBh());

        return xtFjxx;
    }

}
