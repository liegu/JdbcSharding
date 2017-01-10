/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.petesky.jdbcSharding;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Map;

/**
 * Desc:mast-slave JdbcOperations
 * 修改自spring-jdbc JdbcTemplate
 * Created by liegu.pete on 2016/4/18 下午5:01.
 */
public interface JdbcShardingOperations {
    <T> T execute(ConnectionCallback<T> var1, boolean isWrite) throws DataAccessException;

    <T> T execute(StatementCallback<T> var1, boolean isWrite) throws DataAccessException;

    void execute(String var1, boolean isWrite) throws DataAccessException;

    <T> T query(String var1, ResultSetExtractor<T> var2) throws DataAccessException;

    void query(String var1, RowCallbackHandler var2) throws DataAccessException;

    <T> List<T> query(String var1, RowMapper<T> var2) throws DataAccessException;

    <T> T queryForObject(String var1, RowMapper<T> var2) throws DataAccessException;

    <T> T queryForObject(String var1, Class<T> var2) throws DataAccessException;

    Map<String, Object> queryForMap(String var1) throws DataAccessException;

    long queryForLong(String var1) throws DataAccessException;

    int queryForInt(String var1) throws DataAccessException;

    <T> List<T> queryForList(String var1, Class<T> var2) throws DataAccessException;

    List<Map<String, Object>> queryForList(String var1) throws DataAccessException;

    SqlRowSet queryForRowSet(String var1) throws DataAccessException;

    int update(String var1) throws DataAccessException;

    int[] batchUpdate(String[] var1)throws DataAccessException;

    <T> T execute(PreparedStatementCreator var1, PreparedStatementCallback<T> var2, boolean isWrite) throws DataAccessException;

    <T> T execute(String var1, PreparedStatementCallback<T> var2, boolean isWrite) throws DataAccessException;

    <T> T query(PreparedStatementCreator var1, ResultSetExtractor<T> var2) throws DataAccessException;

    <T> T query(String var1, PreparedStatementSetter var2, ResultSetExtractor<T> var3) throws DataAccessException;

    <T> T query(String var1, Object[] var2, int[] var3, ResultSetExtractor<T> var4) throws DataAccessException;

    <T> T query(String var1, Object[] var2, ResultSetExtractor<T> var3) throws DataAccessException;

    <T> T query(String var1, ResultSetExtractor<T> var2, Object... var3) throws DataAccessException;

    void query(PreparedStatementCreator var1, RowCallbackHandler var2) throws DataAccessException;

    void query(String var1, PreparedStatementSetter var2, RowCallbackHandler var3) throws DataAccessException;

    void query(String var1, Object[] var2, int[] var3, RowCallbackHandler var4) throws DataAccessException;

    void query(String var1, Object[] var2, RowCallbackHandler var3) throws DataAccessException;

    void query(String var1, RowCallbackHandler var2, Object... var3) throws DataAccessException;

    <T> List<T> query(PreparedStatementCreator var1, RowMapper<T> var2) throws DataAccessException;

    <T> List<T> query(String var1, PreparedStatementSetter var2, RowMapper<T> var3) throws DataAccessException;

    <T> List<T> query(String var1, Object[] var2, int[] var3, RowMapper<T> var4) throws DataAccessException;

    <T> List<T> query(String var1, Object[] var2, RowMapper<T> var3) throws DataAccessException;

    <T> List<T> query(String var1, RowMapper<T> var2, Object... var3) throws DataAccessException;

    <T> T queryForObject(String var1, Object[] var2, int[] var3, RowMapper<T> var4) throws DataAccessException;

    <T> T queryForObject(String var1, Object[] var2, RowMapper<T> var3) throws DataAccessException;

    <T> T queryForObject(String var1, RowMapper<T> var2, Object... var3) throws DataAccessException;

    <T> T queryForObject(String var1, Object[] var2, int[] var3, Class<T> var4) throws DataAccessException;

    <T> T queryForObject(String var1, Object[] var2, Class<T> var3) throws DataAccessException;

    <T> T queryForObject(String var1, Class<T> var2, Object... var3) throws DataAccessException;

    Map<String, Object> queryForMap(String var1, Object[] var2, int[] var3) throws DataAccessException;

    Map<String, Object> queryForMap(String var1, Object... var2) throws DataAccessException;

    long queryForLong(String var1, Object[] var2, int[] var3) throws DataAccessException;

    long queryForLong(String var1, Object... var2) throws DataAccessException;

    int queryForInt(String var1, Object[] var2, int[] var3) throws DataAccessException;

    int queryForInt(String var1, Object... var2) throws DataAccessException;

    <T> List<T> queryForList(String var1, Object[] var2, int[] var3, Class<T> var4) throws DataAccessException;

    <T> List<T> queryForList(String var1, Object[] var2, Class<T> var3) throws DataAccessException;

    <T> List<T> queryForList(String var1, Class<T> var2, Object... var3) throws DataAccessException;

    List<Map<String, Object>> queryForList(String var1, Object[] var2, int[] var3) throws DataAccessException;

    List<Map<String, Object>> queryForList(String var1, Object... var2) throws DataAccessException;

    SqlRowSet queryForRowSet(String var1, Object[] var2, int[] var3) throws DataAccessException;

    SqlRowSet queryForRowSet(String var1, Object... var2) throws DataAccessException;

    int update(PreparedStatementCreator var1) throws DataAccessException;
    
    Long insert(PreparedStatementCreator var1) throws DataAccessException;

    int update(PreparedStatementCreator var1, KeyHolder var2) throws DataAccessException;

    int update(String var1, PreparedStatementSetter var2) throws DataAccessException;

    int update(String var1, Object[] var2, int[] var3) throws DataAccessException;

    int update(String var1, Object... var2) throws DataAccessException;

    int[] batchUpdate(String var1, BatchPreparedStatementSetter var2) throws DataAccessException;

    <T> T execute(CallableStatementCreator var1, CallableStatementCallback<T> var2, boolean isWrite) throws DataAccessException;

    <T> T execute(String var1, CallableStatementCallback<T> var2, boolean isWrite) throws DataAccessException;

    Map<String, Object> call(CallableStatementCreator var1, List<SqlParameter> var2) throws DataAccessException;
}