package com.ruizhi.data.dto.flwInfo;

import com.ruizhi.data.commons.result.AbstractRequest;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by lvjie on 2020/7/31
 */
@Data
public class SaveFlwInfoRequest extends AbstractRequest {

    /**
     * 作业名
     */
    @NotBlank(message = "作业名称不能为空")
    private String flwNam;

    /**
     * 数据库类型
     */
    @NotBlank(message = "数据库类型不能为空")
    private String srcTyp;

    /**
     * 抽样行数
     */
    @NotBlank(message = "抽样行数不能为空")
    private String smpNum;

    /**
     * 定时种类
     */
    @NotBlank(message = "定时种类不能为空")
    private String tmgTyp;

    /**
     * 执行频率
     */
    @Min(value = 1, message = "最小值为1")
    private Integer extFqy;

    /**
     * 启动时间
     */
    @NotNull(message = "抽样行数不能为空")
    private Date strTme;

    /**
     * 数据源信息
     */
    @NotEmpty(message = "数据源信息不能为空")
    @Valid
    private List<DataBaseInfoDTO> dataBaseInfoDTOList;


    @Override
    public void requestCheck() {

    }
}
