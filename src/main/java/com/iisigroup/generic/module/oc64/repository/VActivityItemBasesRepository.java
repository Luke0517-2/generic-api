package com.iisigroup.generic.module.oc64.repository;

import com.iisigroup.ocapi.entity.VActivityItemBases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VActivityItemBasesRepository extends JpaRepository<VActivityItemBases, String>, JpaSpecificationExecutor<VActivityItemBases> {
    List<VActivityItemBases> findAllByInventoryId(String inventoryId);

    List<VActivityItemBases> findByInventoryIdAndTypeAndDeleteFlagFalse(String inventoryId, String type);

    List<VActivityItemBases> findByInventoryIdAndDeleteFlagFalseAndCheckedTrue(String inventoryId);

}