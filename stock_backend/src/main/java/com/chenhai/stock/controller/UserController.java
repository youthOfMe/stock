package com.chenhai.stock.controller;

import com.chenhai.stock.pojo.entity.SysUser;
import com.chenhai.stock.service.UserService;
import com.chenhai.stock.vo.req.LoginReqVo;
import com.chenhai.stock.vo.res.LoginRespVo;
import com.chenhai.stock.vo.res.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户登录功能
     * @param loginReqVo 登录请求对象
     * @return 返回值
     */
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo loginReqVo) {
        return userService.login(loginReqVo);
    }

}
