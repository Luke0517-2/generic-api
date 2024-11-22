package com.iisigroup.generic.module.oc64.dto.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.iisigroup.generic.constant.ResponseCodeEnum;
import com.iisigroup.generic.exception.ServiceException;
import com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum;
import com.iisigroup.generic.utils.JsonUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ActivityItemsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表格類型 com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum
     */
    @JsonProperty("activityItemsEnum")
    private ActivityBaseEnum activityItemsEnum;

    /**
     * 冷媒設備類型
     * 用來取得逸散率
     */
    @JsonProperty("equipmentEnum")
    private String equipmentEnum;

    /**
     * 由廠商提供的 unique code
     */
    @NotBlank(message = "extensionCode cannot be blank")
    @JsonProperty("extensionCode")
    private String extensionCode;

    @JsonProperty("id")
    private String id;

    /**
     * activity_item_bases.id
     */
    @JsonProperty("baseId")
    private String baseId;


    /**
     * 車號
     */
    @JsonProperty("carNumber")
    private String carNumber;


    /**
     * 名稱
     */
    @JsonProperty("name")
    private String name;


    /**
     * 日期
     */
    @JsonProperty("usageDate")
    private LocalDate usageDate;


    /**
     * 金額
     */
    @JsonProperty("amount")
    private Long amount;


    /**
     * 使用量
     */
    @JsonProperty("usageAmount")
    private BigDecimal usageAmount;


    /**
     * 使用量單位
     */
    @JsonProperty("usageUnit")
    private String usageUnit;


    /**
     * 轉換後使用量
     */
    @JsonProperty("transferUsage")
    private BigDecimal transferUsage;


    /**
     * 轉換後單位
     */
    @JsonProperty("transferUnit")
    private String transferUnit;


    /**
     * 是否生質能源, true: 是、false: 否
     */
    @JsonProperty("bioEnergy")
    private Boolean bioEnergy;


    /**
     * 設備台數
     */
    @JsonProperty("equipment")
    private Integer equipment;


    /**
     * 原始填充量
     */
    @JsonProperty("intensityAmount")
    private BigDecimal intensityAmount;


    /**
     * 使用月份
     */
    @JsonProperty("months")
    private Integer months;


    /**
     * 當前月份(SEPTI_TANK)
     */
    @JsonProperty("usedMonth")
    private Integer uesdMonth;


    /**
     * 設備逸散率
     */
    @JsonProperty("leakageRate")
    private BigDecimal leakageRate;


    /**
     * 逸散量
     */
    @JsonProperty("leakageAmount")
    private BigDecimal leakageAmount;


    /**
     * 逸散量單位
     */
    @JsonProperty("leakageUnit")
    private String leakageUnit;


    /**
     * 轉換後逸散量
     */
    @JsonProperty("transferLeakage")
    private BigDecimal transferLeakage;


    /**
     * 來源, 國家、民間、其他
     */
    @JsonProperty("source")
    private String source;


    /**
     * 電號、蒸氣號、水號
     */
    @JsonProperty("userNumber")
    private String userNumber;


    /**
     * 帳單月份, YYYY/MM
     */
    @JsonProperty("billMonth")
    private String billMonth;


    /**
     * 帳單使用日數
     */
    @JsonProperty("billDays")
    private Integer billDays;


    /**
     * 實際使用日數
     */
    @JsonProperty("usageDays")
    private Integer usageDays;


    /**
     * 單據統計起始日
     */
    @JsonProperty("startDate")
    private LocalDate startDate;


    /**
     * 單據統計迄止日
     */
    @JsonProperty("endDate")
    private LocalDate endDate;


    /**
     *實際用電度數/實際用水度數
     */
    @JsonProperty("usageDegrees")
    private BigDecimal usageDegrees;

    /**
     * 單據總用電度數/單據總用水度數
     */
    @JsonProperty("billDegrees")
    private BigDecimal billDegrees;

    /**
     * 比例
     */
    @JsonProperty("usagePercent")
    private BigDecimal usagePercent;




    /**
     * 運送品項
     */
    @JsonProperty("shippingItem")
    private String shippingItem;


    /**
     * 交通工具種類
     */
    @JsonProperty("vehicleType")
    private String vehicleType;


    /**
     * 運輸起點
     */
    @JsonProperty("startLocation")
    private String startLocation;


    /**
     * 運輸終點
     */
    @JsonProperty("endLocation")
    private String endLocation;


    /**
     * 貨物重量
     */
    @JsonProperty("weight")
    private BigDecimal weight;


    /**
     * 重量單位
     */
    @JsonProperty("weightUnit")
    private String weightUnit;


    /**
     * 行駛距離
     */
    @JsonProperty("distance")
    private BigDecimal distance;


    /**
     * 距離單位
     */
    @JsonProperty("distanceUnit")
    private String distanceUnit;


    /**
     * 延噸公里
     */
    @JsonProperty("tonKm")
    private BigDecimal tonKm;


    /**
     * 搭乘人數
     */
    @JsonProperty("personNumber")
    private Integer personNumber;


    /**
     * 延人公里
     */
    @JsonProperty("manKm")
    private BigDecimal manKm;


    /**
     * 廢棄物處理分類, 0:回收、1:焚化、2:掩埋
     */
    private Integer wasteDisposal;


    /**
     * 備注
     */
    @JsonProperty("itemRemark")
    private String itemRemark;


    /**
     * 佐證資料, JSON
     */
    @JsonProperty("referenceFiles")
    private List<String> referenceFiles;


    /**
     * 進度, 0: 待審核、1: 待修正、2: 通過
     */
    @JsonProperty("status")
    private Integer status;


    /**
     * 系統端延伸資料, JSON
     */
    @JsonProperty("optionsSystem")
    private String optionsSystem;


    /**
     * 客戶端延伸資料, JSON
     */
    @JsonProperty("optionsUser")
    private String optionsUser;


    /**
     * 是否刪除, true: 刪除、false: 未刪除
     */
    @JsonProperty("deleteFlag")
    private Boolean deleteFlag;


    /**
     * 員工數
     */
    @JsonProperty("worker")
    private BigDecimal worker;

    /**
     * 月工作天數
     */
    @JsonProperty("workDays")
    private BigDecimal workDays;

    /**
     * 工作時數
     */
    @JsonProperty("workTime")
    private BigDecimal workTime;



    /**
     * 總人時
     */
    @JsonProperty("totalWorkTime")
    private BigDecimal totalWorkTime;

    /**
     * 退回原因
     */
    private ArrayList<Map<String,Object>> reasonInfo;

    public void setReasonInfo(String reasonFromEntity){
        try {
            if(!StringUtils.isEmpty(reasonFromEntity)){
                ArrayList<Map<String,Object>> reasonInfoList =  JsonUtils.json2Bean(reasonFromEntity, new TypeReference<ArrayList<Map<String,Object>>>() {});
                Collections.reverse(reasonInfoList);
                this.reasonInfo = reasonInfoList;
            }
        } catch (JsonProcessingException e) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1201, "reasonInfo: "+ reasonFromEntity);
        }
    }

}