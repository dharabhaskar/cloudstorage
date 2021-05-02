package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final NoteService noteService;

    public HomeController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }


    @GetMapping
    public String getNotesByUser(Authentication auth, Model model) {
        User user = userService.getUserByUsername(auth.getName());
        List<Note> notes = noteService.getNotesByUser(user.getUserid());
        model.addAttribute("notes",notes);
        System.out.println(notes);
        return "home";
    }
}
