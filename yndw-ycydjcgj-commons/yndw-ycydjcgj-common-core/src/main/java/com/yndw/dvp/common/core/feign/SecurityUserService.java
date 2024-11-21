package com.yndw.dvp.common.core.feign;

import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.feign.fallback.SecurityUserServiceallbackFactory;
import com.yndw.dvp.common.core.model.SecurityUserDetails;
import com.yndw.dvp.common.core.model.SecurityUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "yndw-ycydjcgj-xtgl-auth-service", fallbackFactory = SecurityUserServiceallbackFactory.class, decode404 = true)
public interface SecurityUserService {
    /**
     * 查询用户实体对象LoginUser
     *
     */
    @GetMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyxx/getUserByDlzh/{dlzh}")
    SecurityUser getUserByDlzh(@PathVariable("dlzh") String dlzh);

    /**
     * 查询用户实体对象LoginUserDetails
     * @param dlzh
     * @return
     */
    @GetMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyxx/getUserDetailsByDlzh", params = "dlzh")
    SecurityUserDetails getUserDetailsByDlzh(@RequestParam("dlzh") String dlzh);

    /**
     * 查询用户实体对象LoginUserDetails
     * @param bh
     * @return
     */
    @GetMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyxx/getUserDetailsByCzybh", params = "bh")
    SecurityUserDetails getUserDetailsByCzybh(@RequestParam("bh") String bh);

    /**
     * 更改账户状态
     * @param czybh
     * @return
     */
    @PutMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyxx/lockCzy")
    Boolean lockCzy(@RequestParam(value="czybh") String czybh,@RequestParam(value="isLock") String isLock);

    /**
     * 更新账户登录错误计数
     * @param czybh
     * @return
     */
    @PutMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyxx/updateDlmmycwcs", params = "czybh")
    Boolean updateDlmmycwcs(@RequestParam("czybh") String czybh);

    /**
     * 更新账户登录错误计数
     * @param czybh
     * @return
     */
    @PutMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyxx/updateDlmmycwcsToZero", params = "czybh")
    Boolean updateDlmmycwcsToZero(@RequestParam("czybh") String czybh);

}
