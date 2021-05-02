package com.udacity.jwdnd.course1.cloudstorage.exception;

public class NoteOperationFailed extends RuntimeException{
    public NoteOperationFailed(String msg){
        super(msg);
    }
}
