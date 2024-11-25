package com.iisigroup.generic.config.db.mysql;


import com.iisigroup.multitenant.data.hibernate.ConnectionProvider;
import com.iisigroup.multitenant.data.hibernate.TenantIdentifierResolver;
import com.iisigroup.multitenant.tenantdetails.TenantDetailsService;
import com.iisigroup.ocapi.entity.Roles;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName:MysqlJpaConfiguration
 * Package:com.iisigroup.generic.config.db.mysql
 * Description:
 *
 * @Date:2024/11/15 上午 11:45
 * @Author:2208021
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.iisigroup.generic.module.oc64.repository",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MysqlJpaConfiguration {
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            final TenantIdentifierResolver tenantIdentifierResolver,
            @Qualifier("mysqlConnectionProvider") final ConnectionProvider connectionProvider,
            @Qualifier("mysqlDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean bean = builder
                .dataSource(dataSource)
                .packages(Roles.class)
                .build();

        Map<String, Object> jpaProps = bean.getJpaPropertyMap();

        jpaProps.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
        jpaProps.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
        jpaProps.put(AvailableSettings.LOG_SLOW_QUERY, 0L);
        jpaProps.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        bean.setJpaPropertyMap(jpaProps);

        return bean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(mysqlEntityManagerFactory.getObject()));
    }

    @Bean("mysqlConnectionProvider")
    @ConditionalOnMissingClass
    public ConnectionProvider connectionProvider(
            @Qualifier("mysqlDataSource") final DataSource dataSource,
            final TenantDetailsService tenantDetailsService) {
        return new ConnectionProvider(dataSource, tenantDetailsService);
    }

}
