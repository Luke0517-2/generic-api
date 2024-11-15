package com.iisigroup.generic.module.oc64.repository;


import com.iisigroup.ocapi.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolesRepository extends JpaRepository<Roles, String>, JpaSpecificationExecutor<Roles> {
}