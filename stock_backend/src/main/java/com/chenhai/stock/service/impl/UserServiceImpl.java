package com.chenhai.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.chenhai.stock.content.StockConstant;
import com.chenhai.stock.mapper.SysUserMapper;
import com.chenhai.stock.pojo.entity.SysUser;
import com.chenhai.stock.service.UserService;
import com.chenhai.stock.utils.IdWorker;
import com.chenhai.stock.vo.req.LoginReqVo;
import com.chenhai.stock.vo.res.LoginRespVo;
import com.chenhai.stock.vo.res.R;
import com.chenhai.stock.vo.res.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

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

    /**
     * 获取验证码功能
     * @return 返回值
     */
    @Override
    public R<Map> getCaptchaCode() {
        // 1. 生成图片验证码
        /**
         * 参数1: 图片的宽度
         * 参数2: 图片高度
         * 参数3: 图片中包含验证码的长度
         * 参数4: 干扰线的数量
         */
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        // 设置背景颜色
        captcha.setBackground(Color.LIGHT_GRAY);
        // 自定义生成校验码的规则
        // captcha.setGenerator(new CodeGenerator() {
        //     @Override
        //     public String generate() {
        //         return null;
        //     }
        //
        //     @Override
        //     public boolean verify(String s, String s1) {
        //         return false;
        //     }
        // });

        // 获取校验码
        String checkCode = captcha.getCode();
        // 获取经过base64编码处理的图片资源
        String imageData = captcha.getImageBase64();
        // 2. 生成sessionId 转化成string 避免前端精度丢失
        String sessionId = String.valueOf(idWorker.nextId());
        log.info("当前生成的图片校验码: {}, 会话ID: {}", checkCode, sessionId);
        // 3. 将sessionId作为key, 校验码作为value保存在redis中
        /**
         * 使用redis模拟session的行为, 通过过期时间的设置
         */
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX + sessionId, checkCode, 5, TimeUnit.MINUTES);
        // 4. 组装数据
        Map<String, String> data = new HashMap<>();
        data.put("imageData", imageData);
        data.put("sessionId", sessionId);

        // 5. 响应数据
        return R.ok(data);
    }
}
