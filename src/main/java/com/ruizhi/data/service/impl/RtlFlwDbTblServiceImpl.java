package com.ruizhi.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.dal.entitys.FldCltRst;
import com.ruizhi.data.dal.entitys.FlwInfo;
import com.ruizhi.data.dal.entitys.RtlFlwDbTbl;
import com.ruizhi.data.dal.entitys.SampleResultData;
import com.ruizhi.data.dal.mapper.RtlFlwDbTblMapper;
import com.ruizhi.data.dto.flwInfo.DataBaseTablesInfoRequest;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.TableAndFieldDTO;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.UpdataBusinessTypeRequest;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.UpdataTableInfoRequest;
import com.ruizhi.data.service.FldCltRstService;
import com.ruizhi.data.service.RtlFlwDbTblService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.service.SampleResultDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 采集结果数据源库库表 服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-08-03
 */
@Service
public class RtlFlwDbTblServiceImpl extends ServiceImpl<RtlFlwDbTblMapper, RtlFlwDbTbl> implements RtlFlwDbTblService {

    @Autowired
    private RtlFlwDbTblMapper rtlFlwDbTblMapper;

    @Autowired
    private FldCltRstService fldCltRstService;

    @Autowired
    private SampleResultDataService sampleResultDataService;

    @Override
    public List<RtlFlwDbTbl> getRtlFlwDbTblListRealFlwId(DataBaseTablesInfoRequest request) {
        QueryWrapper<RtlFlwDbTbl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("db.flw_id", request.getId());
        if (StringUtils.isNotEmpty(request.getTableName())) {
            queryWrapper.eq("tbl.tbl_name", request.getTableName());
        }
        return rtlFlwDbTblMapper.getRtlFlwDbTblListRealFlwId(queryWrapper);
    }

    @Override
    public boolean updateTableInfo(UpdataTableInfoRequest request) {
        // 校验数据是否存在
        RtlFlwDbTbl rtlFlwDbTbl = this.getById(request.getId());
        if (Objects.isNull(rtlFlwDbTbl)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        rtlFlwDbTbl.setTblCmt(request.getTblCmt());
        rtlFlwDbTbl.setUpdTime(new Date());
        return this.updateById(rtlFlwDbTbl);
    }

    @Override
    public boolean updateBusinessType(UpdataBusinessTypeRequest request) {
        // 校验数据是否存在
        RtlFlwDbTbl rtlFlwDbTbl = this.getById(request.getId());
        if (Objects.isNull(rtlFlwDbTbl)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        if (StringUtils.isNotEmpty(request.getWrkFstType())) {
            rtlFlwDbTbl.setWrkFstType(request.getWrkFstType());
        }
        if (StringUtils.isNotEmpty(request.getWrkSedType())) {
            rtlFlwDbTbl.setWrkSedType(request.getWrkSedType());
        }
        if (StringUtils.isNotEmpty(request.getWrkThdType())) {
            rtlFlwDbTbl.setWrkThdType(request.getWrkThdType());
        }
        if (StringUtils.isNotEmpty(request.getWrkForType())) {
            rtlFlwDbTbl.setWrkForType(request.getWrkForType());
        }
        rtlFlwDbTbl.setUpdTime(new Date());
        return this.updateById(rtlFlwDbTbl);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertDataConnection(List<TableAndFieldDTO> tableAndFieldDTOList, FlwInfo flwInfo) {
        for (TableAndFieldDTO tableAndFieldDTO : tableAndFieldDTOList) {
            // 1.插入表数据
            RtlFlwDbTbl rtlFlwDbTbl = tableAndFieldDTO.getRtlFlwDbTbl();
            rtlFlwDbTbl.setUpdTime(new Date());
            this.save(rtlFlwDbTbl);
            Integer saveId = rtlFlwDbTbl.getId();
            // 2.插入字段数据
            List<FldCltRst> fldCltRstList = tableAndFieldDTO.getFldCltRstList();
            fldCltRstList.stream().forEach(fldCltRst -> {
                fldCltRst.setTblId(saveId);
            });
            fldCltRstService.saveBatch(fldCltRstList);
            // 3.插入采集数据
            for(FldCltRst fldCltRst : fldCltRstList) {
                Integer fldCltRstId =  fldCltRst.getId();
                List<String> resultData = fldCltRst.getResultData();
                List<SampleResultData> sampleResultDataList = new ArrayList<>();
                for (String s : resultData) {
                    SampleResultData sampleResultData = new SampleResultData();
                    sampleResultData.setResultData(s);
                    sampleResultData.setRstId(fldCltRstId);
                    sampleResultDataList.add(sampleResultData);
                }
                sampleResultDataService.saveBatch(sampleResultDataList);
            }
        }
        return true;
    }
}
