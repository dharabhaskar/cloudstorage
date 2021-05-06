package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.udacity.jwdnd.course1.cloudstorage.utils.Constants.*;

@Controller
@RequestMapping("/home/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(Authentication auth, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes attr) {
        int userid=userService.getUserByUsername(auth.getName()).getUserid();

        if (file.isEmpty()) {
            return sendError(attr, "Please attach a file.");
        }
        try {
            if(fileService.isFileAlreadyExists(file.getOriginalFilename(),userid)){
               return sendError(attr, "File with the same name already exists!!");
            }
            int fileId = fileService.storeFile(userid,file);

            if (fileId > 0) {
                sendSuccess(attr, "File uploaded successfully...");
            } else {
                sendError(attr, "File upload failed.");
            }
        } catch (Exception ex) {
            sendError(attr, "File upload failed.");
            ex.printStackTrace();
        }

        return REDIRECT_HOME;
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity viewFile(@PathVariable("fileId")int fileId){
        Files file=fileService.getFileById(fileId);

        return
        ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=\""+file.getFileName()+"\"")
                .body(file.getFileData());
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId")int fileId,RedirectAttributes attr){
        Files file=fileService.getFileById(fileId);
        try{
            fileService.deleteFile(fileId);
            return sendSuccess(attr,"File "+file.getFileName()+" deleted successfully.");
        }catch (Exception ex){
            return sendError(attr,file.getFileName()+" delete failed!!");
        }
    }

    private String sendError(RedirectAttributes attr, String message) {
        System.out.println(message);
        attr.addAttribute(ERROR, true);
        attr.addAttribute(MESSAGE_FILES, message);
        return REDIRECT_HOME;
    }

    private String sendSuccess(RedirectAttributes attr, String message) {
        System.out.println(message);
        attr.addAttribute(SUCCESS, true);
        attr.addAttribute(MESSAGE_FILES, message);
        return REDIRECT_HOME;
    }


}
