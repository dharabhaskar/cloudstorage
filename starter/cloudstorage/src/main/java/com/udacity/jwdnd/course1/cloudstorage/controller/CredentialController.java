package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.utils.Constants.*;

@Controller
@RequestMapping("/home/credentials")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String getCredentials(Authentication auth, Model model) {
        int userId = userService.getUserByUsername(auth.getName()).getUserid();
        List<Credential> credentials = credentialService.getAllCredentials(userId);

        model.addAttribute(Constants.CREDENTIALS, credentials);

        return Constants.REDIRECT_HOME;
    }

    @PostMapping()
    public String addCredentials(Credential credential, Authentication auth, RedirectAttributes attr) {
        int userId = userService.getUserByUsername(auth.getName()).getUserid();

        if (credential.getCredentialId() == null) {
            try {
                int id = credentialService.insertCredential(userId, credential);
                if (id > 0) {
                    return sendSuccess(attr, "Your credentials added successfully.");
                } else {
                    return sendError(attr, "Add credential failed.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return sendError(attr, "Add credential failed.");
            }
        } else {
            try {
                credentialService.updateCredential(userId, credential);
                return sendSuccess(attr, "Your credentials updated successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
                return sendError(attr, "Credential update failed.");
            }
        }

    }

    //Delete Credentials
    @GetMapping("/delete/{credentialId}")
    public String deleteNote(@PathVariable("credentialId") Integer credentialId,
                             RedirectAttributes attr,
                             Authentication authentication) {

        Integer currentUserId = userService.getUserByUsername(authentication.getName()).getUserid();
        String credentialUrl = null;

        try {

            Credential credentials = credentialService.getCredentialById(credentialId);
            credentialUrl = credentials.getUrl();
            credentialService.deleteCredential(credentialId, currentUserId);
            return sendSuccess(attr, "You successfully deleted credentials for "
                    + credentialUrl + " !");

        } catch (Exception e) {
            sendError(attr,
                    "There was an error deleting the credentials for " +
                            credentialUrl + "!");
        }

        return "redirect:/home";

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
