package xyz.rtxux.utrip.server.config

import com.qiniu.util.Auth
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "qiniu")
class QiniuConfig {
    lateinit var domain: String
    lateinit var accessKey: String
    lateinit var secretKey: String
    lateinit var bucket: String

    @Bean
    fun qiniuAuth(): Auth = Auth.create(accessKey, secretKey)
}