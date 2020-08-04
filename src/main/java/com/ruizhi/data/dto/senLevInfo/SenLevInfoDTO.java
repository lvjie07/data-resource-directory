package com.ruizhi.data.dto.senLevInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lvjie on 2020/7/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SenLevInfoDTO implements Serializable {

    private static final long serialVersionUID = 422496313498957022L;
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 敏感分级
     */
    @NotNull(message = "敏感分级不能为空")
    private String lev;

    /**
     * 分级名称
     */
    @NotNull(message = "分级名称不能为空")
    private String senLev;
}
