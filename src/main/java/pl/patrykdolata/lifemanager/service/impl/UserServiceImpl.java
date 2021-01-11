package pl.patrykdolata.lifemanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.patrykdolata.lifemanager.domain.UserEntity;
import pl.patrykdolata.lifemanager.mapper.UserMapper;
import pl.patrykdolata.lifemanager.model.JwtToken;
import pl.patrykdolata.lifemanager.model.LoginInfo;
import pl.patrykdolata.lifemanager.model.NewUser;
import pl.patrykdolata.lifemanager.repository.UserRepository;
import pl.patrykdolata.lifemanager.security.JwtTokenProvider;
import pl.patrykdolata.lifemanager.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public JwtToken authorize(LoginInfo loginInfo) {
        log.debug("Authorize user: {}", loginInfo.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginInfo.getUsername(),
                loginInfo.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = loginInfo.getRememberMe() != null && loginInfo.getRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);

        return new JwtToken(jwt);
    }

    @Override
    public void register(NewUser user) {
        log.debug("Register new user with username: {}", user.getUsername());
        validNewUser(user);
        UserEntity entity = userMapper.toEntity(user);
        userRepository.save(entity);
    }

    private void validNewUser(NewUser user) {

    }
}
