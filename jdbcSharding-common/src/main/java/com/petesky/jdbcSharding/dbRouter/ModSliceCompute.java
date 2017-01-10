package com.petesky.jdbcSharding.dbRouter;

/**
 * Desc:分片计算器接口
 * Created by liegu.pete on 2016/8/4 下午6:04.
 */
public interface ModSliceCompute {

    String compute(Object shardField,int tableNum);
}
