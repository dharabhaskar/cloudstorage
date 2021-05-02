package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Note {
    private int noteid;
    private String title;
    private String description;
    private int userid;

    @Tolerate
    public Note(){}
}
