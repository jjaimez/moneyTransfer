package transfers.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    private final String id;
    private final String fromId;
    private final String toId;
    private final BigDecimal sourcedAmount;
    private final BigDecimal receivedAmount;
    private final String currency;


    public Transfer(String id, String fromId, String toId, String currency, BigDecimal sourcedAmount, BigDecimal receivedAmount) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.sourcedAmount = sourcedAmount;
        this.receivedAmount = receivedAmount;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public BigDecimal getSourcedAmount() {
        return sourcedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public String getCurrency() {
        return currency;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(id, transfer.id) &&
                Objects.equals(fromId, transfer.fromId) &&
                Objects.equals(toId, transfer.toId) &&
                Objects.equals(sourcedAmount, transfer.sourcedAmount) &&
                Objects.equals(receivedAmount, transfer.receivedAmount) &&
                Objects.equals(currency, transfer.currency);
    }

}
