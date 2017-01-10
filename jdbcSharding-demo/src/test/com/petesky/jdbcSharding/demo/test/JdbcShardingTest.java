package com.petesky.jdbcSharding.demo.test;

import com.petesky.jdbcSharding.demo.JdbcShardingDao.dao.UserShardingDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by liegu.pete on 2017/1/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class JdbcShardingTest extends TestCase {

    @Resource(name = "userShardingDao")
    UserShardingDao userShardingDao;

    @Test
    public void insert() {
        int changeNum=userShardingDao.insert();
        assertEquals(1, changeNum);
    }

    @Test
    public void query() {
        userShardingDao.query();
        userShardingDao.queryForMap();
        userShardingDao.queryList();
    }
}
