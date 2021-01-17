package pl.patrykdolata.lifemanager.config

import org.springframework.beans.factory.BeanInitializationException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter
import org.springframework.web.filter.CorsFilter
import pl.patrykdolata.lifemanager.security.JwtConfigurer
import pl.patrykdolata.lifemanager.security.JwtTokenProvider
import javax.annotation.PostConstruct

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration(
        private val authenticationManagerBuilder: AuthenticationManagerBuilder,
        private val userDetailsService: UserDetailsService, private val tokenProvider: JwtTokenProvider,
        private val corsFilter: CorsFilter
) : WebSecurityConfigurerAdapter() {

    @PostConstruct
    fun init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder())
        } catch (e: Exception) {
            throw BeanInitializationException("Security config failed", e)
        }
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**")
    }

    override fun configure(http: HttpSecurity) {
        http
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
                .exceptionHandling()
                .and()
                .headers()
                .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
                .and()
                .frameOptions()
                .deny()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/users/authenticate").permitAll()
                .antMatchers("/api/users/register").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .apply(securityConfigurerAdapter())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    private fun securityConfigurerAdapter(): JwtConfigurer = JwtConfigurer(tokenProvider)
}
