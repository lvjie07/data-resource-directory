package com.ruizhi.data.dto.flwInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by lvjie on 2020/8/3
 */
@Data
public class DataBaseTablesInfoRequest extends AbstractRequest {

    /**
     * 记录ID
     */
    @NotNull(message = "ID不能为空")
    private Integer id;

    /**
     * 表名
     */
    private String tableName;

    @Override
    public void requestCheck() {

    }
}
