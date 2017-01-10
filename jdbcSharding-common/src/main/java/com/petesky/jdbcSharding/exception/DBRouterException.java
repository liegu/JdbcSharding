package com.petesky.jdbcSharding.exception;

/**
 * Desc:路由异常
 * Created by liegu.pete on 2016/8/4 下午6:10.
 */
public class DBRouterException extends JdbcShardingException
{

    public DBRouterException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public DBRouterException(Throwable cause)
    {
        super(cause);
    }

    public DBRouterException()
    {
    }

    public DBRouterException(String message)
    {
        super(message);
    }
}