package com.ruizhi.data.dto.relResultInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.RelResult;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/8/4
 */
@Data
public class RelResultResponse extends AbstractResponse {

    /**
     * 数据
     */
    private List<RelResult> list;

    /**
     * 总条数
     */
    private long total;

    /**
     * 每页条数
     */
    private long pageCount;
}
