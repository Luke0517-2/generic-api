package com.iisigroup.generic.module.oc64.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iisigroup.ocapi.entity.Emissions;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface EmissionsService {
    List<Map> sumGroupBy(String field, String groupBy, Emissions queryDto) throws InvocationTargetException, IllegalAccessException;

    Map<String, List<Emissions>> groupByActivityItemBaseId(List<String> activityItemBaseIdList);

    BigDecimal sum(String field, Emissions queryDto) throws InvocationTargetException, IllegalAccessException;

    List<Emissions> saveAll(List<Emissions> emissionsList);

    List<Emissions> findByExample(Emissions queryEmissions);

    BigDecimal getBaseTotalEmission(String inventoryId, String activityItemBaseId);

    BigDecimal getTotalEmission(String inventoryId);

    Integer countEmissions(String inventoryId, String activityItemBaseId);

    List<Emissions> updateEmissionByBaseId(String baseId, BigDecimal totalUsage, String transferUnit);
}
