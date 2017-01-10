//package com.duodian.common.dao.jdbcSharding.demo.ity.dao;
//
//import com.team.common.dao.jdbcSharding.dao.NamedParameterJdbcDsShardingDao;
//import com.team.common.dao.jdbcSharding.demo.ity.bean.DrdsTestDO;
//import com.team.common.dao.jdbcSharding.demo.ity.rowmapper.DrdsTestRowMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Desc:Drds写入测试
// * Created by liegu.pengt on 2016/6/22 下午5:15.
// */
//public class DrdsTestDao  {
//
//    /**
//     * 分表主键生成器
//     */
//    @Autowired
//    @Qualifier("moreapps_item_seq")
//    private DataFieldMaxValueIncrementer moreapps_item_seq;
//
//    /***
//     *数据库操作对象
//     */
//    @Autowired
//    @Qualifier("namedParameterJdbcDsShardingDao")
//    private NamedParameterJdbcDsShardingDao<DrdsTestDO> jdbcShardingDao;
//
//    /***
//     * 写入测试
//     * @return
//     */
//    public Long save()
//    {
//        DrdsTestDO drdsTestDO=new DrdsTestDO();
//        drdsTestDO.setUserId(moreapps_item_seq.nextLongValue());
//        Map map=new HashMap();
//        map.put("packageName","com.team");
//        map.put("userId",drdsTestDO.getUserId());
//        jdbcShardingDao.saveOrUpdate(drdsTestDO, DrdsTestDO.sql_insert,map);
//        return drdsTestDO.getUserId();
//    }
//}
