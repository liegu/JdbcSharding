package com.petesky.jdbcSharding.dbRouter.impl.slice.aliyunDrds;

import com.petesky.jdbcSharding.dbRouter.ModSliceCompute;

/**
 * Desc:逻辑库分片计算器
 * Created by liegu.pete on 2016/8/4 下午7:18.
 */
public class DrdsDbSliceComputeImpl implements ModSliceCompute {

    @Override
    public String compute(Object shardField,int tableNum) {
        //计算存储于哪个逻辑db
        return String.format("%04d",(Math.abs((Long)shardField % tableNum)/2));
    }



}
