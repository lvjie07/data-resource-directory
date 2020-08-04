package com.ruizhi.data.controller;


import com.alibaba.fastjson.JSON;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dal.entitys.SenLevInfo;
import com.ruizhi.data.dto.senLevInfo.SaveUpdateSenLevInfoRequest;
import com.ruizhi.data.dto.senLevInfo.SenLevInfoResponse;
import com.ruizhi.data.service.SenLevInfoService;
import com.ruizhi.data.utils.ColaBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  敏感等级Controller
 * </p>
 *
 * @author lvjie
 * @since 2020-07-28
 */
@Slf4j
@RestController
@RequestMapping("/senLevInfo")
public class SenLevInfoController {

    @Autowired
    private SenLevInfoService senLevInfoService;

    /**
     * 敏感等级列表
     * @return
     */
    @PostMapping("/list")
    public SenLevInfoResponse getSenLevInfoList() {
        SenLevInfoResponse response = new SenLevInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        List<SenLevInfo> senLevInfoList = senLevInfoService.list();
        response.setList(senLevInfoList);
        return response;
    }

    /**
     * 保存修改分级敏感等级
     * @param request
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public CommonResponse saveOrUpdateSenLevInfo(@RequestBody @Valid SaveUpdateSenLevInfoRequest request) {
        log.info("SenLevInfoController.saveOrUpdateSenLevInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            request.requestCheck();
            List<SenLevInfo> senLevInfoList =  ColaBeanUtils.copyListProperties(request.getSenLevInfoDTOList(),SenLevInfo::new);
            senLevInfoService.saveOrUpdateBatch(senLevInfoList);
        } catch (Exception e) {
            log.error("SenLevInfoController.saveOrUpdateSenLevInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 根据ID删除分级敏感
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public CommonResponse deleteSenLevInfoById(@PathVariable Integer id) {
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            senLevInfoService.deleteSenLevInfoById(id);
        } catch (Exception e) {
            log.error("SenLevInfoController.deleteSenLevInfoById Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}

