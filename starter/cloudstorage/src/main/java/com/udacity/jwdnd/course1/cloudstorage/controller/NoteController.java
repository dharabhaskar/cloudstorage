package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.NoteOperationFailed;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.input.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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


    @PostMapping()
    public String addNote(Authentication auth, @ModelAttribute NoteForm noteForm, RedirectAttributes attr) {
        User user = userService.getUserByUsername(auth.getName());

        System.out.println("Logged in user: "+user.getUsername());

        noteForm.setNoteId(noteForm.getNoteId().equals("")?"0":noteForm.getNoteId());

        Note note = Note.builder()
                .noteid(Integer.parseInt(noteForm.getNoteId()))
                .title(noteForm.getNoteTitle())
                .description(noteForm.getNoteDescription())
                .userid(user.getUserid())
                .build();
        String message="";

        try {
            message=noteService.addOrUpdate(note);
            attr.addAttribute("success", true);
        } catch (NoteOperationFailed ex) {
            attr.addAttribute("error", true);
            message=ex.getMessage();
            ex.printStackTrace();
        }
        attr.addAttribute("message",message);

        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable int noteId,RedirectAttributes attr){
        noteService.deleteNote(noteId);
        String message="";

        try {
            noteService.deleteNote(noteId);
            attr.addAttribute("success", true);
            message="Note deleted successfully....";
        } catch (Exception ex) {
            attr.addAttribute("error", true);
            message="Note delete failed.";
            ex.printStackTrace();
        }
        attr.addAttribute("message",message);

        return "redirect:/home";
    }
}
