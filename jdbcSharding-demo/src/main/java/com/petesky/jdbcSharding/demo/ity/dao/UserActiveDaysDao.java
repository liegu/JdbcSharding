//package com.duodian.common.dao.jdbcSharding.demo.ity.dao;
//
//import com.duodian.common.dao.jdbcSharding.dao.NamedParameterJdbcDsShardingDao;
//import com.duodian.common.dao.jdbcSharding.dbRouter.DBRouter;
//import com.duodian.common.dao.jdbcSharding.dbRouter.impl.DBRouterImpl;
//import com.duodian.common.dao.jdbcSharding.dbRouter.impl.MulitDBRouterImpl;
//import UserActiveDaysDO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Desc:
// * Created by liegu.pengt on 2016/8/4 下午8:23.
// */
//@Service("userActiveDaysDao")
//public class UserActiveDaysDao  {
//    @Autowired
//    @Qualifier("userActiveDaysRouter")
//    private DBRouterImpl userActiveDaysRouter;
//
//
//
//    @Autowired
//    @Qualifier("namedParameterJdbcDsShardingDao")
//    private NamedParameterJdbcDsShardingDao<UserActiveDaysDO,DBRouter> jdbcShardingDao;
//
//    /**
//     * 查询对象
//     * @return
//     */
//    public UserActiveDaysDO query(){
//        UserActiveDaysDO drdsTestDO=new UserActiveDaysDO();
//        drdsTestDO.setUserId(213825l);
//        drdsTestDO.setId(112048l);
//
//        Map map=new HashMap();
//        map.put("user_id",drdsTestDO.getUserId());
//        map.put("id",drdsTestDO.getId());
//        UserActiveDaysDO t= jdbcShardingDao.queryForObject(userActiveDaysRouter, "select * from  user_active_days where user_id=:user_id and id=:id", map,drdsTestDO);
//
//        return t;
//    }
//
//    @Value("${sql.itry.UserActiveDays.queryList}")
//    String sql_queryList;
//
//    /***
//     * sql写在配置文件示例
//     * @return
//     */
//    public List<UserActiveDaysDO> queryListProperties(){
//        UserActiveDaysDO drdsTestDO=new UserActiveDaysDO();
//        drdsTestDO.setUserId(213825l);
//        drdsTestDO.setId(112048l);
//
//        Map map=new HashMap();
//        map.put("user_id",drdsTestDO.getUserId());
//        map.put("id",drdsTestDO.getId());
//        List<UserActiveDaysDO> t= jdbcShardingDao.queryList(userActiveDaysRouter, sql_queryList, map,drdsTestDO);
//        return t;
//    }
//
//    /***
//     * 查询结果到hashmap示例
//     * @return
//     */
//    public Map queryForHashMap(){
//        UserActiveDaysDO drdsTestDO=new UserActiveDaysDO();
//        drdsTestDO.setUserId(213825l);
//        drdsTestDO.setId(112048l);
//
//        Map map=new HashMap();
//        map.put("user_id",drdsTestDO.getUserId());
//        map.put("id",drdsTestDO.getId());
//        Map t= jdbcShardingDao.queryForMap(userActiveDaysRouter, sql_queryList, map);
//        return t;
//    }
//
//    /**
//     * 查询到对象列表
//     * @return
//     */
//    public List<UserActiveDaysDO> queryList(){
//        UserActiveDaysDO drdsTestDO=new UserActiveDaysDO();
//        drdsTestDO.setUserId(213825l);
//        drdsTestDO.setId(112048l);
//
//        Map map=new HashMap();
//        map.put("user_id",drdsTestDO.getUserId());
//        map.put("id",drdsTestDO.getId());
//        List<UserActiveDaysDO> t= jdbcShardingDao.queryList(userActiveDaysRouter, "select * from  user_active_days limit 0,10", map,drdsTestDO);
//        return t;
//    }
//
//    /***
//     * 联表查询示例
//     */
//    @Autowired
//    @Qualifier("mulitRouter")
//    private MulitDBRouterImpl mulitRouter;
//    public List<Map> mulitQuery(){
//        UserActiveDaysDO drdsTestDO=new UserActiveDaysDO();
//        drdsTestDO.setUserId(213825l);
//        drdsTestDO.setId(112048l);
//
//        Map map=new HashMap();
//        map.put("a.user_id",drdsTestDO.getUserId());
//        map.put("b.user_id",drdsTestDO.getUserId());
//        map.put("b.package_name","com.51job.ios51job");
//        List<Map> t= jdbcShardingDao.queryForList(mulitRouter, "select a.id,b.id,b.disd,b.package_name from  user_active_days a,user_package b where a.user_id=:a.user_id and b.user_id=:b.user_id and b.user_id=a.user_id  and b.package_name=:b.package_name limit 0,10", map);
//        return t;
//    }
////
////    public List<UserActiveDaysDO> insert(){
////        UserActiveDaysDO drdsTestDO=new UserActiveDaysDO();
////        drdsTestDO.setUserId(213825l);
////        drdsTestDO.setId(112048l);
////
////        Map map=new HashMap();
////        map.put("user_id",drdsTestDO.getUserId());
////        map.put("id",drdsTestDO.getId());
////        List<UserActiveDaysDO> t= jdbcShardingDao.queryList(userActiveDaysRouter, "select * from  userActiveDays where user_id=:user_id and id=:id", map,drdsTestDO);
////        return t;
////    }
//}
