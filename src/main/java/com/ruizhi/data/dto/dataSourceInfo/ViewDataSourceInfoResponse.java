package com.ruizhi.data.dto.dataSourceInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.DataSourceInfo;
import lombok.Data;

/**
 * Created by lvjie on 2020/7/31
 */
@Data
public class ViewDataSourceInfoResponse extends AbstractResponse {

    private DataSourceInfo dataSourceInfo;
}
