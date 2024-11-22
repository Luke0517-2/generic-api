package com.iisigroup.generic.module.oc64.dto.activity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ImportDataDTO {

    // 看起來不需要
    private String inventoryId;

    // 用來mapping到對應的schema，原則上不需要
    private String companyId;

    @Valid
    private List<ActivityItemsDTO> activityItems;


    //todo 由另外一隻API得到
    private String baseId;

    // 可以從base來，不用傳
    @Deprecated
    private ActivityBaseEnum activityTypeEnum;


    @Deprecated
    private String equipmentEnum;

}
