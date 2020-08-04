package com.ruizhi.data.dto.catalogTypeInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by lvjie on 2020/8/3
 */
@Data
public class CatalogTypeRequest extends AbstractRequest {

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
     * 作业名
     */
    private String flwName;

    /**
     * 分级分类状态
     */
    private String classifyStatus;

    @Override
    public void requestCheck() {

    }
}
