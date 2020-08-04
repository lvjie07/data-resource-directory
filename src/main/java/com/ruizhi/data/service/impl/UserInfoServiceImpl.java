package com.ruizhi.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.dal.entitys.UserInfo;
import com.ruizhi.data.dal.mapper.UserInfoMapper;
import com.ruizhi.data.dto.userInfo.UserLoginRequest;
import com.ruizhi.data.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lvjie
 * @since 2020-07-28
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public UserInfo userLogin(UserLoginRequest request) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUsername,request.getUserName());
        // 查询用户是否存在
        UserInfo userInfo = this.getOne(queryWrapper);
        if (Objects.isNull(userInfo)) {
            throw new BizException(ResultCode.USER_USERNAME_ERROR.getCode(),ResultCode.USER_USERNAME_ERROR.getMessage());
        }
        if (!userInfo.getPassword().equals(request.getPassword())) {
            throw new BizException(ResultCode.USER_USERNAME_ERROR.getCode(),ResultCode.USER_USERNAME_ERROR.getMessage());
        }
        return userInfo;
    }
}
