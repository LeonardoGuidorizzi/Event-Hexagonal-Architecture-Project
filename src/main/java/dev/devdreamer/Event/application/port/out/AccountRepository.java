package dev.devdreamer.Event.application.port.out;

import dev.devdreamer.Event.domain.Account;

public interface AccountRepository {
    Account save(Account account);
}
