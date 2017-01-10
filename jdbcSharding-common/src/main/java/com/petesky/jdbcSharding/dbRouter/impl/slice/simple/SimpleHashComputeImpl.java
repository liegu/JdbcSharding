package com.petesky.jdbcSharding.dbRouter.impl.slice.simple;

import com.petesky.jdbcSharding.dbRouter.ModSliceCompute;
import com.petesky.jdbcSharding.util.HashUtil;

/**
 * Desc:简单hash分片计算器
 * Created by liegu.pete on 2016/8/8 下午8:21.
 */
public class SimpleHashComputeImpl implements ModSliceCompute {
    @Override
    public String compute(Object shardField, int tableNum) {
        return String.valueOf(HashUtil.KETAMA_HASH(shardField.toString(), tableNum));
    }
}
