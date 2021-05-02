package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.input.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/home/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @GetMapping
    public String getNotesByUser(Authentication auth,RedirectAttributes attr) {
        User user = userService.getUserByUsername(auth.getName());
        try {
            List<Note> notes = noteService.getNotesByUser(user.getUserid());
            attr.addAttribute("notes",notes);
            attr.addAttribute("success", true);
        } catch (Exception ex) {
            attr.addAttribute("error", true);
            ex.printStackTrace();
        }
        attr.addAttribute("message","");



        return "home";
    }

    @PostMapping()
    public String addNote(Authentication auth, @ModelAttribute NoteForm noteForm, RedirectAttributes attr) {
        User user = userService.getUserByUsername(auth.getName());

        Note note = Note.builder()
                .title(noteForm.getNoteTitle())
                .description(noteForm.getNoteDescription())
                .userid(user.getUserid())
                .build();
        String message="";

        try {
            noteService.addNote(note);
            attr.addAttribute("success", true);
            message="Note added successfully....";
        } catch (Exception ex) {
            attr.addAttribute("error", true);
            message="Note add failed.";
            ex.printStackTrace();
        }
        attr.addAttribute("message",message);

        return "redirect:/home";
    }
}
