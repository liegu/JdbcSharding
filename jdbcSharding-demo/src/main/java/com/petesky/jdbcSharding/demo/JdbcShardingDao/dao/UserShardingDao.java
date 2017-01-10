package com.petesky.jdbcSharding.demo.JdbcShardingDao.dao;

import com.petesky.jdbcSharding.dao.NamedParameterJdbcShardingDao;
import com.petesky.jdbcSharding.dbRouter.DBRouter;
import com.petesky.jdbcSharding.demo.bean.UserDO;
import com.petesky.jdbcSharding.demo.bean.UserShardingDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbcSharding 单dataSource多库多表dao操作demo
 * Created by liegu.pete on 2017/1/10.
 */
@Service("userShardingDao")
public class UserShardingDao {

    /***
     * 分表路由对象
     */
    @Autowired
    @Qualifier("userShardingRouter")
    private DBRouter userShardingRouter;
    /***
     * 分表Dao对象
     */
    @Autowired
    @Qualifier("namedParameterJdbcShardingDao")
    private NamedParameterJdbcShardingDao<UserShardingDO,DBRouter> jdbcShardingDao;

    /***
     * id发号器
     */
    @Autowired
    @Qualifier("user_sharding_seq")
    private MySQLMaxValueIncrementer user_sharding_seq;

    /**
     * sql的两种写法:1、是做为字符串直接写在类文件中
     */
    final static String querySql="select * from  user_sharding where username=:username and id=:id";

    /**
     * sql的两种写法:2、是将sql写在sqlmap目录下的userSharding.properties文件中,通过spring @Value注解加载
     */
    @Value("${sql.userShardingDO.queryByUsernameAndId}")
    String queryByUsernameAndIdSql;

    @Value("${sql.userShardingDO.insert}")
    String insertUserSql;

    @Value("${sql.userShardingDO.updatePasswordById}")
    String updatePasswordById;

    /**
     * 查询DO对象示例
     * @return
     */
    public UserShardingDO query(){

        Map map=new HashMap();
        map.put("username","root");
        map.put("id",1l);
        UserShardingDO reslutDO= jdbcShardingDao.queryForObject(userShardingRouter,querySql, map,new UserShardingDO());

        return reslutDO;
    }


    /**
     * 查询对象
     * @return
     */
    public List<UserShardingDO> queryList(){

        Map map=new HashMap();
        map.put("username","root");
        map.put("id",1l);
        List<UserShardingDO> reslut= jdbcShardingDao.queryList(userShardingRouter,queryByUsernameAndIdSql, map,new UserShardingDO());

        return reslut;
    }

    /***
     * 查询结果到hashmap示例
     * @return
     */
    public Map queryForMap(){
        Map map=new HashMap();
        map.put("username", "root");
        map.put("id", 1l);
        Map reslut= jdbcShardingDao.queryForMap(userShardingRouter,queryByUsernameAndIdSql, map);

        return reslut;
    }
    /***
     * 插入记录示例
     * @return
     */
    public int insert(){
        UserShardingDO userShardingDo=new UserShardingDO();
        Map map=new HashMap();
        Date createDate=new Date();
        map.put("username", "user_"+userShardingDo.hashCode());
        map.put("password", "666666");
        map.put("gmt_modified", createDate);
        map.put("gmt_create", createDate);
        map.put("status", "1");
        map.put("id",user_sharding_seq.nextLongValue());

        int reslut= jdbcShardingDao.saveOrUpdate(userShardingRouter,insertUserSql, map);

        return reslut;
    }

    /***
     * 根据id更新用户密码记录
     * @return
     */
    public int updatePasswordById(){
        UserDO userDo=new UserDO();
        Map map=new HashMap();
        map.put("password", String.valueOf(userDo.hashCode()));
        map.put("id", 2l);

        int reslut= jdbcShardingDao.saveOrUpdate(userShardingRouter,updatePasswordById, map);

        return reslut;
    }
}
