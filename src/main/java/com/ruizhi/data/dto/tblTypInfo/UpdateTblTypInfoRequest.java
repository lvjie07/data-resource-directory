package com.ruizhi.data.dto.tblTypInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by lvjie on 2020/7/30
 */
@Data
public class UpdateTblTypInfoRequest extends AbstractRequest {

    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空")
    private Integer id;

    /**
     * 一级业务类型
     */
    @NotBlank(message = "一级业务类型不能为空")
    private String tblBnsTypOne;

    /**
     * 二级业务类型
     */
    @NotBlank(message = "二级业务类型不能为空")
    private String tblBnsTypTwo;

    /**
     * 三级业务类型
     */
    @NotBlank(message = "三级业务类型不能为空")
    private String tblBnsTypThree;

    /**
     * 四级业务类型
     */
    private String tblBnsTypFour;

    @NotEmpty(message = "发现方式不能为空")
    @Valid
    List<Integer> fldIds;

    @Override
    public void requestCheck() {

    }
}
