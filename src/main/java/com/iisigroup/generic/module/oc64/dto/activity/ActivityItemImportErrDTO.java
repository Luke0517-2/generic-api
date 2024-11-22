package com.iisigroup.generic.module.oc64.dto.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iisigroup.generic.module.oc64.constant.ExcelImportErrType;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ActivityItemImportErrDTO implements Serializable {

    /**
     * 由客戶傳進來的特定ID
     * */
    private String extensionCode;

    /**
     * 錯誤類型
     */
    private ExcelImportErrType errorType;

    /**
     * 錯誤欄位
     */
    private String field;

}
