package xyz.rtxux.utrip.server.service

import xyz.rtxux.utrip.server.model.dto.ImageUploadCallbackDTO
import xyz.rtxux.utrip.server.model.po.User
import xyz.rtxux.utrip.server.model.vo.ImagePreUploadVO
import java.time.Duration

interface ImageService {

    fun preUploadImage(user: User, expiration: Duration): ImagePreUploadVO

    fun getImageAccessUrl(id: Int, expiration: Duration): String

    fun qiniuPostUpload(imageUploadCallbackDTO: ImageUploadCallbackDTO)
}