package xyz.rtxux.utrip.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class UTripServerApplication

fun main(args: Array<String>) {
    runApplication<UTripServerApplication>(*args)
}
