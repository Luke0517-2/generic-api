package com.iisigroup.generic.module.oc64.repository;

import com.iisigroup.ocapi.entity.FileNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FileNamesRepository extends JpaRepository<FileNames, String>, JpaSpecificationExecutor<FileNames> {
    List<FileNames> findByBindingIdAndDeleteFlagFalse(String bindingId);

    List<FileNames> findByBindingIdAndOriginalNameAndDeleteFlagFalse(String bindingId, String originalName);

    Long countByBindingIdAndDeleteFlagFalse(String bindingId);
}