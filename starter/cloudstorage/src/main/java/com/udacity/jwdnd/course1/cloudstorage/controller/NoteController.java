package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.input.NoteForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {
    @PostMapping()
    public String addNote(@ModelAttribute NoteForm noteForm, Model model){
        System.out.println("Note add controller called.");
        System.out.println(noteForm);

        return "home";
    }
}
