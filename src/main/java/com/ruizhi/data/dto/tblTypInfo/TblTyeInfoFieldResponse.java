package com.ruizhi.data.dto.tblTypInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/30
 */
@Data
public class TblTyeInfoFieldResponse extends AbstractResponse {

    /**
     * 显示的字段
     */
    private List<FieldDTO> fieldDTOList;
}
