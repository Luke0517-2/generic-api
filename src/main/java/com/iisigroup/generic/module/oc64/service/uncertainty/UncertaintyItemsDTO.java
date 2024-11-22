package com.iisigroup.generic.module.oc64.service.uncertainty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UncertaintyItemsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 類別, 1~6 對應 C1~C6
     */
    private Integer category;

    /**
     * 排放類型
     */
    private String type;

    /**
     * 排放源名稱
     */
    private String emissionSourceName;

    /**
     * 原燃料名稱 activity_item_bases.name
     */
    private String name;

    /**
     * 序號 primary key
     */
    private String id;


    /**
     * uncertainties.id
     */
    private String uncertaintyId;


    /**
     * activity_item_bases.id
     */
    private String activityItemBaseId;


    /**
     * 活動數據之不確定性95%信賴區間之下限
     */
    private BigDecimal activityConfidenceLower;


    /**
     * 活動數據之不確定性95%信賴區間之上限
     */
    private BigDecimal activityConfidenceUpper;


    /**
     * 活動數據之不確定性數據來源
     */
    private String activityDataSource;


    /**
     * 溫室氣體種類, CO2、CH4、N2O、HCFCS、PFCS、SF6、NF3
     */
    private String greenhouseGas;


    /**
     * 排放係數之不確定性95%信賴區間之下限
     */
    private BigDecimal parameterConfidenceLower;


    /**
     * 排放係數之不確定性95%信賴區間之上限
     */
    private BigDecimal parameterConfidenceUpper;


    /**
     * 排放係數之不確定性資料來源
     */
    private String parameterDataSource;


    /**
     * 單一溫室氣體之不確定性95%信賴區間之下限
     */
    private BigDecimal greenhouseGasConfidenceLower;


    /**
     * 單一溫室氣體之不確定性9::5%信賴區間之上限
     */
    private BigDecimal greenhouseGasConfidenceUpper;


    /**
     * 碳排量
     */
    private BigDecimal emission;


    /**
     * 系統端延伸資料, JSON
     */
    private String optionsSystem;


    /**
     * 客戶端延伸資料, JSON
     */
    private String optionsUser;


}