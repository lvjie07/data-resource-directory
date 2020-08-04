package com.ruizhi.data.dto.userInfo;

import com.ruizhi.data.commons.result.AbstractResponse;
import lombok.Data;

/**
 * Created by lvjie on 2020/7/28
 */
@Data
public class UserLoginResponse extends AbstractResponse {

    /**
     * 返回jwt生成的token
     */
    private String token;

    /**
     * 用户名
     */
    private String userName;
}
