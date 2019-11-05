package xyz.rtxux.utrip.server.model.dto

data class TrackDTO(
        val name: String,
        val description: String,
        val beginTime: Long,
        val endTime: Long? = null
)