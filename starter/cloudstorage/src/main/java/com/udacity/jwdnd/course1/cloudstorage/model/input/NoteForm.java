package com.udacity.jwdnd.course1.cloudstorage.model.input;

import lombok.Data;
import lombok.experimental.Tolerate;

@Data
public class NoteForm {
    private String noteId;
    private String noteTitle;
    private String noteDescription;
}
