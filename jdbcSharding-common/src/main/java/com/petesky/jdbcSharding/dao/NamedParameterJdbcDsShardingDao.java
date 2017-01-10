package com.petesky.jdbcSharding.dao;

import com.petesky.jdbcSharding.NamedParameterJdbcShardingTemplate;
import com.petesky.jdbcSharding.dbRouter.DBRouter;
import com.petesky.jdbcSharding.exception.DBRouterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Desc:仅用于多数据源实例，多database，多表负载实现
 * 适用于多DataSource多库多表的情况
 * Created by liegu.pete on 2016/6/22 上午11:54.
 */
public  class NamedParameterJdbcDsShardingDao  <DO extends Serializable,R extends DBRouter>  {

    private static final Logger logger = LoggerFactory.getLogger(NamedParameterJdbcDsShardingDao.class);

    protected List<NamedParameterJdbcShardingTemplate> namedParameterJdbcShardingTemplateList;

    public void setNamedParameterJdbcShardingTemplateList(List<NamedParameterJdbcShardingTemplate> namedParameterJdbcShardingTemplateLIst) {
        this.namedParameterJdbcShardingTemplateList = namedParameterJdbcShardingTemplateLIst;
    }
    /***
     * 按DBRouter规则获取NamedParameterJdbcShardingTemplate
     * @return
     */
    protected NamedParameterJdbcShardingTemplate getJdbcTemplate(R roter,Map map,String sql) {
        NamedParameterJdbcShardingTemplate obj=null;
        try {
            obj= this.namedParameterJdbcShardingTemplateList.get(roter.insRouting(map,sql));
        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",roter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("getJdbcTemplate is Error! {}", t.getMessage());
        }
        return obj;
    }

    /***
     * 查询单个结果为Integer
     * @param dbRoter
     * @param sql
     * @param map
     * @return
     */
    public int queryInt(R dbRoter,String sql, Map<String, ?> map) {
        int obj=0;
        try {
            obj= getJdbcTemplate(dbRoter,map,sql).queryForInt(dbRoter.routing(sql,map), map);
        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}", t.getMessage());
        }
        return obj;
    }

    /***
     * 查询单个结果为Long
     * @param dbRoter
     * @param sql
     * @param map
     * @return
     */
    public Long queryLong(R dbRoter,String sql, Map<String, ?> map) {
        Long obj=null;
        try {
            obj= getJdbcTemplate(dbRoter,map,sql).queryForLong(dbRoter.routing(sql,map), map);
        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}", t.getMessage());
        }
        return obj;
    }

    /***
     * 使用RowMapper查询DO对象集合
     * @param dbRoter
     * @param sql
     * @param map
     * @param rowMapper
     * @return
     */
    public  List<DO> queryList(R dbRoter,String sql, Map<String, ?> map, RowMapper rowMapper){
        List<DO> obj=null;
        try {
            obj= getJdbcTemplate(dbRoter,map,sql).query(dbRoter.routing(sql,map), map, rowMapper);
        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}", t.getMessage());
        }
        return obj;
    }


    /***
     * 使用DO对象查询对象集合
     * @param dbRoter
     * @param sql
     * @param map
     * @param o
     * @return
     */
    public  List<DO> queryList(R dbRoter,String sql, Map<String, ?> map,DO o){
        List<DO> obj=null;
        try {
            obj=  getJdbcTemplate(dbRoter,map,sql).query(dbRoter.routing(sql,map), map, new BeanPropertyRowMapper(o.getClass()));
        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}", t.getMessage());
        }
        return obj;
    }

    /***
     * 保存或更新操作
     * @param dbRoter
     * @param sql
     * @param map
     * @return
     */
    public int saveOrUpdate(R dbRoter,String sql, Map<String, ?> map) {
        int obj=0;
        try {
            obj= getJdbcTemplate(dbRoter,map,sql).update(dbRoter.routing(sql,map), map);
        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}", t.getMessage());
        }
        return obj;
    }

    /***
     * 按RowMapper查询单个DO对象
     * @param dbRoter
     * @param sql
     * @param map
     * @param rowMapper
     * @return
     */
    public DO queryForObject(R dbRoter,String sql, Map<String, ?> map, RowMapper rowMapper)
    {
        DO o=null;
        try {
            o= (DO) getJdbcTemplate(dbRoter,map,sql).queryForObject(dbRoter.routing(sql,map), map, rowMapper);

        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}",t.getMessage());
        }
        return o;
    }

    /***
     * 按DO对象查询单个DO对象
     * @param dbRoter
     * @param sql
     * @param map
     * @param o
     * @return
     */
    public DO queryForObject(R dbRoter,String sql, Map<String, ?> map,DO o)
    {
        DO obj=null;
        try {
            obj= (DO) getJdbcTemplate(dbRoter,map,sql).queryForObject(dbRoter.routing(sql,map), map, new BeanPropertyRowMapper(o.getClass()));

        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}",t.getMessage());
        }
        return obj;
    }

    /***
     * 查询单个数据库对象Map
     * @param dbRoter
     * @param sql
     * @param map
     * @return
     */
    public Map<String, Object> queryForMap(R dbRoter,String sql, Map<String, ?> map) {
        Map obj=null;
        try {
            obj=(Map) getJdbcTemplate(dbRoter,map,sql).queryForObject(dbRoter.routing(sql, map), (Map) map, (RowMapper) (new ColumnMapRowMapper()));
        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}",t.getMessage());
        }
        return obj;
    }


    /***
     * 查询数据库对象Map键值集合
     * @param dbRoter
     * @param sql
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryForList(R dbRoter,String sql, Map<String, ?> map) {
        List<Map<String, Object>> obj=null;
        try {
            obj= getJdbcTemplate(dbRoter,map,sql).queryForList(dbRoter.routing(sql,map), map);
        } catch (DBRouterException t) {
            t.printStackTrace();
            logger.debug("DBRouter error! dbRoter:{},sql:{}",dbRoter,sql);
        } catch (Throwable t){
            t.printStackTrace();
            logger.debug("queryResult is Empty! {}",t.getMessage());
        }
        return obj;
    }

}
