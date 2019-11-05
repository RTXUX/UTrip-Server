package xyz.rtxux.utrip.server.model.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("登录数据")
data class LoginDTO(
        @ApiModelProperty("用户名", required = true)
        val username: String,
        @ApiModelProperty("密码", required = true)
        val password: String
)