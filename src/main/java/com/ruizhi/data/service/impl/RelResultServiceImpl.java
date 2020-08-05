package com.ruizhi.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ConfirmStatusCode;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.dal.entitys.RelResult;
import com.ruizhi.data.dal.entitys.RtlFlwDb;
import com.ruizhi.data.dal.entitys.RtlTbl;
import com.ruizhi.data.dal.mapper.RelResultMapper;
import com.ruizhi.data.dto.relResultInfo.RelResultRequest;
import com.ruizhi.data.service.RelResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.service.RtlTblService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 关联结果表 服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-08-04
 */
@Service
public class RelResultServiceImpl extends ServiceImpl<RelResultMapper, RelResult> implements RelResultService {

    @Autowired
    private RtlTblService rtlTblService;

    @Override
    public IPage<RelResult> queryPage(RelResultRequest request) {
        Page<RelResult> page = new Page<>(request.getPageNum(), request.getPageCount());
        QueryWrapper<RelResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelResult::getFlwId,request.getId());
        if (StringUtils.isNotEmpty(request.getTableName())) {
            queryWrapper.lambda().eq(RelResult::getTblName,request.getTableName());
        }
        if (StringUtils.isNotEmpty(request.getCfmType())) {
            queryWrapper.lambda().eq(RelResult::getCfmTyp,request.getCfmType());
        }
        IPage<RelResult> resultPage = this.page(page,queryWrapper);
        resultPage.getRecords().stream().forEach(relResult -> {
            // 处理确认字段
            relResult.setCfmTyp(ConfirmStatusCode.getMessage(relResult.getCfmTyp()));
            // 查询所有被关联结果表数据
            QueryWrapper<RtlTbl> rtlTblQueryWrapper = new QueryWrapper<RtlTbl>();
            rtlTblQueryWrapper.lambda().eq(RtlTbl::getRelResultId,relResult.getId());
            List<RtlTbl> rtlTblList = rtlTblService.list(rtlTblQueryWrapper);
            rtlTblList.stream().forEach(rtlTbl -> {
                rtlTbl.setCfmTyp(ConfirmStatusCode.getMessage(rtlTbl.getCfmTyp()));
            });
            relResult.setRtlTblList(rtlTblList);
        });
        return resultPage;
    }

    @Override
    public boolean confirm(Integer id) {
        // 1.校验数据是否存在
        RtlTbl rtlTbl = rtlTblService.getById(id);
        if (Objects.isNull(rtlTbl)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.修改当前数据为确认状态
        rtlTbl.setCfmTyp(ConfirmStatusCode.CONFIRM.getCode());
        rtlTblService.updateById(rtlTbl);
        // 3.排除当前数据所有的关联数据都确认了，修改关联结果为已确认
        QueryWrapper<RtlTbl> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RtlTbl::getRelResultId,rtlTbl.getRelResultId());
        queryWrapper.lambda().eq(RtlTbl::getCfmTyp,ConfirmStatusCode.UN_CONFIRM.getCode());
        queryWrapper.lambda().ne(RtlTbl::getId,id);
        int count = rtlTblService.count(queryWrapper);
        if (count == 0) {
            RelResult relResult = this.getById(rtlTbl.getRelResultId());
            relResult.setCfmTyp(ConfirmStatusCode.CONFIRM.getCode());
            this.updateById(relResult);
        }
        return true;
    }

    @Override
    public boolean cancel(Integer id) {
        // 1.校验数据是否存在
        RtlTbl rtlTbl = rtlTblService.getById(id);
        if (Objects.isNull(rtlTbl)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.删除当前数据
        rtlTblService.removeById(id);
        // 3.所有被关联结果集都删除了，删除该结果集
        QueryWrapper<RtlTbl> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RtlTbl::getRelResultId,rtlTbl.getRelResultId());
        queryWrapper.lambda().ne(RtlTbl::getId,id);
        int count = rtlTblService.count(queryWrapper);
        if (count == 0) {
            this.removeById(rtlTbl.getRelResultId());
        }
        return true;
    }
}
