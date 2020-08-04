package com.ruizhi.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.dal.entitys.DataSourceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruizhi.data.dto.dataSourceInfo.DataSourceInfoRequest;
import com.ruizhi.data.dto.dataSourceInfo.SaveDataSourceInfoRequest;
import com.ruizhi.data.dto.flwInfo.DataBaseInfoDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-27
 */
public interface DataSourceInfoService extends IService<DataSourceInfo> {

    /**
     * 分页查询数据源信息列表
     * @param request
     * @return
     */
    IPage<DataSourceInfo> queryPage(DataSourceInfoRequest request);

    /**
     * 通过ID查询数据
     * @param id
     * @return
     */
    DataSourceInfo getDataSourceInfoById(Integer id);

    /**
     * 通过数据库类型数据所有数据源
     * @param dataBaseType
     * @return
     */
    List<DataBaseInfoDTO> getDataBaseInfoListByType(String dataBaseType);
}
