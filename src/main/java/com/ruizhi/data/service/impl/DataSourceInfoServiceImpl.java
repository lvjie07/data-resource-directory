package com.ruizhi.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.dal.entitys.DataSourceInfo;
import com.ruizhi.data.dal.mapper.DataSourceInfoMapper;
import com.ruizhi.data.dto.dataSourceInfo.DataSourceInfoRequest;
import com.ruizhi.data.dto.flwInfo.DataBaseInfoDTO;
import com.ruizhi.data.service.DataSourceInfoService;
import com.ruizhi.data.utils.ColaBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-27
 */
@Service
public class DataSourceInfoServiceImpl extends ServiceImpl<DataSourceInfoMapper, DataSourceInfo> implements DataSourceInfoService {

    @Override
    public IPage<DataSourceInfo> queryPage(DataSourceInfoRequest request) {
        Page<DataSourceInfo> page = new Page<>(request.getPageNum(), request.getPageCount());
        QueryWrapper<DataSourceInfo> queryWrapper =  new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getSrcTyp())) {
            queryWrapper.lambda().eq(DataSourceInfo::getSrcTyp,request.getSrcTyp());
        }
        if (StringUtils.isNotEmpty(request.getSrcName())) {
            queryWrapper.lambda().like(DataSourceInfo::getSrcName,request.getSrcName());
        }
        IPage<DataSourceInfo> resultPage = this.page(page,queryWrapper);
        return resultPage;
    }

    @Override
    public DataSourceInfo getDataSourceInfoById(Integer id) {
        // 1.校验数据是否存在
        DataSourceInfo dataSourceInfo = this.getById(id);
        if (Objects.isNull(dataSourceInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        return dataSourceInfo;
    }

    @Override
    public List<DataBaseInfoDTO> getDataBaseInfoListByType(String dataBaseType) {
        QueryWrapper<DataSourceInfo> queryWrapper =  new QueryWrapper<>();
        queryWrapper.lambda().eq(DataSourceInfo::getSrcTyp,dataBaseType);
        List<DataSourceInfo> dataSourceInfoList = this.list(queryWrapper);
        List<DataBaseInfoDTO> dataBaseInfoDTOList = new ArrayList<>();
        dataBaseInfoDTOList = ColaBeanUtils.copyListProperties(dataSourceInfoList,DataBaseInfoDTO::new);
        return dataBaseInfoDTOList;
    }
}
