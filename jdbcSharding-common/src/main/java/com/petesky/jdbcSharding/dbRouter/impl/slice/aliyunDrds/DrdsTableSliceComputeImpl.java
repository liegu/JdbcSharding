package com.petesky.jdbcSharding.dbRouter.impl.slice.aliyunDrds;

import com.petesky.jdbcSharding.dbRouter.ModSliceCompute;

/**
 * Desc:逻辑表分片计算器
 * Created by liegu.pete on 2016/8/4 下午7:19.
 */
public class DrdsTableSliceComputeImpl implements ModSliceCompute {

    @Override
    public String compute(Object shardField,int tableNum) {
        //计算所在表
        return String.format("%02d", Math.abs((Long)shardField % tableNum));
    }
}
