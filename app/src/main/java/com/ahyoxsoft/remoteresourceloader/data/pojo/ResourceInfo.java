package com.ahyoxsoft.remoteresourceloader.data.pojo;

import java.util.UUID;

public class ResourceInfo {
    private String baseUrl;
    private String fileName;
    private String resourceId;

    public ResourceInfo() {
        this("", "", UUID.randomUUID().toString());
    }

    public ResourceInfo(String baseUrl, String fileName) {
        this(baseUrl, fileName, UUID.randomUUID().toString());
    }

    public ResourceInfo(String baseUrl, String fileName, String resourceId) {
        this.baseUrl = baseUrl;
        this.fileName = fileName;
        this.resourceId = resourceId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "ResourceInfo{" +
                "baseUrl='" + baseUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", resourceId='" + resourceId + '\'' +
                '}';
    }
}
