package com.petesky.jdbcSharding.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.petesky.jdbcSharding.NamedParameterJdbcShardingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.petesky.jdbcSharding.dbRouter.DBRouter;
import com.petesky.jdbcSharding.exception.DBRouterException;

/**
 * Desc:分表数据操作基类
 * 适用于单DataSource多库多表的情况
 * Created by liegu.pete on 2016/6/14 下午3:17.
 */
public class NamedParameterJdbcShardingDao <DO extends Serializable,R extends DBRouter>  {

    private static final Logger logger = LoggerFactory.getLogger(NamedParameterJdbcShardingDao.class);

    protected NamedParameterJdbcShardingTemplate namedParameterJdbcShardingTemplate;

    public void setNamedParameterJdbcShardingTemplate(NamedParameterJdbcShardingTemplate namedParameterJdbcShardingTemplate) {
        this.namedParameterJdbcShardingTemplate = namedParameterJdbcShardingTemplate;
    }

    
    public Integer queryInt(R dbRoter,String sql, Map<String, ?> map) {
        int obj=0;
        try {
            obj= namedParameterJdbcShardingTemplate.queryForInt(dbRoter.routing(sql, map), map);
        }catch (EmptyResultDataAccessException e){
        	logger.debug("queryForObject is Empty! {}", e.getMessage());
        	return null;
        } catch (DBRouterException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Long queryLong(R dbRoter,String sql, Map<String, ?> map) {
        Long obj=null;
        try {
            obj= namedParameterJdbcShardingTemplate.queryForLong(dbRoter.routing(sql, map), map);
        }catch (EmptyResultDataAccessException e){
        	logger.debug("queryForObject is Empty! {}", e.getMessage());
        	return null;
        } catch (DBRouterException e) {
            e.printStackTrace();
        }
        return obj;
    }


    @SuppressWarnings("unchecked")
    public  List<DO> queryList(R dbRoter,String sql, Map<String, ?> map, RowMapper rowMapper){
        List<DO> obj=null;
        try {
            obj= namedParameterJdbcShardingTemplate.query(dbRoter.routing(sql, map), map, rowMapper);
        } catch (DBRouterException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public List<Map<String, Object>> queryForList(R dbRoter,String sql, Map<String, ?> map) {
        List<Map<String, Object>> obj=null;
        try {
            obj= namedParameterJdbcShardingTemplate.queryForList(dbRoter.routing(sql, map), map);
        } catch (DBRouterException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public  List<DO> queryList(R dbRoter,String sql, Map<String, ?> map,DO o){
        List<DO> obj=null;
        try {
            obj=  namedParameterJdbcShardingTemplate.query(dbRoter.routing(sql, map), map, new BeanPropertyRowMapper(o.getClass()));
        } catch (DBRouterException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public int saveOrUpdate(R dbRoter,String sql, Map<String, ?> map) {
        int obj=0;
        try {
            obj= namedParameterJdbcShardingTemplate.update(dbRoter.routing(sql, map), map);
        } catch (DBRouterException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public DO queryForObject(R dbRoter,String sql, Map<String, ?> map, RowMapper rowMapper)
    {
        DO o=null;
        try {
            o= (DO) namedParameterJdbcShardingTemplate.queryForObject(dbRoter.routing(sql, map), map, rowMapper);

        }catch (EmptyResultDataAccessException e){
        	logger.debug("queryForObject is Empty! {}", e.getMessage());
        	return null;
        }catch (Throwable t){
        	t.printStackTrace();
        }
        return o;
    }

    public DO queryForObject(R dbRoter,String sql, Map<String, ?> map,DO o)
    {
        DO obj=null;
        try {
            obj= (DO) namedParameterJdbcShardingTemplate.queryForObject(dbRoter.routing(sql, map), map, new BeanPropertyRowMapper(o.getClass()));

        }catch (EmptyResultDataAccessException e){
        	logger.debug("queryForObject is Empty! {}", e.getMessage());
        	return null;
        }catch (Throwable t){
            t.printStackTrace();
        }
        return obj;
    }

    public Map<String, Object> queryForMap(R dbRoter,String sql, Map<String, ?> map) {
        Map obj=null;
        try {
            obj=(Map) namedParameterJdbcShardingTemplate.queryForObject(dbRoter.routing(sql, map), (Map) map, (RowMapper) (new ColumnMapRowMapper()));
        
        }catch (EmptyResultDataAccessException e){
        	logger.debug("queryForObject is Empty! {}", e.getMessage());
        	return null;
        }catch (Throwable t){
            t.printStackTrace();
        }
        return obj;
    }
    

    
    /**
     * 批量插入或更新
     * @param dbRoter
     * @param sql
     * @param batchValues
     * @return 提交条数
     * @author wangchao
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public int batchSaveOrUpdate(R dbRoter,String sql, Map[] batchValues) {
    	int totalCnt = 0;
    	Map<String,List<Map>> realSqlMap = new HashMap<String,List<Map>>();
    	String reslutSql = null;
    	for (Map values : batchValues) {
			try{
				 reslutSql = dbRoter.routing(sql, values);
			}catch (Throwable t){
			    t.printStackTrace();
			    logger.debug("dbRoter.routing error",t.getMessage());
			    return -1;
			}
			if (realSqlMap.containsKey(reslutSql)) {
				realSqlMap.get(reslutSql).add(values);
			} else {
				List<Map> list = new LinkedList<Map>();
				list.add(values);
				realSqlMap.put(reslutSql, list);
			}
		}

    	for (String realSql : realSqlMap.keySet()) {
    		List<Map> listParams = realSqlMap.get(realSql);
    		int[] cntA = namedParameterJdbcShardingTemplate.batchUpdate(realSql, listParams.toArray(new HashMap[listParams.size()]));
    		for (int i : cntA) {
    			totalCnt += i;
			}
		}
    	return totalCnt;
    }
    
    /**
     * in查询，且分表规则还是in的条件
     * @param dbRoter
     * @param sql
     * @param map 不包含分表条件的其他参数
     * @param shardField 分表的字段
     * @param shardFieldValues in的参数
     * @return
     * @author wangchao
     */
    public List<Map<String, Object>> queryForListWithIn(R dbRoter,String sql, Map<String, Object> map,String shardField,List<String> shardFieldValues) {
        List<Map<String, Object>> result= new ArrayList<Map<String, Object>>();
        
        Map<String,List<String>> realSqlMap = new HashMap<String,List<String>>();
        String reslutSql = null;
        for (String value : shardFieldValues) {
        	Map<String,Object> shardMap = new HashMap<String,Object>();
        	shardMap.put(shardField, Long.parseLong(value));
        	try {
        		reslutSql = dbRoter.routing(sql, shardMap);
			} catch (Throwable t){
			    t.printStackTrace();
			    logger.debug("dbRoter.routing error",t.getMessage());
			    return null;
			}
        	if (realSqlMap.containsKey(reslutSql)) {
				realSqlMap.get(reslutSql).add(value);
			} else {
				List<String> list = new LinkedList<String>();
				list.add(value);
				realSqlMap.put(reslutSql, list);
			}
		}
        
        for (String realSql : realSqlMap.keySet()) {
        	map.put(shardField, realSqlMap.get(realSql));
        	List<Map<String, Object>> obj = namedParameterJdbcShardingTemplate.queryForList(realSql, map);
        	result.addAll(obj);
		}
        return result;
    }
}
