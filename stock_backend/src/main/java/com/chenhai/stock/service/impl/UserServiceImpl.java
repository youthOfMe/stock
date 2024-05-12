package com.chenhai.stock.service.impl;

import com.chenhai.stock.mapper.SysUserMapper;
import com.chenhai.stock.pojo.entity.SysUser;
import com.chenhai.stock.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名称查询用户信息
     * @param userName 用户名称
     * @return 返回值
     */
    @Override
    public SysUser findByUserName(String userName) {
        return sysUserMapper.findUserInfoByUserName(userName);
    }
}
