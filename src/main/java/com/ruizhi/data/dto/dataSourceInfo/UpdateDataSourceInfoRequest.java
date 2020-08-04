package com.ruizhi.data.dto.dataSourceInfo;

import com.ruizhi.data.commons.exception.ValidateException;
import com.ruizhi.data.commons.result.AbstractRequest;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.utils.DBUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by lvjie on 2020/7/27
 */
@Data
public class UpdateDataSourceInfoRequest extends AbstractRequest {

    /**
     * ID不能为空
     */
    @NotNull(message = "数据源名称不能为空")
    private Integer id;

    /**
     * 数据源名称
     */
    @NotNull(message = "数据源名称不能为空")
    private String srcName;

    /**
     * 数据源分类
     */
    @NotNull(message = "数据源分类不能为空")
    private String srcCtl;

    /**
     * 数据库子类型
     */
    @NotNull(message = "数据库子类型不能为空")
    private String srcTyp;

    /**
     * IP地址
     */
    @NotNull(message = "IP地址不能为空")
    private String ipAdr;

    /**
     * 端口
     */
    @NotNull(message = "端口不能为空")
    private String prt;

    /**
     * 实例名
     */
    @NotNull(message = "实例名不能为空")
    private String istNam;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String usr;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String pwd;

    @Override
    public void requestCheck() {
        DBUtil dbUtil = new DBUtil(srcTyp,ipAdr,prt,usr,pwd,istNam);
        try {
            dbUtil.getConnection();
        } catch (Exception e) {
            throw new ValidateException(ResultCode.DB_EXCEPTION.getCode(),ResultCode.DB_EXCEPTION.getMessage());
        }
    }
}
