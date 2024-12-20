package com.chiiiplow.clouddrive.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddrive.entity.User;
import com.chiiiplow.clouddrive.mapper.UserMapper;
import com.chiiiplow.clouddrive.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户业务实现
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Resource
    private UserMapper userMapper;


}
