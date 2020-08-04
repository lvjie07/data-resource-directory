package com.ruizhi.data.controller;


import com.alibaba.fastjson.JSON;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.UpdataBusinessTypeRequest;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.UpdataTableInfoRequest;
import com.ruizhi.data.service.RtlFlwDbTblService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 采集结果数据源库库表 前端控制器
 * </p>
 *
 * @author lvjie
 * @since 2020-08-03
 */
@Slf4j
@RestController
@RequestMapping("/rtlFlwDbTbl")
public class RtlFlwDbTblController {

    @Autowired
    private RtlFlwDbTblService rtlFlwDbTblService;

    /**
     * 修改采集表注释信息
     * @param request
     * @return
     */
    @PostMapping("/update")
    public CommonResponse updateTableInfo(@RequestBody @Valid UpdataTableInfoRequest request) {
        log.info("RtlFlwDbTblController.updateTableInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            rtlFlwDbTblService.updateTableInfo(request);
        } catch (Exception e) {
            log.error("RtlFlwDbTblController.updateFldTypInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 修改业务类型
     * @param request
     * @return
     */
    @PostMapping("/updateBusinessType")
    public CommonResponse updateBusinessType(@RequestBody @Valid UpdataBusinessTypeRequest request) {
        log.info("RtlFlwDbTblController.updateBusinessType,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            rtlFlwDbTblService.updateBusinessType(request);
        } catch (Exception e) {
            log.error("RtlFlwDbTblController.updateBusinessType Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

}

