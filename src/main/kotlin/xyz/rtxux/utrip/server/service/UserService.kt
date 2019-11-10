package xyz.rtxux.utrip.server.service

import xyz.rtxux.utrip.server.model.dto.RegisterDTO
import xyz.rtxux.utrip.server.model.vo.RegisterVO

interface UserService {
    fun register(registerDTO: RegisterDTO): RegisterVO
}