package com.ruizhi.data.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.dal.entitys.FlwInfo;
import com.ruizhi.data.dto.flwInfo.DataBaseInfoDTO;
import com.ruizhi.data.dto.flwInfo.DataBaseInfoResponse;
import com.ruizhi.data.dto.flwInfo.DataBaseTablesInfoRequest;
import com.ruizhi.data.dto.flwInfo.DataBaseTablesInfoResponse;
import com.ruizhi.data.dto.flwInfo.FlwInfoRequest;
import com.ruizhi.data.dto.flwInfo.FlwInfoResponse;
import com.ruizhi.data.dto.flwInfo.SaveFlwInfoRequest;
import com.ruizhi.data.dto.flwInfo.TableAndFieldResponse;
import com.ruizhi.data.service.DataSourceInfoService;
import com.ruizhi.data.service.FlwInfoService;
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
 * 作业记录表 前端控制器
 * </p>
 *
 * @author lvjie
 * @since 2020-07-31
 */
@Slf4j
@RestController
@RequestMapping("/flwInfo")
public class FlwInfoController {

    @Autowired
    private FlwInfoService flwInfoService;

    @Autowired
    private DataSourceInfoService dataSourceInfoService;

    /**
     * 分页查询采集列表
     *
     * @param request
     * @return
     */
    @PostMapping("/list")
    public FlwInfoResponse getPageList(@RequestBody @Valid FlwInfoRequest request) {
        log.info("FlwInfoController.getPageList,入参为：{}", JSON.toJSONString(request));
        FlwInfoResponse response = new FlwInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        IPage<FlwInfo> resultPage = flwInfoService.queryPage(request);
        response.setTotal(resultPage.getTotal());
        response.setPageCount(resultPage.getSize());
        response.setList(resultPage.getRecords());
        return response;
    }

    /**
     * 保存
     *
     * @param request
     * @return
     */
    @PostMapping("/save")
    public CommonResponse saveFlwInfo(@RequestBody @Valid SaveFlwInfoRequest request) {
        log.info("FlwInfoController.saveFlwInfo,入参为：{}", JSON.toJSONString(request));
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            flwInfoService.saveFlwInfo(request);
        } catch (Exception e) {
            log.error("FlwInfoController.saveFlwInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 通过数据库类型查询配置的数据源信息
     * @param dataBaseType
     * @return
     */
    @PostMapping("/getDataBaseInfoList/{dataBaseType}")
    public DataBaseInfoResponse getDataBaseInfoListByType(@PathVariable("dataBaseType") String dataBaseType) {
        DataBaseInfoResponse response = new DataBaseInfoResponse();
        ResponseUtils.resultSuccessResponse(response);
        List<DataBaseInfoDTO> dataBaseInfoDTOList = dataSourceInfoService.getDataBaseInfoListByType(dataBaseType);
        response.setDataBaseInfoDTOList(dataBaseInfoDTOList);
        return response;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public CommonResponse deleteFlwInfoById(@PathVariable("id") Integer id) {
        CommonResponse response = new CommonResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            flwInfoService.deleteFlwInfoById(id);
        } catch (Exception e) {
            log.error("FlwInfoController.saveFlwInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 获取树形结构的数据库相关信息
     * @param request
     * @return
     */
    @PostMapping("/getTreeDataBaseInfo")
    public DataBaseTablesInfoResponse getTreeDataBaseInfo(@RequestBody @Valid DataBaseTablesInfoRequest request) {
        log.info("FlwInfoController.getTreeDataBaseInfo,入参为：{}", JSON.toJSONString(request));
        DataBaseTablesInfoResponse response = new DataBaseTablesInfoResponse();
        try {
            response = flwInfoService.getTreeDataBaseInfo(request);
            ResponseUtils.resultSuccessResponse(response);
        } catch (Exception e) {
            log.error("FlwInfoController.getTreeDataBaseInfo Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 通过采集表ID查询表信息和字段信息
     * @param id
     * @return
     */
    @PostMapping("/getTableAndFieldInfoById/{id}")
    public TableAndFieldResponse getTableAndFieldInfoById(@PathVariable Integer id) {
        TableAndFieldResponse response = new TableAndFieldResponse();
        response = flwInfoService.getTableAndFieldInfoById(id);
        ResponseUtils.resultSuccessResponse(response);
        return response;
    }


}

