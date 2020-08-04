package com.ruizhi.data.dto.catalogTypeInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.FlwInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/8/3
 */
@Data
public class CatalogTypeResponse extends AbstractResponse {

    /**
     * 数据
     */
    private List<FlwInfo> list;

    /**
     * 总条数
     */
    private long total;

    /**
     * 每页条数
     */
    private long pageCount;

}
