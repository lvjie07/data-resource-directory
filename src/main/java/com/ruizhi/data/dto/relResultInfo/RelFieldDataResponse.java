package com.ruizhi.data.dto.relResultInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.SampleResultData;
import lombok.Data;

/**
 * 关联表字段数据
 * Created by lvjie on 2020/8/5
 */
@Data
public class RelFieldDataResponse extends AbstractResponse {

    /**
     * 主表数据
     */
    private SampleResultDataDTO masterSampleResultData;

    /**
     * 关联表数据
     */
    private SampleResultDataDTO relSampleResultData;
}
