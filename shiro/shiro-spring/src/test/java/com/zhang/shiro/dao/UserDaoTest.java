package com.zhang.shiro.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangyu
 * @create 2018-11-08 14:58
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring.xml", "classpath*:spring/spring-dao.xml", "classpath*:spring/springmvc.xml"})
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findPassWordByUserNameTest() {
        userDao.findPassWordByUserName("admin");
    }
}
