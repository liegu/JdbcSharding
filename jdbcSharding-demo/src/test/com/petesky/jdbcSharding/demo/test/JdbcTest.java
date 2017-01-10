package com.petesky.jdbcSharding.demo.test;

import com.petesky.jdbcSharding.demo.jdbcDao.dao.UserDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class JdbcTest extends TestCase {


    @Resource(name = "userDao")
    UserDao userDao;

    @Test
    public void insert() {
        int changeNum=userDao.insert();
        assertEquals(1,changeNum);
    }

    @Test
    public void query() {
        userDao.query();
        userDao.queryForMap();
        userDao.queryList();
    }

}