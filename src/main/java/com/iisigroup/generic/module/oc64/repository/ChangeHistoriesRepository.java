package com.iisigroup.generic.module.oc64.repository;

import com.iisigroup.ocapi.entity.ChangeHistories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChangeHistoriesRepository extends JpaRepository<ChangeHistories, String>, JpaSpecificationExecutor<ChangeHistories> {
    Long countByTableName(String tableName);
}