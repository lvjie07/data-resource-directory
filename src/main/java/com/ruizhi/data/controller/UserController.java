package com.ruizhi.data.controller;

import com.alibaba.fastjson.JSON;
import com.ruizhi.data.annotation.JwtIgnore;
import com.ruizhi.data.commons.exception.ExceptionProcessorUtils;
import com.ruizhi.data.commons.result.ResponseUtils;
import com.ruizhi.data.config.jwt.Audience;
import com.ruizhi.data.dal.entitys.UserInfo;
import com.ruizhi.data.dto.userInfo.UserLoginRequest;
import com.ruizhi.data.dto.userInfo.UserLoginResponse;
import com.ruizhi.data.service.UserInfoService;
import com.ruizhi.data.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by lvjie on 2020/7/28
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Audience audience;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户登录
     *
     * @param request
     * @return
     */
    @JwtIgnore
    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody @Valid UserLoginRequest request) {
        log.info("UserController.login,入参为：{}", JSON.toJSONString(request));
        UserLoginResponse response = new UserLoginResponse();
        ResponseUtils.resultSuccessResponse(response);
        try {
            UserInfo userInfo = userInfoService.userLogin(request);
            // 创建token
            String token = JwtTokenUtil.createJWT(userInfo.getId().toString(), request.getUserName(), audience);
            log.info("### 登录成功, token={} ###", token);
            response.setToken(token);
            response.setUserName(userInfo.getUsername());
        } catch (Exception e) {
            log.error("UserController.login Exception: {}", e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}
