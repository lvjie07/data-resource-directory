package com.ruizhi.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.dal.entitys.FldTypInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruizhi.data.dto.fldTypInfo.FldTypInfoListDTO;
import com.ruizhi.data.dto.fldTypInfo.FldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.SaveFldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.UpdateFldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.UpdateRuleRequest;

/**
 * <p>
 * 字段分级分类表 服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
public interface FldTypInfoService extends IService<FldTypInfo> {

    /**
     * 分页查询字段分级分类列表
     * @param request
     * @return
     */
    IPage<FldTypInfoListDTO> queryPage(FldTypInfoRequest request);

    /**
     * 通过ID删除数据
     * @param id
     * @return
     */
    boolean deleteFldTypInfoById(Integer id);

    /**
     * 修改规则设置
     * @param request
     * @return
     */
    boolean updateRule(UpdateRuleRequest request);

    /**
     * 修改字段分级分类
     * @param request
     * @return
     */
    boolean updateFldTypInfo(UpdateFldTypInfoRequest request);

    /**
     * 通过ID查询数据
     * @param id
     * @return
     */
    FldTypInfo getFldTypInfoById(Integer id);

    /**
     * 保存分类设置
     * @param request
     * @return
     */
    boolean saveFldTypInfo(SaveFldTypInfoRequest request);
}
