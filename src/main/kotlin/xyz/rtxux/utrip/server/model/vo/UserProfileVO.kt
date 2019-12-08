package xyz.rtxux.utrip.server.model.vo

import xyz.rtxux.utrip.server.model.po.UserProfile

data class UserProfileVO(
        val userId: Int,
        val username: String,
        val nickname: String,
        val gender: String? = null,
        val email: String? = null,
        val phone: String? = null,
        val qq: String? = null
) {
    fun toUserProfile(): UserProfile {
        return UserProfile(
                nickname, gender, email, phone, qq
        )
    }
    companion object {
        fun fromUserProfile(userProfile: UserProfile, username: String, userId: Int): UserProfileVO {
            return UserProfileVO(
                    userId = userId,
                    username = username,
                    nickname = userProfile.nickname,
                    gender = userProfile.gender,
                    email = userProfile.email,
                    phone = userProfile.phone,
                    qq = userProfile.qq
            )
        }
    }
}