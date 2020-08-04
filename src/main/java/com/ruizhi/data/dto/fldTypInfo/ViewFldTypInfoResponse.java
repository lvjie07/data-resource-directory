package com.ruizhi.data.dto.fldTypInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.FldTypInfo;
import lombok.Data;

/**
 * Created by lvjie on 2020/7/30
 */
@Data
public class ViewFldTypInfoResponse extends AbstractResponse {

    /**
     * 字段分类设置
     */
    private FldTypInfo fldTypInfo;
}
