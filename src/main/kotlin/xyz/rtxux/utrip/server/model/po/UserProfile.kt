package xyz.rtxux.utrip.server.model.po


data class UserProfile(
        val nickname: String,
        val gender: String? = null,
        val email: String? = null,
        val phone: String? = null,
        val qq: String? = null
)