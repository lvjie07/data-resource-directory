package com.ruizhi.data.dto.flwInfo;

import lombok.Data;

/**
 * 索引信息DTO
 * Created by lvjie on 2020/8/6
 */
@Data
public class IndexInfoDTO {

    /**
     * 索引列名称
     */
    private String columnName;

    /**
     * 是否唯一
     * 如果为真则说明索引值不唯一，为假则说明索引值必须唯一
     */
    private String nonUnique;
}
