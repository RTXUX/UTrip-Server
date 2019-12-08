package xyz.rtxux.utrip.server.service

import xyz.rtxux.utrip.server.model.vo.UserProfileVO

interface UserProfileService {
    fun getUserProfileById(userId: Int): UserProfileVO

    fun setUserAvatar(userId: Int, avatarId: Int)

    fun getUserAvatarUrl(userId: Int): String

    fun updateUserProfile(userId: Int, updatedProfile: UserProfileVO): UserProfileVO
}