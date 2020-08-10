package com.ruizhi.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ClassifyStatusCode;
import com.ruizhi.data.constant.DataBaseTypeCode;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.constant.TaskStatusCode;
import com.ruizhi.data.dal.entitys.FldCltRst;
import com.ruizhi.data.dal.entitys.FldTblPnd;
import com.ruizhi.data.dal.entitys.FldTypInfo;
import com.ruizhi.data.dal.entitys.FlwInfo;
import com.ruizhi.data.dal.entitys.RtlFlwDb;
import com.ruizhi.data.dal.entitys.RtlFlwDbTbl;
import com.ruizhi.data.dal.entitys.SenLevInfo;
import com.ruizhi.data.dal.entitys.TblTypInfo;
import com.ruizhi.data.dal.mapper.FlwInfoMapper;
import com.ruizhi.data.dto.catalogTypeInfo.CatalogTypeRequest;
import com.ruizhi.data.dto.flwInfo.DataBaseInfoDTO;
import com.ruizhi.data.dto.flwInfo.DataBaseTablesInfoRequest;
import com.ruizhi.data.dto.flwInfo.DataBaseTablesInfoResponse;
import com.ruizhi.data.dto.flwInfo.FlwInfoRequest;
import com.ruizhi.data.dto.flwInfo.SaveFlwInfoRequest;
import com.ruizhi.data.dto.flwInfo.TableAndFieldResponse;
import com.ruizhi.data.dto.flwInfo.TableInfoDTO;
import com.ruizhi.data.dto.flwInfo.TreeDataBaseInfoDTO;
import com.ruizhi.data.dto.tblTypInfo.RuleFieldDTO;
import com.ruizhi.data.event.DataCollectionEvent;
import com.ruizhi.data.service.FldCltRstService;
import com.ruizhi.data.service.FldTblPndService;
import com.ruizhi.data.service.FldTypInfoService;
import com.ruizhi.data.service.FlwInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.service.RtlFlwDbService;
import com.ruizhi.data.service.RtlFlwDbTblService;
import com.ruizhi.data.service.SenLevInfoService;
import com.ruizhi.data.service.TblTypInfoService;
import com.ruizhi.data.utils.ColaBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 作业记录表 服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-31
 */
@Service
public class FlwInfoServiceImpl extends ServiceImpl<FlwInfoMapper, FlwInfo> implements FlwInfoService {

    @Autowired
    private FlwInfoMapper flwInfoMapper;

    @Autowired
    private RtlFlwDbService rtlFlwDbService;

    @Autowired
    private RtlFlwDbTblService rtlFlwDbTblService;

    @Autowired
    private FldCltRstService fldCltRstService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private TblTypInfoService tblTypInfoService;

    @Autowired
    private FldTblPndService fldTblPndService;

    @Autowired
    private FldTypInfoService fldTypInfoService;

    @Autowired
    private SenLevInfoService senLevInfoService;

