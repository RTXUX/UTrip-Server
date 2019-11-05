package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.ImagePreUploadVO

@RestController
@RequestMapping("/image")
@Api(tags = arrayOf("图片模块"))
class ImageController @Autowired constructor(

) {

    @PostMapping("/upload")
    @ApiOperation("图片预上传")
    fun preUploadImage(): ApiResponseVO<ImagePreUploadVO>? = null

    @GetMapping("{id}")
    @ApiOperation("获取图片")
    @ApiResponses(
            ApiResponse(code = 307, message = "重定向", responseHeaders = arrayOf(
                    ResponseHeader(name = "Location", description = "真实图片地址", response = String::class)
            ))
    )
    @ResponseStatus(code = HttpStatus.TEMPORARY_REDIRECT)
    fun getImage(@ApiParam("图片ID", example = "1", required = true) @PathVariable("id") imageId: Int): ResponseEntity<*>? = null

}