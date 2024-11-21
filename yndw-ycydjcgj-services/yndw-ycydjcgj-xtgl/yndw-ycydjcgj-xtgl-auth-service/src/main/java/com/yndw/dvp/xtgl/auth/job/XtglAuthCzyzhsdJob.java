package com.yndw.dvp.xtgl.auth.job;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.service.IXtCzyxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Create By Carlos
 * 2020/11/11
 */
@Component
public class XtglAuthCzyzhsdJob {

    /**
     * #### 账户锁定时间 单位：分钟
     */
    @Value("${dvp.czyxx.dlmmsdsj}")
    private int dlmmsdsj;

    @Autowired
    private IXtCzyxxService czyxxService;

    @XxlJob("xtglAuthCzyzhsdJob")
    public ReturnT<String> fwglFwpzFwyxqkJob(String param) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("czyztdm", ZtdmConstant.LOCK.getValue());
        List<XtCzyxx> list = czyxxService.list(queryWrapper);
        long dlmmsdsjMs = dlmmsdsj * 60;
        for (XtCzyxx czyxx : list) {
            long sjc = DateUtil.betweenMs(czyxx.getZtbgsj(), new Date()) / 1000;
            if (sjc > dlmmsdsjMs) {
                czyxxService.lockCzy(czyxx.getBh(), "false");
                czyxxService.updateDlmmycwcsToZero(czyxx.getBh());
            }
        }
        return ReturnT.SUCCESS;
    }

}
