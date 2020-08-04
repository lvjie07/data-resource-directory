package com.ruizhi.data.dto.flwInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by lvjie on 2020/7/31
 */
@Data
public class FlwInfoRequest extends AbstractRequest {

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
     * 作业状态
     */
    private String taskStatus;

    @Override
    public void requestCheck() {

    }
}
