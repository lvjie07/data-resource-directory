package com.ruizhi.data.dto.tblTypInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.TblTypInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/30
 */
@Data
public class TblTypInfoResponse extends AbstractResponse {

    /**
     * 数据
     */
    private List<TblTypInfo> list;

    /**
     * 总条数
     */
    private long total;

    /**
     * 每页条数
     */
    private long pageCount;
}
