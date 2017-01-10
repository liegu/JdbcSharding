package com.petesky.jdbcSharding.demo.JdbcShardingDao.slice;

import com.petesky.jdbcSharding.dbRouter.ModSliceCompute;

/**
 * 自定义分片计算器举例
 * user_sharding表由64个分表组成,分布在2个逻辑库中,
 * 逻辑库的分布规则为:
 * 1、小于32时在0号表中
 * 2、大于31时在1号表中
 *
 * Created by liegu.pete on 2017/1/10.
 */
public class JdbcShardingDBSliceComputeImpl implements ModSliceCompute {
    @Override
    public String compute(Object shardField, int tableNum) {
        Long num=Math.abs((Long)shardField % tableNum);
        return String.valueOf((num<32)?0:1);
    }
}
