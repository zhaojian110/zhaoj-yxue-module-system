import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.zhaoj.YxueApp;
import com.zhaoj.controller.EchartsController;
import com.zhaoj.dao.CommentDao;
import com.zhaoj.dao.UserDao;
import com.zhaoj.entity.Comment;
import com.zhaoj.entity.User;
import com.zhaoj.service.CommentService;
import com.zhaoj.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 作者:zhaoj
 * 创建时间:2020/9/28    15:49
 * 类的作用:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YxueApp.class)
public class TestComment {

    @Autowired
    private CommentDao dao;

    @Autowired
    private CommentService service;
    @Autowired
    private UserDao userDao;
    @Autowired
    private EchartsController controller;

    @Test
    public void test0(){
        List<User> users = userDao.queryByStatus();
        for (User user : users) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            String createdate = sdf.format(user.getRegTime());
            System.out.println(createdate+"======="+user.getSum());
            System.out.println(user);
        }
    }
    @Test
    public void test100(){
        User user = new User();
        user.setId("user5");
        user.setUsername("456");
        user.setStatus("Y");
        user.setRegTime(new Date());
        userDao.insertA0(user);
        controller.queryUser();
    }

    @Test
    public void test1(){
        List<Comment> comments = service.queryAllFirst(1, 5);
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }
    @Test
    public void test2(){
       /* List<Comment> comments = service.queryAllDouble(1, 5, "com1");
        for (Comment comment : comments) {
            System.out.println(comment);
        }*/
        List<Comment> list = service.list();
        for (Comment comment : list) {
            System.out.println(comment);
        }
    }
}
