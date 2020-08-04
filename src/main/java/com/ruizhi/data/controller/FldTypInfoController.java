package com.ruizhi.data.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dal.entitys.FldTypInfo;
import com.ruizhi.data.dto.fldTypInfo.SaveFldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.ViewFldTypInfoResponse;
import com.ruizhi.data.dto.fldTypInfo.FldTypInfoListDTO;
import com.ruizhi.data.dto.fldTypInfo.FldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.FldTypInfoResponse;
import com.ruizhi.data.dto.fldTypInfo.UpdateFldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.UpdateRuleRequest;
import com.ruizhi.data.service.FldTypInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 字段分级分类表 前端控制器
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
@Slf4j
@RestController
@RequestMapping("/fldTypInfo")
public class FldTypInfoController {

    @Autowired
    private FldTypInfoService fldTypInfoService;

    /**
     * 分页查询字段分级分类列表数据
     * @param request
     * @return
     */
    @PostMapping("/list")
    public FldTypInfoResponse getPageList(@RequestBody @Valid FldTypInfoRequest request) {
        log.info("FldTypInfoController.getPageList,入参为：{}", JSON.toJSONString(request));
        FldTypInfoResponse response = new FldTypInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        IPage<FldTypInfoListDTO> resultPage = fldTypInfoService.queryPage(request);
        response.setTotal(resultPage.getTotal());
        response.setPageCount(resultPage.getSize());
        response.setList(resultPage.getRecords());
        return response;
    }

    /**
     * 根据ID删除
     * @return
     */
    @PostMapping("/delete/{id}")
    public CommonResponse deleteById(@PathVariable Integer id) {
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            fldTypInfoService.deleteFldTypInfoById(id);
        } catch (Exception e) {
            log.error("FldTypInfoController.deleteById Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 修改规则设置
     * @param request
     * @return
     */
    @PostMapping("/updateRule")
    public CommonResponse updateRule(@RequestBody @Valid UpdateRuleRequest request) {
        log.info("FldTypInfoController.updateRule,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            fldTypInfoService.updateRule(request);
        } catch (Exception e) {
            log.error("FldTypInfoController.updateRule Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 修改字段分级设置
     * @param request
     * @return
     */
    @PostMapping("/updateFldTypInfo")
    public CommonResponse updateFldTypInfo(@RequestBody @Valid UpdateFldTypInfoRequest request) {
        log.info("FldTypInfoController.updateFldTypInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            fldTypInfoService.updateFldTypInfo(request);
        } catch (Exception e) {
            log.error("FldTypInfoController.updateFldTypInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 查看字段分级
     * @param id
     * @return
     */
    @PostMapping("/getFldTypInfoById/{id}")
    public ViewFldTypInfoResponse getFldTypInfoById(@PathVariable("id") Integer id) {
        ViewFldTypInfoResponse response = new ViewFldTypInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            FldTypInfo fldTypInfo = fldTypInfoService.getFldTypInfoById(id);
            response.setFldTypInfo(fldTypInfo);
        } catch (Exception e) {
            log.error("FldTypInfoController.getFldTypInfoById Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 保存分类管理
     * @param request
     * @return
     */
    @PostMapping("/save")
    public CommonResponse saveFldTypInfo(@RequestBody @Valid SaveFldTypInfoRequest request) {
        log.info("FldTypInfoController.saveFldTypInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            fldTypInfoService.saveFldTypInfo(request);
        } catch (Exception e) {
            log.error("FldTypInfoController.saveFldTypInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}

