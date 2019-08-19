package transfers.validators;

import transfers.requests.CreateTransferRequest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public enum  CreateAccountRequestValidator {
    INSTACE;

    public boolean isValid(CreateTransferRequest createTransferRequest){
        boolean result = true;

        result &= isValidId(createTransferRequest.getToId());

        result &= isValidId(createTransferRequest.getFromId());

        if (result) {
            result &= !createTransferRequest.getFromId().equals(createTransferRequest.getToId());
        }

        result &= isValidAmount(createTransferRequest.getAmount());

        result &= isValidCurrency(createTransferRequest.getCurrency());

        return result;
    }

    private boolean isValidId(String id){
        return !Objects.isNull(id) && !id.isEmpty();
    }

    private boolean isValidAmount(BigDecimal amount){
        return !Objects.isNull(amount) && amount.compareTo(BigDecimal.ZERO) == 1;
    }

    private boolean isValidCurrency(String currency){
        if (Objects.isNull(currency)){
            return false;
        }

        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

}
