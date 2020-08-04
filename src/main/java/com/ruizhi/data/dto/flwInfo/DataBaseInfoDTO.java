package com.ruizhi.data.dto.flwInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lvjie on 2020/7/31
 */
@Data
public class DataBaseInfoDTO implements Serializable {

    /**
     * 数据源ID
     */
    private Integer id;

    /**
     * 数据源名称
     */
    private String srcName;
}
