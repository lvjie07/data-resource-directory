package com.ruizhi.data.dto.cascadeSortInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

/**
 * Created by lvjie on 2020/7/29
 */
@Data
public class CascadeSortInfoRequest extends AbstractRequest {

    /**
     * 分级名称
     */
    private String levName;

    @Override
    public void requestCheck() {

    }
}
