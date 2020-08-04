package com.ruizhi.data.dto.senLevInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by lvjie on 2020/7/28
 */
@Data
public class SaveUpdateSenLevInfoRequest extends AbstractRequest {

    @NotEmpty(message = "数据不能为空")
    @Valid
    private List<SenLevInfoDTO> senLevInfoDTOList;

    @Override
    public void requestCheck() {

    }
}
