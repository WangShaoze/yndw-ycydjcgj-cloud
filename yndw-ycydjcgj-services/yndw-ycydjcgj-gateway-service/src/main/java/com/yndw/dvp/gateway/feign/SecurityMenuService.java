package com.yndw.dvp.gateway.feign;

import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.gateway.feign.fallback.SecurityMenuServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *
 * @author carlos
 */
@FeignClient(name = "yndw-ycydjcgj-xtgl-auth-service", fallbackFactory = SecurityMenuServiceFallbackFactory.class, decode404 = true)
public interface SecurityMenuService {
	/**
	 * 角色菜单列表
	 * @param jsbhs
	 */
	@GetMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/gnxx/findMenuByJsbhs/{jsbhs}")
	List<SecurityMenu> findMenuByJsbhs(@PathVariable("jsbhs") String jsbhs);

	/**
	 * 操作员编号
	 * @param czybh
	 */
	@GetMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/czyqx/findGnxxByCzybh/{czybh}")
	List<SecurityMenu> findGnxxByCzybh(@PathVariable("czybh") String czybh);


	/**
	 * 获取用户已授权功能
	 * @param czybh
	 * */
	@GetMapping(value = "/yndw/xtgl/auth/yndw-ycydjcgj-xtgl-auth-service/V1/gnxx/gnxxByCzy/{czybh}")
	List<SecurityMenu> authGnxxByCzy(@PathVariable("czybh") String czybh);
}
