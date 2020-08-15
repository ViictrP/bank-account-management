package rest.bankaccount;

import java.time.LocalDateTime;

public class BankTransactionDTO implements Dto {

    private String id;
    private String sourceAccount;
    private String destinationAccount;
    private String type;
    private Double value;
    private String when;

    public BankTransactionDTO(Builder builder) {
        this.id = builder.id;
        this.sourceAccount = builder.sourceAccount;
        this.destinationAccount = builder.destinationAccount;
        this.type = builder.type;
        this.value = builder.value;
        this.when = builder.when;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public static class Builder {
        private String id;
        private String sourceAccount;
        private String destinationAccount;
        private String type;
        private Double value;
        private String when;

        public Builder id(String id) {
            this.id = id;
            return this;
        }
        public Builder sourceAccount(String sourceAccount) {
            this.sourceAccount = sourceAccount;
            return this;
        }
        public Builder destinationAccount(String destinationAccount) {
            this.destinationAccount = destinationAccount;
            return this;
        }
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        public Builder value(Double value) {
            this.value = value;
            return this;
        }
        public Builder when(LocalDateTime when) {
            this.when = when.toString();
            return this;
        }

        public BankTransactionDTO build() {
            return new BankTransactionDTO(this);
        }
    }
}
