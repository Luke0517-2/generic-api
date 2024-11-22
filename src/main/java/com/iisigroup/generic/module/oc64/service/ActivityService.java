package com.iisigroup.generic.module.oc64.service;


import com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum;
import com.iisigroup.generic.module.oc64.dto.activity.*;
import jakarta.validation.Valid;

import java.util.List;

public interface ActivityService {

    List<ActivityItemsDTO> findAll();

    ResponseDTO importData(ImportDataDTO importData);


    TypeDetailDTO getActivityItemsInfo(ActivityBaseEnum typeEnum);


    List<TypeDetailDTO> getActivityItemsInfo();


    List<BaseListDTO> getAllBase(@Valid String inventoryId);
}

