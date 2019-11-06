package xyz.rtxux.utrip.server.exception

class AppException(
        val code: Int,
        override val message: String? = null,
        override val cause: Throwable? = null
) : RuntimeException(message, cause)