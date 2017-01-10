//package com.petesky.common.dao.jdbcSharding.demo.ity.dao;
//
//import NamedParameterJdbcShardingDao;
//import DBRouterImpl;
//import com.petesky.passport.domain.bean.SysUserPartnerDO;
//import com.petesky.passport.domain.bean.UserPartnerTypeEnum;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
//import org.springframework.stereotype.Service;
//
///**
// * Desc:用户联合登录表数据连接对象
// * Created by liegu.pengt on 2016/6/2 下午3:57.
// */
//@Service("sysUserPartnerDao")
//public class SysUserPartnerDao {
//
//    /**
//     * 分表主键生成器
//     */
//    @Autowired
//    @Qualifier("sys_user_partner_seq")
//    private DataFieldMaxValueIncrementer sys_user_partner_seq;
//    /***
//     * 用户表路由对象
//     */
//    @Autowired
//    @Qualifier("sysUserPartnerRouter")
//    private DBRouterImpl router;
//
//    /***
//     *数据库操作对象
//     */
//    @Autowired
//    @Qualifier("namedParameterJdbcShardingDao")
//    private NamedParameterJdbcShardingDao<SysUserPartnerDO,DBRouterImpl> jdbcShardingDao;
//
//    @Value("${passport.sysUserPartner.sql_insert}")
//    String sql_insert;
//
//    @Value("${passport.sysUserPartner.sql_findByOpenidType}")
//    String sql_findByOpenidType;
//
//    /**
//     * 保存用户联登对象
//     * @param objctDO
//     * @return
//     */
//    public Long save(SysUserPartnerDO objctDO)
//    {
//        objctDO.setId(sys_user_partner_seq.nextLongValue());
//        jdbcShardingDao.saveOrUpdate(router, sql_insert, objctDO.build_sql_insert());
//        return objctDO.getId();
//    }
//
//    /***
//     * 按openid,联登类型查询
//     * @param openId
//     * @param partnerTypeEnum
//     * @return
//     */
//    public SysUserPartnerDO findByOpenIdAndType(String openId,UserPartnerTypeEnum partnerTypeEnum)
//    {
//        SysUserPartnerDO updo=new SysUserPartnerDO();
//        updo.setOpenId(openId);
//        updo.setPartnerType((short)partnerTypeEnum.getCode());
//        updo=jdbcShardingDao.queryForObject(router, sql_findByOpenidType, updo.build_sql_findByOpenidType(), updo);
//        return updo;
//    }
//}
