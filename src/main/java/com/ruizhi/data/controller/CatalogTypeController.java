package com.ruizhi.data.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dal.entitys.FlwInfo;
import com.ruizhi.data.dto.catalogTypeInfo.CatalogTypeRequest;
import com.ruizhi.data.dto.catalogTypeInfo.CatalogTypeResponse;
import com.ruizhi.data.service.FlwInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 目录分级分类Controller
 * Created by lvjie on 2020/8/3
 */
@Slf4j
@RestController
@RequestMapping("/catalogType")
public class CatalogTypeController {

    @Autowired
    private FlwInfoService flwInfoService;

    @PostMapping("list")
    public CatalogTypeResponse getPageList(@RequestBody @Valid CatalogTypeRequest request) {
        log.info("CatalogTypeController.getPageList,入参为：{}", JSON.toJSONString(request));
        CatalogTypeResponse response = new CatalogTypeResponse();
        ResponseUtils.resultSuccessResponse(response);
        IPage<FlwInfo> resultPage = flwInfoService.queryCatalogTypePage(request);
        response.setTotal(resultPage.getTotal());
        response.setPageCount(resultPage.getSize());
        response.setList(resultPage.getRecords());
        return response;
    }
}
