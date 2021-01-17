package pl.patrykdolata.lifemanager.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtFilter(private val tokenProvider: JwtTokenProvider) : GenericFilterBean() {

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val request: HttpServletRequest = servletRequest as HttpServletRequest
        val jwt: String? = resolveToken(request)
        jwt?.let { token ->
            if (token.isNotEmpty() && tokenProvider.validateToken(token)) {
                val authentication: Authentication = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader(AUTHORIZATION_HEADER)
        return if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("Bearer "))
            bearerToken.substring(7) else null
    }
}
