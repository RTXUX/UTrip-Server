package xyz.rtxux.utrip.server.controller

import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
class CsrfController {
    @ApiIgnore
    @GetMapping("/csrf")
    fun csrf(token: CsrfToken?): CsrfToken? = token
}