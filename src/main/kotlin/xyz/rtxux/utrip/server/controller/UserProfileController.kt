package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.UserProfileVO

@RestController
@RequestMapping("/user/{id}")
@Api(tags = arrayOf("用户信息"))
class UserProfileController @Autowired constructor(

) {
    @GetMapping("/profile")
    @ApiOperation("获取指定用户信息")
    fun getUserProfile(@PathVariable("id") userId: Int?): ApiResponseVO<UserProfileVO>? = null
}