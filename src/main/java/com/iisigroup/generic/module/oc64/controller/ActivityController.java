package com.iisigroup.generic.module.oc64.controller;


import com.iisigroup.generic.aop.Loggable;
import com.iisigroup.generic.dto.R;
import com.iisigroup.generic.dto.R2;
import com.iisigroup.generic.module.oc64.constant.ActivityBaseEnum;
import com.iisigroup.generic.module.oc64.dto.activity.*;
import com.iisigroup.generic.module.oc64.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ActivityController", description = "活動數據相關")
@Validated
@RestController
@RequestMapping("/oc64/activity")
@RequiredArgsConstructor
public class ActivityController {


    private final ActivityService activityService;

    @GetMapping("/findAll")
    @Loggable
    @Operation(description = "find all activity by enum")
    public R<List<ActivityItemsDTO>> findAll()  {

        return R.ok(activityService.findAll());
    }


    @PostMapping("/import")
    @Loggable
    @Operation(description = "insert data")
    public R2<String> importData(@Valid @RequestBody ImportDataDTO importData)  {
        ResponseDTO responseDTO = activityService.importData(importData);
        List<ActivityItemImportErrDTO> errDTOList = responseDTO.getErrDTOList();
        Integer successCount = responseDTO.getSize();

        if (errDTOList == null || errDTOList.isEmpty())
            return R2.ok("success import " + successCount + " data");
        else {
            return R2.ok("importData fail", errDTOList);
        }
    }

     /** 取得特定活動項目的填寫資訊
     * todo 待確認是否需要開發
     * */
    @PostMapping("/getAllActivityItemsInfo/{activityItemsEnum}")
    @Loggable
    @Operation(description = "get data")
    public R<TypeDetailDTO> getActivityItemsInfo(@NotNull(message = "activityItemsEnum is required")  @PathVariable String activityItemsEnum)  {
        ActivityBaseEnum typeEnum = ActivityBaseEnum.valueOf(activityItemsEnum);
        return R.ok(activityService.getActivityItemsInfo(typeEnum));
    }


    /**
     * 取得所有活動項目的填寫資訊
     * todo 待確認是否需要開發
     * */
    @PostMapping("/getAllActivityItemsInfo")
    @Loggable
    @Operation(description = "insert data")
    public R<List<TypeDetailDTO>> getActivityItemsInfo()  {
        return R.ok(activityService.getActivityItemsInfo());
    }


    /**
     * 取得所有base的資訊，供import資料使用
     * */
    @PostMapping("/getAllBase/{inventoryId}")
    @Loggable
    @Operation(description = "get All base information")
    public R<List<BaseListDTO>> getAllBase(@Valid @PathVariable String inventoryId)  {
        return R.ok(activityService.getAllBase(inventoryId));
    }


}
