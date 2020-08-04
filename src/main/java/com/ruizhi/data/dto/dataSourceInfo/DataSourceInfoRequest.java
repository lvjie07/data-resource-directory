package com.ruizhi.data.dto.dataSourceInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 数据源信息配置列表
 * Created by lvjie on 2020/7/27
 */
@Data
public class DataSourceInfoRequest extends AbstractRequest {

    /**
     * 页数
     */
    @Min(value = 1, message = "页数最少从第1页开始")
    private int pageNum;

    /**
     * 每页条数
     */
    @Min(value = 1, message = "每页最少显示1条数据")
    private int pageCount;

    /**
     * 数据库类型
     */
    private String srcTyp;

    /**
     * 数据源名称
     */
    private String srcName;

    @Override
    public void requestCheck() {

    }
}
