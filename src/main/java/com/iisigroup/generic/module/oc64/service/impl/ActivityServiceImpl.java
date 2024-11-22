package com.iisigroup.generic.module.oc64.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iisigroup.generic.constant.ResponseCodeEnum;
import com.iisigroup.generic.exception.ServiceException;
import com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum;
import com.iisigroup.generic.module.oc64.constant.ActivityTypeEnum;
import com.iisigroup.generic.module.oc64.constant.EquipmentEnum;
import com.iisigroup.generic.module.oc64.constant.ExcelImportErrType;
import com.iisigroup.generic.module.oc64.dto.activity.*;
import com.iisigroup.generic.module.oc64.repository.ActivityItemBasesRepository;
import com.iisigroup.generic.module.oc64.repository.ActivityItemsRepository;
import com.iisigroup.generic.module.oc64.repository.EmissionsRepository;
import com.iisigroup.generic.module.oc64.repository.VActivityItemBasesRepository;
import com.iisigroup.generic.module.oc64.service.ActivityService;
import com.iisigroup.generic.module.oc64.service.EmissionsService;
import com.iisigroup.generic.module.oc64.service.uncertainty.UncertaintyService;
import com.iisigroup.generic.utils.Convert;
import com.iisigroup.generic.utils.I18nUtils;
import com.iisigroup.generic.utils.JsonUtils;
import com.iisigroup.generic.utils.StringUtils;
import com.iisigroup.ocapi.entity.ActivityItems;
import com.iisigroup.ocapi.entity.Emissions;
import com.iisigroup.ocapi.entity.VActivityItemBases;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.iisigroup.generic.constant.ResponseCodeEnum.*;
import static com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum.*;
import static com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum.MOBILE_COMBUSTION;
import static com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum.PURCHASED_WATER;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {


    private final ActivityItemBasesRepository activityItemBasesRepository;
    private final ActivityItemsRepository activityItemsRepository;

    private final EntityManager entityManager;
    private final EmissionsService emissionsService;
    private final EmissionsRepository emissionsRepository;
    private final UncertaintyService uncertaintyService;
    private final String activityItemCancelTemplate = "{\"date\":\"%s\",\"content\":\"%s\"}";
    private final VActivityItemBasesRepository vActivityItemBasesRepository;

//    private ReferenceFilesService referenceFilesService;
//    private final OilPricesService oilPricesService;
//    private ActivityItemBasesService activityItemBasesService;
//    private final AccountsService accountsService;
//    private final InboxRecordsService inboxRecordsService;



    private final MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);


    private static Map<String, ActivityBaseEnum> activityItemsEnumMap;
    private static Map<ActivityTypeEnum,List<String>> checkFieldsByActivityItemEnum;
    private static Map<String, EquipmentEnum> equipmentEnumMap;



    static {
        activityItemsEnumMap = new HashMap<>();
        checkFieldsByActivityItemEnum = new HashMap<>();
        equipmentEnumMap = new HashMap<>();

        activityItemsEnumMap.put("t-mobile-combustion", MOBILE_COMBUSTION); // 移動燃燒
        activityItemsEnumMap.put("t-stationary-combustion", STATIONARY_COMBUSTION); // 固定燃燒
        activityItemsEnumMap.put("t-process-emissions", PROCESS_EMISSIONS); // 工業製程
        activityItemsEnumMap.put("t-fugitive-emissions", FUGITIVE_EMISSIONS); // 逸散排放
        activityItemsEnumMap.put("t-externally-purchased-electricity", EXTERNAL_PURCHASED_ELECTRICITY); // 外購電力
        activityItemsEnumMap.put("t-externally-purchased-energy-steam", EXTERNAL_PURCHASED_STEAM); // 外購蒸汽
        activityItemsEnumMap.put("t-upstream-transportation", UPSTREAM_TRANSPORTATION); // 上游運輸及配送
        activityItemsEnumMap.put("t-business-travel", BUSINESS_TRAVEL); // 商務旅行
        activityItemsEnumMap.put("t-employee-commuting", EMPLOYEE_COMMUTING); // 員工通勤
        activityItemsEnumMap.put("t-downstream-transportation", DOWNSTREAM_TRANSPORTATION); // 下游運輸及配送
        activityItemsEnumMap.put("t-purchased-goods-and-service", PURCHASED_GOODS_AND_SERVICES); // 購買的產品與服務 (通用excel)
        activityItemsEnumMap.put("t-capital-goods", CAPITAL_GOODS); // 資本產品 (通用excel)
        activityItemsEnumMap.put("t-externally-purchased-fuel-and-resources", EXTERNAL_PURCHASED_FUEL_AND_RESOURCES); // 燃料與能源相關活動 (通用excel)
        activityItemsEnumMap.put("t-waste-treatment", WASTE_TREATMENT); // 營運過程產生的廢棄物
        activityItemsEnumMap.put("t-upstream-leasing", UPSTREAM_LEASING); // 上游資產租賃 (通用excel)
        activityItemsEnumMap.put("t-product-processing", PRODUCT_PROCESSING); // 售出產品的加工 (通用excel)
        activityItemsEnumMap.put("t-product-use", PRODUCT_USE); // 售出產品的使用 (通用excel)
        activityItemsEnumMap.put("t-end-of-life-treatment-of-products", END_OF_LIFE_TREATMENT_OF_PRODUCTS); // 售出產品的最終處理 (通用excel)
        activityItemsEnumMap.put("t-downstream-leasing", DOWNSTREAM_LEASING); // 下游資產租賃 (通用excel)
        activityItemsEnumMap.put("t-investment-emissions", INVESTMENT_EMISSIONS); // 投資 (通用excel)
        activityItemsEnumMap.put("t-other-indirect-emissions", OTHER_INDIRECT_EMISSIONS); // 其他來源 (通用excel)
        activityItemsEnumMap.put("t-franchises", FRANCHISES); // 特許經營(加盟) (通用excel)
        activityItemsEnumMap.put("t-purchased-water", PURCHASED_WATER); // 購買的產品與服務-自來水
        activityItemsEnumMap.put("t-fugitive-emissions-septic-tank", FUGITIVE_EMISSIONS_SEPTIC_TANK); // 逸散排放-化糞池(未接管)

        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.BIO_ENERGY, List.of("name", "usageAmount", "usageUnit", "bioEnergy"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.BASIC, List.of("name", "usageAmount", "usageUnit"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.MOBILE_COMBUSTION, List.of("name", "usageAmount", "usageUnit", "bioEnergy", "usageDate"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.LEAKAGE, List.of("name", "equipment", "intensityAmount", "months", "leakageUnit"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.ENERGY, List.of("source", "userNumber",
                "billMonth",
                "startDate", "endDate",
                "usageDays", "usageDegrees",
                "usageUnit"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.PURCHASED_WATER, List.of("source", "userNumber",
                "billMonth",
                "startDate", "endDate",
                "usageDays", "usageDegrees",
                "usageUnit"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.OUTSOURCING_STEAM, List.of("source", "userNumber", "billMonth", "usageAmount", "usageUnit"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.WEIGHT_TRANSPORT, List.of("shippingItem", "vehicleType", "weight", "weightUnit", "distance", "distanceUnit"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.PERSON_TRANSPORT, List.of("vehicleType", "personNumber", "distance", "distanceUnit"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.OPERATIONAL_WASTE_GENERATED, List.of("name", "usageAmount", "usageUnit", "wasteDisposal"));
        checkFieldsByActivityItemEnum.put(ActivityTypeEnum.SEPTI_TANK, List.of("worker", "workDays", "workTime"));

        equipmentEnumMap.put("t-domestic-refrigeration", EquipmentEnum.Domestic_Refrigeration); // 家用冷凍
        equipmentEnumMap.put("t-commercial-applications", EquipmentEnum.Commercial_Applications); // 商業應用
        equipmentEnumMap.put("t-commercial-refrigeration", EquipmentEnum.Commercial_Refrigeration); // 商業冷凍
        equipmentEnumMap.put("t-transport-refrigeration", EquipmentEnum.Transport_Refrigeration); // 運輸冷凍
        equipmentEnumMap.put("t-industrial-refrigeration", EquipmentEnum.Industrial_Refrigeration); // 工業冷凍
        equipmentEnumMap.put("t-chillers", EquipmentEnum.Chillers);  // 冷卻機
        equipmentEnumMap.put("t-residential-and-commercial-ac", EquipmentEnum.Residential_and_Commercial_AC); // 住宅和商業空調
        equipmentEnumMap.put("t-mobile-ac", EquipmentEnum.Mobile_AC); // 移動空調
        equipmentEnumMap.put("t-others", EquipmentEnum.Others); // 其他

    }


    public ActivityTypeEnum transItemToTypeEnum (ActivityBaseEnum baseEnum) {
        return switch (baseEnum) {
            case MOBILE_COMBUSTION -> ActivityTypeEnum.MOBILE_COMBUSTION;
            case STATIONARY_COMBUSTION, PROCESS_EMISSIONS -> ActivityTypeEnum.BIO_ENERGY;
            case FUGITIVE_EMISSIONS -> ActivityTypeEnum.LEAKAGE;
            case EXTERNAL_PURCHASED_ELECTRICITY -> ActivityTypeEnum.ENERGY;
            case PURCHASED_WATER -> ActivityTypeEnum.PURCHASED_WATER;
            case EXTERNAL_PURCHASED_STEAM -> ActivityTypeEnum.OUTSOURCING_STEAM;
            case UPSTREAM_TRANSPORTATION, DOWNSTREAM_TRANSPORTATION -> ActivityTypeEnum.WEIGHT_TRANSPORT;
            case BUSINESS_TRAVEL, EMPLOYEE_COMMUTING -> ActivityTypeEnum.PERSON_TRANSPORT;
            case PURCHASED_GOODS_AND_SERVICES,
                 CAPITAL_GOODS,
                 EXTERNAL_PURCHASED_FUEL_AND_RESOURCES,
                 UPSTREAM_LEASING ,PRODUCT_PROCESSING,
                 PRODUCT_USE,
                 END_OF_LIFE_TREATMENT_OF_PRODUCTS,
                 DOWNSTREAM_LEASING,
                 INVESTMENT_EMISSIONS,
                 OTHER_INDIRECT_EMISSIONS,
                 FRANCHISES -> ActivityTypeEnum.BASIC;
            case WASTE_TREATMENT -> ActivityTypeEnum.OPERATIONAL_WASTE_GENERATED;
            case FUGITIVE_EMISSIONS_SEPTIC_TANK -> ActivityTypeEnum.SEPTI_TANK;
            default -> throw new ServiceException(ERROR_CODE_9999);
        };
    }


    @Override
    public ResponseDTO importData(ImportDataDTO importDataDTO) {

        ResponseDTO responseDTO = new ResponseDTO();
        String baseId = importDataDTO.getBaseId();
        VActivityItemBases vactivityItemBases = vActivityItemBasesRepository
                .findById(baseId)
                .orElseThrow(() -> new ServiceException(ResponseCodeEnum.ERROR_CODE_1201, "ActivityItemBases base id with " + baseId +" not found" )
        );
        log.info("vActivityItemBases: {}", vactivityItemBases);

        String type = vactivityItemBases.getType();
        ActivityBaseEnum activityTypeEnum = activityItemsEnumMap.get(type);
        List<ActivityItemsDTO> activityItemList = importDataDTO.getActivityItems();

        List<ActivityItemImportErrDTO> errDTOList = validateActivityItemFields(activityItemList, activityTypeEnum);

        if (!errDTOList.isEmpty()) {
            responseDTO.setErrDTOList(errDTOList);
            responseDTO.setSize(0);
        } else {
            activityItemList.parallelStream().forEach(activityItemsDTO -> {
                activityItemsDTO.setBaseId(importDataDTO.getBaseId());
                activityItemsDTO.setActivityItemsEnum(activityTypeEnum);
                if ("LEAKAGE".equals(activityTypeEnum.toString())) {
                    String equipmentEnum =  equipmentEnumMap.get(vactivityItemBases.getEmissionSourceName()).name();
                    activityItemsDTO.setEquipmentEnum(equipmentEnum);
                }
            });
            importEntities(activityItemList);
            responseDTO.setErrDTOList(null);
            responseDTO.setSize(activityItemList.size());
        }

        return responseDTO;
    }



    @Override
    public List<ActivityItemsDTO> findAll() {
        //todo
        return List.of();
    }



    @Override
    public List<TypeDetailDTO> getActivityItemsInfo() {
        //todo
        return List.of();
    }

    @Override
    public List<BaseListDTO> getAllBase(String inventoryId) {
        List<VActivityItemBases> activityItemBasesList = vActivityItemBasesRepository.findByInventoryIdAndDeleteFlagFalseAndCheckedTrue(inventoryId);
        return activityItemBasesList.stream()
                .map(item -> {

                    ActivityBaseEnum activityItemsEnum = activityItemsEnumMap.get(item.getType());
                    if (activityItemsEnum == null) {
                        log.info("name:{} with type {}" , item.getName(), item.getType());
                        throw new ServiceException(ERROR_CODE_1108 , "ActivityItemsEnum not found with type " + item.getType());
                    }

                    BaseListDTO baseListDTO = new BaseListDTO();
                    baseListDTO.setBaseId(item.getId());
                    // 待確以下locale是否由前端傳入
                    baseListDTO.setTypeName(I18nUtils.getI18n(item.getType(),"TW"));
                    baseListDTO.setEmissionSourceName(I18nUtils.getI18n(item.getEmissionSourceName(),"TW"));
                    baseListDTO.setBaseName(I18nUtils.getI18n(item.getName(),"TW"));
                    baseListDTO.setActivityBaseEnum(activityItemsEnum);
                    baseListDTO.setActivityTypeEnum(transItemToTypeEnum(activityItemsEnum));
                    return baseListDTO;
                })
                .toList();
    }

    @Override
    public TypeDetailDTO getActivityItemsInfo(ActivityBaseEnum activityItemsEnum) {

        return null;
    }


    private void importEntities(List<ActivityItemsDTO> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1202, "entityList is empty");
        }
        String baseId = entityList.get(0).getBaseId();
        List<ActivityItems> activityItemsList = entityList.stream().peek(item -> {
            item.setId(null);
            item.setStatus(0);
            item.setDeleteFlag(false);
            compute(item);
        }).map(item -> {
            ActivityItems result = transferTool(item, ActivityItems.class);
            result.setReferenceFiles("[]");
            return result;
        }).toList();
        activityItemsRepository.saveAllAndFlush(activityItemsList);

        updateAfterActivityItemChange(baseId, entityList.get(0).getActivityItemsEnum(), entityList.get(0).getTransferUnit());
    }

    private <T> T transferTool(Object source, Class<T> targetClass) {
        T result = null;
        try {
            result = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            throw new ServiceException(ERROR_CODE_9999);
        }
        return result;
    }


    private List<ActivityItemImportErrDTO> validateActivityItemFields(List<ActivityItemsDTO> entityList, ActivityBaseEnum activityItemsEnum) {
        //record current_row
        //for loop
        List<ActivityItemImportErrDTO> errorList = new ArrayList<>();
        ActivityTypeEnum currentActivityTypeEnum = transItemToTypeEnum(activityItemsEnum);

        log.info("activityItemsEnum: {}", activityItemsEnum);
        log.info("currentActivityTypeEnum: {}", currentActivityTypeEnum);

        entityList.forEach(entity -> {
            if (currentActivityTypeEnum.equals(ActivityTypeEnum.SEPTI_TANK)){
                validateFieldIsEmptyForSEPTI_TANK(entity, errorList);
            }else{
                validateFieldIsEmpty(entity, checkFieldsByActivityItemEnum.get(currentActivityTypeEnum), errorList);
            }
        });
        if (!errorList.isEmpty()) {
            try {
                log.error("Excel import errorDetail: {}", JsonUtils.bean2Json(errorList));
            } catch (JsonProcessingException e) {
                throw new ServiceException(ERROR_CODE_1201, "converting excel errorDetail to Json");
            }
        }
        return errorList;
    }


    private void validateFieldIsEmpty(ActivityItemsDTO dto, List<String> attributeCheckList, List<ActivityItemImportErrDTO> errDtos) {
        attributeCheckList.forEach(attributeName -> {
            try {
                Field field = dto.getClass().getDeclaredField(attributeName);
                ReflectionUtils.makeAccessible(field);
                //
                if (ObjectUtils.isEmpty(field.get(dto))) {
                    errDtos.add(ActivityItemImportErrDTO.builder().extensionCode(dto.getExtensionCode()).errorType(ExcelImportErrType.EMPTY).field(attributeName).build());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void validateFieldIsEmptyForSEPTI_TANK(ActivityItemsDTO dto,List<ActivityItemImportErrDTO> errDtos){
        if ( dto.getTotalWorkTime() == null && (dto.getWorkTime() == null || dto.getWorker() == null)){
            errDtos.add(ActivityItemImportErrDTO.builder().extensionCode(dto.getExtensionCode()).errorType(ExcelImportErrType.EMPTY).field("totalWorkTime").build());
        }
    }



    /**
     * 在創建或更新活動項目後，更新總使用量、排放量和不確定性項目。
     *
     * @param activityItemBaseId 活動項目基礎的ID
     */
    public void updateAfterActivityItemChange(String activityItemBaseId, ActivityBaseEnum activityItemsEnum, String transferUnit) {

        // 計算總使用量
        BigDecimal totalUsage = sumFromActivityItems(usageSwitchByEnum(activityItemsEnum), ActivityItemsDTO.builder().baseId(activityItemBaseId).deleteFlag(false).build());

        // 更新排放量
        List<Emissions> emissionsList = emissionsService.updateEmissionByBaseId(activityItemBaseId, totalUsage, transferUnit);

        // 更新不確定性項目
        uncertaintyService.updateUncertaintyItemsWhenEmissionsUpdateByActivityItemBases(activityItemBaseId, emissionsList);
    }


    public BigDecimal sumFromActivityItems(String field, ActivityItemsDTO queryDto) {
        try {
            return sum(field, queryDto);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public BigDecimal sum(String field, ActivityItemsDTO queryDto) throws InvocationTargetException, IllegalAccessException {
        checkNumberField(field);
        queryDto.setDeleteFlag(false);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<ActivityItems> root = criteriaQuery.from(ActivityItems.class);

        Object result = entityManager
                .createQuery(
                        criteriaQuery
                                .select(criteriaBuilder.sum(root.get(field)))
                                .where(getPredicate(criteriaBuilder, root, queryDto)))
                .getSingleResult();
        //if return activityItem.emission is null return zero
        BigDecimal resultDecimal = Convert.toBigDecimal(result);
        if (resultDecimal == null) {
            return BigDecimal.ZERO;
        }
        return resultDecimal;
    }


    private void checkNumberField(String field) {
        Optional<PropertyDescriptor> propOptional = Arrays.stream(BeanUtils.getPropertyDescriptors(ActivityItems.class))
                .filter(prop -> prop.getName().equals(field))
                .findFirst();
        if (propOptional.isPresent()) {
            Class<?> type = propOptional.get().getPropertyType();
            if (!(type.equals(Long.class) || type.equals(Integer.class) || type.equals(Double.class) || type.equals(BigDecimal.class))) {
                throw new IllegalArgumentException("Field type not allowed: " + field);
            }
        } else {
            throw new IllegalArgumentException("Field not found:" + field);
        }
    }


    public void compute(ActivityItemsDTO activityItemsDTO) {
        if (activityItemsDTO.getActivityItemsEnum() == null) {
            throw new IllegalArgumentException("ActivityTypeEnum is required");
        }
        switch (transItemToTypeEnum(activityItemsDTO.getActivityItemsEnum())) {
            case MOBILE_COMBUSTION:
                //TODO 使用者於輸入時僅填寫金額，則根據單據日期去資料庫尋找對應油價，回推使用量
                //移動燃燒 轉換後單位為 kL
                activityItemsDTO.setTransferUsage(transferVolume(activityItemsDTO.getUsageUnit(), activityItemsDTO.getUsageAmount()));
                activityItemsDTO.setTransferUnit("kL");
                break;
            case BIO_ENERGY:
                //固定燃燒、工業製程 轉換後單位為 kL
                activityItemsDTO.setTransferUsage(transferVolume(activityItemsDTO.getUsageUnit(), activityItemsDTO.getUsageAmount()));
                activityItemsDTO.setTransferUnit("kL");
                break;
            case LEAKAGE:
                EquipmentEnum equipmentEnum = Optional.ofNullable(activityItemsDTO.getEquipmentEnum())
                        .map(EquipmentEnum::valueOf)
                        .orElseThrow(() -> new IllegalArgumentException("EquipmentEnum is required"));

                BigDecimal leakageRate = equipmentEnum.getLeakageRate();

                if (EquipmentEnum.Others.equals(equipmentEnum)) {
                    leakageRate = Optional.ofNullable(activityItemsDTO.getLeakageRate())
                            .orElseThrow(() -> new IllegalArgumentException("LeakageRate is required for 'Others' equipment type"));
                }
                //逸散量的計算公式為：設備台數 * 原始填充量 * (使用月份/12) * 設備逸散率(%)
                activityItemsDTO.setLeakageAmount(
                        activityItemsDTO.getIntensityAmount()
                                .multiply(BigDecimal.valueOf(activityItemsDTO.getEquipment()))
                                .multiply(BigDecimal.valueOf(activityItemsDTO.getMonths()).divide(BigDecimal.valueOf(12), mathContext))
                                .multiply(leakageRate).divide(BigDecimal.valueOf(100), mathContext)//百分比
                );
                activityItemsDTO.setLeakageRate(leakageRate);
                activityItemsDTO.setTransferLeakage(transferWeight(activityItemsDTO.getLeakageUnit(), activityItemsDTO.getLeakageAmount()));
                activityItemsDTO.setTransferUnit("ton");
                break;
            case ENERGY, PURCHASED_WATER:
                //帳單用電日數：單據統計起始日到單據統計迄止日經歷天數 (迄止-起始) + 1
                long between = ChronoUnit.DAYS.between(activityItemsDTO.getStartDate(), activityItemsDTO.getEndDate()) + 1;
                activityItemsDTO.setBillDays((int) between);
                //使用量計算公式為：(單據總用電度數 / 帳單用電日數) * 實際用電日數
                activityItemsDTO.setUsageAmount(
                        (activityItemsDTO.getUsageDegrees().multiply(new BigDecimal(activityItemsDTO.getUsageDays().toString(), mathContext)))
                                .divide(new BigDecimal(activityItemsDTO.getBillDays().toString()), mathContext));
                //電量轉換後單位為 MWh
                if (activityItemsDTO.getActivityItemsEnum().equals(ActivityTypeEnum.ENERGY.name())) {
                    activityItemsDTO.setTransferUsage(transferToMWh(activityItemsDTO.getUsageUnit(), activityItemsDTO.getUsageAmount()));
                    activityItemsDTO.setTransferUnit("MWh");
                }
                //自來水
                if (activityItemsDTO.getActivityItemsEnum().equals(PURCHASED_WATER.name())) {
                    activityItemsDTO.setTransferUsage(activityItemsDTO.getUsageAmount());
                    activityItemsDTO.setTransferUnit("m3");
                }
                //save||update parameter into optionSystem
                Map<String,Object> optionSystemMap = null;
                try {
                    if(org.apache.commons.lang3.StringUtils.isEmpty(activityItemsDTO.getOptionsSystem())){
                        optionSystemMap = new HashMap<>();
                    }else{
                        optionSystemMap = JsonUtils.json2Map(activityItemsDTO.getOptionsSystem());
                    }
                    BigDecimal billDegrees = activityItemsDTO.getUsagePercent().multiply(activityItemsDTO.getUsageDegrees(), mathContext);
                    optionSystemMap.put("usagePercent",activityItemsDTO.getUsagePercent().toString());
                    optionSystemMap.put("billDegrees",billDegrees.toString());
                    activityItemsDTO.setOptionsSystem(JsonUtils.map2Json(optionSystemMap));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                break;
            case WEIGHT_TRANSPORT:
                //延噸公里: 貨物重量(ton) * 行駛距離(km)
                activityItemsDTO.setTonKm(transferWeight(activityItemsDTO.getWeightUnit(), activityItemsDTO.getWeight()).multiply(transferDistance(activityItemsDTO.getDistanceUnit(), activityItemsDTO.getDistance())));
                activityItemsDTO.setTransferUnit("tkm");
                break;
            case PERSON_TRANSPORT:
                //延人公里: 搭乘人數 * 行駛距離(km)
                activityItemsDTO.setManKm(transferDistance(activityItemsDTO.getDistanceUnit(), activityItemsDTO.getDistance()).multiply(BigDecimal.valueOf(activityItemsDTO.getPersonNumber())));
                activityItemsDTO.setTransferUnit("pkm");
                break;
            case OUTSOURCING_STEAM:
            case OPERATIONAL_WASTE_GENERATED:
                //[營運過程產生的廢棄物]
                activityItemsDTO.setTransferUsage(transferWeight(activityItemsDTO.getUsageUnit(), activityItemsDTO.getUsageAmount()));
                activityItemsDTO.setTransferUnit("ton");
                break;
            case BASIC:
                //[外購蒸汽]
                //[資本財、燃料與能源相關活動、上游資產租賃、售出產品的加工、售出產品的使用、售出產品的最終處理、下游資產租賃、加盟或投資、其他來源、特許經營]
                // 無轉換
                activityItemsDTO.setTransferUsage(activityItemsDTO.getUsageAmount());
                activityItemsDTO.setTransferUnit(activityItemsDTO.getUsageUnit());
                break;
            case SEPTI_TANK:
                //逸散排放-化糞池
                //人數*總工作時數 = 總時數
                if (activityItemsDTO.getTotalWorkTime() == null ){
                    BigDecimal totalWorkTime = activityItemsDTO.getWorker().multiply(activityItemsDTO.getWorkTime(),mathContext);
                    activityItemsDTO.setTotalWorkTime(totalWorkTime);
                }
                activityItemsDTO.setTransferUsage(activityItemsDTO.getTotalWorkTime().divide(BigDecimal.valueOf(24), MathContext.DECIMAL32).divide(BigDecimal.valueOf(365), MathContext.DECIMAL32));
                activityItemsDTO.setTransferUnit("manYear");
                break;
            default:
                throw new IllegalArgumentException("ActivityTypeEnum not found: " + activityItemsDTO.getActivityItemsEnum());
        }
    }

    private BigDecimal transferVolume(String unit, BigDecimal usageAmount) {
        // 1 m3 = 1 KL
        if ("m3".equalsIgnoreCase(unit)){
            return usageAmount;
        }

        if (!"kL".equalsIgnoreCase(unit)) {
            return usageAmount.divide(BigDecimal.valueOf(1000));
        }
        return usageAmount;
    }



    /**
     * convert kg or ton to ton <br>
     *
     * @param unit
     * @param usageAmount
     * @return
     */
    private BigDecimal transferWeight(String unit, BigDecimal usageAmount) {
        if (!"ton".equalsIgnoreCase(unit)) {
            return usageAmount.divide(BigDecimal.valueOf(1000));
        }
        return usageAmount;
    }

    /**
     * convert m or km   to km <br>
     *
     * @param unit
     * @param usageAmount
     * @return
     */
    private BigDecimal transferDistance(String unit, BigDecimal usageAmount) {
        if (!"km".equalsIgnoreCase(unit)) {
            return usageAmount.divide(BigDecimal.valueOf(1000));
        }
        return usageAmount;
    }

    /**
     * convert kWh or MWh to MWh
     *
     * @param unit
     * @param usageAmount
     * @return
     */
    private BigDecimal transferToMWh(String unit, BigDecimal usageAmount) {
        if ("kWh".equals(unit)) {
            return usageAmount.divide(BigDecimal.valueOf(1000));
        }
        if ("mWh".equals(unit)) {
            return usageAmount.divide(BigDecimal.valueOf(1000000));
        }
        return usageAmount;
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<ActivityItems> root, ActivityItemsDTO queryDto) throws InvocationTargetException, IllegalAccessException {
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


    private String usageSwitchByEnum(ActivityBaseEnum activityItemsEnum) {
        return switch (transItemToTypeEnum(activityItemsEnum)) {
            case MOBILE_COMBUSTION, BIO_ENERGY, ENERGY, PURCHASED_WATER, OUTSOURCING_STEAM, BASIC,
                 OPERATIONAL_WASTE_GENERATED, SEPTI_TANK -> "transferUsage";
            case LEAKAGE -> "transferLeakage";
            case WEIGHT_TRANSPORT -> "tonKm";
            case PERSON_TRANSPORT -> "manKm";
            default ->
                    throw new IllegalStateException("Unexpected value: " + transItemToTypeEnum(activityItemsEnum));
        };
    }


}
