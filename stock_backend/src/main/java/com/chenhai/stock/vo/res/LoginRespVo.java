package com.chenhai.stock.vo.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by niuma
 * @Date 2021/12/24
 * @Description 登录后响应前端的vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @ApiModel("登录响应数据封装")
public class LoginRespVo {
    /**
     * 用户ID
     * 将Long类型数字进行json格式转化时，转成String格式类型
     */
    // @ApiModelProperty("主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 电话
     */
    // @ApiModelProperty("手机号")
    private String phone;
    /**
     * 用户名
     */
    // @ApiModelProperty("用户名")
    private String username;
    /**
     * 昵称
     */
    // @ApiModelProperty("昵称")
    private String nickName;

}