package com.iisigroup.generic.module.oc64.repository;

import com.iisigroup.ocapi.entity.ActivityItemBases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ActivityItemBasesRepository extends JpaRepository<ActivityItemBases, String>, JpaSpecificationExecutor<ActivityItemBases> {

    ActivityItemBases findByInventoryIdAndFuelMaterialIdAndDeleteFlagFalseAndCheckedTrue(String inventoryId ,String fuelMaterialId);


    List<ActivityItemBases> findByInventoryIdAndDeleteFlagFalseAndCheckedTrue(String inventoryId);
}
