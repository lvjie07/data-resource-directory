package com.ruizhi.data.service;

import com.ruizhi.data.dal.entitys.SenLevInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-28
 */
public interface SenLevInfoService extends IService<SenLevInfo> {

    /**
     * 删除敏感等级数据
     * @param id
     * @return
     */
    boolean deleteSenLevInfoById(Integer id);

}
