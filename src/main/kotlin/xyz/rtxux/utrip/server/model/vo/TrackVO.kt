package xyz.rtxux.utrip.server.model.vo

data class TrackVO(
        val trackId: Int,
        val name: String,
        val description: String,
        val beginTime: Long,
        val endTime: Long? = null,
        val userId: Int,
        val like: Int,
        val points: List<PointVO>,
        val status: Int
)