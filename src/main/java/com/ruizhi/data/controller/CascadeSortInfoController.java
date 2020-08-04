package com.ruizhi.data.controller;


import com.alibaba.fastjson.JSON;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dto.cascadeSortInfo.CascadeSortInfoRequest;
import com.ruizhi.data.dto.cascadeSortInfo.CascadeSortInfoResponse;
import com.ruizhi.data.dto.cascadeSortInfo.SaveCascadeSortInfoRequest;
import com.ruizhi.data.dto.cascadeSortInfo.TreeDTO;
import com.ruizhi.data.service.CascadeSortInfoService;
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
 * 级联分类管理 前端控制器
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
@Slf4j
@RestController
@RequestMapping("/cascadeSortInfo")
public class CascadeSortInfoController {

    @Autowired
    private CascadeSortInfoService cascadeSortInfoService;

    /**
     * 分类管理树形数据
     * @return
     */
    @PostMapping("/getTree")
    public CascadeSortInfoResponse getTree(@RequestBody CascadeSortInfoRequest request) {
        log.info("CascadeSortInfoController.getTree,入参为：{}", JSON.toJSONString(request));
        CascadeSortInfoResponse response = new CascadeSortInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        List<TreeDTO> treeDTOList = cascadeSortInfoService.getTree(request);
        response.setTreeDTOList(treeDTOList);
        return response;
    }

    /**
     * 通过ID查询所有的子数据
     * @param id
     * @return
     */
    @PostMapping("/getChildByParentId/{id}")
    public CascadeSortInfoResponse getChildByParentId(@PathVariable("id") Integer id) {
        CascadeSortInfoResponse response = new CascadeSortInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        List<TreeDTO> treeDTOList = cascadeSortInfoService.getChildByParentId(id);
        response.setTreeDTOList(treeDTOList);
        return response;
    }

    /**
     * 保存分类管理树形数据
     * @param request
     * @return
     */
    @PostMapping("/save")
    public CommonResponse saveCascadeSortInfo(@RequestBody @Valid SaveCascadeSortInfoRequest request) {
        log.info("CascadeSortInfoController.saveCascadeSortInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            cascadeSortInfoService.saveCascadeSortInfo(request);
        } catch (Exception e) {
            log.error("CascadeSortInfoController.saveCascadeSortInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }



}

