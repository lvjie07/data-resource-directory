package com.ruizhi.data.dto.cascadeSortInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/29
 */
@Data
public class CascadeSortInfoResponse extends AbstractResponse {

    /**
     * 分类管理树形数据
     */
    private List<TreeDTO> treeDTOList;
}
