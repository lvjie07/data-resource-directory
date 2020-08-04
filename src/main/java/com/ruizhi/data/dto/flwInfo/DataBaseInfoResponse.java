package com.ruizhi.data.dto.flwInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/31
 */
@Data
public class DataBaseInfoResponse extends AbstractResponse {

    private List<DataBaseInfoDTO> dataBaseInfoDTOList;
}
