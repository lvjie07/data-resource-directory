package com.ruizhi.data.dto.dataSourceInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.DataSourceInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/27
 */
@Data
public class DataSourceInfoResponse extends AbstractResponse{

    /**
     * 数据
     */
    private List<DataSourceInfo> list;

    /**
     * 总条数
     */
    private long total;

    /**
     * 每页条数
     */
    private long pageCount;
}
