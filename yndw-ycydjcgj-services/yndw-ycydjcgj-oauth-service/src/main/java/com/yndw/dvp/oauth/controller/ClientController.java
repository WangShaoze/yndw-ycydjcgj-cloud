package com.yndw.dvp.oauth.controller;

import com.google.common.collect.Maps;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.PageResult;
import com.yndw.dvp.oauth.dto.ClientDto;
import com.yndw.dvp.oauth.model.Client;
import com.yndw.dvp.oauth.service.IClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 应用相关接口
 *
 */
@Api(tags = "应用")
@RestController
@RequestMapping("/yndw/oauth/yndw-ycydjcgj-oauth-service/V1/client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping("/list")
    @ApiOperation(value = "应用列表")
    public PageResult<Client> list(@RequestParam Map<String, Object> params) {
        return clientService.listClient(params, true);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取应用")
    public Client get(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有应用")
    public CommonResult<List<Client>> allClient() {
        PageResult<Client> page = clientService.listClient(Maps.newHashMap(), false);
        return CommonResult.succeed(page.getData());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除应用")
    public void delete(@PathVariable Long id) {
        clientService.delClient(id);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存或者修改应用")
    public CommonResult saveOrUpdate(@RequestBody ClientDto clientDto) throws Exception {
        return clientService.saveClient(clientDto);
    }
}
