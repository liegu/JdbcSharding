package com.petesky.jdbcSharding.dbRouter;

import com.petesky.jdbcSharding.exception.DBRouterException;

import java.util.Map;

/**
 * Desc:数据实例、库、表路由接口
 * Created by liegu.pete on 2016/8/4 下午7:57.
 */
public interface DBRouter {
    /**
     * 生成路由库表字符串
     * @param sql
     * @param map
     * @return
     * @throws DBRouterException
     */
    String  routing(String sql,Map<String,?> map)throws DBRouterException;

    /**
     * 生成dataSource路由分片编号
     * @param map
     * @param sql
     * @return
     * @throws DBRouterException
     */
    int insRouting(Map<String, ?> map,String sql)throws DBRouterException;

    }
