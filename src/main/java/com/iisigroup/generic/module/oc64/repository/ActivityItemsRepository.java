package com.iisigroup.generic.module.oc64.repository;

import com.iisigroup.ocapi.entity.ActivityItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ActivityItemsRepository extends JpaRepository<ActivityItems, String>, JpaSpecificationExecutor<ActivityItems> {

    List<ActivityItems> findAllByBaseId(String baseId);

    List<ActivityItems> findByBaseIdAndDeleteFlagFalse(String baseId);

    @Query("SELECT SUM(a.transferUsage) FROM ActivityItems a WHERE a.baseId = :baseId AND a.bioEnergy = true AND a.deleteFlag = false")
    BigDecimal sumByBaseId(String baseId);

}