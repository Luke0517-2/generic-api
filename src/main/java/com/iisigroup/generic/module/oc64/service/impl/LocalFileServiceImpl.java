package com.iisigroup.generic.module.oc64.service.impl;

import com.iisigroup.generic.config.FileProperties;
import com.iisigroup.generic.constant.ResponseCodeEnum;
import com.iisigroup.generic.exception.ServiceException;
import com.iisigroup.generic.module.oc64.repository.FileNamesRepository;
import com.iisigroup.generic.module.oc64.service.LocalFileService;
import com.iisigroup.generic.utils.StringUtils;
import com.iisigroup.ocapi.entity.FileNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocalFileServiceImpl implements LocalFileService {
    private final FileNamesRepository fileNamesRepository;
    private final FileProperties fileProperties;

    @Override
    public void delete(String bindingId, String fileName) {
        checkDeleteFile(bindingId, fileName);
        fileNamesRepository.findByBindingIdAndOriginalNameAndDeleteFlagFalse(bindingId, fileName).stream()
                .findAny()
                .ifPresent(fileNames -> {
                    fileNames.setDeleteFlag(true);
                    fileNamesRepository.save(fileNames);
                });
    }

    @Override
    public Resource download(String bindingId, String fileName) {
        FileNames fileMapping = checkDownloadFile(bindingId, fileName);
        String[] split = bindingId.split("-");
        File path = Paths.get(
                fileProperties.getUploadDir(),
                split[0],
                split[1],
                split[2],
                split[3],
                split[4],
                fileMapping.getFileName()
        ).toFile();
        checkFilePath(path);
        return new FileSystemResource(path);
    }

    @Override
    public void upload(String bindingIdAndExtensionCode, String fileName, byte[] file) throws IOException {
        String bindingId = checkUploadFile(bindingIdAndExtensionCode, fileName, file);
        String[] split = bindingId.split("-");
        String newFileName = UUID.randomUUID().toString();

        File path = Paths.get(
                fileProperties.getUploadDir(),
                split[0],
                split[1],
                split[2],
                split[3],
                split[4],
                newFileName
        ).toFile();
        createParentDir(path);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(path, file);
        createFileMapping(bindingId, fileName, newFileName);
    }


    @Override
    public Resource getEmptyCustomizeReport(String inventoryId, String locale) throws IOException {
        String[] split = inventoryId.split("-");
        File path = Paths.get(
                fileProperties.getUploadDir(),
                split[0],
                split[1],
                split[2],
                split[3],
                split[4],
                "report",
                "customize_report_" + locale + ".docx"
        ).toFile();

        try {
            checkFilePath(path);
            return new FileSystemResource(path);
        } catch (ServiceException e) {
            log.error("there are no customize report with inventoryId:{}, locale:{}, download default template", inventoryId, locale);

            ClassPathResource resource = new ClassPathResource("template_sample/" + locale + "/REPORTS.docx");
            File tempFile = Files.createTempFile("temp-", ".docx").toFile();
            try (InputStream inputStream = resource.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return new FileSystemResource(tempFile);
        }
    }

    /**
     * 不同專案會有不同的自訂報告書模板，並以語系重新進行命名
     */
    @Override
    public void uploadCustomizeReport(String inventoryId, String locale, byte[] file) throws IOException {
        if (!isExtensionDocx(file)) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1107, " the file extension only allow docx");
        }
        if (inventoryId == null || file == null) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1107, " inventoryId, file are required");
        }
        if (locale == null)
            locale = "zh-TW";

        String[] split = inventoryId.split("-");
        File path = Paths.get(
                fileProperties.getUploadDir(),
                split[0],
                split[1],
                split[2],
                split[3],
                split[4],
                "report",
                "customize_report_" + locale + ".docx"
        ).toFile();
        createParentDir(path);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(path, file);
    }

    private static boolean isExtensionDocx(byte[] file) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(file);
             XWPFDocument doc = new XWPFDocument(bais)) {
            // 如果能成功創建 XWPFDocument，則說明這是有效的 DOCX 文件
            return true;
        } catch (IOException | POIXMLException e) {
            // 如果創建失敗，則說明這不是 DOCX 文件
            return false;
        }
    }


    @Override
    public List<String> findFileNames(String bindingId) {
        if (StringUtils.isBlank(bindingId)) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1107, "bindingId is required");
        }
        return fileNamesRepository.findByBindingIdAndDeleteFlagFalse(bindingId).stream()
                .map(FileNames::getOriginalName)
                .toList();
    }

    /**
     * Common
     */
    @Override
    public void checkFilePath(File file) {
        if (!file.exists() || file.isDirectory()) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1101, "file path not found");
        }
    }

    private void createParentDir(File file) {
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    private void checkDeleteFile(String bindingId, String filename) {
        if (StringUtils.isBlank(bindingId) || StringUtils.isBlank(filename)) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1101, "bindingId, fileName are required");
        }

        if (!bindingId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1101, "bindingId must be UUID format");
        }
    }

    private FileNames checkDownloadFile(String bindingId, String filename) {
        if (StringUtils.isBlank(bindingId) || StringUtils.isBlank(filename)) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1101, "bindingId, fileName are required");
        }

        if (!bindingId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1101, "bindingId must be UUID format");
        }

        return fileNamesRepository.findByBindingIdAndDeleteFlagFalse(bindingId).stream()
                .filter(mapping -> mapping.getOriginalName().equals(filename))
                .findFirst()
                .orElseThrow(() -> new ServiceException(ResponseCodeEnum.ERROR_CODE_1101, "please check filename and bindingId"));
    }

    private String checkUploadFile(String bindingIdAndExtensionCode, String filename, byte[] file) {
        if (StringUtils.isBlank(bindingIdAndExtensionCode) || StringUtils.isBlank(filename) || StringUtils.isNull(file)) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1107, "bindingId, filename, file are required");
        }

        String bindingId = "";
        if (bindingIdAndExtensionCode.length() >= 36) {
            bindingId = bindingIdAndExtensionCode.substring(0, 36);
            if (!bindingId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
                throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1107, "bindingId first 36 characters must be UUID format");
            }
        } else {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1107, "bindingId first 36 characters must be UUID format");
        }

        List<FileNames> fileMappings = fileNamesRepository.findByBindingIdAndDeleteFlagFalse(bindingId);

        if (fileMappings.size() >= 5) {
            throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1107, "file count limit exceed");
        }

        fileMappings.stream()
                .filter(fileNames -> fileNames.getOriginalName().equals(filename))
                .findAny()
                .ifPresent(fileNames -> {
                    throw new ServiceException(ResponseCodeEnum.ERROR_CODE_1102);
                });

        return bindingId;
    }

    private void createFileMapping(String bindingId, String originalName, String newFileName) {
        fileNamesRepository.save(FileNames.builder()
                .bindingId(bindingId)
                .originalName(originalName)
                .fileName(newFileName)
                .deleteFlag(false)
                .build());
    }
}
