package com.ruizhi.data.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dal.entitys.TblTypInfo;
import com.ruizhi.data.dto.tblTypInfo.FieldDTO;
import com.ruizhi.data.dto.tblTypInfo.SaveTblTypInfoRequest;
import com.ruizhi.data.dto.tblTypInfo.TblTyeInfoFieldResponse;
import com.ruizhi.data.dto.tblTypInfo.TblTypInfoRequest;
import com.ruizhi.data.dto.tblTypInfo.TblTypInfoResponse;
import com.ruizhi.data.dto.tblTypInfo.UpdateTblTypInfoRequest;
import com.ruizhi.data.dto.tblTypInfo.ViewTblTypInfoResponse;
import com.ruizhi.data.service.TblTypInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 业务类型表 前端控制器
 * </p>
 *
 * @author lvjie
 * @since 2020-07-30
 */
@Slf4j
@RestController
@RequestMapping("/tblTypInfo")
public class TblTypInfoController {

    @Autowired
    private TblTypInfoService tblTypInfoService;

    /**
     * 分页查询业务类型列表
     * @param request
     * @return
     */
    @PostMapping("/list")
    public TblTypInfoResponse getPageList(@RequestBody @Valid TblTypInfoRequest request) {
        log.info("TblTypInfoController.getPageList,入参为：{}", JSON.toJSONString(request));
        TblTypInfoResponse response = new TblTypInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        IPage<TblTypInfo> resultPage = tblTypInfoService.queryPage(request);
        response.setTotal(resultPage.getTotal());
        response.setPageCount(resultPage.getSize());
        response.setList(resultPage.getRecords());
        return response;
    }

    /**
     * 根据ID删除业务类型
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public CommonResponse deleteById(@PathVariable Integer id) {
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            tblTypInfoService.deleteTblTypInfoById(id);
        } catch (Exception e) {
            log.error("TblTypInfoController.deleteById Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 保存
     * @param request
     * @return
     */
    @PostMapping("/save")
    public CommonResponse saveTblTypInfo(@RequestBody @Valid SaveTblTypInfoRequest request) {
        log.info("TblTypInfoController.saveTblTypInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            tblTypInfoService.saveTblTypInfo(request);
        } catch (Exception e) {
            log.error("TblTypInfoController.saveTblTypInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 查询发现方式的所有字段
     * @return
     */
    @PostMapping("/getField")
    public TblTyeInfoFieldResponse getField() {
        TblTyeInfoFieldResponse response = new TblTyeInfoFieldResponse();
        ResponseUtils.resultSuccessResponse(response);
        List<FieldDTO> fieldDTOList = tblTypInfoService.getField();
        response.setFieldDTOList(fieldDTOList);
        return response;
    }

    /**
     * 通过ID查询数据
     * @param id
     * @return
     */
    @PostMapping("/getTblTypInfoById/{id}")
    public ViewTblTypInfoResponse getTblTypInfoById(@PathVariable("id") Integer id) {
        ViewTblTypInfoResponse response = new ViewTblTypInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            TblTypInfo tblTypInfo = tblTypInfoService.getTblTypInfoById(id);
            response.setTblTypInfo(tblTypInfo);
            // 查询所有字段数据
            List<FieldDTO> fieldDTOList = tblTypInfoService.getFieldByTblId(id);
            response.setFieldDTOList(fieldDTOList);
        } catch (Exception e) {
            log.error("TblTypInfoController.getFldTypInfoById Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 修改数据
     * @param request
     * @return
     */
    @PostMapping("/update")
    public CommonResponse updateTblTypInfo(@RequestBody @Valid UpdateTblTypInfoRequest request) {
        log.info("TblTypInfoController.updateTblTypInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            tblTypInfoService.updateTblTypInfo(request);
        } catch (Exception e) {
            log.error("FldTypInfoController.updateRule Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }




}

