package com.iisigroup.generic.module.oc64.repository;

import com.iisigroup.ocapi.entity.UncertaintyItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UncertaintyItemsRepository extends JpaRepository<UncertaintyItems, String>, JpaSpecificationExecutor<UncertaintyItems> {
}