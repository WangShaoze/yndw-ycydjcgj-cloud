package com.yndw.dvp.oauth.controller;

import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.oauth.model.TokenVo;
import com.yndw.dvp.oauth.service.ITokensService;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Create By Carlos
 * 2020/6/23
 */
@Api(tags = "Token管理")
@RestController
@RequestMapping("/yndw/oauth/yndw-ycydjcgj-oauth-service/V1/token")
public class TokensController {
    @Autowired
    private ITokensService tokensService;

    @GetMapping("list")
    @ApiOperation(value = "token列表")
    public PageResult<TokenVo> list(@RequestParam Map<String, Object> params, String tenantId) {
        return tokensService.listTokens(params, tenantId);
    }
}
