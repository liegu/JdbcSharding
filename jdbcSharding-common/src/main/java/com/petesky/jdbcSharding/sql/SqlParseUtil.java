package com.petesky.jdbcSharding.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc:sql 解析工具类
 * Created by liegu.pete on 2016/8/4 下午5:16.
 */
public class SqlParseUtil {

    private final static String update = "update";
    private final static String delete = "delete";
    private final static String select = "select";
    private final static String insert = "insert";
    private final static String where = "where";
    private final static String into = "into";
    private final static String from = "from";
    private final static String order_by = "order by";
    private final static String group = "group";
    private final static String limit = "limit";
    private final static String set = "set";
    private final static String leftBracket = "(";

    public final static String _tableName="_table_name";

    /**
     * 解析单个表名
     * @param sql
     * @return
     */
    public static String parseSqlTable(String sql) {
        String tablename=null;
        //转换小写
        sql=sql.toLowerCase();
        //将带有as 的sql转换为空格表达
        sql=sql.replaceAll(" as "," ");

        if(sql.startsWith(insert)){
            tablename=getInsertTable(sql);
        }else if(sql.startsWith(select)){
            tablename=getSelectTable(sql);
        }else if(sql.startsWith(update)) {
            tablename=getUpdateTable(sql);
        }else if(sql.startsWith(delete)){
            tablename=getDelete(sql);
        }

        return tablename;
    }

    /**
     * 解析多个表名，及相关别名
     * @param sql
     * @return
     */
    public static Map parseMulitSqlTable(String sql){
        HashMap<String,String> map=new HashMap();
        String tableNames=parseSqlTable(sql);
        String [] subTable =tableNames.split(",");
        String [] tbAlias=null;
        for(String tableStr:subTable){
            tbAlias=tableStr.trim().split(" ");
            map.put(tbAlias[0],tbAlias[1]);
        }
        map.put(_tableName,tableNames);
        return map;
    }

    /**
     * 解析insert语句表名
     * @param sql
     * @return
     */
    private static String getInsertTable(String sql){
        int start=sql.indexOf(into)+4;
        int end=sql.indexOf(leftBracket);
        return sql.substring(start,end).trim();
    }

    /**
     * 解析select语句表名
     * @param sql
     * @return
     */
    private static String getSelectTable(String sql){
        int start=sql.indexOf(from)+4;
        int end=sql.indexOf(where);
        if(end==-1){
            end=sql.indexOf(limit);
        }
        if(end==-1){
            end=sql.indexOf(order_by);
        }
        if(end==-1){
            end=sql.indexOf(group);
        }
        if(end==-1){
            return sql.substring(start).trim();
        }
        return sql.substring(start,end).trim();
    }

    /**
     * 解析update语句表名
     * @param sql
     * @return
     */
    private static String getUpdateTable(String sql){
        int start=sql.indexOf(update)+6;
        int end=sql.indexOf(set);
        if(end==-1){
            return sql.substring(start).trim();
        }
        return sql.substring(start,end).trim();
    }
    /**
     * 解析delete语句表名
     * @param sql
     * @return
     */
    private static String getDelete(String sql){
        int start=sql.indexOf(from)+4;
        int end=sql.indexOf(where);
        if(end==-1){
            return sql.substring(start).trim();
        }
        return sql.substring(start,end).trim();
    }
}
