package com.petesky.jdbcSharding;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Desc:
 * Created by liegu.pete on 2016/6/14 下午12:02.
 */
public class NamedParameterJdbcShardingTemplate implements NamedParameterJdbcShardingOperations {

    private  JdbcShardingOperations classicJdbcTemplate;
    private volatile int cacheLimit = 256;
    private final Map<String, ParsedSql> parsedSqlCache = new LinkedHashMap<String, ParsedSql> (256, 0.75F, true) {
        @Override
        protected boolean removeEldestEntry( Entry<String, ParsedSql> eldest) {
            return this.size() > NamedParameterJdbcShardingTemplate.this.getCacheLimit();
        }
    };

    public JdbcShardingOperations getClassicJdbcTemplate() {
        return classicJdbcTemplate;
    }

    public void setClassicJdbcTemplate(JdbcShardingOperations classicJdbcTemplate) {
        this.classicJdbcTemplate = classicJdbcTemplate;
    }

    public void setCacheLimit(int cacheLimit) {
        this.cacheLimit = cacheLimit;
    }

    public int getCacheLimit() {
        return this.cacheLimit;
    }

    @Override
    public JdbcShardingOperations getJdbcOperations() {
        return classicJdbcTemplate;
    }



    public <T> T query(String sql, SqlParameterSource paramSource, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.getJdbcOperations().query(this.getPreparedStatementCreator(sql, paramSource), rse);
    }

