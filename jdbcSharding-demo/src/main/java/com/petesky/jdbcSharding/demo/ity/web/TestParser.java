package com.petesky.jdbcSharding.demo.ity.web;

import java.io.StringReader;
import java.sql.SQLSyntaxErrorException;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
/**
 * Desc:
 * Created by liegu.pengt on 2016/8/4 下午4:30.
 */
public class TestParser {
    final static String update = "update";
    final static String select = "select";
    final static String insert = "insert";
    final static String where = "where";
    final static String into = "into";
    final static String from = "from";
    final static String set = "set";



    public static void main(String[] args) {
        String sql = "insert into employee(id,name,sharding_id) values(5, 'wdw',10010)";
        sql = "update employee set id=1";
        int count = 10000;
        long start = System.currentTimeMillis();
//
        for(int i = 0; i < count; i++) {
//        MySqlStatementParser parser = new MySqlStatementParser(sql);
//
//        SQLStatement statement = parser.parseStatement();
            parseSqlTable(sql);
        long end = System.currentTimeMillis();

//        System.out.println(statement.toString());
        }

       long end = System.currentTimeMillis();
        System.out.println(count + "times parse ,druid cost:" + (end - start) + "ms");
    }

    public static void parseSqlTable(String sql) {
        String tablename=null;
//        String sql = "insert into employee(id,name,sharding_id) values(5, 'wdw',10010)";
//        sql = "update employee set id=1";
//        sql = "select * from employeese_lsect";

        if(sql.startsWith(insert)){
            tablename=getInsertTable(sql);
        }else if(sql.startsWith(select)){
            tablename=getSelectTable(sql);
        }else if(sql.startsWith(update)) {
            tablename=getUpdateTable(sql);
        }
//        System.out.println("table:"+tablename);
    }

    private static String getInsertTable(String sql){
        int start=sql.indexOf(into)+4;
        int end=sql.indexOf("(");
        return sql.substring(start,end).trim();
    }

    private static String getSelectTable(String sql){
        int start=sql.indexOf(from)+4;
        int end=sql.indexOf(where);
        if(end==-1){
            return sql.substring(start).trim();
        }
        return sql.substring(start,end).trim();
    }

    private static String getUpdateTable(String sql){
        int start=sql.indexOf(update)+6;
        int end=sql.indexOf(set);
        if(end==-1){
            return sql.substring(start).trim();
        }
        return sql.substring(start,end).trim();
    }
}
