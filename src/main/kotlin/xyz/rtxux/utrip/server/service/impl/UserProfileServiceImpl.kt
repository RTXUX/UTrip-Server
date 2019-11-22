package xyz.rtxux.utrip.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.vo.UserProfileVO
import xyz.rtxux.utrip.server.repository.ImageRepository
import xyz.rtxux.utrip.server.repository.UserRepsitory
import xyz.rtxux.utrip.server.service.ImageService
import xyz.rtxux.utrip.server.service.UserProfileService
import java.time.Duration

@Service
class UserProfileServiceImpl @Autowired constructor(
        private val userRepsitory: UserRepsitory,
        private val imageRepository: ImageRepository,
        private val imageService: ImageService
) : UserProfileService {
    override fun getUserProfileById(userId: Int): UserProfileVO {
        return userRepsitory.findById(userId).orElseThrow { AppException(404, 1004, "用户不存在") }.toUserProfileVO().run {
            copy(
                    avatarUrl = if (avatarUrl != "") imageService.getImageAccessUrl(avatarUrl.toInt(), Duration.ofSeconds(10)) else "https://minervastrategies.com/wp-content/uploads/2016/03/default-avatar.jpg"
            )
        }
    }

    @Transactional
    override fun setUserAvatar(userId: Int, avatarId: Int): UserProfileVO {
        val user = userRepsitory.findById(userId).orElseThrow { AppException(500, 0, "服务器错误") }
        val image = imageRepository.findById(avatarId).orElseThrow { AppException(404, 4001, "头像未找到") }
        if (user.id!! != image.user!!.id!!) {
            throw AppException(400, 0, "未经授权的操作")
        }
        user.avatarUrl = avatarId.toString()
        return userRepsitory.save(user).toUserProfileVO().run {
            copy(
                    avatarUrl = if (avatarUrl != "") imageService.getImageAccessUrl(avatarUrl.toInt(), Duration.ofSeconds(10)) else "https://minervastrategies.com/wp-content/uploads/2016/03/default-avatar.jpg"
            )
        }
    }
}