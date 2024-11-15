package com.iisigroup.generic.config.db.h2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
@Configuration 
@ConditionalOnProperty(prefix = "ocarbon.db", name = "type", havingValue = "h2") 
public class H2DatasourceConfig {
	
	@Bean
	@ConfigurationProperties("spring.datasource.h2")
	@ConditionalOnProperty(prefix = "ocarbon.db", name = "type", havingValue = "h2") 
    public DataSourceProperties h2DataSourceProperties() {
		DataSourceProperties result = new DataSourceProperties();
        return result;
    }
	@Bean
	@Primary 
	@ConfigurationProperties("spring.datasource.h2.hikari")
	@ConditionalOnProperty(prefix = "ocarbon.db", name = "type", havingValue = "h2") 
	public DataSource embeddedDataSource(
		@Qualifier("h2DataSourceProperties")
	    DataSourceProperties 	embeddedDataSourceProperties) {
		DataSource result = embeddedDataSourceProperties
	      .initializeDataSourceBuilder()
	      .build();
	    return result;
	}
}
