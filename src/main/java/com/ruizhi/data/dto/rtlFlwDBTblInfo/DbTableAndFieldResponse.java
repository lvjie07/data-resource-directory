package com.ruizhi.data.dto.rtlFlwDBTblInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/8/7
 */
@Data
public class DbTableAndFieldResponse extends AbstractResponse {

    /**
     * 数据库表和字段信息
     */
    private List<TableAndFieldDTO> tableAndFieldDTOList;
}
