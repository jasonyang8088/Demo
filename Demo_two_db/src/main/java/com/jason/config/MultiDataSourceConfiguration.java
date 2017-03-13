package com.jason.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultiDataSourceConfiguration {
	
	@Bean
	@Primary
	@ConfigurationProperties("datasource.primary")
	public DataSourceProperties primaryDataSourceProperties() {
	    return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("datasource.primary")
	public DataSource primaryDataSource() {
	    return primaryDataSourceProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean
	@ConfigurationProperties("datasource.secondary")
	public DataSourceProperties secondaryDataSourceProperties() {
	    return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("datasource.secondary")
	public DataSource secondaryDataSource() {
	    return secondaryDataSourceProperties().initializeDataSourceBuilder().build();
	}
	
//	@Bean(name = "primaryJdbcTemplate")
//	public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
//		return new JdbcTemplate(primaryDataSource);
//	}
//	
//	@Bean(name = "secondaryJdbcTemplate")
//	public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
//		return new JdbcTemplate(secondaryDataSource);
//	}
	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
//	        EntityManagerFactoryBuilder builder) {
//	    return builder
//	            .dataSource(primaryDataSource())
//	            .packages(Subject.class)
//	            .persistenceUnit("jyeoo")
//	            .build();
//	}
//
//	@Bean
//	public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
//	        EntityManagerFactoryBuilder builder) {
//		return builder
//	            .dataSource(secondaryDataSource())
//	            .packages(Subject2.class)
//	            .persistenceUnit("koolearn")
//	            .build();
//	}
	

}
