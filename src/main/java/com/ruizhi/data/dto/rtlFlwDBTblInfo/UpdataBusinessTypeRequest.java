package com.ruizhi.data.dto.rtlFlwDBTblInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改业务类型
 * Created by lvjie on 2020/8/4
 */
@Data
public class UpdataBusinessTypeRequest extends AbstractRequest {

    /**
     * 采集表ID
     */
    @NotNull(message = "ID不能为空")
    private Integer id;

    /**
     * 一级业务类型
     */
    private String wrkFstType;

    /**
     * 二级业务类型
     */
    private String wrkSedType;

    /**
     * 三级业务类型
     */
    private String wrkThdType;

    /**
     * 四级业务类型
     */
    private String wrkForType;

    @Override
    public void requestCheck() {

    }
}
