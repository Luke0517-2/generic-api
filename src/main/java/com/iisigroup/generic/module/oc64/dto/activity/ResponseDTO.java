package com.iisigroup.generic.module.oc64.dto.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private Integer size;
    private List<ActivityItemImportErrDTO>  errDTOList;

}
