package com.petesky.jdbcSharding;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Desc:
 * Created by liegu.pete on 2016/6/14 下午3:08.
 */
public class NamedParameterBatchUpdateShardingUtils {


    public static int[] executeBatchUpdateWithNamedParameters(final ParsedSql parsedSql, final SqlParameterSource[] batchArgs, JdbcShardingOperations jdbcOperations) {
        if(batchArgs.length <= 0) {
            return new int[1];
        } else {
            String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, batchArgs[0]);
            return jdbcOperations.batchUpdate(sqlToUse, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Object[] values = NamedParameterUtils.buildValueArray(parsedSql, batchArgs[i], (List)null);
                    int[] columnTypes = NamedParameterUtils.buildSqlTypeArray(parsedSql, batchArgs[i]);
                    setStatementParameters(values, ps, columnTypes);
                }

                public int getBatchSize() {
                    return batchArgs.length;
                }
            });
        }
    }

    protected static void setStatementParameters(Object[] values, PreparedStatement ps, int[] columnTypes) throws SQLException {
        int colIndex = 0;
        Object[] var7 = values;
        int var6 = values.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Object value = var7[var5];
            ++colIndex;
            if(value instanceof SqlParameterValue) {
                SqlParameterValue var9 = (SqlParameterValue)value;
                StatementCreatorUtils.setParameterValue(ps, colIndex, var9, var9.getValue());
            } else {
                int colType;
                if(columnTypes != null && columnTypes.length >= colIndex) {
                    colType = columnTypes[colIndex - 1];
                } else {
                    colType = -2147483648;
                }

                StatementCreatorUtils.setParameterValue(ps, colIndex, colType, value);
            }
        }

    }
}
