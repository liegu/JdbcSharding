package com.petesky.jdbcSharding.demo.jdbcDao.dao;

import com.petesky.jdbcSharding.dao.NamedParameterJdbcDao;
import com.petesky.jdbcSharding.demo.bean.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbcSharding 单dataSource单库单表dao操作demo
 * Created by liegu.pete on 2017/1/3.
 */
@Service("userDao")
public class UserDao {

    /**
     * 【NamedParameterJdbcDao说明】:
     * namedParameterJdbcDao bean配置在 classpath:spring/spring-datasource.xml中
     * 配置好后可直接使用,不需要进行配置DBRouter 及相关分片计算器 ModSliceCompute
     * 在spring-orm.xml中根据对应dataSource配置事务
     */
    @Autowired
    @Qualifier("namedParameterJdbcDao")
    private NamedParameterJdbcDao<UserDO> jdbcShardingDao;

    /**
     * sql的两种写法:1、是做为字符串直接写在类文件中
     */
    final static String querySql="select * from  user where username=:username and id=:id";

    /**
     * sql的两种写法:2、是将sql写在sqlmap目录下的user.properties文件中,通过spring @Value注解加载
     */
    @Value("${sql.userDO.queryByUsernameAndId}")
    String queryByUsernameAndIdSql;

    @Value("${sql.userDO.insert}")
    String insertUserSql;

    @Value("${sql.userDO.updatePasswordById}")
    String updatePasswordById;

    /**
     * 查询DO对象示例
     * @return
     */
    public UserDO query(){

        Map map=new HashMap();
        map.put("username","root");
        map.put("id",1);
        UserDO reslutDO= jdbcShardingDao.queryForObject(querySql, map,new UserDO());

        return reslutDO;
    }


    /**
     * 查询对象
     * @return
     */
    public List<UserDO> queryList(){

        Map map=new HashMap();
        map.put("username","root");
        map.put("id",1);
        List<UserDO> reslut= jdbcShardingDao.queryList(queryByUsernameAndIdSql, map,new UserDO());

        return reslut;
    }

    /***
     * 查询结果到hashmap示例
     * @return
     */
    public Map queryForMap(){
        Map map=new HashMap();
        map.put("username", "root");
        map.put("id", 1);
        Map reslut= jdbcShardingDao.queryForMap(queryByUsernameAndIdSql, map);

        return reslut;
    }
    /***
     * 插入记录示例
     * @return
     */
    public int insert(){
        UserDO userDo=new UserDO();
        Map map=new HashMap();
        Date createDate=new Date();
        map.put("username", "user_"+userDo.hashCode());
        map.put("password", "666666");
        map.put("gmt_modified", createDate);
        map.put("gmt_create", createDate);
        map.put("status", "1");

        int reslut= jdbcShardingDao.saveOrUpdate(insertUserSql, map);

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
        map.put("id", "1");

        int reslut= jdbcShardingDao.saveOrUpdate(updatePasswordById, map);

        return reslut;
    }

}
