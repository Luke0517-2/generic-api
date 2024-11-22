package com.iisigroup.generic.module.oc64.service.uncertainty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UncertaintyItemsReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * inventories.id
     * 查詢用
     */
    private String inventoryId;

    /**
     * uncertainty_items List
     * 查詢回傳，更新用
     */
    private List<UncertaintyItemsDTO> uncertaintyItemsDTOList;

}