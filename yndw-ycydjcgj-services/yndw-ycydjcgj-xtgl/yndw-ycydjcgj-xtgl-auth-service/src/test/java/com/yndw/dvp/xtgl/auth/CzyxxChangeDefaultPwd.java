package com.yndw.dvp.xtgl.auth;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yndw.dvp.XtglAuthAppRun;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.constant.SecurityConstants;
import com.yndw.dvp.common.redis.template.RedisRepository;
import com.yndw.dvp.xtgl.auth.mapper.XtCzyxxMapper;
import com.yndw.dvp.xtgl.auth.model.XtCzyxx;
import com.yndw.dvp.xtgl.auth.service.IXtCzyxxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Create By Carlos
 * 2020/6/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XtglAuthAppRun.class)
public class CzyxxChangeDefaultPwd {
    @Autowired
    private IXtCzyxxService czyxxService;
    @Autowired
    private XtCzyxxMapper czyxxMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void changePwd() {
        List<XtCzyxx> czyxxList = czyxxMapper.selectList(new QueryWrapper<>());
        for (XtCzyxx czyxx : czyxxList) {
            //czyxxMapper.initPassword(passwordEncoder.encode(czyxx.getDlzh()+ CommonConstant.DEF_USER_PASSWORD));
        }
    }

    @Autowired
    private RedisRepository redisRepository;

    @Test
    public void getVal() {
        int[] startEnds = PageUtil.transToStartEnd(1, 10);
        long size = redisRepository.length("client_id_to_access:webApp");
        List<Object> objs = redisRepository.getList("client_id_to_access:webApp", startEnds[0], startEnds[1] - 1);
        System.out.println(objs.size());
    }
}
