package xyz.rtxux.utrip.server.service.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.qiniu.util.Auth
import com.qiniu.util.StringMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.utrip.server.config.QiniuConfig
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.dto.ImageUploadCallbackDTO
import xyz.rtxux.utrip.server.model.po.Image
import xyz.rtxux.utrip.server.model.po.User
import xyz.rtxux.utrip.server.model.vo.ImagePreUploadVO
import xyz.rtxux.utrip.server.repository.ImageRepository
import xyz.rtxux.utrip.server.service.ImageService
import java.net.URLEncoder
import java.time.Duration
import java.time.Instant

@Service
class ImageServiceImpl @Autowired constructor(
        private val imageRepository: ImageRepository,
        private val qiniuAuth: Auth,
        private val qiniuConfig: QiniuConfig
) : ImageService {

    companion object UploadUtils {
        const val CALLBACK_URL = "http://api.utrip.rtxux.xyz:4399/image/postupload"

        fun randomKey(length: Int): String {
            val chars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
            return (1..length).map {
                chars.random()
            }.joinToString("")
        }

        fun createUploadPolicy(id: Int, key: String, bucket: String): StringMap = StringMap().apply {
            put("scope", "${bucket}:${key}")
            put("callbackUrl", CALLBACK_URL)
            put("callbackBody", jacksonObjectMapper().writeValueAsString(ImageUploadCallbackDTO(
                    name = "$(fname)",
                    hash = "$(etag)",
                    id = id
            )))
            put("callbackBodyType", "application/json")
            put("saveKey", key)
            put("forceSaveKey", "true")
        }
    }

    @Transactional
    override fun qiniuPostUpload(imageUploadCallbackDTO: ImageUploadCallbackDTO) {
        var image = imageRepository.findById(imageUploadCallbackDTO.id).orElseThrow { AppException(404, 3001, "图片不存在") }
        image.finished = true
        image.timestamp = Instant.now()
        image = imageRepository.save(image)
    }

    @Transactional
    override fun preUploadImage(user: User, expiration: Duration): ImagePreUploadVO {
        val key = randomKey(20)
        val image = imageRepository.save(Image(
                like = 0,
                imageUrl = key,
                finished = false,
                timestamp = Instant.now(),
                user = user
        ))
        val uploadPolicy = createUploadPolicy(image.id!!, key, qiniuConfig.bucket)
        val token = qiniuAuth.uploadToken(qiniuConfig.bucket, key, expiration.toSeconds(), uploadPolicy)
        return ImagePreUploadVO(
                id = image.id!!,
                key = key,
                policy = "",
                url = "",
                signature = token
        )
    }

    override fun getImageAccessUrl(id: Int, expiration: Duration): String {
        val image = imageRepository.findById(id).orElseThrow { AppException(404, 3001, "图片不存在") }
        val url = "http://${qiniuConfig.domain}/${URLEncoder.encode(image.imageUrl, Charsets.UTF_8).replace("+", "%20")}"
        return qiniuAuth.privateDownloadUrl(url, expiration.toSeconds())
    }
}