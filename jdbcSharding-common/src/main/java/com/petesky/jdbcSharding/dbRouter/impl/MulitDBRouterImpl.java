package com.petesky.jdbcSharding.dbRouter.impl;

import com.petesky.jdbcSharding.dbRouter.DBRouter;
import com.petesky.jdbcSharding.exception.DBRouterException;
import com.petesky.jdbcSharding.sql.SqlParseUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Desc:多数据库分表
 * tips:测试阶段
 * Created by liegu.pete on 2016/8/6 下午2:41.
 */
public class MulitDBRouterImpl implements DBRouter {

    List<DBRouterImpl> dbRouterList; //多个表的dbRouter
    //后期加入解析缓存
    Map<String,Map> aliasMapCache=new ConcurrentHashMap() ;

    @Override
    public String routing(String sql, Map<String, ?> map) throws DBRouterException {
        //解析表名
        Map<String,String> tableAliasMap= getParseSqlMap(sql);
        String computedTableName=computeTable(sql,map,tableAliasMap);
        String reslutSql=sql.replace(tableAliasMap.get(SqlParseUtil._tableName),computedTableName);
        return reslutSql;
    }

    public String computeTable(String sql,Map<String, ?> map,Map<String,String> tableAliasMap) throws DBRouterException {

        StringBuilder sqlBuilder=new StringBuilder();
        sqlBuilder.append(" ");
        String tmpTableName=null;
        for(DBRouterImpl dbRouter:dbRouterList){
            //1、处理shardField,得到表名
            tmpTableName=dbRouter.computeTable(map, getMulitShardFieldName(sql,dbRouter));
            //2、构造新多表查询表名
            sqlBuilder.append(tmpTableName).append(" ").append(tableAliasMap.get(dbRouter.getTableName())).append(",");

        }
        if(sqlBuilder.length()<0){
            throw new DBRouterException("mulitTable sqlBuilder is empty");
        }
        return sqlBuilder.substring(0,sqlBuilder.length()-1).toString();
    }


    @Override
    public int insRouting(Map<String, ?> map,String sql) throws DBRouterException {

        DBRouterImpl dbRouter=dbRouterList.get(0);
        //计算实例分片
        String num=dbRouter.getInsModSliceCompute().compute(getShardFieldValue(map,sql,dbRouter),dbRouter.getTableNumber());

        return Integer.valueOf(num);

    }
    private String getMulitShardFieldName(String sql , DBRouterImpl dbRouter){
        Map<String,String> tableAliasMap= getParseSqlMap(sql);
        String aliasStr=tableAliasMap.get(dbRouter.getTableName());
        return aliasStr+"."+dbRouter.getShardField();

    }
    private Object getShardFieldValue(Map<String, ?> map,String sql , DBRouterImpl dbRouter){

        return dbRouter.getShardFieldValue(map, getMulitShardFieldName(sql, dbRouter));

    }
    private Map<String,String> getParseSqlMap(String sql){
        Map<String,String> tableAliasMap=null;
        if(aliasMapCache.containsKey(sql)){
            return aliasMapCache.get(sql);
        }else {
            tableAliasMap = SqlParseUtil.parseMulitSqlTable(sql);
            aliasMapCache.put(sql,tableAliasMap);
        }
        return tableAliasMap;
    }
    public List<DBRouterImpl> getDbRouterList() {
        return dbRouterList;
    }

    public void setDbRouterList(List<DBRouterImpl> dbRouterList) {
        this.dbRouterList = dbRouterList;
    }
}
