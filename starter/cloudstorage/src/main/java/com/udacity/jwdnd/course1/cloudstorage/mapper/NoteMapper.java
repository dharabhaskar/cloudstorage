package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoteMapper {
    //Get all notes of a specific user.
    @Select("select * from notes where username=#{username}")
    public List<Note> getNotesByUser();

    //Insert note
    @Insert("insert into notes(noteid,notetitle,notedescrption) values(#{id},#{title},#{description})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int insertNote(Note note);

    //Delete note

    //Update date.
}
