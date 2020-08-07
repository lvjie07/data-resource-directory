package com.ruizhi.data.dto.rtlFlwDBTblInfo;

import com.ruizhi.data.dal.entitys.FldCltRst;
import com.ruizhi.data.dal.entitys.RtlFlwDbTbl;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/8/5
 */
@Data
public class TableAndFieldDTO {

    /**
     * 表信息
     */
    private RtlFlwDbTbl rtlFlwDbTbl;

    /**
     * 字段信息
     */
    private List<FldCltRst> fldCltRstList;
}
