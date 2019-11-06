package xyz.rtxux.utrip.server.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import xyz.rtxux.utrip.server.model.po.User
import java.util.*

@Repository
interface UserRepsitory : JpaRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
}