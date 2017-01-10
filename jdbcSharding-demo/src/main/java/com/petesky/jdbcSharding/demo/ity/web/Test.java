//package com.duodian.common.dao.jdbcSharding.demo.ity.web;
//
//
//import com.duodian.common.dao.jdbcSharding.demo.ity.dao.SysUserPartnerDao;
//import com.duodian.common.dao.jdbcSharding.demo.ity.dao.UserActiveDaysDao;
//import com.duodian.passport.domain.bean.UserPartnerTypeEnum;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//
///**
// * Desc:
// * Created by liegu.pengt on 2016/6/23 下午4:44.
// */
//@Controller
//public class Test {
//
//
//    @Resource(name = "userActiveDaysDao")
//    UserActiveDaysDao userActiveDaysDao;
//
//
//    @Resource(name = "sysUserPartnerDao")
//    SysUserPartnerDao sysUserPartnerDao;
//
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public String mobileProtocol() {
////        UserActiveDaysDO doo= userActiveDaysDao.query();
//
////        List<UserActiveDaysDO> list=userActiveDaysDao.queryList();
////        List<UserActiveDaysDO> list2=userActiveDaysDao.qu1eryListProperties();
////
////        Map m=userActiveDaysDao.queryForHashMap();
//
////        List<Map> t=userActiveDaysDao.mulitQuery();
//        sysUserPartnerDao.findByOpenIdAndType("asdfasfdasdfasdf", UserPartnerTypeEnum.IDFA_Ipad);
//        return null;
//    }
//}
