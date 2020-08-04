package com.ruizhi.data.service.impl;

import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.dal.entitys.SenLevInfo;
import com.ruizhi.data.dal.mapper.SenLevInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.service.SenLevInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-28
 */
@Service
public class SenLevInfoServiceImpl extends ServiceImpl<SenLevInfoMapper, SenLevInfo> implements SenLevInfoService {

    @Override
    public boolean deleteSenLevInfoById(Integer id) {
        // 1.校验数据是否存在
        SenLevInfo senLevInfo = this.getById(id);
        if (Objects.isNull(senLevInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.校验数据是否被引用，引用不可以删除
        // TODO
        // 3.删除数据
        return this.removeById(id);
    }
}
