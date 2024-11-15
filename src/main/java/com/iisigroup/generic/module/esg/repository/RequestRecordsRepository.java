package com.iisigroup.generic.module.esg.repository;


import com.iisigroup.generic.module.esg.entity.RequestRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestRecordsRepository extends JpaRepository<RequestRecords, String>, JpaSpecificationExecutor<RequestRecords> {
}