package pl.patrykdolata.lifemanager.service;

import pl.patrykdolata.lifemanager.model.JwtToken;
import pl.patrykdolata.lifemanager.model.LoginInfo;
import pl.patrykdolata.lifemanager.model.NewUser;

public interface AccountService {
    JwtToken authorize(LoginInfo loginInfo);

    void register(NewUser user);
}
