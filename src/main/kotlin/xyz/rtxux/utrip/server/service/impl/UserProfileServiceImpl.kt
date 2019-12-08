package xyz.rtxux.utrip.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.po.UserProfile
import xyz.rtxux.utrip.server.model.vo.UserProfileVO
import xyz.rtxux.utrip.server.repository.ImageRepository
import xyz.rtxux.utrip.server.repository.UserRepsitory
import xyz.rtxux.utrip.server.service.ImageService
import xyz.rtxux.utrip.server.service.UserProfileService

@Service
class UserProfileServiceImpl @Autowired constructor(
        private val userRepository: UserRepsitory,
        private val imageRepository: ImageRepository,
        private val imageService: ImageService
) : UserProfileService {
    @Transactional(readOnly = true)
    override fun getUserProfileById(userId: Int): UserProfileVO {
        val user = userRepository.findById(userId).orElseThrow{ AppException(404, 1004, "用户未找到") }
        val clonedUserProfile = user.userProfile?.copy()?: UserProfile(nickname = user.username!!)
        return UserProfileVO.fromUserProfile(clonedUserProfile, user.username!!, user.id!!)
    }

    @Transactional
    override fun setUserAvatar(userId: Int, avatarId: Int) {
        val user = userRepository.findById(userId).orElseThrow { AppException(500, 0, "服务器错误") }
        val image = imageRepository.findById(avatarId).orElseThrow { AppException(404, 4001, "头像未找到") }
        if (user.id!! != image.user!!.id!!) {
            throw AppException(400, 0, "未经授权的操作")
        }
        user.avatarUrl = avatarId.toString()
        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    override fun getUserAvatarUrl(userId: Int): String {
        val user = userRepository.findById(userId).orElseThrow { AppException(404, 1004, "未找到用户") }
        if (user.avatarUrl!=null) {
            return "/image/${user.avatarUrl}"
        }
        return "https://minervastrategies.com/wp-content/uploads/2016/03/default-avatar.jpg"
    }

    @Transactional
    override fun updateUserProfile(userId: Int, updatedProfile: UserProfileVO): UserProfileVO {
        val user = userRepository.findById(userId).orElseThrow { AppException(500, 1004, "内部错误") }
        val userProfile = updatedProfile.toUserProfile()
        user.userProfile = userProfile
        userRepository.save(user)
        return UserProfileVO.fromUserProfile(userProfile, user.username!!, user.id!!)
    }
}