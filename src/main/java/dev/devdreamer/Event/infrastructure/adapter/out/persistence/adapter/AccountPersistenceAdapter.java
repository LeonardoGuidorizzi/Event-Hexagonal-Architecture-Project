package dev.devdreamer.Event.infrastructure.adapter.out.persistence.adapter;

import dev.devdreamer.Event.application.port.out.AccountRepository;
import dev.devdreamer.Event.domain.Account;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.AccountEntity;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.repository.AccountJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountPersistenceAdapter implements AccountRepository {
    private final AccountJpaRepository repository;
    public AccountPersistenceAdapter(AccountJpaRepository repository) { this.repository = repository; }
    @Override public Account save(Account account) {
        repository.save(new AccountEntity(account.id(), account.userId(), account.name(), account.lastname(), account.birthdate()));
        return account;
    }
}
