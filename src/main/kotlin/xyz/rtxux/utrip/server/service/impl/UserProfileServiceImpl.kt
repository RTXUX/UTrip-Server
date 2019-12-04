package xyz.rtxux.utrip.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.util.UriComponentsBuilder
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.po.UserProfileEntry
import xyz.rtxux.utrip.server.model.po.UserProfileWrapper
import xyz.rtxux.utrip.server.model.vo.UserProfileVO
import xyz.rtxux.utrip.server.repository.ImageRepository
import xyz.rtxux.utrip.server.repository.UserRepsitory
import xyz.rtxux.utrip.server.service.ImageService
import xyz.rtxux.utrip.server.service.UserProfileService
import java.net.URI
import javax.servlet.http.HttpServletRequest

@Service
class UserProfileServiceImpl @Autowired constructor(
        private val userRepository: UserRepsitory,
        private val imageRepository: ImageRepository,
        private val imageService: ImageService
) : UserProfileService {
    @Transactional(readOnly = true)
    override fun getUserProfileById(userId: Int, filter: Boolean): Map<String, UserProfileEntry> {
        val user = userRepository.findById(userId).orElseThrow{ AppException(404, 1004, "用户未找到") }
        val clonedUserProfile = mutableMapOf<String, UserProfileEntry>()
        (user.userProfile?.userProfile?: mapOf<String, UserProfileEntry>()).forEach { k, v -> clonedUserProfile[k] = v.copy() }
        if (filter) {
            clonedUserProfile.forEach { key, value ->
                if (value.hidden) {
                    value.value = null
                }
            }
        }
        return clonedUserProfile
    }

    @Transactional
    override fun setUserAvatar(userId: Int, avatarId: Int, httpServletRequest: HttpServletRequest): UserProfileVO {
        val uri = URI(httpServletRequest.requestURL.toString()).run {
            URI(scheme, authority, httpServletRequest.contextPath, null, null)
        }
        val user = userRepository.findById(userId).orElseThrow { AppException(500, 0, "服务器错误") }
        val image = imageRepository.findById(avatarId).orElseThrow { AppException(404, 4001, "头像未找到") }
        if (user.id!! != image.user!!.id!!) {
            throw AppException(400, 0, "未经授权的操作")
        }
        user.avatarUrl = avatarId.toString()
        return userRepository.save(user).toUserProfileVO().run {
            copy(
                    avatarUrl = if (avatarUrl != "") UriComponentsBuilder.fromUri(uri).path("/image/{id}").buildAndExpand(avatarUrl).toUriString() else "https://minervastrategies.com/wp-content/uploads/2016/03/default-avatar.jpg"
            )
        }
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
    override fun updateUserProfile(userId: Int, updatedProfile: Map<String, UserProfileEntry>): Map<String, UserProfileEntry> {
        val user = userRepository.findById(userId).orElseThrow { AppException(500, 1004, "内部错误") }
        val userProfile = (user.userProfile?: UserProfileWrapper(mutableMapOf())).userProfile
        updatedProfile.filter {
            it.value.key==it.key
        }.forEach { k, v ->
            if (v.value!=null) {
                userProfile.merge(k, v) { _, new -> new}
            } else {
                userProfile.remove(k)
            }
        }
        user.userProfile = UserProfileWrapper(userProfile)
        userRepository.save(user)
        return userProfile
    }
}