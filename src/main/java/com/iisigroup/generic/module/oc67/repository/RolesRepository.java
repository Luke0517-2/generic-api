package com.iisigroup.generic.module.oc67.repository;


import com.iisigroup.generic.module.oc67.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolesRepository extends JpaRepository<Roles, String>, JpaSpecificationExecutor<Roles> {
}