package com.ruizhi.data.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dal.entitys.RelResult;
import com.ruizhi.data.dto.relResultInfo.RelFieldDataResponse;
import com.ruizhi.data.dto.relResultInfo.RelResultRequest;
import com.ruizhi.data.dto.relResultInfo.RelResultResponse;
import com.ruizhi.data.service.RelResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 关联结果表 前端控制器
 * </p>
 *
 * @author lvjie
 * @since 2020-08-04
 */
@Slf4j
@RestController
@RequestMapping("/relResult")
public class RelResultController {

    @Autowired
    private RelResultService relResultService;

    /**
     *  分页查询表格分级列表
     * @param request
     * @return
     */
    @PostMapping("/list")
    public RelResultResponse getPageList(@RequestBody @Valid RelResultRequest request) {
        RelResultResponse response = new RelResultResponse();
        ResponseUtils.resultSuccessResponse(response);
        IPage<RelResult> resultPage = relResultService.queryPage(request);
        response.setTotal(resultPage.getTotal());
        response.setPageCount(resultPage.getSize());
        response.setList(resultPage.getRecords());
        return response;
    }

    /**
     * 确认
     * @param id
     * @return
     */
    @PostMapping("/confirm/{id}")
    public CommonResponse confirm(@PathVariable("id") Integer id) {
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            relResultService.confirm(id);
        }catch (Exception e) {
            log.error("RelResultController.confirm Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 取消
     * @param id
     * @return
     */
    @PostMapping("/cancel/{id}")
    public CommonResponse cancel(@PathVariable("id") Integer id) {
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            relResultService.cancel(id);
        }catch (Exception e) {
            log.error("RelResultController.cancel Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 获取采样数据
     * @param id
     * @return
     */
    @PostMapping("/getFieldData/{id}")
    public RelFieldDataResponse getFieldData(@PathVariable("id") Integer id) {
        RelFieldDataResponse response = new RelFieldDataResponse();
        try {
            response = relResultService.getFieldData(id);
            ResponseUtils.resultSuccessResponse(response);
        } catch (Exception e) {
            log.error("RelResultController.getFieldData Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }


}

