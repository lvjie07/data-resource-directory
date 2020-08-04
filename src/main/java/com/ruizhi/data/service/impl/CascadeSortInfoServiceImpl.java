package com.ruizhi.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.dal.entitys.CascadeSortInfo;
import com.ruizhi.data.dal.mapper.CascadeSortInfoMapper;
import com.ruizhi.data.dto.cascadeSortInfo.CascadeSortInfoRequest;
import com.ruizhi.data.dto.cascadeSortInfo.SaveCascadeSortInfoRequest;
import com.ruizhi.data.dto.cascadeSortInfo.TreeDTO;
import com.ruizhi.data.service.CascadeSortInfoService;
import com.ruizhi.data.utils.ColaBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 级联分类管理 服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
@Service
public class CascadeSortInfoServiceImpl extends ServiceImpl<CascadeSortInfoMapper, CascadeSortInfo> implements CascadeSortInfoService {

    /**
     * 根节点
     */
    private List<TreeDTO> rootList;

    /**
     * 子节点
     */
    private List<TreeDTO> childrenList;

    @Override
    public List<TreeDTO> getTree(CascadeSortInfoRequest request) {
        // 查询根节点
        QueryWrapper<CascadeSortInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CascadeSortInfo::getParentId, -1);
        List<CascadeSortInfo> rootCascadeSortInfoList = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(rootCascadeSortInfoList)) {
            rootList = ColaBeanUtils.copyListProperties(rootCascadeSortInfoList, TreeDTO::new);
            // 查询所有子节点
            queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().ne(CascadeSortInfo::getParentId, -1);
            String levName = request.getLevName();
            if (StringUtils.isNotEmpty(levName)) {
                queryWrapper.lambda().like(CascadeSortInfo::getLevName, levName);
            }
            List<CascadeSortInfo> childCascadeSortInfoList = this.list(queryWrapper);
            // TODO 子节点存在是否需要查询改节点的父节点
            childrenList = ColaBeanUtils.copyListProperties(childCascadeSortInfoList, TreeDTO::new);
            //声明一个map，用来过滤已操作过的数据
            Map<Integer, Integer> map = new HashMap<>(childrenList.size());
            rootList.forEach(beanTree -> getChild(beanTree, map));
            return rootList;
        }
        return null;
    }

    /**
     * 封装子分类数据
     *
     * @param treeDto
     * @param map
     */
    private void getChild(TreeDTO treeDto, Map<Integer, Integer> map) {
        List<TreeDTO> childList = new ArrayList<>();
        childrenList.stream()
                .filter(c -> !map.containsKey(c.getId()))
                .filter(c -> c.getParentId().equals(treeDto.getId()))
                .forEach(c -> {
                    map.put(c.getId(), c.getParentId());
                    getChild(c, map);
                    childList.add(c);
                });
        treeDto.setChildTreeDto(childList);
    }

    @Override
    public List<TreeDTO> getChildByParentId(Integer id) {
        QueryWrapper<CascadeSortInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CascadeSortInfo::getParentId, id);
        List<CascadeSortInfo> cascadeSortInfoList = this.list(queryWrapper);
        List<TreeDTO> childList = ColaBeanUtils.copyListProperties(cascadeSortInfoList, TreeDTO::new);
        return childList;
    }

    @Override
    public boolean saveCascadeSortInfo(SaveCascadeSortInfoRequest request) {
        // 1.校验数据
        Integer parentId = request.getParentId();
        CascadeSortInfo cascadeSortInfo = this.getById(parentId);
        if (Objects.isNull(cascadeSortInfo)) {
            throw new BizException(ResultCode.DB_DATA_NOTEXIST.getCode(),"父节点数据不存在");
        }
        // 2.保存数据
        CascadeSortInfo cascadeSortInfo1 = new CascadeSortInfo();
        cascadeSortInfo1.setLevel(cascadeSortInfo.getLevel()+1);
        cascadeSortInfo1.setLevName(request.getLevName());
        cascadeSortInfo1.setParentId(cascadeSortInfo.getId());
        cascadeSortInfo1.setPath(cascadeSortInfo.getPath()+"/"+request.getLevName());
        return this.save(cascadeSortInfo1);
    }
}
