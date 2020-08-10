package com.ruizhi.data.dto.tblTypInfo;

import lombok.Data;

/**
 * 匹配正则表达式规则
 * Created by lvjie on 2020/8/10
 */
@Data
public class RuleFieldDTO {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 业务类型表主键
     */
    private Integer tblId;

    /**
     * 字段分级分类表主键
     */
    private Integer fldId;

    /**
     * 匹配表达式
     */
    private String prt;

    /**
     * 敏感分级
     */
    private String lev;

    /**
     * 分级名称
     */
    private String senLev;

}
