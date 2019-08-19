package transfers.service;


import accounts.model.Account;
import accounts.service.AccountService;
import exceptions.BadRequestException;
import transfers.model.Transfer;
import transfers.repository.TransferRepository;
import utils.IdGenerator;
import utils.moneyConverter.MoneyConverter;

import java.math.BigDecimal;


public class CreateTransferService {

    private final IdGenerator idGenerator;
    private final TransferRepository transferRepository;
    private final MoneyConverter moneyConverter;
    private final AccountService accountService;

    public CreateTransferService(final TransferRepository transferRepository, final AccountService accountService,final IdGenerator idGenerator, final MoneyConverter moneyConverter) {
        this.transferRepository = transferRepository;
        this.idGenerator = idGenerator;
        this.moneyConverter =  moneyConverter;
        this.accountService = accountService;
    }

    public Transfer create(String fromId, String toId, BigDecimal amount, String currency) {

        Account to = accountService.getAccount(toId);
        Account from = accountService.getAccount(fromId);

        if (!currency.equals(from.getCurrency()) && !currency.equals(to.getCurrency())){
            throw new BadRequestException();
        }

        BigDecimal convertedFromAmount = amount;
        if (!from.getCurrency().equals(currency)){
             convertedFromAmount = moneyConverter.convert(currency, from.getCurrency(), amount);
        }

        BigDecimal convertedToAmount = amount;
        if (!to.getCurrency().equals(currency)) {
            convertedToAmount = moneyConverter.convert(currency, to.getCurrency(), amount);
        }

        accountService.subtractAmount(from, convertedFromAmount);
        accountService.addAmount(to, convertedToAmount);


        String id = generateId();
        Transfer transfer = new Transfer(id, fromId, toId, currency, convertedFromAmount, convertedToAmount);

        return transferRepository.create(transfer);
    }


    private String generateId(){
        String id = idGenerator.generate();
        while (transferRepository.findById(id).isPresent()) {
            id = idGenerator.generate();
        }
        return id;
    }
}
