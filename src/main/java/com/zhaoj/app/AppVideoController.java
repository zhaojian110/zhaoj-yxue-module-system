package com.zhaoj.app;

import com.zhaoj.dto.Detail.VideoListDTO;
import com.zhaoj.dto.DetailVideoDTO;
import com.zhaoj.dto.SearchVideoDTO;
import com.zhaoj.dto.VideoDTO;
import com.zhaoj.entity.Video;
import com.zhaoj.service.VideoService;
import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("app")
public class AppVideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping("queryByReleaseTime")
    public Map<String, Object> queryByReleaseTime() {
        System.out.println("====queryByReleaseTime====");
        List<Video> list = videoService.queryAl();
        //自己封装结果
        List<VideoDTO> mainDTOS = new ArrayList<>();
        for (Video video : list) {
            VideoDTO mainDTO = new VideoDTO(
                    video.getId(),
                    video.getTitle(),
                    "http://localhost:8989/yingx/upload_file/videoImg/"+video.getCoverUrl(),
                    "http://qhb8occix.hn-bkt.clouddn.com/"+video.getVideoUrl(),
                    video.getCreateAt(),
                    video.getIntro(),
                    video.getCou(),//喜欢的数量
                    video.getCategory().getName(),//类别数据
                    "http://localhost:8989/yingx/upload_file/img/"+video.getUser().getHeadShow()
            );
            mainDTOS.add(mainDTO);
        }
        return result(mainDTOS);
    }

    @GetMapping("queryByLikeVideoName")
    public Map<String, Object> queryByLikeVideoName(String content) {
        List<Video> list = videoService.queryByLikeVideoName(content);
        //自己封装结果
        List<SearchVideoDTO> mainDTOS = new ArrayList<>();
        for (Video video : list) {
            SearchVideoDTO mainDTO = new SearchVideoDTO(
                    video.getId(),
                    video.getTitle(),
                    "http://localhost:8989/yingx/upload_file/videoImg/"+video.getCoverUrl(),
                    "http://qhb8occix.hn-bkt.clouddn.com/"+video.getVideoUrl(),
                    new Date(),
                    video.getIntro(),
                    video.getCou(),//喜欢的数量
                    video.getCategory().getName(),
                    video.getCategory().getId(),
                    video.getUser().getId(),
                    video.getUser().getUsername()
                    /*video.getId(),
                    video.getTitle(),
                    "http://localhost:8989/yingx/upload_file/videoImg/"+video.getCoverUrl(),
                    "http://qhb8occix.hn-bkt.clouddn.com/"+video.getVideoUrl(),
                    video.getCreateAt(),
                    video.getIntro(),
                    video.getCou(),//喜欢的数量
                    video.getCategory().getName(),//类别数据
                    "http://localhost:8989/yingx/upload_file/img/"+video.getUser().getHeadShow()*/
            );
            mainDTOS.add(mainDTO);
        }
        return result(mainDTOS);
    }

    @GetMapping("queryByVideoDetail")
    public Map<String, Object> queryByVideoDetail(String videoId,String cateId,String userId) {

        if(cateId.equals("undefined")){
            cateId="";
        }
        if(userId.equals("undefined")){
            userId="";
        }
        Video video = videoService.queryByVideoDetail(videoId, cateId, userId);
        //自己封装结果
        List<Video> videos = videoService.queryAl();
        DetailVideoDTO videoDTO = new DetailVideoDTO();
        videoDTO.setId(video.getId());
        videoDTO.setVideoTitle(video.getTitle());
        videoDTO.setCover("http://localhost:8989/yingx/upload_file/videoImg/"+video.getCoverUrl());
        videoDTO.setPath("http://qhb8occix.hn-bkt.clouddn.com/"+video.getVideoUrl());
        videoDTO.setUploadTime(video.getCreateAt());
        videoDTO.setDescription(video.getIntro());
        videoDTO.setLikeCount(video.getCou());
        if(video.getCategory()!= null){
            videoDTO.setCategoryId(video.getCategory().getId());
            videoDTO.setCateName(video.getCategory().getName());
        }
        if(video.getUser()!=null){
            videoDTO.setUserId(video.getUser().getId());
            videoDTO.setUserPicImg(video.getUser().getHeadShow());
            videoDTO.setUserName(video.getUser().getUsername());
        }
        videoDTO.setPlayCount(99/*video.getPlay().getPlayNum()*/);
        ArrayList<VideoListDTO> list = new ArrayList<>();
        for (Video video1 : videos) {
            if(!video1.getId().equals(videoId)){
                VideoListDTO dto = new VideoListDTO();
                dto.setId(video1.getId());
                dto.setVideoTitle(video1.getTitle());
                dto.setCover("http://localhost:8989/yingx/upload_file/videoImg/"+video1.getCoverUrl());
                dto.setPath("http://qhb8occix.hn-bkt.clouddn.com/"+video1.getVideoUrl());
                dto.setUploadTime(video1.getCreateAt());
                dto.setDescription(video1.getIntro());
                dto.setLikeCount(video1.getCou());
                if(video1.getCategory()!=null){
                    dto.setCategoryId(video1.getCategory().getId());
                    dto.setCateName(video1.getCategory().getName());
                }
                if(video1.getUser()!=null){
                    dto.setUserId(video1.getUser().getId());
                }
                list.add(dto);
            }
        }
        videoDTO.setVideoList(list);
        videoDTO.setAttention(false);
        return result(videoDTO);
    }

    public Map<String, Object> result(Object obj) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", obj);
        result.put("message", "操作成功！~");
        result.put("status", 100);
        return result;
    }

}
