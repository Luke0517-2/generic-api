package com.iisigroup.generic.module.oc64.service.uncertainty.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iisigroup.generic.exception.ServiceException;
import com.iisigroup.generic.module.oc64.repository.ActivityItemsRepository;
import com.iisigroup.generic.module.oc64.repository.EmissionsRepository;
import com.iisigroup.generic.module.oc64.repository.VActivityItemBasesRepository;
import com.iisigroup.generic.module.oc64.service.EmissionsService;
import com.iisigroup.generic.module.oc64.service.uncertainty.UncertaintyService;
import com.iisigroup.generic.utils.Convert;
import com.iisigroup.generic.utils.JsonUtils;
import com.iisigroup.generic.utils.StringUtils;
import com.iisigroup.ocapi.entity.Emissions;
import com.iisigroup.ocapi.entity.VActivityItemBases;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.iisigroup.generic.constant.ResponseCodeEnum.ERROR_CODE_1101;
import static com.iisigroup.generic.constant.ResponseCodeEnum.ERROR_CODE_1201;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class EmissionsServiceImpl implements EmissionsService {
    private final EmissionsRepository emissionsRepository;
    private final VActivityItemBasesRepository vActivityItemBasesRepository;
    private final UncertaintyService uncertaintyService;
    private final EntityManager entityManager;
    private final ActivityItemsRepository activityItemsRepository;
    private static final Map<String, Map<String, BigDecimal>> UNIT_CONVERSION_MAP = new HashMap<>();

    static {
        Map<String, BigDecimal> kWhMap = new HashMap<>();
        kWhMap.put("MWh", BigDecimal.valueOf(0.001));

        Map<String, BigDecimal> MWhMap = new HashMap<>();
        MWhMap.put("kWh", BigDecimal.valueOf(1000));

        Map<String, BigDecimal> kgMap = new HashMap<>();
        kgMap.put("ton", BigDecimal.valueOf(0.001));

        Map<String, BigDecimal> tonMap = new HashMap<>();
        tonMap.put("kg", BigDecimal.valueOf(1000));

        Map<String, BigDecimal> hourMap = new HashMap<>();
        hourMap.put("day", BigDecimal.ONE.divide(BigDecimal.valueOf(24), MathContext.DECIMAL32));

        Map<String, BigDecimal> dayMap = new HashMap<>();
        dayMap.put("hour", BigDecimal.valueOf(24));

        //manhour to manYear
        Map<String, BigDecimal> manYearMap = new HashMap<>();
        manYearMap.put("manYear", BigDecimal.ONE.divide(BigDecimal.valueOf(24), MathContext.DECIMAL32).divide(BigDecimal.valueOf(365), MathContext.DECIMAL32));

        Map<String, BigDecimal> LMap = new HashMap<>();
        LMap.put("kL", BigDecimal.valueOf(0.001));

        Map<String, BigDecimal> kLMap = new HashMap<>();
        kLMap.put("L", BigDecimal.valueOf(1000));

        UNIT_CONVERSION_MAP.put("kWh", kWhMap);
        UNIT_CONVERSION_MAP.put("MWh", MWhMap);
        UNIT_CONVERSION_MAP.put("kg", kgMap);
        UNIT_CONVERSION_MAP.put("ton", tonMap);
        UNIT_CONVERSION_MAP.put("hour", hourMap);
        UNIT_CONVERSION_MAP.put("day", dayMap);
        UNIT_CONVERSION_MAP.put("L", LMap);
        UNIT_CONVERSION_MAP.put("kL", kLMap);
        UNIT_CONVERSION_MAP.put("manYear", manYearMap);
    }

    @Override
    public List<Map> sumGroupBy(String field, String groupBy, Emissions queryDto) throws InvocationTargetException, IllegalAccessException {
        checkNumberField(field);
        checkFieldExists(groupBy);

        log.info("field: " + field + ", groupBy: " + groupBy + ", queryDto: " + queryDto.getInventoryId());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Map> criteriaQuery = criteriaBuilder.createQuery(Map.class);
        Root<Emissions> root = criteriaQuery.from(Emissions.class);

        return entityManager
                .createQuery(
                        criteriaQuery
                                .multiselect(criteriaBuilder.sum(root.get(field)).alias(field), root.get(groupBy).alias(groupBy))
                                .groupBy(root.get(groupBy))
                                .where(getPredicate(criteriaBuilder, root, queryDto)))
                .getResultList();
    }

    @Override
    public Map<String, List<Emissions>> groupByActivityItemBaseId(List<String> activityItemBaseIdList) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Emissions> criteriaQuery = criteriaBuilder.createQuery(Emissions.class);
        Root<Emissions> emissionsRoot = criteriaQuery.from(Emissions.class);

        // Create a Predicate for filtering activityItemBaseId
        Predicate predicate = emissionsRoot.get("activityItemBaseId").in(activityItemBaseIdList);

        // Apply the where clause
        criteriaQuery.select(emissionsRoot).where(predicate);

        List<Emissions> results = entityManager.createQuery(criteriaQuery).getResultList();

        // Group by activityItemBaseId and collect to Map
        return results.stream()
                .collect(Collectors.groupingBy(Emissions::getActivityItemBaseId));
    }

    @Override
    public BigDecimal sum(String field, Emissions queryDto) throws InvocationTargetException, IllegalAccessException {
        checkNumberField(field);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<Emissions> root = criteriaQuery.from(Emissions.class);

        Object result = entityManager
                .createQuery(
                        criteriaQuery
                                .select(criteriaBuilder.sum(root.get(field)))
                                .where(getPredicate(criteriaBuilder, root, queryDto)))
                .getSingleResult();
        return Convert.toBigDecimal(result);
    }

    private void checkNumberField(String field) {
        Optional<PropertyDescriptor> propOptional = Arrays.stream(BeanUtils.getPropertyDescriptors(Emissions.class))
                .filter(prop -> prop.getName().equals(field))
                .findFirst();
        if (propOptional.isPresent()) {
            Class<?> type = propOptional.get().getPropertyType();
            if (!(type.equals(Long.class) || type.equals(Integer.class) || type.equals(Double.class) || type.equals(BigDecimal.class))) {
                throw new IllegalArgumentException("Field type not allowed: " + field);
            }
        } else {
            throw new IllegalArgumentException("Field not found: " + field);
        }
    }

    private void checkFieldExists(String field) {
        boolean fieldExists = Arrays.stream(BeanUtils.getPropertyDescriptors(Emissions.class))
                .map(PropertyDescriptor::getName)
                .anyMatch(name -> name.equals(field));
        if (!fieldExists) {
            throw new IllegalArgumentException("Field not found: " + field);
        }
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<Emissions> root, Emissions queryDto) throws InvocationTargetException, IllegalAccessException {
        Predicate predicate = criteriaBuilder.conjunction();
        for (PropertyDescriptor prop : BeanUtils.getPropertyDescriptors(queryDto.getClass())) {
            Object value = prop.getReadMethod().invoke(queryDto);
            if (StringUtils.isNull(value) || prop.getName().equals("class")) {
                continue;
            }
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(prop.getName()), value));
        }
        return predicate;
    }


    @Override
    public List<Emissions> saveAll(List<Emissions> emissionsList) {
        return emissionsRepository.saveAll(emissionsList);
    }


    @Override
    public List<Emissions> findByExample(Emissions queryEmissions) {
        return emissionsRepository.findAll(Example.of(queryEmissions));
    }


    @Override
    public BigDecimal getBaseTotalEmission(String inventoryId, String activityItemBaseId) {
        return emissionsRepository.sumBaseTotalEmission(inventoryId, activityItemBaseId);
    }

    @Override
    public BigDecimal getTotalEmission(String inventoryId) {
        return emissionsRepository.sumTotalEmission(inventoryId);
    }

    @Override
    public Integer countEmissions(String inventoryId, String activityItemBaseId) {
        return emissionsRepository.countEmissions(inventoryId, activityItemBaseId);
    }

    @Override
    public List<Emissions> updateEmissionByBaseId(String baseId, BigDecimal totalUsage, String transferUnit) {
        List<Emissions> emissionsList = emissionsRepository.findAll(Example.of(Emissions.builder().activityItemBaseId(baseId).deleteFlag(false).build()));
        if (emissionsList.isEmpty()) {
            throw new ServiceException(ERROR_CODE_1101);
        }

        BigDecimal totalUsageWithoutBio = getTotalUsageWithoutBio(baseId, totalUsage);

        emissionsList.forEach(item -> {
            item.setAmount("CO2".equals(item.getGreenhouseGas()) ? totalUsageWithoutBio : totalUsage);
            item.setUnit(transferUnit);
            item.setEmission(BigDecimal.ZERO);

            String optionsSystem = item.getOptionsSystem();
            try {
                if (StringUtils.isNotEmpty(optionsSystem)) { // optionsSystem!=null 代表已經選過係數了
                    Map<String, Object> map = JsonUtils.json2Map(optionsSystem);
                    String parameterUnit = ObjectUtils.isNotEmpty(map.get("parameterUnit")) ? StringUtils.substringAfter(map.get("parameterUnit").toString(), "/") : null;
                    BigDecimal newAmount = transformUnit(parameterUnit, item.getUnit(), item.getAmount()).setScale(4, RoundingMode.HALF_UP);
                    // 取得統一單位的使用量後計算總排放量
                    item.setEmission(newAmount
                            .multiply(item.getParameterValue())
                            .multiply(item.getGwpValue())
                            .movePointLeft(3)
                            .setScale(4, RoundingMode.HALF_UP));

                    map.put("newAmount", newAmount);
                    optionsSystem = JsonUtils.map2Json(map);
                }
                item.setOptionsSystem(optionsSystem);
            } catch (JsonProcessingException e) {
                throw new ServiceException(ERROR_CODE_1201);
            }
            uncertaintyService.updateSingleUncertaintyItemsWhenSingleEmissionsUpdate(item);
        });

        return emissionsRepository.saveAllAndFlush(emissionsList);
    }

    private BigDecimal getTotalUsageWithoutBio(String baseId, BigDecimal totalUsage) {
        /**
         * 使用總量如果達成以下條件，使用量加總需扣除生質能源使用量
         * (排放類型為移動燃燒、固定燃燒、工業製程) && (溫室氣體為CO2)
         * 準備計算排放量的使用總量 = 使用量加總 - 生質能源使用量
         */
        Optional<VActivityItemBases> itemBaseOptional = vActivityItemBasesRepository.findById(baseId);
        if (itemBaseOptional.isPresent()) {
            VActivityItemBases itemBase = itemBaseOptional.get();
            String type = itemBase.getType();
            if (!itemBase.getDeleteFlag()
                    && (type.equals("t-stationary-combustion") || type.equals("t-mobile-combustion") || type.equals("t-process-emissions"))
                    && itemBase.getGreenhouseGases().contains("CO2")) {
                BigDecimal bioEnergyUsage = activityItemsRepository.sumByBaseId(baseId);
                if (StringUtils.isNull(bioEnergyUsage)) {
                    return totalUsage;
                }
                return totalUsage.subtract(bioEnergyUsage);
            }
        }
        return totalUsage;
    }

    private BigDecimal transformUnit(String parameterUnit, String amountUnit, BigDecimal amount) {
        if (StringUtils.isBlank(parameterUnit) || StringUtils.isBlank(amountUnit)) {
            return amount;
        }

        if (Objects.equals(parameterUnit, amountUnit)) {
            return amount;
        }

        BigDecimal conversionRatio = UNIT_CONVERSION_MAP.getOrDefault(amountUnit, new HashMap<>()).getOrDefault(parameterUnit, BigDecimal.ONE);

        return amount.multiply(conversionRatio);
    }

}