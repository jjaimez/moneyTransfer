package transfers.requests;

import java.math.BigDecimal;

public class CreateTransferRequest {

    private String fromId;
    private String toId;
    private BigDecimal amount;
    private String currency;

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public CreateTransferRequest() {
    }

    public CreateTransferRequest(String fromId, String toId, BigDecimal amount, String currency) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
        this.currency = currency;
    }
}
