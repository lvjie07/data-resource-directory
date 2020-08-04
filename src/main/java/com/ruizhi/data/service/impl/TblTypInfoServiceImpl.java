package com.ruizhi.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.dal.entitys.FldTblPnd;
import com.ruizhi.data.dal.entitys.FldTypInfo;
import com.ruizhi.data.dal.entitys.TblTypInfo;
import com.ruizhi.data.dal.mapper.TblTypInfoMapper;
import com.ruizhi.data.dto.tblTypInfo.FieldDTO;
import com.ruizhi.data.dto.tblTypInfo.SaveTblTypInfoRequest;
import com.ruizhi.data.dto.tblTypInfo.TblTypInfoRequest;
import com.ruizhi.data.dto.tblTypInfo.UpdateTblTypInfoRequest;
import com.ruizhi.data.service.FldTblPndService;
import com.ruizhi.data.service.FldTypInfoService;
import com.ruizhi.data.service.TblTypInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 业务类型表 服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-30
 */
@Service
public class TblTypInfoServiceImpl extends ServiceImpl<TblTypInfoMapper, TblTypInfo> implements TblTypInfoService {

    @Autowired
    private FldTblPndService fldTblPndService;

    @Autowired
    private TblTypInfoMapper tblTypInfoMapper;

    @Autowired
    private FldTypInfoService fldTypInfoService;

    @Override
    public IPage<TblTypInfo> queryPage(TblTypInfoRequest request) {
        Page<TblTypInfo> page = new Page<>(request.getPageNum(), request.getPageCount());
        QueryWrapper<TblTypInfo> queryWrapper = new QueryWrapper<>();
        IPage<TblTypInfo> resultPage = this.page(page, queryWrapper);
        return resultPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteTblTypInfoById(Integer id) {
        // 1.校验数据
        TblTypInfo tblTypInfo = this.getById(id);
        if (Objects.isNull(tblTypInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.删除数据
        // 2.1 删除主表数据
        this.removeById(id);
        // 2.2 删除对应字段表数据
        QueryWrapper<FldTblPnd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FldTblPnd::getTblId, id);
        fldTblPndService.remove(queryWrapper);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveTblTypInfo(SaveTblTypInfoRequest request) {
        // 1.保存主表数据
        TblTypInfo tblTypInfo = new TblTypInfo();
        BeanUtils.copyProperties(request, tblTypInfo);
        tblTypInfoMapper.insert(tblTypInfo);
        Integer id = tblTypInfo.getId();
        // 2.保存对应字段表数据
        List<FldTblPnd> fldTblPndList = new ArrayList<>();
        request.getFldIds().stream().forEach(fldId -> {
            FldTblPnd fldTblPnd = new FldTblPnd();
            fldTblPnd.setFldId(fldId);
            fldTblPnd.setTblId(id);
            fldTblPndList.add(fldTblPnd);
        });
        fldTblPndService.saveBatch(fldTblPndList);
        return true;
    }

    @Override
    public List<FieldDTO> getField() {
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        List<FldTypInfo> fldTypInfoList = fldTypInfoService.list();
        fldTypInfoList.stream().forEach(f -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setId(f.getId());
            StringBuffer sb = new StringBuffer();
            sb.append(f.getFirstBnsTypeName()).append("-")
                    .append(f.getSecondBnsTypeName());
            if (StringUtils.isNotEmpty(f.getThreeBnsTypeName())) {
                sb.append("-").append(f.getThreeBnsTypeName());
            }
            fieldDTO.setFieldName(sb.toString());
            fieldDTOList.add(fieldDTO);
        });
        return fieldDTOList;
    }

    @Override
    public TblTypInfo getTblTypInfoById(Integer id) {
        // 1.校验数据是否存在
        TblTypInfo tblTypInfo = this.getById(id);
        if (Objects.isNull(tblTypInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        return tblTypInfo;
    }

    @Override
    public List<FieldDTO> getFieldByTblId(Integer id) {
        // 1.查询关联表数据
        QueryWrapper<FldTblPnd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FldTblPnd::getTblId, id);
        List<FldTblPnd> fldTblPndList = fldTblPndService.list(queryWrapper);
        List<Integer> fldIds = new ArrayList<>();
        fldTblPndList.stream().forEach(f -> {
            fldIds.add(f.getFldId());
        });
        // 2.查询字段表数据
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        if (fldIds.size() > 0) {
            List<FldTypInfo> fldTypInfoList = (List<FldTypInfo>) fldTypInfoService.listByIds(fldIds);
            if (Objects.nonNull(fldTblPndList) && fldTypInfoList.size() > 0) {
                fldTypInfoList.stream().forEach(f -> {
                    FieldDTO fieldDTO = new FieldDTO();
                    fieldDTO.setId(f.getId());
                    StringBuffer sb = new StringBuffer();
                    sb.append(f.getFirstBnsTypeName()).append("-")
                            .append(f.getSecondBnsTypeName());
                    if (StringUtils.isNotEmpty(f.getThreeBnsTypeName())) {
                        sb.append("-").append(f.getThreeBnsTypeName());
                    }
                    fieldDTO.setFieldName(sb.toString());
                    fieldDTOList.add(fieldDTO);
                });
            }
        }
        return fieldDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateTblTypInfo(UpdateTblTypInfoRequest request) {
        // 1.校验数据
        // 1.校验数据是否存在
        TblTypInfo tblTypInfo = this.getById(request.getId());
        if (Objects.isNull(tblTypInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.修改数据
        BeanUtils.copyProperties(request,tblTypInfo);
        this.updateById(tblTypInfo);
        // 3.删除所有关联表数据
        QueryWrapper<FldTblPnd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FldTblPnd::getTblId, tblTypInfo.getId());
        fldTblPndService.remove(queryWrapper);
        // 4.插入关联表数据
        List<FldTblPnd> fldTblPndList = new ArrayList<>();
        request.getFldIds().stream().forEach(fldId -> {
            FldTblPnd fldTblPnd = new FldTblPnd();
            fldTblPnd.setFldId(fldId);
            fldTblPnd.setTblId(tblTypInfo.getId());
            fldTblPndList.add(fldTblPnd);
        });
        fldTblPndService.saveBatch(fldTblPndList);
        return true;
    }
}
