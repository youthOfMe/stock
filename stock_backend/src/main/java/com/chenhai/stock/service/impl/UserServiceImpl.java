package com.chenhai.stock.service.impl;

import com.chenhai.stock.content.StockConstant;
import com.chenhai.stock.mapper.SysUserMapper;
import com.chenhai.stock.pojo.entity.SysUser;
import com.chenhai.stock.service.UserService;
import com.chenhai.stock.vo.req.LoginReqVo;
import com.chenhai.stock.vo.res.LoginRespVo;
import com.chenhai.stock.vo.res.R;
import com.chenhai.stock.vo.res.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        String redisCode = (String) redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + loginReqVo.getSessionId());
        if (StringUtils.isBlank(redisCode)) {
            // 验证码过期
            return R.error(ResponseCode.CHECK_CODE_TIMEOUT);
        }
        if (!redisCode.equalsIgnoreCase(loginReqVo.getCode())) {
            // 验证码错误
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }

        // 2. 根据用户名去数据库查询用户信息, 获取密码的明文
        SysUser dbUser = sysUserMapper.findUserInfoByUserName(loginReqVo.getUsername());
        if (dbUser == null) {
            // 用户不存在
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }

        // 3. 调用密码匹配器匹配输入的明文密码和数据库的密文密码
        if (!passwordEncoder.matches(loginReqVo.getPassword(), dbUser.getPassword())) {
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }

        // 4. 响应
        LoginRespVo respVo = new LoginRespVo();
        BeanUtils.copyProperties(dbUser, respVo);

        return R.ok(respVo);
    }
}
