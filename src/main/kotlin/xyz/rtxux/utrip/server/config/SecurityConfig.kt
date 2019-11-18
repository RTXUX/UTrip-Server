package xyz.rtxux.utrip.server.config

import com.qiniu.util.Auth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import xyz.rtxux.utrip.server.service.impl.ImageServiceImpl
import xyz.rtxux.utrip.server.service.impl.UserDetailsServiceImpl
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
class SecurityConfig @Autowired constructor(
        private val userDetailsService: UserDetailsServiceImpl,
        private val myAuthenticationSuccessHandler: MyAuthenticationSuccessHandler
        //private val qiniuCallbackAuthenticationFilter: QiniuCallbackAuthenticationFilter
) : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun configure(http: HttpSecurity?) {
        http?.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        }
        http?.authorizeRequests {
            it.antMatchers("/auth/**").permitAll()
            it.antMatchers("/image/postupload").permitAll()
            it.anyRequest().authenticated()
        }
        http?.formLogin()?.apply {
            this.successHandler(myAuthenticationSuccessHandler)
            this.permitAll()
        }
        //http?.addFilterBefore(qiniuCallbackAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        http?.csrf()?.disable()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }
}

@Component
class MyAuthenticationSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val requestedWithHeader = request!!.getHeader("X-Requested-With")
        if (requestedWithHeader == null) {
            super.onAuthenticationSuccess(request, response, authentication)
            return
        }
        response!!.sendRedirect("/auth/validate")
    }
}

//@Component
class QiniuCallbackAuthenticationFilter @Autowired constructor(
        private val qiniuAuth: Auth,
        private val qiniuConfig: QiniuConfig
) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request!! as HttpServletRequest
        if (httpRequest.requestURL.toString() != ImageServiceImpl.UploadUtils.CALLBACK_URL) {
            chain?.doFilter(request, response)
            return
        }
        val authorization = httpRequest.getHeader("Authorization")
        val valid = qiniuAuth.isValidCallback(authorization, ImageServiceImpl.UploadUtils.CALLBACK_URL, httpRequest.inputStream.readAllBytes(), httpRequest.contentType)
        if (valid) {
            SecurityContextHolder.getContext().authentication = PreAuthenticatedAuthenticationToken("Qiniu", null, listOf(SimpleGrantedAuthority("QINIU")))
        }
        chain?.doFilter(request, response)
    }
}