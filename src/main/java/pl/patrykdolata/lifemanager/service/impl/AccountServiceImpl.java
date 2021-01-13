package pl.patrykdolata.lifemanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.patrykdolata.lifemanager.domain.AccountEntity;
import pl.patrykdolata.lifemanager.mapper.AccountMapper;
import pl.patrykdolata.lifemanager.model.NewAccount;
import pl.patrykdolata.lifemanager.repository.AccountRepository;
import pl.patrykdolata.lifemanager.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Long create(NewAccount account) {
        log.debug("Create new account with name: {}", account.getName());
        AccountEntity newAccount = accountRepository.save(accountMapper.toEntity(account));

        return newAccount.getId();
    }
}
