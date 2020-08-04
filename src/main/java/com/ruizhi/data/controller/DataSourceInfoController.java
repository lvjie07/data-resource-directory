package com.ruizhi.data.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dal.entitys.DataSourceInfo;
import com.ruizhi.data.dto.dataSourceInfo.DataSourceInfoRequest;
import com.ruizhi.data.dto.dataSourceInfo.DataSourceInfoResponse;
import com.ruizhi.data.dto.dataSourceInfo.SaveDataSourceInfoRequest;
import com.ruizhi.data.dto.dataSourceInfo.UpdateDataSourceInfoRequest;
import com.ruizhi.data.dto.dataSourceInfo.ViewDataSourceInfoResponse;
import com.ruizhi.data.service.DataSourceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 数据源配置信息controller
 * </p>
 *
 * @author lvjie
 * @since 2020-07-27
 */
@Slf4j
@RestController
@RequestMapping("/dataSourceInfo")
public class DataSourceInfoController {

    @Autowired
    private DataSourceInfoService dataSourceInfoService;


    /**
     * 分页查询数据源信息配置列表
     *
     * @param request
     * @return
     */
    @PostMapping("/list")
    public DataSourceInfoResponse getPageList(@RequestBody @Valid DataSourceInfoRequest request) {
        log.info("DataSourceInfoController.getPageList,入参为：{}", JSON.toJSONString(request));
        DataSourceInfoResponse response = new DataSourceInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        IPage<DataSourceInfo> resultPage = dataSourceInfoService.queryPage(request);
        response.setTotal(resultPage.getTotal());
        response.setPageCount(resultPage.getSize());
        response.setList(resultPage.getRecords());
        return response;
    }

    /**
     * 保存数据源信息配置
     *
     * @param request
     * @return
     */
    @PostMapping("/save")
    public CommonResponse saveDataSourceInfo(@RequestBody @Valid SaveDataSourceInfoRequest request) {
        log.info("DataSourceInfoController.saveDataSourceInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            request.requestCheck();
            DataSourceInfo dataSourceInfo = new DataSourceInfo();
            BeanUtils.copyProperties(request, dataSourceInfo);
            dataSourceInfo.setAddTyp("手动添加");
            dataSourceInfoService.save(dataSourceInfo);
        } catch (Exception e) {
            log.error("DataSourceInfoController.saveDataSourceInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 修改数据源信息配置
     *
     * @param request
     * @return
     */
    @PostMapping("/update")
    public CommonResponse updateDataSourceInfo(@RequestBody @Valid UpdateDataSourceInfoRequest request) {
        log.info("DataSourceInfoController.updateDataSourceInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            request.requestCheck();
            DataSourceInfo dataSourceInfo = new DataSourceInfo();
            BeanUtils.copyProperties(request, dataSourceInfo);
            dataSourceInfoService.updateById(dataSourceInfo);
        } catch (Exception e) {
            log.error("DataSourceInfoController.updateDataSourceInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 根据ID删除数据源信息配置
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public CommonResponse deleteDataSourceInfoById(@PathVariable Integer id) {
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            dataSourceInfoService.removeById(id);
        } catch (Exception e) {
            log.error("DataSourceInfoController.deleteDataSourceInfoById Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public ViewDataSourceInfoResponse getDataSourceInfoById(@PathVariable Integer id) {
        ViewDataSourceInfoResponse response = new ViewDataSourceInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            DataSourceInfo dataSourceInfo = dataSourceInfoService.getDataSourceInfoById(id);
            response.setDataSourceInfo(dataSourceInfo);
        } catch (Exception e) {
            log.error("DataSourceInfoController.getDataSourceInfoById Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}

