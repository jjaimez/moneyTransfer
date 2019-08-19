package accounts.repository;

import accounts.model.Account;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public enum  InMermoryAccountRepository implements AccountRepository {
    INSTANCE;

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }


    @Override
    public Account update(Account account) {
        return accounts.replace(account.getId(),account);
    }

    public Account create(Account account) {
        return accounts.put(account.getId(),account);
    }

    public void delete(String id) {
        accounts.remove(id);
    }
}
