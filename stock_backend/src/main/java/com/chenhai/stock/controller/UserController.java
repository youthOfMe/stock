package com.chenhai.stock.controller;

import com.chenhai.stock.pojo.entity.SysUser;
import com.chenhai.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定义用户处理器接口
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名称查询用户信息
     * @param userName 用户名称
     * @return 返回值
     */
    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable String userName) {
        return userService.findByUserName(userName);
    }

}
