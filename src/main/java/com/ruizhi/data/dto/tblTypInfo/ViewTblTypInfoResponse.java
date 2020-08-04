package com.ruizhi.data.dto.tblTypInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.TblTypInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/30
 */
@Data
public class ViewTblTypInfoResponse extends AbstractResponse {

    /**
     * 业务类型数据
     */
    private TblTypInfo tblTypInfo;

    /**
     * 字段
     */
    private List<FieldDTO> fieldDTOList;
}
