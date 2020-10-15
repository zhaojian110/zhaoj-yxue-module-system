package com.zhaoj.dto.Detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 作者:zhaoj
 * 创建时间:2020/10/11    13:30
 * 类的作用:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoListDTO {
    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadTime;
    private String description;
    private String likeCount;
    private String cateName;
    private String categoryId;
    private String userId;
}
