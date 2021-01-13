package pl.patrykdolata.lifemanager.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.patrykdolata.lifemanager.domain.UserEntity;
import pl.patrykdolata.lifemanager.exceptions.NotLoggedUserException;

@UtilityClass
public class AuthenticationUtils {

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AuthenticatedUser) {
            AuthenticatedUser principal = (AuthenticatedUser) authentication.getPrincipal();
            return principal.getId();
        }
        throw new NotLoggedUserException();
    }

    public UserEntity getCurrentUser() {
        Long id = getCurrentUserId();
        UserEntity user = new UserEntity();
        user.setId(id);

        return user;
    }
}
