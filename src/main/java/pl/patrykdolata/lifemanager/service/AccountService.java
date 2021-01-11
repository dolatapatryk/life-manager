package pl.patrykdolata.lifemanager.service;

import pl.patrykdolata.lifemanager.model.NewAccount;

public interface AccountService {
    Long create(NewAccount account);
}
