package com.petesky.jdbcSharding.exception;

/**
 * Desc:服务异常
 * Created by liegu.pete on 2016/8/4 下午6:10.
 */
public class JdbcShardingException extends Exception{
    public JdbcShardingException()
    {
    }

    public JdbcShardingException(String msg)
    {
        super(msg);
    }

    public JdbcShardingException(Throwable cause) {
        super(cause);
    }

    public JdbcShardingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
