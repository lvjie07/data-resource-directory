package com.ruizhi.data.service;

import com.ruizhi.data.dal.entitys.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruizhi.data.dto.userInfo.UserLoginRequest;
import com.ruizhi.data.dto.userInfo.UserLoginResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-28
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户登录
     * @param request
     */
    UserInfo userLogin(UserLoginRequest request);
}
