package xyz.rtxux.utrip.server.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.dto.RegisterDTO
import xyz.rtxux.utrip.server.model.po.User
import xyz.rtxux.utrip.server.model.vo.RegisterVO
import xyz.rtxux.utrip.server.repository.UserRepsitory
import xyz.rtxux.utrip.server.service.UserService

@Service
class UserServiceImpl @Autowired constructor(
        private val userRepsitory: UserRepsitory,
        private val passwordEncoder: PasswordEncoder
) : UserService {
    @Transactional
    override fun register(registerDTO: RegisterDTO): RegisterVO {
        if (userRepsitory.findByUsername(registerDTO.username).isPresent) {
            throw AppException(1001, "用户已存在")
        }
        val user = User(
                username = registerDTO.username,
                password = passwordEncoder.encode(registerDTO.password),
                nickname = registerDTO.nickname,
                gender = "无"
        )
        val savedUser = userRepsitory.save(user)
        return RegisterVO(savedUser.id!!, savedUser.username!!)
    }
}