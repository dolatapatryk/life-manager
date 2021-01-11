package pl.patrykdolata.lifemanager.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdolata.lifemanager.domain.UserEntity;
import pl.patrykdolata.lifemanager.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);
    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.debug("Authentication: {}", login);
        // todo sprawdzic czy to email czy username
        Optional<UserEntity> userOpt = userRepository.findOneByUsername(login);
        return userOpt.map(this::createUser).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    private AuthenticatedUser createUser(UserEntity user) {
        return new AuthenticatedUser(user.getId(), user.getUsername(), user.getPassword(), user.getActivated(),
                Collections.emptyList());
    }
}
