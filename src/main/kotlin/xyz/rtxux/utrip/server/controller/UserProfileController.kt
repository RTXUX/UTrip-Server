package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.UserProfileVO
import xyz.rtxux.utrip.server.repository.UserRepsitory
import xyz.rtxux.utrip.server.service.UserProfileService
import xyz.rtxux.utrip.server.service.impl.MyUserDetails

@RestController
@RequestMapping("/user/{id}")
@Api(tags = arrayOf("用户信息"))
class UserProfileController @Autowired constructor(
        private val userProfileService: UserProfileService,
        private val userRepository: UserRepsitory
) {
    @GetMapping("/profile")
    @ApiOperation("获取指定用户信息")
    fun getUserProfile(@PathVariable("id") userId: Int, @ApiIgnore @AuthenticationPrincipal userDetails: MyUserDetails): ApiResponseVO<UserProfileVO> {
        return ApiResponseVO(0, "Success", userProfileService.getUserProfileById(userId))
    }

    @GetMapping("/avatar")
    fun getUserAvatar(@PathVariable("id") userId: Int): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header("Location", userProfileService.getUserAvatarUrl(userId)).body(null)
    }

    @PostMapping("/avatar")
    fun setUserAvatar(@PathVariable("id") userId: Int, @RequestParam("avatarId") avatarId: Int, @AuthenticationPrincipal userDetails: MyUserDetails): ApiResponseVO<Unit> {
        if (userDetails.userId != userId) {
            throw AppException(403, 2005, "未经授权的操作")
        }
        return ApiResponseVO(0, "Success", Unit)
    }

    @PostMapping("/profile")
    fun updateUserProfile(@PathVariable("id") userId: Int, @AuthenticationPrincipal userDetails: MyUserDetails, @RequestBody updatedProfile: UserProfileVO): ApiResponseVO<UserProfileVO> {
        if (userDetails.userId != userId || updatedProfile.userId != userId) {
            throw AppException(403, 2005, "未经授权的操作")
        }
        return ApiResponseVO(0, "Success", userProfileService.updateUserProfile(userId, updatedProfile))
    }

}