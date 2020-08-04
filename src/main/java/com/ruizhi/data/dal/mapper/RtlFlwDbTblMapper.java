package com.ruizhi.data.dal.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruizhi.data.dal.entitys.RtlFlwDbTbl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 采集结果数据源库库表 Mapper 接口
 * </p>
 *
 * @author lvjie
 * @since 2020-08-03
 */
public interface RtlFlwDbTblMapper extends BaseMapper<RtlFlwDbTbl> {

    /**
     * 查询采集表信息
     * @param query
     * @return
     */
    List<RtlFlwDbTbl> getRtlFlwDbTblListRealFlwId(@Param(Constants.WRAPPER) Wrapper query);
}
