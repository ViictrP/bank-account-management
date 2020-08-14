package rest.bankaccount;

import org.apache.tomcat.jni.Local;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public class BankAccountDTO {

    private String id;
    private String customerCpf;
    private Double balance;
    private String createdDate;
    private String lastModifiedDate;
    private boolean deleted;
    private EndpointStatus status;
    private String message;

    public BankAccountDTO() {

    }

    public BankAccountDTO(Builder builder) {
        this.id = builder.id;
        this.customerCpf = builder.customerCpf;
        this.balance = builder.balance;
        this.createdDate = builder.createdDate;
        this.lastModifiedDate = builder.createdDate;
        this.deleted = builder.deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerCpf() {
        return customerCpf;
    }

    public void setCustomerCpf(String customerCpf) {
        this.customerCpf = customerCpf;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public EndpointStatus getStatus() {
        return status;
    }

    public void setStatus(EndpointStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Builder {
        private String id;
        private String customerCpf;
        private Double balance;
        private String createdDate;
        private String lastModifiedDate;
        private boolean deleted;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder customer(String cpf) {
            this.customerCpf = cpf;
            return this;
        }

        public Builder balance(Double balance) {
            this.balance = balance;
            return this;
        }

        public Builder createdDate(@NonNull LocalDateTime createdDate) {
            this.createdDate = createdDate.toString();
            return this;
        }

        public Builder lastModifiedDate(@NonNull LocalDateTime lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate.toString();
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public BankAccountDTO build() {
            return new BankAccountDTO(this);
        }
    }
}
