package com.iisigroup.generic.module.oc64.service;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface LocalFileService {
    void delete(String bindingId, String fileName);

    Resource download(String bindingId, String fileName);

    void upload(String bindingIdAndExtensionCode, String fileName, byte[] file) throws IOException;

    Resource getEmptyCustomizeReport(String inventoryId, String locale) throws IOException;

    void uploadCustomizeReport(String inventoryId, String locale, byte[] file) throws IOException;

    List<String> findFileNames(String bindingId);

    void checkFilePath(File file);
}
