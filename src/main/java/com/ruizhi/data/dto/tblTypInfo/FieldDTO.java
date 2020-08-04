package com.ruizhi.data.dto.tblTypInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lvjie on 2020/7/30
 */
@Data
public class FieldDTO implements Serializable {
    private static final long serialVersionUID = -6092790395507814186L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 显示的字段名称
     */
    private String fieldName;
}