    @Override
    public IPage<FlwInfo> queryPage(FlwInfoRequest request) {
        Page<FlwInfo> page = new Page<>(request.getPageNum(), request.getPageCount());
        QueryWrapper<FlwInfo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getFlwName())) {
            queryWrapper.lambda().like(FlwInfo::getFlwNam, request.getFlwName());
        }
        if (StringUtils.isNotEmpty(request.getTaskStatus())) {
            queryWrapper.lambda().eq(FlwInfo::getFlwColTyp, request.getTaskStatus());
        }
        IPage<FlwInfo> resultPage = this.page(page, queryWrapper);
        resultPage.getRecords().stream().forEach(f -> {
            f.setFlwColTyp(TaskStatusCode.getMessage(f.getFlwColTyp()));
            f.setSrcTyp(DataBaseTypeCode.getMessage(f.getSrcTyp()));
        });
        return resultPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveFlwInfo(SaveFlwInfoRequest request) {
        // TODO　校验数据

        // 保存数据
        FlwInfo flwInfo = new FlwInfo();
        BeanUtils.copyProperties(request, flwInfo);
        flwInfo.setFlwAddTime(new Date());
        flwInfo.setFlwColTyp(TaskStatusCode.UN_COMPLETE.getCode());
        // TODO　创建人名称
        flwInfo.setFlwAddName("管理员");
        List<DataBaseInfoDTO> dataBaseInfoDTOS = request.getDataBaseInfoDTOList();
        StringBuffer sb = new StringBuffer();
        dataBaseInfoDTOS.stream().forEach(d -> {
            sb.append(d.getSrcName()).append(",");
        });
        // 数据源名称拼接
        if (sb.length() > 0) {
            flwInfo.setSrcNam(sb.substring(0, sb.length() - 1));
        }
        flwInfoMapper.insert(flwInfo);
        Integer flwInfoId = flwInfo.getId();
        // 保存数据源关联表数据
        List<RtlFlwDb> rtlFlwDbList = new ArrayList<>();
        dataBaseInfoDTOS.stream().forEach(d -> {
            RtlFlwDb rtlFlwDb = new RtlFlwDb();
            rtlFlwDb.setFlwId(flwInfoId);
            rtlFlwDb.setSrcId(d.getId());
            rtlFlwDbList.add(rtlFlwDb);
        });
        rtlFlwDbService.saveBatch(rtlFlwDbList);
        // TODO 后台异步执行采集数据库表信息任务
        applicationEventPublisher.publishEvent(new DataCollectionEvent(this, flwInfoId));
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteFlwInfoById(Integer id) {
        // 1.校验数据是否存在
        FlwInfo flwInfo = this.getById(id);
        if (Objects.isNull(flwInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 2.删除数据
        this.removeById(id);
        QueryWrapper<RtlFlwDb> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RtlFlwDb::getFlwId, id);
        rtlFlwDbService.remove(queryWrapper);
        return true;
    }

    @Override
    public DataBaseTablesInfoResponse getTreeDataBaseInfo(DataBaseTablesInfoRequest request) {
        FlwInfo flwInfo = this.getById(request.getId());
        if (Objects.isNull(flwInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 查询采集表数据
        List<RtlFlwDbTbl> rtlFlwDbTblList = rtlFlwDbTblService.getRtlFlwDbTblListRealFlwId(request);
        Map<Integer, List<RtlFlwDbTbl>> resultMap = rtlFlwDbTblList.stream().collect(
                Collectors.groupingBy(RtlFlwDbTbl::getSrcId));
        DataBaseTablesInfoResponse response = new DataBaseTablesInfoResponse();
        response.setDataBaseSchem(DataBaseTypeCode.getMessage(flwInfo.getSrcTyp()));
        List<TreeDataBaseInfoDTO> treeDataBaseInfoDTOList = new ArrayList<>();
        for (Map.Entry<Integer, List<RtlFlwDbTbl>> entry : resultMap.entrySet()) {
            Integer key = entry.getKey();
            List<RtlFlwDbTbl> value = entry.getValue();
            TreeDataBaseInfoDTO treeDataBaseInfoDTO = new TreeDataBaseInfoDTO();
            TableInfoDTO tableInfoDTO = new TableInfoDTO();
            List<TableInfoDTO> tableInfoDTOList = new ArrayList<>();
            value.stream().forEach(r -> {
                tableInfoDTO.setTableId(r.getId());
                tableInfoDTO.setTableName(r.getTblName());
                tableInfoDTOList.add(tableInfoDTO);
            });
            treeDataBaseInfoDTO.setTableInfoDTOList(tableInfoDTOList);
            treeDataBaseInfoDTO.setDataBaseName(value.get(0).getDbName());
            treeDataBaseInfoDTOList.add(treeDataBaseInfoDTO);
        }
        response.setTreeDataBaseInfoDTOList(treeDataBaseInfoDTOList);
        return response;
    }

    @Override
    public TableAndFieldResponse getTableAndFieldInfoById(Integer id) {
        TableAndFieldResponse response = new TableAndFieldResponse();
        // 1.查询采集表数据是否存在
        RtlFlwDbTbl rtlFlwDbTbl = rtlFlwDbTblService.getById(id);
        if (Objects.isNull(rtlFlwDbTbl)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        response.setRtlFlwDbTbl(rtlFlwDbTbl);
        // 2.查询字段信息
        QueryWrapper<FldCltRst> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FldCltRst::getTblId, id);
        List<FldCltRst> fldCltRstList = fldCltRstService.list(queryWrapper);
        response.setFldCltRstList(fldCltRstList);
        return response;
    }

    @Override
    public IPage<FlwInfo> queryCatalogTypePage(CatalogTypeRequest request) {
        Page<FlwInfo> page = new Page<>(request.getPageNum(), request.getPageCount());
        QueryWrapper<FlwInfo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getFlwName())) {
            queryWrapper.lambda().like(FlwInfo::getFlwNam, request.getFlwName());
        }
        if (StringUtils.isNotEmpty(request.getClassifyStatus())) {
            queryWrapper.lambda().eq(FlwInfo::getWrkTyp, request.getClassifyStatus());
        }
        IPage<FlwInfo> resultPage = this.page(page, queryWrapper);
        resultPage.getRecords().stream().forEach(f -> {
            f.setWrkTyp(ClassifyStatusCode.getMessage(f.getWrkTyp()));
            f.setSrcTyp(DataBaseTypeCode.getMessage(f.getSrcTyp()));
        });
        return resultPage;
    }

    @Override
    public boolean updateCatalogType(Integer id) {
        FlwInfo flwInfo = this.getById(id);
        if (Objects.isNull(flwInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(), ResultCode.DB_DATA_NOTEXIST.getMessage());
        }
        // 查询所有分级分类数据
        List<TblTypInfo> tblTypInfoList = tblTypInfoService.list();
        tblTypInfoList.stream().forEach(tblTypInfo -> {
            QueryWrapper<FldTblPnd> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FldTblPnd::getTblId, tblTypInfo.getId());
            List<FldTblPnd> fldTblPndList = fldTblPndService.list(queryWrapper);
            List<RuleFieldDTO> ruleFieldDTOList = ColaBeanUtils.copyListProperties(fldTblPndList, RuleFieldDTO::new);
            ruleFieldDTOList.stream().forEach(ruleFieldDTO -> {
                // 查询规则表数据
                FldTypInfo fldTypInfo = fldTypInfoService.getById(ruleFieldDTO.getFldId());
                if (Objects.nonNull(fldTypInfo)) {
                    ruleFieldDTO.setPrt(fldTypInfo.getPrt());
                    Integer sendId = fldTypInfo.getSenId();
                    if (Objects.nonNull(sendId)) {
                        SenLevInfo senLevInfo = senLevInfoService.getById(sendId);
                        if (Objects.nonNull(senLevInfo)) {
                            ruleFieldDTO.setLev(senLevInfo.getLev());
                            ruleFieldDTO.setSenLev(senLevInfo.getSenLev());
                        }
                    }
                }
            });
            tblTypInfo.setRuleFieldDTOList(ruleFieldDTOList);
        });
        // 查询采集表对应的字段
        DataBaseTablesInfoRequest request = new DataBaseTablesInfoRequest();
        request.setId(id);
        List<RtlFlwDbTbl> rtlFlwDbTblList = rtlFlwDbTblService.getRtlFlwDbTblListRealFlwId(request);
        rtlFlwDbTblList.stream().forEach(rtlFlwDbTbl -> {
            QueryWrapper<FldCltRst> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.lambda().eq(FldCltRst::getTblId, rtlFlwDbTbl.getId());
            List<FldCltRst> fldCltRstList = fldCltRstService.list(queryWrapper1);
            rtlFlwDbTbl.setFldCltRstList(fldCltRstList);
        });
        // 匹配所有的采集表对应的字段
        for (TblTypInfo tblTypInfo : tblTypInfoList) {
            List<RuleFieldDTO> ruleFieldDTOList = tblTypInfo.getRuleFieldDTOList();
            for (int i = 0; i < rtlFlwDbTblList.size(); i++) {
                RtlFlwDbTbl rtlFlwDbTbl = rtlFlwDbTblList.get(i);
                Map<String,Object> resultMap = this.isLike(ruleFieldDTOList, rtlFlwDbTbl);
                boolean isLike = (Boolean) resultMap.get("isLike");
                List<FldCltRst> updateData = (List<FldCltRst>) resultMap.get("updateData");
                if (isLike) {
                    // 更新一级、二级、三级、四级类型
                    rtlFlwDbTbl.setWrkFstType(tblTypInfo.getTblBnsTypOne());
                    rtlFlwDbTbl.setWrkSedType(tblTypInfo.getTblBnsTypTwo());
                    rtlFlwDbTbl.setWrkThdType(tblTypInfo.getTblBnsTypThree());
                    rtlFlwDbTbl.setWrkForType(tblTypInfo.getTblBnsTypFour());
                    rtlFlwDbTbl.setUpdTime(new Date());
                    rtlFlwDbTblService.updateById(rtlFlwDbTbl);
                    // 更新字段的敏感分级和分级名称
                    if (updateData.size() > 0) {
                        fldCltRstService.updateBatchById(updateData);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 校验正则表达式是否匹配
     *
     * @param ruleFieldDTOList
     * @param rtlFlwDbTbl
     * @return
     */
    private Map<String,Object> isLike(List<RuleFieldDTO> ruleFieldDTOList, RtlFlwDbTbl rtlFlwDbTbl) {
        Map<String,Object> resultMap = new HashMap<>();
        List<FldCltRst> resultFldCltRstList = new ArrayList<>();
        // 每个规则是否匹配上字段的个数，如果每个规则都有字段匹配上才更新数据
        int likeNum = 0;
        for (RuleFieldDTO ruleFieldDTO : ruleFieldDTOList) {
            boolean isLike = false;
            // 正则表达式
            String prt = ruleFieldDTO.getPrt();
            if (StringUtils.isNotEmpty(prt)) {
                // 业务表中配置的所有字段都和表中字段匹配上才修改业务类型
                List<FldCltRst> fldCltRstList = rtlFlwDbTbl.getFldCltRstList();
                for (FldCltRst fldCltRst : fldCltRstList) {
                    String fldCmt = fldCltRst.getFldCmt();
                    boolean isMatch = Pattern.matches(prt, fldCmt);
                    if (isMatch) {
                        isLike = true;
                        fldCltRst.setWrkLvl(ruleFieldDTO.getLev());
                        fldCltRst.setSenName(ruleFieldDTO.getSenLev());
                        resultFldCltRstList.add(fldCltRst);
                    }
                }
            }
            // 匹配上数量加1
            if (isLike) {
                likeNum++;
            }
        }
        resultMap.put("isLike",likeNum == ruleFieldDTOList.size() ? true : false);
        resultMap.put("updateData",resultFldCltRstList);
        return resultMap;
    }
}
