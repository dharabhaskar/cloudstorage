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
    @Select("select * from NOTES where userid=#{userid}")
    public List<Note> getNotesByUser(int userid);

    //Insert note
    @Insert("insert into NOTES(notetitle,notedescription) values(#{title},#{description})")
    @Options(useGeneratedKeys = true,keyProperty = "noteid")
    public int insertNote(Note note);


    //Delete note

    //Update date.
}
