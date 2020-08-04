package com.ruizhi.data.dto.flwInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lvjie on 2020/8/3
 */
@Data
public class TableInfoDTO implements Serializable {
    private static final long serialVersionUID = 930321269713424052L;

    /**
     * 表保存在采集表中的ID
     */
    private Integer tableId;

    /**
     * 表名
     */
    private String tableName;
}
