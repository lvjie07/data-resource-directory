package com.ruizhi.data.dto.rtlFlwDBTblInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by lvjie on 2020/8/3
 */
@Data
public class UpdataTableInfoRequest extends AbstractRequest {

    /**
     * 采集表ID
     */
    @NotNull(message = "ID不能为空")
    private Integer id;

    /**
     * 注释
     */
    @NotBlank(message = "注释不能为空")
    private String tblCmt;

    @Override
    public void requestCheck() {

    }
}
