package xyz.rtxux.utrip.server.exception

class AppException(
        val httpCode: Int,
        val code: Int,
        val friendlyMessage: String? = null,
        override val cause: Throwable? = null
) : RuntimeException(friendlyMessage, cause)