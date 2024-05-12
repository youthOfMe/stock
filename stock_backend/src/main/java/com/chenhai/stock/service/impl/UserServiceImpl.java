package com.chenhai.stock.service.impl;

import com.chenhai.stock.mapper.SysUserMapper;
import com.chenhai.stock.pojo.entity.SysUser;
import com.chenhai.stock.service.UserService;
import com.chenhai.stock.vo.req.LoginReqVo;
import com.chenhai.stock.vo.res.LoginRespVo;
import com.chenhai.stock.vo.res.R;
import com.chenhai.stock.vo.res.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名称查询用户信息
     * @param userName 用户名称
     * @return 返回值
     */
    @Override
    public SysUser findByUserName(String userName) {
        return sysUserMapper.findUserInfoByUserName(userName);
    }

    /**
     * 用户登录功能
     * @param loginReqVo 登录请求VO
     * @return 返回值
     */
    @Override
    public R<LoginRespVo> login(LoginReqVo loginReqVo) {
        // 1. 判断参数是否合法
        if (loginReqVo == null || StringUtils.isBlank(loginReqVo.getUsername()) || StringUtils.isBlank(loginReqVo.getPassword()) || StringUtils.isBlank(loginReqVo.getCode())) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        // 验证验证码
        if (StringUtils.isBlank(loginReqVo.getCode()) || StringUtils.isBlank(loginReqVo.getSessionId())) {
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }
        // 判断redis中保存的验证码与输入的验证码是否相同(比较是忽略大小写)
        // String redisCode = (String) redisTemplate.opsForValue().get()

        return null;
    }
}
