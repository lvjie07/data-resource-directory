package com.ruizhi.data.dto.flwInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.FldCltRst;
import com.ruizhi.data.dal.entitys.RtlFlwDbTbl;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/8/3
 */
@Data
public class TableAndFieldResponse extends AbstractResponse {

    /**
     * 表信息
     */
    private RtlFlwDbTbl rtlFlwDbTbl;

    /**
     * 字段信息
     */
    private List<FldCltRst> fldCltRstList;
}
