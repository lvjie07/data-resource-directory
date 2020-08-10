package com.ruizhi.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.dal.entitys.FlwInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruizhi.data.dto.catalogTypeInfo.CatalogTypeRequest;
import com.ruizhi.data.dto.flwInfo.DataBaseTablesInfoRequest;
import com.ruizhi.data.dto.flwInfo.FlwInfoRequest;
import com.ruizhi.data.dto.flwInfo.SaveFlwInfoRequest;
import com.ruizhi.data.dto.flwInfo.DataBaseTablesInfoResponse;
import com.ruizhi.data.dto.flwInfo.TableAndFieldResponse;

/**
 * <p>
 * 作业记录表 服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-31
 */
public interface FlwInfoService extends IService<FlwInfo> {

    /**
     * 分页查询数据
     * @param request
     * @return
     */
    IPage<FlwInfo> queryPage(FlwInfoRequest request);

    /**
     * 保存作业记录数据
     * @param request
     * @return
     */
    boolean saveFlwInfo(SaveFlwInfoRequest request);

    /**
     * 删除数据
     * @param id
     * @return
     */
    boolean deleteFlwInfoById(Integer id);

    /**
     * 返回树形数据库信息
     * @param request
     * @return
     */
    DataBaseTablesInfoResponse getTreeDataBaseInfo(DataBaseTablesInfoRequest request);

    /**
     * 通过ID查询采集的表信息和字段信息
     * @param id
     * @return
     */
    TableAndFieldResponse getTableAndFieldInfoById(Integer id);

    /**
     * 分页查询目录分级分类数据
     * @param request
     * @return
     */
    IPage<FlwInfo> queryCatalogTypePage(CatalogTypeRequest request);

    /**
     * 修改分级分类
     * @param id
     * @return
     */
    boolean updateCatalogType(Integer id);
}
