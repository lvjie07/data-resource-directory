package com.ruizhi.data.dto.flwInfo;

import lombok.Data;

/**
 * 表外键信息DTO
 * Created by lvjie on 2020/8/6
 */
@Data
public class ForeignKeyDTO {

    /**
     * 外键列名称
     */
    private String fkColumnName;

    /**
     * 外键关联表名
     */
    private String fkTableName;

    /**
     * 外键关联表列名称
     */
    private String pkColumnName;

    /**
     * 外键位置
     */
    private Integer index;

    /**
     * 外键关联表名注释
     */
    private String fkTableNameComment;
}
