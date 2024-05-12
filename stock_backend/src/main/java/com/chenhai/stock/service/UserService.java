package com.chenhai.stock.service;

import com.chenhai.stock.pojo.entity.SysUser;
import com.chenhai.stock.vo.req.LoginReqVo;
import com.chenhai.stock.vo.res.LoginRespVo;
import com.chenhai.stock.vo.res.R;

import java.util.Map;

public interface UserService {

    /**
     * 根据用户名称查询用户信息
     * @param userName 用户名称
     * @return 返回值
     */
    SysUser findByUserName(String userName);

    /**
     * 用户登录
     * @param loginReqVo 登录请求VO
     * @return 返回值
     */
    R<LoginRespVo> login(LoginReqVo loginReqVo);

    /**
     * 获取验证码功能
     * @return 返回值
     */
    R<Map> getCaptchaCode();
}
