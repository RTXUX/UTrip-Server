package xyz.rtxux.utrip.server.model.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "注册数据")
data class RegisterDTO(
        @ApiModelProperty("用户名", required = true)
        val username: String,
        @ApiModelProperty("密码", required = true)
        val password: String,
        @ApiModelProperty(value = "昵称", required = false)
        val nickname: String? = null
)