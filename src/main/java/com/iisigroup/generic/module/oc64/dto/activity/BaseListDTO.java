package com.iisigroup.generic.module.oc64.dto.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum;
import com.iisigroup.generic.module.oc64.constant.ActivityTypeEnum;
import lombok.*;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class BaseListDTO {

    private String baseId;
    private String typeName;
    private String emissionSourceName;
    private String baseName;


    /**
     * 以下是為了測試方便，正式release不需要
     * */
    private ActivityBaseEnum activityBaseEnum;
    private ActivityTypeEnum activityTypeEnum;
}
