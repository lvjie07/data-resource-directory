package com.ruizhi.data.dto.flwInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lvjie on 2020/8/3
 */
@Data
public class TreeDataBaseInfoDTO implements Serializable {

    /**
     * 数据库名字
     */
    private String dataBaseName;

    /**
     * 数据库中的表信息
     */
    private List<TableInfoDTO> tableInfoDTOList;
}
