package com.iisigroup.generic.module.oc64.dto.activity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Description:  OC14064 - com.iisigroup.ocapi.domain.common.ExcelImportDTO
 *
 */
@EqualsAndHashCode
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExcelImportDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String inventoryId;

    private String companyId;

    private String category;

    /**
     *  Excel base64 string
     */
    private String content;

    //是否模板下載
    private Boolean isTemplate;

    /**
     * Map : store what you want for your service
     */
    private Map<String,Object> serviceMap;

    private String locale;

    private String fileName;

}
