//package com.duodian.common.dao.jdbcSharding.demo.ity.dao;
//
//import com.team.common.dao.jdbcSharding.dao.NamedParameterJdbcDao;
//import com.team.common.dao.jdbcSharding.dao.NamedParameterJdbcDsShardingDao;
//import com.team.common.dao.jdbcSharding.demo.ity.bean.DrdsTestDO;
//import com.team.common.dao.jdbcSharding.demo.ity.bean.DrdsUserActiveDaysDO;
//import com.team.common.dao.jdbcSharding.demo.ity.rowmapper.DrdsTestRowMapper;
//import com.team.common.dao.jdbcSharding.demo.ity.rowmapper.DrdsUserActiveDaysRowMapper;
//import com.team.common.dao.jdbcSharding.demo.ity.web.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Desc:
// * Created by liegu.pengt on 2016/6/27 下午2:22.
// */

//public class DrdsUserActiveDaysDao {
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
//    @Qualifier("namedParameterJdbcDao0")
//    private NamedParameterJdbcDao namedParameterJdbcDao0;
//
//    @Autowired
//    @Qualifier("namedParameterJdbcDao1")
//    private NamedParameterJdbcDao namedParameterJdbcDao1;
//
//    @Autowired
//    @Qualifier("namedParameterJdbcDao2")
//    private NamedParameterJdbcDao namedParameterJdbcDao2;
//
//    @Autowired
//    @Qualifier("namedParameterJdbcDsShardingDao")
//    private NamedParameterJdbcDsShardingDao<DrdsUserActiveDaysDO> jdbcShardingDao;
//
//    /***
//     * 写入测试
//     * @return
//     */
//    public DrdsUserActiveDaysDO query(){
//        DrdsUserActiveDaysDO drdsTestDO=new DrdsUserActiveDaysDO();
//        drdsTestDO.setUserId(213825l);
//        drdsTestDO.setId(112048l);
//
//        Map map=new HashMap();
//        map.put("userId",drdsTestDO.getUserId());
//        map.put("id",drdsTestDO.getId());
//        DrdsUserActiveDaysDO t=jdbcShardingDao.queryForObject(drdsTestDO,DrdsUserActiveDaysDO.sql_query,map);
//
//        return t;
//    }
////    public void Test()
////    {
////        NamedParameterJdbcDao [] dsInsArrays=new NamedParameterJdbcDao[]{namedParameterJdbcDao0,namedParameterJdbcDao1,namedParameterJdbcDao2};
////        NamedParameterJdbcDao dsIns=null;
////        //数据源
////        for(int ds=0;ds<3;ds++){
////            dsIns=dsInsArrays[ds];
////            for(int db=ds*8;db<ds*8+8;db++){
////                for(int otable=0;otable<9;otable++){
////                    for(int ntable=db*2;ntable<db*2+2;ntable++){
////
//////                      System.out.println("ds:"+ds+"  db:"+db+" old:"+otable+" new:"+ntable);
////                        String sql_queryLimit="select * from  drds_data_itry_qzui_"+String.format("%04d", db)+".user_active_days_"+otable+"_"+ String.format("%02d", ntable)+" order by create_time desc limit 0,100";
////                        //System.out.println(sql_queryLimit);
////                        List<DrdsUserActiveDaysDO> drdsUserActiveDaysDOs=dsIns.queryList(sql_queryLimit, null, drdsUserActiveDaysRowMapper);
////                        if(CollectionUtils.isEmpty(drdsUserActiveDaysDOs)){
////                            return;
////                        }
////                        for(DrdsUserActiveDaysDO dado:drdsUserActiveDaysDOs){
////
////                            System.out.println("=========================");
////                            if(dado.getDbInstanceNum()!=ds){
////                                System.out.println("dbinc:"+dado.getDbInstanceNum()+" ds:"+ds+"  db:"+db+" old:"+otable+" new:"+ntable);
////                                System.out.println("ins error:uid "+dado.getUserId());
////                            }
////                            if(dado.getUserId()%8!=db){
////                                System.out.println("db:"+(dado.getUserId()%8)+" ds:"+ds+"  db:"+db+" old:"+otable+" new:"+ntable);
////                                System.out.println("ins error:uid "+dado.getUserId());
////                            }
////                            if(dado.getTableNum()!=ntable){
////                                System.out.println("ntable:"+dado.getTableNum()+" ds:"+ds+"  db:"+db+" old:"+otable+" new:"+ntable);
////                                System.out.println("nt error:uid "+dado.getTableNum());
////                            }
////                            if(dado.getUserId()%10!=otable){
////                                System.out.println("otable:"+(dado.getUserId()%10)+" ds:"+ds+"  db:"+db+" old:"+otable+" new:"+ntable);
////                                System.out.println("ot error:uid "+dado.getTableNum());
////                            }
////                        }
////                    }
////                }
////            }
////        }
//////        DrdsTestDO drdsTestDO=new DrdsTestDO();
//////        drdsTestDO.setUserId(moreapps_item_seq.nextLongValue());
//////
//////        Map map=new HashMap();
//////        map.put("packageName","com.team");
//////        map.put("userId",drdsTestDO.getUserId());
//////        jdbcShardingDao.saveOrUpdate(drdsTestDO, DrdsTestDO.sql_insert,map);
//////        return drdsTestDO.getUserId();
////    }
//
//}
