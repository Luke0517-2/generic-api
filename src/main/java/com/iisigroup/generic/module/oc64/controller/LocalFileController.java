package com.iisigroup.generic.module.oc64.controller;

import com.iisigroup.generic.aop.Loggable;
import com.iisigroup.generic.constant.Constants;
import com.iisigroup.generic.dto.R;
import com.iisigroup.generic.module.oc64.service.LocalFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "LocalFileController", description = "檔案")
@Validated
@RestController
@RequestMapping("/oc64/file")
@RequiredArgsConstructor
public class LocalFileController {
    private final LocalFileService localFileService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Loggable
    @Operation(description = "檔案上傳")
    public R<String> upload(@NotNull(message = "file is required") @RequestPart MultipartFile file,
                            @RequestParam String bindingIdAndExtensionCode) throws IOException {
        localFileService.upload(bindingIdAndExtensionCode, file.getOriginalFilename(), file.getBytes());
        return R.ok(Constants.SUCCESS_MSG);
    }
}
