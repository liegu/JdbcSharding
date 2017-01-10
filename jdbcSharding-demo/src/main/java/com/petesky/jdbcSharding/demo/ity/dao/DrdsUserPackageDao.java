//package com.duodian.common.dao.jdbcSharding.demo.ity.dao;
//
//import com.team.common.dao.jdbcSharding.dao.NamedParameterJdbcDao;
//import com.team.common.dao.jdbcSharding.dao.NamedParameterJdbcDsShardingDao;
//import com.team.common.dao.jdbcSharding.demo.ity.bean.DrdsUserActiveDaysDO;
//import com.team.common.dao.jdbcSharding.demo.ity.bean.DrdsUserPackageDO;
//import com.team.common.dao.jdbcSharding.demo.ity.rowmapper.DrdsUserPackageRowMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
///**
// * Desc:用户包按装列表
// * Created by liegu.pengt on 2016/6/29 下午2:02.
// */
//@Service("drdsUserPackageDao")
//public class DrdsUserPackageDao {
//
//    /**
//     * 分表主键生成器
//     */
//    @Autowired
//    @Qualifier("moreapps_item_seq")
//    private DataFieldMaxValueIncrementer moreapps_item_seq;
//
//    /***
//     * 表映射对象
//     */
//    @Autowired
//    @Qualifier("drdsUserPackageRowMapper")
//    private DrdsUserPackageRowMapper drdsUserPackageRowMapper;
//
//    /***
//     * 数据库操作对象
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
//    private NamedParameterJdbcDsShardingDao<DrdsUserPackageDO> jdbcShardingDao;
//
//
//    public void Test() {
//        NamedParameterJdbcDao[] dsInsArrays = new NamedParameterJdbcDao[]{namedParameterJdbcDao0, namedParameterJdbcDao1, namedParameterJdbcDao2};
//        NamedParameterJdbcDao dsIns = null;
//        //数据源
//        for (int ds = 0; ds < 3; ds++) {
//            dsIns = dsInsArrays[ds];
//            for (int db = ds * 8; db < ds * 8 + 8; db++) {
//                for (int otable = 0; otable < 9; otable++) {
//                    for (int ntable = db * 2; ntable < db * 2 + 2; ntable++) {
//
////                      System.out.println("ds:"+ds+"  db:"+db+" old:"+otable+" new:"+ntable);
//                        String sql_queryLimit = "select * from  drds_data_itry_qzui_" + String.format("%04d", db) + ".user_package_" + otable + "_" + String.format("%02d", ntable) + " order by c_time desc limit 0,100";
//                        //System.out.println(sql_queryLimit);
//                        List<DrdsUserPackageDO> drdsUserPackageDOs = dsIns.queryList(sql_queryLimit, null, drdsUserPackageRowMapper);
//                        if (CollectionUtils.isEmpty(drdsUserPackageDOs)) {
//                            break;
//                        }
//                        for (DrdsUserPackageDO dado : drdsUserPackageDOs) {
//
//                            System.out.println("=========================");
//                            if (dado.getDbInstanceNum() != ds) {
//                                System.out.println("dbinc:" + dado.getDbInstanceNum() + " ds:" + ds + "  db:" + db + " old:" + otable + " new:" + ntable);
//                                System.out.println("ins error:uid " + dado.getUserId());
//                            }
//                            if ((dado.getTableNum()/2)!= db) {
//                                System.out.println("db:" + (dado.getTableNum()/2) + " ds:" + ds + "  db:" + db + " old:" + otable + " new:" + ntable);
//                                System.out.println("ins error:uid " + dado.getUserId());
//                            }
//                            if (dado.getTableNum() != ntable) {
//                                System.out.println("ntable:" + dado.getTableNum() + " ds:" + ds + "  db:" + db + " old:" + otable + " new:" + ntable);
//                                System.out.println("nt error:uid " + dado.getTableNum());
//                            }
//                            if (dado.getUserId() % 10 != otable) {
//                                System.out.println("otable:" + (dado.getUserId() % 10) + " ds:" + ds + "  db:" + db + " old:" + otable + " new:" + ntable);
//                                System.out.println("ot error:uid " + dado.getTableNum());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
