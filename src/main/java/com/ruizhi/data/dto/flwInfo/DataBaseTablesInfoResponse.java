package com.ruizhi.data.dto.flwInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/8/3
 */
@Data
public class DataBaseTablesInfoResponse extends AbstractResponse {

    /**
     * 数据库类型名称
     */
    private String DataBaseSchem;

    /**
     * 数据库表信息
     */
    private List<TreeDataBaseInfoDTO> treeDataBaseInfoDTOList;
}
