package com.ruizhi.data.dto.cascadeSortInfo;

import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/29
 */
@Data
public class TreeDTO {

    /**
     * ID
     */
    private Integer id;

    /**
     * 分级名称
     */
    private String levName;

    /**
     * 父ID
     */
    private Integer parentId;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 子集合对象
     */
    private List<TreeDTO> childTreeDto;
}
