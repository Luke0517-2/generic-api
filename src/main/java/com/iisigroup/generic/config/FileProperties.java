package com.iisigroup.generic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String uploadDir = "/tmp/ocarbon-file/upload-dir";
    private String uploadTemplateDir = "/tmp/ocarbon-file/upload-template-dir";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadTemplateDir() {
        return uploadTemplateDir;
    }

    public void setUploadTemplateDir(String uploadTemplateDir) {
        this.uploadTemplateDir = uploadTemplateDir;
    }
}
