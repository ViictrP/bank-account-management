package com.victorprado.donus.core.usecase.performtransaction;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.TransactionType;
import com.victorprado.donus.core.usecase.createaccount.GetBankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class PerformTransactionUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformTransactionUseCase.class);

    private final GetBankAccount getBankAccount;
    private final UpdateBankAccountBalance updateBankAccountBalance;
    private final SaveTransaction saveTransaction;

    public PerformTransactionUseCase(GetBankAccount getBankAccount, UpdateBankAccountBalance updateBankAccountBalance, SaveTransaction saveTransaction) {
        this.getBankAccount = getBankAccount;
        this.updateBankAccountBalance = updateBankAccountBalance;
        this.saveTransaction = saveTransaction;
    }

    public BankTransaction transfer(String sourceAccountNumber, String destinationAccountNumber, Double value) {
        LOGGER.info("obtaining the source account {}", sourceAccountNumber);
        BankAccount sourceAccount = getBankAccount.getAccountByNumber(sourceAccountNumber)
                .orElseThrow(BankAccountNotFoundException::new);

        LOGGER.info("obtaining the destination account {}", destinationAccountNumber);
        BankAccount destinationAccount = getBankAccount.getAccountByNumber(destinationAccountNumber)
                .orElseThrow(BankAccountNotFoundException::new);

        LOGGER.info("getting the value from source account {}", sourceAccountNumber);
        sourceAccount.reduceBalance(value);

        LOGGER.info("putting the value into destination account {}", destinationAccountNumber);
        destinationAccount.increaseBalance(value);

        BankTransaction transaction = new BankTransaction.Builder()
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .value(value)
                .type(TransactionType.TRANSFER)
                .when(LocalDateTime.now())
                .build();

        LOGGER.info("updating source account balance {}", sourceAccountNumber);
        updateBankAccountBalance.updateBalance(sourceAccount, sourceAccount.getBalance());

        LOGGER.info("updating destination account balance {}", destinationAccountNumber);
        updateBankAccountBalance.updateBalance(destinationAccount, destinationAccount.getBalance());

        LOGGER.info("saving the transaction {}", transaction.getId());
        saveTransaction.saveTransaction(transaction);

        return transaction;
    }
}
