package pl.patrykdolata.lifemanager.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct

@Component
class JwtTokenProvider(
        @Value("\${jwt.expiration-time}")
        private val tokenExpirationTime: Long,

        @Value("\${jwt.remember-me-expiration-time}")
        private val rememberMeExpirationTime: Long,

        @Value("\${jwt.secret}")
        private val jwtSecret: String
) {

    companion object {
        private const val AUTHORITIES_KEY = "auth"
        private const val ID_KEY = "id"
    }

    private val log: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    private lateinit var key: Key

    @PostConstruct
    fun init() {
        val keyBytes: ByteArray = Decoders.BASE64.decode(jwtSecret)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun createToken(authentication: Authentication, rememberMe: Boolean = false): String {
        val now: Long = Date().time
        val validity: Date = if (rememberMe) Date(now + rememberMeExpirationTime) else
            Date(now + tokenExpirationTime)
        return Jwts.builder()
                .setSubject(authentication.name)
                .claim(AUTHORITIES_KEY, getAuthorities(authentication))
                .claim(ID_KEY, getId(authentication))
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getTokenClaims(token)
        val authorities = getAuthorities(claims)
        val id: Long = getId(claims)
        val principal = AuthenticatedUser(id, claims.subject)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            when (e) {
                is JwtException, is IllegalArgumentException -> log.debug("Invalid JWT token")
            }
        }
        return false
    }

    private fun getTokenClaims(token: String): Claims {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
    }

    private fun getId(authentication: Authentication): Long = (authentication.principal as AuthenticatedUser).id

    private fun getAuthorities(authentication: Authentication) = authentication.authorities.joinToString(";")

    private fun getAuthorities(claims: Claims): Collection<GrantedAuthority> =
            claims[AUTHORITIES_KEY].toString().split(",").map { SimpleGrantedAuthority(it) }


    private fun getId(claims: Claims): Long = claims[ID_KEY].toString().toLong()
}
