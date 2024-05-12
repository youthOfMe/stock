package com.chenhai.stock.service;

import com.chenhai.stock.pojo.entity.SysUser;

public interface UserService {

    /**
     * 根据用户名称查询用户信息
     * @param userName 用户名称
     * @return 返回值
     */
    SysUser findByUserName(String userName);
}
