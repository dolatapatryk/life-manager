package pl.patrykdolata.lifemanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.patrykdolata.lifemanager.model.NewAccount;
import pl.patrykdolata.lifemanager.repository.AccountRepository;
import pl.patrykdolata.lifemanager.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Long create(NewAccount account) {
        return null;
    }
}
