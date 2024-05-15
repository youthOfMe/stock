package com.chenhai.stock.vo.req;

import lombok.Data;

/**
 * @author by niuma
 * @Date 2021/12/30
 * @Description 登录请求vo
 */
@Data
// @ApiModel
public class LoginReqVo {
    /**
     * 用户名
     */
    // @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    // @ApiModelProperty(value = "明文密码")
    private String password;
    /**
     * 验证码
     */
    // @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 会话ID
     */
    // @ApiModelProperty(value = "会话ID")
    private String sessionId;
}