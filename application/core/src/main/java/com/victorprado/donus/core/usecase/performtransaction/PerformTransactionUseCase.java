package com.victorprado.donus.core.usecase.performtransaction;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.TransactionType;
import com.victorprado.donus.core.usecase.createaccount.GetBankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PerformTransactionUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformTransactionUseCase.class);
    public static final BigDecimal TAX = BigDecimal.valueOf(0.01);
    public static final BigDecimal BONUS = BigDecimal.valueOf(0.005);
    private static final String SAVING_TRANSACTION_LOG_MESSAGE = "saving the transaction {}";

    private final GetBankAccount getBankAccount;
    private final UpdateBankAccountBalance updateBankAccountBalance;
    private final SaveTransaction saveTransaction;

    public PerformTransactionUseCase(GetBankAccount getBankAccount, UpdateBankAccountBalance updateBankAccountBalance, SaveTransaction saveTransaction) {
        this.getBankAccount = getBankAccount;
        this.updateBankAccountBalance = updateBankAccountBalance;
        this.saveTransaction = saveTransaction;
    }

    public BankTransaction transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal value) {
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

        LOGGER.info(SAVING_TRANSACTION_LOG_MESSAGE, transaction.getId());
        saveTransaction.saveTransaction(transaction);

        return transaction;
    }

    public BankTransaction withdraw(String accountNumber, BigDecimal withdrawValue) {
        LOGGER.info("obtaining the account {}", accountNumber);
        BankAccount account = getBankAccount.getAccountByNumber(accountNumber)
                .orElseThrow(BankAccountNotFoundException::new);

        LOGGER.info("withdrawing value {} from account {}", withdrawValue, accountNumber);
        BigDecimal valueWithTax = withdrawValue.add(withdrawValue.multiply(TAX));
        account.reduceBalance(valueWithTax);

        LOGGER.info("updating account balance {}", accountNumber);
        updateBankAccountBalance.updateBalance(account, account.getBalance());

        BankTransaction transaction = new BankTransaction.Builder()
                .sourceAccount(account)
                .value(withdrawValue)
                .type(TransactionType.WITHDRAW)
                .when(LocalDateTime.now())
                .build();

        LOGGER.info(SAVING_TRANSACTION_LOG_MESSAGE, transaction.getId());
        saveTransaction.saveTransaction(transaction);

        return transaction;
    }

    public BankTransaction deposit(String accountNumber, BigDecimal depositValue) {
        LOGGER.info("obtaining the account {}", accountNumber);
        BankAccount account = getBankAccount.getAccountByNumber(accountNumber)
                .orElseThrow(BankAccountNotFoundException::new);

        LOGGER.info("depositing value {} into account {}", depositValue, accountNumber);
        BigDecimal depositWithBonus = depositValue.add(depositValue.multiply(BONUS));
        account.increaseBalance(depositWithBonus);

        LOGGER.info("updating account balance {}", accountNumber);
        updateBankAccountBalance.updateBalance(account, account.getBalance());

        BankTransaction transaction = new BankTransaction.Builder()
                .sourceAccount(account)
                .value(depositValue)
                .type(TransactionType.DEPOSIT)
                .when(LocalDateTime.now())
                .build();

        LOGGER.info(SAVING_TRANSACTION_LOG_MESSAGE, transaction.getId());
        saveTransaction.saveTransaction(transaction);

        return transaction;
    }
}
