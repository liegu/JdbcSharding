/*
 * Copyright 2002-2007 the original author or authors.
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * mast-slave JdbcAccessor
 * 修改自spring-jdbc JdbcAccessor
 * 调整原代码增加读写分离功能
 * Created by liegu.pete on 2016/4/18 下午5:01.
 */
public abstract class JdbcAccessor implements InitializingBean {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private DataSource dataSource;
    private List<DataSource> dataSourceSlaves = new ArrayList<DataSource>();

    private Random random = new Random();

    /**
     * model: master: write & read; slave: read
     * @param isWrite
     * @return
     */
    public DataSource getDataSource(boolean isWrite) {
        if(isWrite || dataSourceSlaves == null || dataSourceSlaves.size() == 0){
            return dataSource;
        }else{
            if(dataSourceSlaves.size() == 1){
                return dataSourceSlaves.get(0);
            }else {
                int rd = random.nextInt(dataSourceSlaves.size());
                return dataSourceSlaves.get(rd);
            }
        }
    }
    private SQLExceptionTranslator exceptionTranslator;
    private boolean lazyInit = true;

    public JdbcAccessor() {
    }


    public void setDatabaseProductName(String dbName) {
        this.exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dbName);
    }

    public void setExceptionTranslator(SQLExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    public synchronized SQLExceptionTranslator getExceptionTranslator(DataSource dataSource) {
        if(this.exceptionTranslator == null) {
            if(dataSource != null) {
                this.exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            } else {
                this.exceptionTranslator = new SQLStateSQLExceptionTranslator();
            }
        }

        return this.exceptionTranslator;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public boolean isLazyInit() {
        return this.lazyInit;
    }

    public void afterPropertiesSet() {
        DataSource ds=this.getDataSource(true);
        if(ds == null) {
            throw new IllegalArgumentException("Property \'dataSource\' is required");
        } else {
            if(!this.isLazyInit()) {
                this.getExceptionTranslator(ds);
            }

        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSourceSlaves(List<DataSource> dataSourceSlaves) {
        this.dataSourceSlaves = dataSourceSlaves;
    }
}