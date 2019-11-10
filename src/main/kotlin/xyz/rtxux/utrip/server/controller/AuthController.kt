package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import xyz.rtxux.utrip.server.model.dto.LoginDTO
import xyz.rtxux.utrip.server.model.dto.RegisterDTO
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.LoginVO
import xyz.rtxux.utrip.server.model.vo.RegisterVO
import xyz.rtxux.utrip.server.service.UserService
import xyz.rtxux.utrip.server.service.impl.MyUserDetails

@RestController
@RequestMapping("/auth")
@Api(tags = arrayOf("认证"))
class AuthController @Autowired constructor(
        private val userService: UserService
) {
    @ApiOperation("登录")
    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ApiResponseVO<LoginVO>? = null

    @ApiOperation("注册")
    @PostMapping("/register")
    @Transactional
    fun register(@RequestBody registerDTO: RegisterDTO): ApiResponseVO<RegisterVO> {
        return ApiResponseVO(0, "Success", userService.register(registerDTO))
    }

    @ApiOperation("注销")
    @PostMapping("/logout")
    fun logout(): ApiResponseVO<Unit>? = null

    @ApiOperation("验证登录有效性")
    @GetMapping("/validate")
    fun validate(@AuthenticationPrincipal user: MyUserDetails?): ApiResponseVO<LoginVO> {
        if (user == null) {
            return ApiResponseVO(1001, "Not authenticated")
        }
        return ApiResponseVO(0, "Success", LoginVO(user.userId, user.username))
    }
}