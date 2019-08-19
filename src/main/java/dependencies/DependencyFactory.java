package dependencies;

import accounts.repository.AccountRepository;
import accounts.repository.InMermoryAccountRepository;
import accounts.service.AccountService;
import transfers.repository.InMermoryTransferRepository;
import transfers.repository.TransferRepository;
import utils.IdGenerator;
import utils.UuidGenerator;
import utils.jsonParser.GsonJsonParser;
import utils.jsonParser.JsonParser;
import utils.keyLock.InMemoryKeyLock;
import utils.keyLock.KeyLock;
import utils.moneyConverter.MoneyConverter;
import utils.moneyConverter.MoneyJavaMoneyConvert;


public class DependencyFactory {



    public static MoneyConverter getMoneyConverter(){
        return MoneyJavaMoneyConvert.INSTANCE;
    }

    public static AccountRepository getAccountRepository() {
        return InMermoryAccountRepository.INSTANCE;
    }

    public static TransferRepository getTransferRepository() {
        return InMermoryTransferRepository.INSTANCE;
    }

    public static JsonParser getJsonParser() {
        return GsonJsonParser.INSTACE;
    }

    public static IdGenerator getIdGenerator() {
        return new UuidGenerator();
    }

    public static KeyLock getKeyLock() {
        return InMemoryKeyLock.INSTANCE;
    }

    public static AccountService getAccountService(){
        return new AccountService(DependencyFactory.getAccountRepository());
    }


}
