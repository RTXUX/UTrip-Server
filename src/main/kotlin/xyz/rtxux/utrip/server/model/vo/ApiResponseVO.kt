package xyz.rtxux.utrip.server.model.vo

data class ApiResponseVO<T>(
        val code: Int,
        val msg: String? = null,
        val data: T? = null
)