package com.dukcode.authservice.config;

import jakarta.persistence.EntityManagerFactory;
import java.beans.ConstructorProperties;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EntityScan
@EnableJpaRepositories
public class PersistenceJpaConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new JdbcTransactionManager();
  }

  @Bean
  public TransactionTemplate writeTransactionOperations(PlatformTransactionManager transactionManager) {
    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    transactionTemplate.setReadOnly(false);
    return transactionTemplate;
  }

  @Bean
  public TransactionTemplate readTransactionOperations(PlatformTransactionManager transactionManager) {
    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    transactionTemplate.setReadOnly(true);
    return transactionTemplate;
  }

}
