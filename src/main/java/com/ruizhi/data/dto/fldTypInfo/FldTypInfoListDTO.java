package com.ruizhi.data.dto.fldTypInfo;

import com.ruizhi.data.dal.entitys.FldTypInfo;
import lombok.Data;

/**
 * Created by lvjie on 2020/7/29
 */
@Data
public class FldTypInfoListDTO extends FldTypInfo {

    /**
     * 敏感分级
     */
    private String lev;

    /**
     * 分级名称
     */
    private String senLev;
}
