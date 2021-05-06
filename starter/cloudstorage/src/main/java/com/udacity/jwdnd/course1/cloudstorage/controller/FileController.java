package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/files")
public class FileController {
    private final String ERROR = "error";
    private final String MESSAGE = "message";
    private final String SUCCESS = "success";
    private final String REDIRECT_HOME = "redirect:/home";

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

    private String sendError(RedirectAttributes attr, String message) {
        System.out.println(message);
        attr.addAttribute(ERROR, true);
        attr.addAttribute(MESSAGE, message);
        return REDIRECT_HOME;
    }

    private String sendSuccess(RedirectAttributes attr, String message) {
        System.out.println(message);
        attr.addAttribute(SUCCESS, true);
        attr.addAttribute(MESSAGE, message);
        return REDIRECT_HOME;
    }
}
