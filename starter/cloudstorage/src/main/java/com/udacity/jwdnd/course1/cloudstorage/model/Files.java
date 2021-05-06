package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class Files {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private int userId;
    private long fileSize;
    private byte[] fileData;

    public Files(Integer fileId, String fileName, String contentType, int userId, long fileSize, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.userId = userId;
        this.fileSize = fileSize;
        this.fileData = fileData;
    }
}
