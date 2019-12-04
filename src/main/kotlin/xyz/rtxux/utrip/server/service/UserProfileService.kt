package xyz.rtxux.utrip.server.service

import xyz.rtxux.utrip.server.model.po.UserProfileEntry
import xyz.rtxux.utrip.server.model.vo.UserProfileVO
import javax.servlet.http.HttpServletRequest

interface UserProfileService {
    fun getUserProfileById(userId: Int, filter: Boolean): Map<String, UserProfileEntry>

    fun setUserAvatar(userId: Int, avatarId: Int, httpServletRequest: HttpServletRequest): UserProfileVO

    fun getUserAvatarUrl(userId: Int): String

    fun updateUserProfile(userId: Int, updatedProfile: Map<String, UserProfileEntry>): Map<String, UserProfileEntry>
}