package com.blog;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.blog.service.*;
import com.blog.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(value = true)
@Transactional
class BlogApplicationTests {
    @Autowired
    private  UserService userService;

    @Autowired
    private  TagService tagService;

    @Test
    void contextLoads() {
        System.out.println(tagService.getBlogTag());
    }

}
