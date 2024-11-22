package com.iisigroup.generic.module.oc64.repository;

import com.iisigroup.ocapi.entity.Emissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface EmissionsRepository extends JpaRepository<Emissions, String>, JpaSpecificationExecutor<Emissions> {

    @Query("""
    SELECT SUM(emission) FROM Emissions WHERE inventoryId = :inventoryId AND activityItemBaseId = :activityItemBaseId AND deleteFlag = false
    """)
    BigDecimal sumBaseTotalEmission(@Param("inventoryId") String inventoryId, @Param("activityItemBaseId") String activityItemBaseId);


    @Query(""" 
    SELECT SUM(emission) FROM Emissions WHERE inventoryId = :inventoryId AND deleteFlag = false
    """)
    BigDecimal sumTotalEmission(@Param("inventoryId") String inventoryId);

    @Query("""
    SELECT SUM(emission) FROM Emissions WHERE inventoryId = :inventoryId AND deleteFlag = false
    AND (:category IS NULL OR category = :category)
    AND (:greenhouseGas IS NULL OR greenhouseGas = :greenhouseGas)
    """)
    BigDecimal sumTotalByCategoryAndGHGEmission(@Param("inventoryId") String inventoryId, @Param("category") Integer category, @Param("greenhouseGas") String greenhouseGas);

    @Query(""" 
    SELECT COUNT(1) FROM Emissions WHERE inventoryId = :inventoryId AND activityItemBaseId = :activityItemBaseId AND deleteFlag = false
    """)
    Integer countEmissions(@Param("inventoryId") String inventoryId, @Param("activityItemBaseId") String activityItemBaseId);


}