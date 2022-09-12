package com.philvigus.robot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.util.List;

// Adapted from https://objectpartners.com/2021/07/14/resetting-database-between-spring-integration-tests/
@RequiredArgsConstructor
@Service
public class DatabaseResetService {
    private final EntityManager entityManager;
    private List<String> tableNames;

    @PostConstruct
    void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(entityType -> entityType.getJavaType().getAnnotation(Table.class) != null)
                .map(entityType -> entityType.getJavaType().getAnnotation(Table.class))
                .map(this::convertToTableName).toList();
    }

    private String convertToTableName(final Table table) {
        final String schema = table.schema();
        final String tableName = table.name();

        final String convertedSchema = StringUtils.hasText(schema) ? schema.toLowerCase() + "." : "";
        final String convertedTableName = tableName.replaceAll("([a-z])([A-Z])", "$1_$2");

        return convertedSchema + convertedTableName;
    }

    @Transactional
    public void resetDatabase() {
        entityManager.createNativeQuery("BEGIN").executeUpdate();

        entityManager.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();

        for (final String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }

        entityManager.createNativeQuery("COMMIT").executeUpdate();
    }
}
