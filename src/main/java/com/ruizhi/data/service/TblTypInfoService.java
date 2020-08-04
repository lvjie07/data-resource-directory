package com.ruizhi.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.dal.entitys.TblTypInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruizhi.data.dto.tblTypInfo.FieldDTO;
import com.ruizhi.data.dto.tblTypInfo.SaveTblTypInfoRequest;
import com.ruizhi.data.dto.tblTypInfo.TblTypInfoRequest;
import com.ruizhi.data.dto.tblTypInfo.UpdateTblTypInfoRequest;

import java.util.List;

/**
 * <p>
 * 业务类型表 服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-30
 */
public interface TblTypInfoService extends IService<TblTypInfo> {

    /**
     * 分页查询业务类型列表
     * @param request
     * @return
     */
    IPage<TblTypInfo> queryPage(TblTypInfoRequest request);

    /**
     * 通过ID删除数据
     * @param id
     * @return
     */
    boolean deleteTblTypInfoById(Integer id);

    /**
     * 保存业务类型表
     * @param request
     * @return
     */
    boolean saveTblTypInfo(SaveTblTypInfoRequest request);

    /**
     * 查询所有字段
     * @return
     */
    List<FieldDTO> getField();

    /**
     * 通过ID查询数据
     * @param id
     * @return
     */
    TblTypInfo getTblTypInfoById(Integer id);

    /**
     * 通过业务类型表ID查询所有字段数据
     * @param id
     * @return
     */
    List<FieldDTO> getFieldByTblId(Integer id);

    /**
     * 修改信息
     * @param request
     * @return
     */
    boolean updateTblTypInfo(UpdateTblTypInfoRequest request);
}
