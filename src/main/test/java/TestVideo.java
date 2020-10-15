import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaoj.YxueApp;
import com.zhaoj.dao.VideoDao;
import com.zhaoj.entity.Category;
import com.zhaoj.entity.User;
import com.zhaoj.entity.Video;
import com.zhaoj.service.CategoryService;
import com.zhaoj.service.VideoService;
import com.zhaoj.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 作者:zhaoj
 * 创建时间:2020/9/25    16:15
 * 类的作用:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YxueApp.class)
public class TestVideo {

    @Autowired
    private VideoService service;

    @Autowired
    private VideoDao dao;


    @Test
    public void test1(){
        User user = new User();
    }
    @Test
    public void test7(){
        List<Video> videos = service.queryByLikeVideoName("");
        System.out.println(videos);
    }
    @Test
    public void test2(){
        String stringMD5 = MD5Util.getStringMD5(null);
        System.out.println(stringMD5);
    }


    @Autowired
    private CategoryService service1;

    @Test
    public void Test3(){
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        QueryWrapper<Category> level = wrapper.eq("level", "2");
        List<Category> list = service1.list(level);
        for (Category category : list) {
            System.out.println(category);
        }

    }
}
