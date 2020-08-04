package com.ruizhi.data.service;

import com.ruizhi.data.dal.entitys.CascadeSortInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruizhi.data.dto.cascadeSortInfo.CascadeSortInfoRequest;
import com.ruizhi.data.dto.cascadeSortInfo.SaveCascadeSortInfoRequest;
import com.ruizhi.data.dto.cascadeSortInfo.TreeDTO;

import java.util.List;

/**
 * <p>
 * 级联分类管理 服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
public interface CascadeSortInfoService extends IService<CascadeSortInfo> {

    /**
     * 返回树形结构数据
     * @param request
     * @return
     */
    List<TreeDTO> getTree(CascadeSortInfoRequest request);

    /**
     * 通过ID查询所有子数据
     * @param id
     * @return
     */
    List<TreeDTO> getChildByParentId(Integer id);

    /**
     * 保存分类管理
     * @param request
     * @return
     */
    boolean saveCascadeSortInfo(SaveCascadeSortInfoRequest request);
}
