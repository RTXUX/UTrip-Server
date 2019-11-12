package xyz.rtxux.utrip.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.web.http.CookieSerializer
import org.springframework.session.web.http.DefaultCookieSerializer

@Configuration
class SessionConfig {
    @Bean
    fun cookieSerializer(): CookieSerializer = DefaultCookieSerializer().apply {
        setCookieMaxAge(86400)
    }
}