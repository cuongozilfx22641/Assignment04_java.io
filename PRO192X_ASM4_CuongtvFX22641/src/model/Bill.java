package model;

import java.io.Serializable;

public class Bill implements Serializable {
    private final static long serialVersionUID = 1L;
    private String customerId;
    private String acountNumber;
    private String amount;
    private String createAt;
    private TransationType type;

    public TransationType getType() {
        return type;
    }

    public void setType(TransationType type) {
        this.type = type;
    }

    public Bill(String customerId, String acountNumber , String amount, String createAt , TransationType type) {
        this.customerId = customerId;
        this.amount = amount;
        this.createAt = createAt;
        this.acountNumber = acountNumber;
        this.type = type;
    }
    public String getAcountNumber() {
        return acountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
