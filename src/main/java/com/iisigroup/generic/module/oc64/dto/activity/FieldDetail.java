package com.iisigroup.generic.module.oc64.dto.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class FieldDetail {

    private String name;
    private String description;
    private boolean isRequired;

    // 待確認需不需要
    // private String limitMessage;
    // private String example;
}
