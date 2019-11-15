package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.dto.ImageUploadCallbackDTO
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.ImagePreUploadVO
import xyz.rtxux.utrip.server.repository.UserRepsitory
import xyz.rtxux.utrip.server.service.ImageService
import xyz.rtxux.utrip.server.service.impl.MyUserDetails
import java.time.Duration

@RestController
@RequestMapping("/image")
@Api(tags = arrayOf("图片模块"))
class ImageController @Autowired constructor(
        private val userRepsitory: UserRepsitory,
        private val imageService: ImageService
) {

    @PostMapping("/postupload")
    @ApiIgnore
    fun postUploadImage(@RequestBody imageUploadCallbackDTO: ImageUploadCallbackDTO): ApiResponseVO<Nothing> {
        imageService.qiniuPostUpload(imageUploadCallbackDTO)
        return ApiResponseVO(0, "Success")
    }

    @PostMapping("/upload")
    @ApiOperation("图片预上传")
    fun preUploadImage(@ApiIgnore @AuthenticationPrincipal userDetails: MyUserDetails): ApiResponseVO<ImagePreUploadVO> {
        val user = userRepsitory.findById(userDetails.userId).orElseThrow { AppException(401, 1002, "用户不存在") }
        return ApiResponseVO(0, "Success", imageService.preUploadImage(user, Duration.ofSeconds(300)))
    }

    @GetMapping("/{id}")
    @ApiOperation("获取图片")
    @ApiResponses(
            ApiResponse(code = 307, message = "重定向", responseHeaders = arrayOf(
                    ResponseHeader(name = "Location", description = "真实图片地址", response = String::class)
            ))
    )
    @ResponseStatus(code = HttpStatus.TEMPORARY_REDIRECT)
    fun getImage(@ApiParam("图片ID", example = "1", required = true) @PathVariable("id") imageId: Int): ResponseEntity<*> {
        val url = imageService.getImageAccessUrl(imageId, Duration.ofSeconds(30))
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header("Location", url).body(null)
    }

}