package com.victorprado.donus.configuration;

import com.victorprado.donus.database.bank.BankDatabaseDataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseDataProviderConfiguration {

    @Bean
    public BankDatabaseDataProvider customerDatabaseDataProvider(JdbcTemplate jdbcTemplate) {
        return new BankDatabaseDataProvider(jdbcTemplate);
    }
}
