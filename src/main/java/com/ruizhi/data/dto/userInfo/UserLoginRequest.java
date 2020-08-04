package com.ruizhi.data.dto.userInfo;

import com.ruizhi.data.commons.result.AbstractRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by lvjie on 2020/7/28
 */
@Data
public class UserLoginRequest extends AbstractRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    @Override
    public void requestCheck() {

    }
}
