package com.udacity.jwdnd.course1.cloudstorage.service;


import com.udacity.jwdnd.course1.cloudstorage.exception.NoteOperationFailed;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }


    public void deleteNote(int noteid){
        noteMapper.deleteNote(noteid);
    }
    public List<Note> getNotesByUser(int userid) {
        return noteMapper.getNotesByUser(userid);
    }

    public String addOrUpdate(Note note) {
        boolean isUpdate=note.getNoteid()>0;
        try{
            if(isUpdate){
                noteMapper.updateNote(note);
                return "Note updated successfully.";
            }else{
                noteMapper.insertNote(note);
                return "Note added successfully";
            }
        }catch (Exception ex){
            throw new NoteOperationFailed("Note "+(isUpdate?" update ":" add")+" failed");
        }
    }
}
