package com.petesky.jdbcSharding.dbRouter.impl.slice.aliyunDrds;

import com.petesky.jdbcSharding.dbRouter.ModSliceCompute;

/**
 * Desc:数据库实例分片计算器
 * Created by liegu.pete on 2016/8/4 下午7:17.
 */
public class DrdsInsSliceComputeImpl implements ModSliceCompute {

    @Override
    public String compute(Object shardField,int tableNum) {
        return String.valueOf(Math.abs((Long)shardField % tableNum)/16);
    }
}
