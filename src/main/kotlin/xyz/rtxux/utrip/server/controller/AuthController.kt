package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.rtxux.utrip.server.model.dto.LoginDTO
import xyz.rtxux.utrip.server.model.dto.RegisterDTO
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.LoginVO
import xyz.rtxux.utrip.server.model.vo.RegisterVO

@RestController
@RequestMapping("/auth")
@Api(tags = arrayOf("认证"))
class AuthController @Autowired constructor(

) {
    @ApiOperation("登录")
    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ApiResponseVO<LoginVO>? = null

    @ApiOperation("注册")
    @PostMapping("/register")
    fun register(@RequestBody registerDTO: RegisterDTO): ApiResponseVO<RegisterVO>? = null

    @ApiOperation("注销")
    @PostMapping("/logout")
    fun logout(): ApiResponseVO<Unit>? = null
}