package com.iisigroup.generic.module.oc64.service.uncertainty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
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
public class UncertaintyDefaultsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 數據來源
     */
    @NotNull(message = "source is required")
    private String source;


    /**
     * 類別, 1~6 對應 C1~C6
     */
    @NotNull(message = "category is required")
    private Integer category;


    /**
     * 排放類型
     */
    @NotNull(message = "type is required")
    private String type;


    /**
     * 原燃物料
     */
    @NotNull(message = "fuelMaterial is required")
    private String fuelMaterial;


    /**
     * 溫室氣體種類, CO2、CH4、N2O、HCFCS、PFCS、SF6、NF3
     */
    @NotNull(message = "greenhouseGas is required")
    private String greenhouseGas;


    /**
     * 不確定性95%信賴區間之下限
     */
    private BigDecimal lowerBound;


    /**
     * 不確定性95%信賴區間之上限
     */
    private BigDecimal upperBound;


    /**
     * 系統端延伸資料, JSON
     */
    private String optionsSystem;


    /**
     * 客戶端延伸資料, JSON
     */
    private String optionsUser;


}