    public <T> T query(String sql, Map<String, ?> paramMap, ResultSetExtractor<T> rse) throws DataAccessException {
        return (T) this.query(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)), (ResultSetExtractor) rse);
    }

    public void query(String sql, SqlParameterSource paramSource, RowCallbackHandler rch) throws DataAccessException {
        this.getJdbcOperations().query(this.getPreparedStatementCreator(sql, paramSource), rch);
    }

    public void query(String sql, Map<String, ?> paramMap, RowCallbackHandler rch) throws DataAccessException {
        this.query(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)), (RowCallbackHandler) rch);
    }

    public <T> List<T> query(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException {
        return this.getJdbcOperations().query(this.getPreparedStatementCreator(sql, paramSource), rowMapper);
    }

    public <T> List<T> query(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws DataAccessException {
        return this.query(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)), (RowMapper) rowMapper);
    }

    public <T> T queryForObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException {
        List results = this.getJdbcOperations().query(this.getPreparedStatementCreator(sql, paramSource), rowMapper);
        return (T) DataAccessUtils.requiredSingleResult(results);
    }

    public <T> T queryForObject(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws DataAccessException {
        return (T) this.queryForObject(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)), (RowMapper) rowMapper);
    }

    public <T> T queryForObject(String sql, SqlParameterSource paramSource, Class<T> requiredType) throws DataAccessException {
        return (T) this.queryForObject(sql, (SqlParameterSource) paramSource, (RowMapper) (new SingleColumnRowMapper(requiredType)));
    }

    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType) throws DataAccessException {
        return (T) this.queryForObject(sql, (Map) paramMap, (RowMapper) (new SingleColumnRowMapper(requiredType)));
    }

    public Map<String, Object> queryForMap(String sql, SqlParameterSource paramSource) throws DataAccessException {
        return (Map) this.queryForObject(sql, (SqlParameterSource) paramSource, (RowMapper) (new ColumnMapRowMapper()));
    }

    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) throws DataAccessException {
        return (Map) this.queryForObject(sql, (Map) paramMap, (RowMapper) (new ColumnMapRowMapper()));
    }

    public long queryForLong(String sql, SqlParameterSource paramSource) throws DataAccessException {
        Number number = (Number) this.queryForObject(sql, paramSource, Number.class);
        return number != null ? number.longValue() : 0L;
    }

    public long queryForLong(String sql, Map<String, ?> paramMap) throws DataAccessException {
        return this.queryForLong(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)));
    }

    public int queryForInt(String sql, SqlParameterSource paramSource) throws DataAccessException {
        Number number = (Number) this.queryForObject(sql, paramSource, Number.class);
        return number != null ? number.intValue() : 0;
    }

    public int queryForInt(String sql, Map<String, ?> paramMap) throws DataAccessException {
        return this.queryForInt(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)));
    }

    public <T> List<T> queryForList(String sql, SqlParameterSource paramSource, Class<T> elementType) throws DataAccessException {
        return this.query(sql, (SqlParameterSource) paramSource, (RowMapper) (new SingleColumnRowMapper(elementType)));
    }

    public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> elementType) throws DataAccessException {
        return this.queryForList(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)), elementType);
    }

    public List<Map<String, Object>> queryForList(String sql, SqlParameterSource paramSource) throws DataAccessException {
        return this.query(sql, (SqlParameterSource) paramSource, (RowMapper) (new ColumnMapRowMapper()));
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) throws DataAccessException {
        return this.queryForList(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)));
    }

    public SqlRowSet queryForRowSet(String sql, SqlParameterSource paramSource) throws DataAccessException {
        return (SqlRowSet) this.getJdbcOperations().query(this.getPreparedStatementCreator(sql, paramSource), new SqlRowSetResultSetExtractor());
    }

    public SqlRowSet queryForRowSet(String sql, Map<String, ?> paramMap) throws DataAccessException {
        return this.queryForRowSet(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)));
    }

    public int update(String sql, SqlParameterSource paramSource) throws DataAccessException {
        return this.getJdbcOperations().update(this.getPreparedStatementCreator(sql, paramSource));
    }

    public int update(String sql, Map<String, ?> paramMap) throws DataAccessException {
        return this.update(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)));
    }
    
    public Long insert(String sql, SqlParameterSource paramSource) throws DataAccessException {
    	return this.getJdbcOperations().insert(this.getPreparedStatementCreator(sql, paramSource));
    }
    
    public Long insert(String sql, Map<String, ?> paramMap) throws DataAccessException {
    	return this.insert(sql, (SqlParameterSource) (new MapSqlParameterSource(paramMap)));
    	
    }

    public int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder) throws DataAccessException {
        return this.update(sql, paramSource, generatedKeyHolder, (String[]) null);
    }

    public int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder, String[] keyColumnNames) throws DataAccessException {
        ParsedSql parsedSql = this.getParsedSql(sql);
        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
        Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, (List) null);
        List declaredParameters = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, declaredParameters);
        if (keyColumnNames != null) {
            pscf.setGeneratedKeysColumnNames(keyColumnNames);
        } else {
            pscf.setReturnGeneratedKeys(true);
        }

        return this.getJdbcOperations().update(pscf.newPreparedStatementCreator(params), generatedKeyHolder);
    }

    public int[] batchUpdate(String sql, Map<String, ?>[] batchValues) {
        SqlParameterSource[] batchArgs = new SqlParameterSource[batchValues.length];
        int i = 0;
        Map[] var8 = batchValues;
        int var7 = batchValues.length;

        for (int var6 = 0; var6 < var7; ++var6) {
            Map values = var8[var6];
            batchArgs[i] = new MapSqlParameterSource(values);
            ++i;
        }

        return this.batchUpdate(sql, batchArgs);
    }

    public int[] batchUpdate(String sql, SqlParameterSource[] batchArgs) {
        ParsedSql parsedSql = this.getParsedSql(sql);
        return NamedParameterBatchUpdateShardingUtils.executeBatchUpdateWithNamedParameters(parsedSql, batchArgs, this.getJdbcOperations());
    }

    protected PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource) {
        ParsedSql parsedSql = this.getParsedSql(sql);
        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
        Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, (List) null);
        List declaredParameters = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, declaredParameters);
        pscf.setReturnGeneratedKeys(true);
        return pscf.newPreparedStatementCreator(params);
    }

    protected ParsedSql getParsedSql(String sql) {
        if (this.getCacheLimit() <= 0) {
            return NamedParameterUtils.parseSqlStatement(sql);
        } else {
            Map var2 = this.parsedSqlCache;
            synchronized (this.parsedSqlCache) {
                ParsedSql parsedSql = (ParsedSql) this.parsedSqlCache.get(sql);
                if (parsedSql == null) {
                    parsedSql = NamedParameterUtils.parseSqlStatement(sql);
                    this.parsedSqlCache.put(sql, parsedSql);
                }

                return parsedSql;
            }
        }
    }
}