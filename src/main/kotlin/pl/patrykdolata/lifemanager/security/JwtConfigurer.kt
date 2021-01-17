package pl.patrykdolata.lifemanager.security

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfigurer(private val tokenProvider: JwtTokenProvider) :
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(http: HttpSecurity) {
        val filter = JwtFilter(tokenProvider)
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
