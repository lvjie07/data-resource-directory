package com.ruizhi.data.dto.fldTypInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by lvjie on 2020/7/29
 */
@Data
public class UpdateRuleRequest extends AbstractRequest {

    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空")
    private Integer id;

    /**
     * 匹配表达式
     */
    @NotNull(message = "字段内容正则匹配不能为空")
    private String prt;

    /**
     * 匹配样本
     */
    private String istNam;

    @Override
    public void requestCheck() {

    }
}
