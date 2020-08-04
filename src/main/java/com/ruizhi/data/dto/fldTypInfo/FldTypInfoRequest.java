package com.ruizhi.data.dto.fldTypInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by lvjie on 2020/7/29
 */
@Data
public class FldTypInfoRequest extends AbstractRequest {

    /**
     * 敏感等级ID
     */
    private Integer senId;

    /**
     * 业务类型
     */
    private String businessType;

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

    @Override
    public void requestCheck() {

    }
}
