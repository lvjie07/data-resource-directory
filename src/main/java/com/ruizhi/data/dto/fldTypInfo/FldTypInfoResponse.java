package com.ruizhi.data.dto.fldTypInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/29
 */
@Data
public class FldTypInfoResponse extends AbstractResponse {

    /**
     * 数据
     */
    private List<FldTypInfoListDTO> list;

    /**
     * 总条数
     */
    private long total;

    /**
     * 每页条数
     */
    private long pageCount;
}
