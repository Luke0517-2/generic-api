package com.iisigroup.generic.module.oc64.dto.activity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class TypeDetailDTO {

    private String ActivityTypeName;
    private Map<String, FieldDetail> Detail;

}
