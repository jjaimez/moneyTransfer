import accounts.model.Account;
import accounts.repository.InMermoryAccountRepository;
import dependencies.DependencyFactory;
import router.Router;
import spark.Spark;

import java.math.BigDecimal;

public enum Main {
    ;

    public static void main(String[] args) {
        Spark.port(8080);
        new Router().init();
        Spark.awaitInitialization();
        boostrap();


        System.out.println("Listening on http://localhost:8080/");
    }

    private static void boostrap(){
        Account a = new Account("10","USD");
        InMermoryAccountRepository.INSTANCE.create(a);
        DependencyFactory.getAccountService().addAmount(a, BigDecimal.TEN);

        a = new Account("11","USD");
        InMermoryAccountRepository.INSTANCE.create(a);

        a = new Account("12","EUR");
        InMermoryAccountRepository.INSTANCE.create(a);

        a = new Account("13","EUR");
        InMermoryAccountRepository.INSTANCE.create(a);
        DependencyFactory.getAccountService().addAmount(a, BigDecimal.TEN);
    }

}
