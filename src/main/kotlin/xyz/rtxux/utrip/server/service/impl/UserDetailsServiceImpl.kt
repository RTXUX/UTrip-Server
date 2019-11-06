package xyz.rtxux.utrip.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.utrip.server.repository.UserRepsitory

@Service
class UserDetailsServiceImpl @Autowired constructor(
        private val userRepository: UserRepsitory
) : UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByUsername(username!!).orElseThrow { UsernameNotFoundException("User ${username} not found") }
        return MyUserDetails(user.id!!, user.username!!, user.password!!)
    }
}

data class MyUserDetails(
        val userId: Int,
        val usernameInternal: String,
        val passwordInternal: String
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("USER"))
    }

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = usernameInternal

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = passwordInternal

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}