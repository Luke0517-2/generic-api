package com.iisigroup.generic.module.oc64.service.uncertainty.impl;


import com.iisigroup.generic.constant.Constants;
import com.iisigroup.generic.constant.ResponseCodeEnum;
import com.iisigroup.generic.exception.ServiceException;
import com.iisigroup.generic.module.oc64.repository.ActivityItemBasesRepository;
import com.iisigroup.generic.module.oc64.repository.EmissionsRepository;
import com.iisigroup.generic.module.oc64.repository.UncertaintiesRepository;
import com.iisigroup.generic.module.oc64.repository.UncertaintyItemsRepository;
import com.iisigroup.generic.module.oc64.service.uncertainty.UncertaintyService;
import com.iisigroup.ocapi.entity.*;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.MathContext;
import java.util.*;


/**
 * ClassName:UncertaintiesServiceImpl
 * Package:com.iisigroup.ocapi.domain.uncertainties.impl
 * Description:
 *
 * @Date:2024/3/20 上午 11:11
 * @Author:2208021
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UncertaintyServiceImpl implements UncertaintyService {


    private final UncertaintiesRepository uncertaintiesRepository;


    private final UncertaintyItemsRepository uncertaintyItemsRepository;

    private final EmissionsRepository emissionsRepository;
    private final EntityManager entityManager;
    private final ActivityItemBasesRepository activityItemBasesRepository;
    /**
     * 計算方面，小數點取10位數，四捨五入
     * 後續要更改從這邊改
     */
    private final MathContext mathContext = Constants.mathContext;



    @Override
    public String updateUncertaintyItemsWhenEmissionsUpdateByActivityItemBases(String activityItemBasesId, List<Emissions> emissionsList) {
        // 更新不確定性項目中的排放量
        List<UncertaintyItems> uncertaintyItemsList = uncertaintyItemsRepository.findAll(Example.of(UncertaintyItems.builder().activityItemBaseId(activityItemBasesId).deleteFlag(false).build()));
        if (CollectionUtils.isEmpty(uncertaintyItemsList)) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1101);
        }
        uncertaintyItemsList.stream()
                .peek(uncertaintyItems -> {
                    emissionsList.stream()
                            .filter(emissionsEntity -> StringUtils.equals(emissionsEntity.getGreenhouseGas(), uncertaintyItems.getGreenhouseGas()))
                            .findFirst()
                            .ifPresent(emissionsEntity -> uncertaintyItems.setEmission(emissionsEntity.getEmission()));
                })
                .toList();
        uncertaintyItemsRepository.saveAll(uncertaintyItemsList);
        return Constants.SUCCESS_MSG;
    }

    @Override
    public String updateSingleUncertaintyItemsWhenSingleEmissionsUpdate(Emissions emissions) {
        if (ObjectUtils.isEmpty(emissions.getEmission())){
            throw new IllegalStateException("Emission is required");
        }
        UncertaintyItems uncertaintyItems = uncertaintyItemsRepository.findOne(
                Example.of(UncertaintyItems.builder()
                        .activityItemBaseId(emissions.getActivityItemBaseId())
                        .greenhouseGas(emissions.getGreenhouseGas())
                        .deleteFlag(false)
                        .build())
            ).orElseThrow(() -> new ServiceException(ResponseCodeEnum.ERROR_CODE_1101));
        uncertaintyItems.setEmission(emissions.getEmission());
        uncertaintyItemsRepository.save(uncertaintyItems);
        return Constants.SUCCESS_MSG;
    }

}
