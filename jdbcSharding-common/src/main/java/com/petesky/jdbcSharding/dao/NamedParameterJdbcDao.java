package com.petesky.jdbcSharding.dao;

import com.petesky.jdbcSharding.NamedParameterJdbcShardingTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Desc:单表数据操作基类
 * 适用于单实例,单虚拟库,单表的情况
 * 将
 * Created by liegu.pete on 2016/6/14 下午5:21.
 */

public class NamedParameterJdbcDao <DO extends Serializable>{

    private static final Logger logger = LoggerFactory.getLogger(NamedParameterJdbcDao.class);


    protected NamedParameterJdbcShardingTemplate namedParameterJdbcShardingTemplate;

    public void setNamedParameterJdbcShardingTemplate(NamedParameterJdbcShardingTemplate namedParameterJdbcShardingTemplate) {
        this.namedParameterJdbcShardingTemplate = namedParameterJdbcShardingTemplate;
    }

    public Integer queryInt(String sql,  Map<String, ?> map) {
    	Integer re=null;
    	try{
    		re=namedParameterJdbcShardingTemplate.queryForInt(sql, map);
    	}catch (EmptyResultDataAccessException t){
            logger.debug("queryForObject is Empty! {}", t.getMessage());
            return null;
        }
    	
        return re;
    }

    public Long queryLong(String sql,  Map<String, ?> map) {
    	Long re=null;
    	try{
    		re=namedParameterJdbcShardingTemplate.queryForLong(sql, map);
    	}catch (EmptyResultDataAccessException e){
    		logger.debug("queryForObject is Empty! {}", e.getMessage());
    		return null;
        }
        return re;
    }

    public  List<DO> queryList(String sql,  Map<String, ?> map, RowMapper rowMapper){
        return namedParameterJdbcShardingTemplate.query(sql, map, rowMapper);
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> map) {
        return namedParameterJdbcShardingTemplate.queryForList(sql, map);
    }

    public  List<DO> queryList(String sql,  Map<String, ?> map,DO o){
        return namedParameterJdbcShardingTemplate.query(sql, map, new BeanPropertyRowMapper(o.getClass()));
    }

    public int saveOrUpdate(String sql,  Map<String, ?> map) {

        return namedParameterJdbcShardingTemplate.update(sql, map);
    }
    
    public Long insertReturnId(String sql,  Map<String, ?> map) {

        return namedParameterJdbcShardingTemplate.insert(sql, map);
    }

    public DO queryForObject(String sql, Map<String, ?> map, RowMapper rowMapper) {
        DO o=null;
        try {
            o= (DO) namedParameterJdbcShardingTemplate.queryForObject(sql, map, rowMapper);

        }catch (EmptyResultDataAccessException t){
            logger.debug("queryForObject is Empty! {}", t.getMessage());
            return null;
        }
        return o;
    }

    public DO queryForObject(String sql, Map<String, ?> map,DO o) {
        DO obj=null;
        try {
            obj= (DO) namedParameterJdbcShardingTemplate.queryForObject(sql, map, new BeanPropertyRowMapper(o.getClass()));

        }catch (EmptyResultDataAccessException t){
            logger.debug("queryForObject is Empty! {}", t.getMessage());
            return null;
        }
        return obj;
    }

    public Map<String, Object> queryForMap(String sql, Map<String, ?> map) {
        Map obj=null;
        try {
            obj=(Map) namedParameterJdbcShardingTemplate.queryForObject(sql, (Map) map, (RowMapper) (new ColumnMapRowMapper()));
        }catch (EmptyResultDataAccessException t){
            logger.debug("queryForObject is Empty! {}", t.getMessage());
            return null;
        }
        return obj;
    }
    
    public int[] batchUpdate(String sql,  Map<String, ?>[] batchValues) {
        return namedParameterJdbcShardingTemplate.batchUpdate(sql, batchValues);
    }
}
