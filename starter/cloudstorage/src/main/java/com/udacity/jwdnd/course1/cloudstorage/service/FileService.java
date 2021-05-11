package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int storeFile(int userid,MultipartFile file) throws IOException {
        Files files=new Files(null,
                file.getOriginalFilename(),
                file.getContentType(),userid,file.getSize(),file.getBytes());

//        return fileMapper.addFile(Files.builder()
//                .fileName(file.getOriginalFilename())
//                .contentType(file.getContentType())
//                .fileSize(file.getSize())
//                .fileData(file.getBytes())
//                .userId(userid)
//                .build());

        return fileMapper.addFile(files);
    }

    public boolean isFileAlreadyExists(String fileName,int userId) {
       return fileMapper.getFileByName(fileName,userId) !=null;
    }

    public List<Files> getFilesByUser(int userid) {
        return fileMapper.getAllFilesByUser(userid);
    }

    public Files getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    public void deleteFile(int fileId) {
        fileMapper.deleteFile(fileId);
    }
}
