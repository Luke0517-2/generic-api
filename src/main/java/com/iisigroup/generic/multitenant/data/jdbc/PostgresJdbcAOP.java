package com.iisigroup.generic.multitenant.data.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
@Aspect
@Slf4j
public class PostgresJdbcAOP {

    private final MultiTenantConnectionProvider<String> connectionProvider;
    private final CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver;

    public PostgresJdbcAOP(
            @Qualifier("postgresConnectionProvider") MultiTenantConnectionProvider<String> connectionProvider,
            CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver) {
        this.connectionProvider = connectionProvider;
        this.currentTenantIdentifierResolver = currentTenantIdentifierResolver;
    }

    @Around("execution(* org.springframework.jdbc.core.JdbcOperations.*(..) )  ")
    public Object runJdbcOperationsAroundAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        return runAroundAdvice(pjp);
    }

    @Around("execution(* org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations.*(..) )  ")
    public Object runNamedParameterJdbcOperationsAroundAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        return runAroundAdvice(pjp);
    }


    protected Object runAroundAdvice(final ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();

        log.info("Executing method: {} in class: {}", methodName, className);

        String tenantId = currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();
        log.info("Resolved tenantId: {}", tenantId);

        if (tenantId == null) {
            log.warn("No tenantId resolved, proceeding without tenant-specific context.");
            return pjp.proceed();
        }

        Connection connection = connectionProvider.getConnection(tenantId);
        try {
            log.debug("Tenant-specific connection obtained for tenantId: {}", tenantId);
            return pjp.proceed();
        } finally {
            connectionProvider.releaseConnection(tenantId, connection);
            log.debug("Tenant-specific connection released for tenantId: {}", tenantId);
        }
    }
}
