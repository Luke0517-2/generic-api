package com.iisigroup.generic.module.oc64.repository;

import com.iisigroup.ocapi.entity.Uncertainties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UncertaintiesRepository extends JpaRepository<Uncertainties, String>, JpaSpecificationExecutor<Uncertainties> {
}