package accounts.repository;

import accounts.model.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findById(String id);

    Account update(Account account);

}