package com.yndw.dvp.oauth;

import com.yndw.dvp.OAuthAppRun;
import com.yndw.dvp.common.redis.template.RedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Create By Carlos
 * 2020/6/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuthAppRun.class)
public class RedisTest {
    @Autowired
    private RedisRepository redisRepository;
    @Test
    public void getVal(){
        List<Object> objs = redisRepository.getList("client_id_to_access:webApp",1,2);
        System.out.println(objs.size());
    }
}
