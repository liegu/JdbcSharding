package com.petesky.jdbcSharding.dbRouter.impl.slice.simple;

import com.petesky.jdbcSharding.dbRouter.ModSliceCompute;

/**
 * Desc:简单取模分片计算器
 * Created by liegu.pete on 2016/8/8 下午8:20.
 */
public class SimpleModComputeImpl implements ModSliceCompute {
    @Override
    public String compute(Object shardField, int tableNum) {
        return String.valueOf(Math.abs((Long)shardField % tableNum));
    }
}
