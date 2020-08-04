package com.ruizhi.data.dto.relResultInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by lvjie on 2020/8/4
 */
@Data
public class RelResultRequest extends AbstractRequest {


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
     * 作业ID
     */
    @NotNull(message = "ID不能为空")
    private Integer id;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 确认状态
     */
    private String cfmType;

    @Override
    public void requestCheck() {

    }
}
