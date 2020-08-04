package com.ruizhi.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.dal.entitys.CascadeSortInfo;
import com.ruizhi.data.dal.entitys.DataSourceInfo;
import com.ruizhi.data.dal.entitys.FldTypInfo;
import com.ruizhi.data.dal.entitys.SenLevInfo;
import com.ruizhi.data.dal.mapper.FldTypInfoMapper;
import com.ruizhi.data.dto.fldTypInfo.FldTypInfoListDTO;
import com.ruizhi.data.dto.fldTypInfo.FldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.SaveFldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.UpdateFldTypInfoRequest;
import com.ruizhi.data.dto.fldTypInfo.UpdateRuleRequest;
import com.ruizhi.data.service.CascadeSortInfoService;
import com.ruizhi.data.service.FldTypInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.service.SenLevInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 字段分级分类表 服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
@Service
public class FldTypInfoServiceImpl extends ServiceImpl<FldTypInfoMapper, FldTypInfo> implements FldTypInfoService {

    @Autowired
    private FldTypInfoMapper fldTypInfoMapper;

    @Autowired
    private CascadeSortInfoService cascadeSortInfoService;

    @Autowired
    private SenLevInfoService senLevInfoService;

    @Override
    public IPage<FldTypInfoListDTO> queryPage(FldTypInfoRequest request) {
        Page<FldTypInfoListDTO> page = new Page<>(request.getPageNum(), request.getPageCount());
        QueryWrapper<DataSourceInfo> queryWrapper =  new QueryWrapper<>();
        if (Objects.nonNull(request.getSenId())) {
           queryWrapper.eq("typ.sen_id",request.getSenId());
        }
        String businessType = request.getBusinessType();
        if (StringUtils.isNotEmpty(businessType)) {
            queryWrapper.like("(typ.first_bns_type_name", businessType).or()
                    .like("typ.second_bns_type_name",businessType).or()
                    .like("typ.three_bns_type_name",businessType)
                    .last(")");
        }
        IPage<FldTypInfoListDTO> resultPage = fldTypInfoMapper.queryPage(page,queryWrapper);
        return resultPage;
    }

    @Override
    public boolean deleteFldTypInfoById(Integer id) {
        // 1.校验数据是否存在
        FldTypInfo fldTypInfo = this.getById(id);
        if (Objects.isNull(fldTypInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.校验数据是否被引用，引用不可以删除
        // TODO
        // 3.删除数据
        return this.removeById(id);
    }

    @Override
    public boolean updateRule(UpdateRuleRequest request) {
        // 1.校验数据是否存在
        FldTypInfo fldTypInfo = this.getById(request.getId());
        if (Objects.isNull(fldTypInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.修改数据
        fldTypInfo.setPrt(request.getPrt());
        fldTypInfo.setIstNam(request.getIstNam());
        return this.updateById(fldTypInfo);
    }

    @Override
    public boolean updateFldTypInfo(UpdateFldTypInfoRequest request) {
        // 1.校验数据是否存在
        FldTypInfo fldTypInfo = this.getById(request.getId());
        if (Objects.isNull(fldTypInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.修改数据
        fldTypInfo.setFirstBnsTypeId(request.getFirstBnsTypeId());
        fldTypInfo.setFirstBnsTypeName(request.getFirstBnsTypeName());
        fldTypInfo.setSecondBnsTypeId(request.getSecondBnsTypeId());
        fldTypInfo.setSecondBnsTypeName(request.getSecondBnsTypeName());
        fldTypInfo.setThreeBnsTypeId(request.getThreeBnsTypeId());
        fldTypInfo.setThreeBnsTypeName(request.getThreeBnsTypeName());
        fldTypInfo.setSenId(request.getSenId());
        return this.updateById(fldTypInfo);
    }

    @Override
    public FldTypInfo getFldTypInfoById(Integer id) {
        // 1.校验数据是否存在
        FldTypInfo fldTypInfo = this.getById(id);
        if (Objects.isNull(fldTypInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        return fldTypInfo;
    }

    @Override
    public boolean saveFldTypInfo(SaveFldTypInfoRequest request) {
        // 1.校验数据合理性
        Integer firstBnsTypeId = request.getFirstBnsTypeId();
        CascadeSortInfo cascadeSortInfo1 = cascadeSortInfoService.getById(firstBnsTypeId);
        if (Objects.isNull(cascadeSortInfo1)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),"一级业务类型数据不存在");
        }
        Integer secondBnsTypeId = request.getSecondBnsTypeId();
        CascadeSortInfo cascadeSortInfo2 = cascadeSortInfoService.getById(secondBnsTypeId);
        if (Objects.isNull(cascadeSortInfo2)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),"二级业务类型数据不存在");
        }
        Integer threeBnsTypeId = request.getThreeBnsTypeId();
        CascadeSortInfo cascadeSortInfo3 = null;
        if (Objects.nonNull(threeBnsTypeId)) {
            cascadeSortInfo3 = cascadeSortInfoService.getById(threeBnsTypeId);
            if (Objects.isNull(cascadeSortInfo3)) {
                throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),"三级业务类型数据不存在");
            }
        }
        Integer senId = request.getSenId();
        SenLevInfo senLevInfo = senLevInfoService.getById(senId);
        if (Objects.isNull(senLevInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),"敏感等级数据不存在");
        }
        // 2.保存数据
        FldTypInfo fldTypInfo = new FldTypInfo();
        BeanUtils.copyProperties(request,fldTypInfo);
        fldTypInfo.setFirstBnsTypeName(cascadeSortInfo1.getLevName());
        fldTypInfo.setSecondBnsTypeName(cascadeSortInfo2.getLevName());
        if (Objects.nonNull(cascadeSortInfo3)) {
            fldTypInfo.setThreeBnsTypeName(cascadeSortInfo3.getLevName());
        }
        return this.save(fldTypInfo);
    }
}
