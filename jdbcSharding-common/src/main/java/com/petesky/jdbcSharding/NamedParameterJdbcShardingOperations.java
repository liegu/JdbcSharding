package com.petesky.jdbcSharding;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Map;

/**
 * Desc:
 * Created by liegu.pete on 2016/6/14 下午12:04.
 */
public interface NamedParameterJdbcShardingOperations {
    JdbcShardingOperations getJdbcOperations();


    <T> T query(String var1, SqlParameterSource var2, ResultSetExtractor<T> var3) throws DataAccessException;

    <T> T query(String var1, Map<String, ?> var2, ResultSetExtractor<T> var3) throws DataAccessException;

    void query(String var1, SqlParameterSource var2, RowCallbackHandler var3) throws DataAccessException;

    void query(String var1, Map<String, ?> var2, RowCallbackHandler var3) throws DataAccessException;

    <T> List<T> query(String var1, SqlParameterSource var2, RowMapper<T> var3) throws DataAccessException;

    <T> List<T> query(String var1, Map<String, ?> var2, RowMapper<T> var3) throws DataAccessException;

    <T> T queryForObject(String var1, SqlParameterSource var2, RowMapper<T> var3) throws DataAccessException;

    <T> T queryForObject(String var1, Map<String, ?> var2, RowMapper<T> var3) throws DataAccessException;

    <T> T queryForObject(String var1, SqlParameterSource var2, Class<T> var3) throws DataAccessException;

    <T> T queryForObject(String var1, Map<String, ?> var2, Class<T> var3) throws DataAccessException;

    Map<String, Object> queryForMap(String var1, SqlParameterSource var2) throws DataAccessException;

    Map<String, Object> queryForMap(String var1, Map<String, ?> var2) throws DataAccessException;

    long queryForLong(String var1, SqlParameterSource var2) throws DataAccessException;

    long queryForLong(String var1, Map<String, ?> var2) throws DataAccessException;

    int queryForInt(String var1, SqlParameterSource var2) throws DataAccessException;

    int queryForInt(String var1, Map<String, ?> var2) throws DataAccessException;

    <T> List<T> queryForList(String var1, SqlParameterSource var2, Class<T> var3) throws DataAccessException;

    <T> List<T> queryForList(String var1, Map<String, ?> var2, Class<T> var3) throws DataAccessException;

    List<Map<String, Object>> queryForList(String var1, SqlParameterSource var2) throws DataAccessException;

    List<Map<String, Object>> queryForList(String var1, Map<String, ?> var2) throws DataAccessException;

    SqlRowSet queryForRowSet(String var1, SqlParameterSource var2) throws DataAccessException;

    SqlRowSet queryForRowSet(String var1, Map<String, ?> var2) throws DataAccessException;

    int update(String var1, SqlParameterSource var2) throws DataAccessException;

    int update(String var1, Map<String, ?> var2) throws DataAccessException;

    int update(String var1, SqlParameterSource var2, KeyHolder var3) throws DataAccessException;

    int update(String var1, SqlParameterSource var2, KeyHolder var3, String[] var4) throws DataAccessException;

    int[] batchUpdate(String var1, Map<String, ?>[] var2);

    int[] batchUpdate(String var1, SqlParameterSource[] var2);
}
