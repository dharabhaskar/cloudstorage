package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("select * from FILES where userid=#{userid}")
    public List<Files> getAllFilesByUser(int userid);

    @Insert("insert into FILES(filename,contenttype,filesize,userid,filedata) values(" +
            "#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(keyProperty = "fileId",useGeneratedKeys = true)
    public int addFile(Files file);

    @Select("select * from FILES where filename=#{fileName} and userid=#{userId}")
    Files getFileByName(String fileName,int userId);
}
