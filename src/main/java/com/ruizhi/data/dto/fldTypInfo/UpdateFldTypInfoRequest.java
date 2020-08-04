package com.ruizhi.data.dto.fldTypInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by lvjie on 2020/7/30
 */
@Data
public class UpdateFldTypInfoRequest extends AbstractRequest {

    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空")
    private Integer id;

    /**
     * 一级业务类型ID
     */
    @NotNull(message = "一级业务类型ID不能为空")
    private Integer firstBnsTypeId;

    /**
     * 一级业务类型名称
     */
    @NotBlank(message = "一级业务类型名称不能为空")
    private String firstBnsTypeName;

    /**
     * 二级业务类型ID
     */
    @NotNull(message = "二级业务类型ID不能为空")
    private Integer secondBnsTypeId;

    /**
     * 二级业务类型名称
     */
    @NotBlank(message = "二级业务类型名称不能为空")
    private String secondBnsTypeName;

    /**
     * 三级业务类型ID
     */
    private Integer threeBnsTypeId;

    /**
     * 三级业务类型名称
     */
    private String threeBnsTypeName;

    /**
     * 敏感等级
     */
    @NotNull(message = "敏感等级ID不能为空")
    private Integer senId;

    @Override
    public void requestCheck() {

    }
}
