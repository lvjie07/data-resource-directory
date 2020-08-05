package com.ruizhi.data.dto.relResultInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 样例数据DTO
 * Created by lvjie on 2020/8/5
 */
@Data
public class SampleResultDataDTO implements Serializable {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 采集结果数据
     */
    private List<String> resultDataList;
}
