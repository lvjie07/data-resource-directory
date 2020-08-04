package com.ruizhi.data.dto.cascadeSortInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by lvjie on 2020/7/30
 */
@Data
public class SaveCascadeSortInfoRequest extends AbstractRequest {


    /**
     * 父ID
     */
    @NotNull(message = "父ID不能为空")
    private Integer parentId;

    /**
     * 分级名称
     */
    @NotBlank(message = "分级名称不能为空")
    private String levName;

    @Override
    public void requestCheck() {

    }
}
