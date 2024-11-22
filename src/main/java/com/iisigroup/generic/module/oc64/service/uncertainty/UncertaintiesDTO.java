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
public class UncertaintiesDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * inventories.id
     */
    @NotNull(message = "inventoryId is required")
    private String inventoryId;


    /**
     * 不確定性評估之排放佔比%
     */
    private BigDecimal emissionRatio;


    /**
     * 不確定性95%信賴區間之下限
     */
    private BigDecimal confidenceLower;


    /**
     * 不確定性95%信賴區間之上限
     */
    private BigDecimal confidenceUpper;

    /**
     * 系統端延伸資料, JSON
     */
    private String optionsSystem;


    /**
     * 客戶端延伸資料, JSON
     */
    private String optionsUser;


}