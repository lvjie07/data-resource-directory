package com.ruizhi.data.dal.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruizhi.data.dal.entitys.FldTypInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruizhi.data.dto.fldTypInfo.FldTypInfoListDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字段分级分类表 Mapper 接口
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
public interface FldTypInfoMapper extends BaseMapper<FldTypInfo> {

    /**
     * 分页查询
     * @param page
     * @param query
     * @return
     */
    IPage<FldTypInfoListDTO> queryPage(IPage<FldTypInfoListDTO> page,@Param(Constants.WRAPPER) Wrapper query);

    List<FldTypInfo> test();
}
