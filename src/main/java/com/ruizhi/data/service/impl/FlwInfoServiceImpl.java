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
import com.ruizhi.data.dal.entitys.FlwInfo;
import com.ruizhi.data.dal.entitys.RtlFlwDb;
import com.ruizhi.data.dal.entitys.RtlFlwDbTbl;
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
import com.ruizhi.data.service.FldCltRstService;
import com.ruizhi.data.service.FlwInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.service.RtlFlwDbService;
import com.ruizhi.data.service.RtlFlwDbTblService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            flwInfo.setSrcNam(sb.substring(0,sb.length()-1));
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
        queryWrapper.lambda().eq(RtlFlwDb::getFlwId,id);
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
        Map<Integer,List<RtlFlwDbTbl>> resultMap =  rtlFlwDbTblList.stream().collect(
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
            value.stream().forEach(r->{
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
        queryWrapper.lambda().eq(FldCltRst::getTblId,id);
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
}
