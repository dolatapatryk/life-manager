package pl.patrykdolata.lifemanager.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenProvider tokenProvider;

    public JwtFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.debug("JwtFilter doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Optional<String> jwt = resolveToken(request);
        jwt.ifPresent(token -> {
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        });
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        log.debug("token: {}, request: {}", bearerToken, request.getRequestURI());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }
}
