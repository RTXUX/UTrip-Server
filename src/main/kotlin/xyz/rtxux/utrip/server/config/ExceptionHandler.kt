package xyz.rtxux.utrip.server.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO

@ControllerAdvice
class ExceptionHandler @Autowired constructor(
        private val objectMapper: ObjectMapper
) {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponseVO<String>> {
        e.printStackTrace()
        if (e is AppException) {
            return ResponseEntity.status(e.httpCode).body(ApiResponseVO(e.code, e.friendlyMessage, e.cause?.message))
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseVO(-1, "服务器错误", e.message))
    }
}