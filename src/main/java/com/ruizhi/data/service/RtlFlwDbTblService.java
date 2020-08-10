package com.ruizhi.data.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruizhi.data.dal.entitys.FlwInfo;
import com.ruizhi.data.dal.entitys.RtlFlwDbTbl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruizhi.data.dto.flwInfo.DataBaseTablesInfoRequest;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.TableAndFieldDTO;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.UpdataBusinessTypeRequest;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.UpdataTableInfoRequest;

import java.util.List;

/**
 * <p>
 * 采集结果数据源库库表 服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-08-03
 */
public interface RtlFlwDbTblService extends IService<RtlFlwDbTbl> {

    /**
     * 查询采集表关联作业表数据
     * @param request
     * @return
     */
    List<RtlFlwDbTbl> getRtlFlwDbTblListRealFlwId(DataBaseTablesInfoRequest request);

    /**
     * 修改采集表信息
     * @param request
     * @return
     */
    boolean updateTableInfo(UpdataTableInfoRequest request);

    /**
     * 修改采集表业务类型数据
     * @param request
     * @return
     */
    boolean updateBusinessType(UpdataBusinessTypeRequest request);

    /**
     * 插入采集数据
     * @param tableAndFieldDTOList
     * @param flwInfo
     * @return
     */
    boolean insertDataConnection(List<TableAndFieldDTO> tableAndFieldDTOList,FlwInfo flwInfo);
}
