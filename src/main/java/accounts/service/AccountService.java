package accounts.service;

import accounts.model.Account;
import accounts.repository.AccountRepository;
import exceptions.BadRequestException;

import java.math.BigDecimal;

public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getAccount(String id){
        return repository.findById(id).orElseThrow(() -> new BadRequestException());
    }

    public Account addAmount(Account account, BigDecimal amount){
        account.addAmount(amount);
        return repository.update(account);
    }

    public Account subtractAmount(Account account, BigDecimal amount){
        if (account.getBalance().compareTo(amount) == -1) {
            throw new BadRequestException();
        }

        account.subtractAmount(amount);
        return repository.update(account);
    }


}
