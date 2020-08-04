package com.ruizhi.data.dto.senLevInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.dal.entitys.SenLevInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by lvjie on 2020/7/28
 */
@Data
public class SenLevInfoResponse extends AbstractResponse {

    private List<SenLevInfo> list;
}
