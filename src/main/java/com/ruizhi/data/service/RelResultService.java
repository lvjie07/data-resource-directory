package com.ruizhi.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruizhi.data.dal.entitys.RelResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruizhi.data.dto.relResultInfo.RelResultRequest;

/**
 * <p>
 * 关联结果表 服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-08-04
 */
public interface RelResultService extends IService<RelResult> {

    /**
     * 分页查询列表数据
     * @param request
     * @return
     */
    IPage<RelResult> queryPage(RelResultRequest request);

    /**
     * 确认被关联结果集字段
     * @param id
     * @return
     */
    boolean confirm(Integer id);

    /**
     * 取消被关联结果集字段
     * @param id
     * @return
     */
    boolean cancel(Integer id);
}
