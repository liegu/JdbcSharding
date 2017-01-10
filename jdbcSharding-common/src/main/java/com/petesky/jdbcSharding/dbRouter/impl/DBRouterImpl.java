package com.petesky.jdbcSharding.dbRouter.impl;

import com.petesky.jdbcSharding.bean.ShardingTypeEnum;
import com.petesky.jdbcSharding.dbRouter.DBRouter;
import com.petesky.jdbcSharding.dbRouter.ModSliceCompute;
import com.petesky.jdbcSharding.exception.DBRouterException;
import com.petesky.jdbcSharding.sql.SqlParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Desc:数据库单表查询路由对象
 * Created by liegu.pete on 2016/8/4 下午5:56.
 */
public class DBRouterImpl implements DBRouter {

    private static final Logger logger = LoggerFactory.getLogger(DBRouterImpl.class);
    String  tableName; //表名称
    String  tableRule; //表正则
    Integer tableNumber;//分表总数
    String  shardField; //分表主键
    List<ModSliceCompute> sliceComputeList; //sql 逻辑db,table分片计算
    ModSliceCompute insModSliceCompute; //实例分片计算
    String shardingType; //分片类型


    /***
     * 计算路由并替换对应sql
     * @param sql
     * @return
     * @throws DBRouterException
     */
    public String  routing(String sql,Map<String,?> map)throws DBRouterException {
        sql=sql.toLowerCase();
        //解析表名
        String tableName= SqlParseUtil.parseSqlTable(sql);
        //运算表名
        String tmpTable=computeTable(map,shardField);
        //替换表名
        String reslutSql=sql.replace(tableName, tmpTable);
        if(logger.isDebugEnabled()) {
            logger.debug(" reslutSql:{}",reslutSql);
        }
        return reslutSql;
    }

    /***
     * 调用计算器推算数据所在库表
     * @param map
     * @return
     * @throws DBRouterException
     */
    public String computeTable(Map<String,?> map,String shardField)throws DBRouterException {
        //校验分片计算器
        if(CollectionUtils.isEmpty(sliceComputeList)){
            throw new DBRouterException("SliceCompute list is NULL");
        }
        //构建新rule
        String tmpTable=new String(tableRule);

        Object shardFieldValue=getShardFieldValue(map,shardField);
        //逐级替换
        for(int i=0,j=sliceComputeList.size();i<j;i++){
            ModSliceCompute sc=sliceComputeList.get(i);
            if(shardFieldValue==null){
                throw new DBRouterException("ShardField Value is NULL");
            }
            String num=sc.compute(shardFieldValue, tableNumber);
            tmpTable= tmpTable.replace("{"+i+"}", num);
        }
        return tmpTable;
    }
    /**
     * 计算数据库实例分片
     * @param map
     * @return
     * @throws DBRouterException
     */
    public int insRouting(Map<String, ?> map,String sql)throws DBRouterException {
        //校验分片计算器
        if(insModSliceCompute==null){
            throw new DBRouterException("InsModSliceCompute is empty");
        }
        //计算实例分片
        String num=insModSliceCompute.compute( getShardFieldValue( map,shardField),tableNumber);
        if(logger.isDebugEnabled()) {
            logger.debug(" db ins:{}",num);
        }
        return Integer.valueOf(num);
    }

    /***
     * 拆分参与分片计算的表字段名称
     * @param shardField
     * @return
     */
    public String[] splitShardField(String shardField){
        return shardField.replaceAll(" ","").split(",");
    }

    /***
     * 获取分表字段对象
     * @param map
     * @return
     */
    public Object getShardFieldValue(Map<String,?> map,String shardField){
        Object shardFieldValue=null;

        if(ShardingTypeEnum.MOD.getText().equals(shardingType)){
            shardFieldValue=map.get(shardField);
        }else if(ShardingTypeEnum.HASH.getText().equals(shardingType)){
            String[] shardFields=splitShardField(shardField);
            StringBuilder sb=new StringBuilder();
            for(String shardFieldKey:shardFields){
                sb.append(map.get(shardFieldKey));
            }
            shardFieldValue=sb.toString();
        }
        return shardFieldValue;
    }
    public void setTableRule(String tableRule) {
        this.tableRule = tableRule;
    }

    public void setInsModSliceCompute(ModSliceCompute insModSliceCompute) {
        this.insModSliceCompute = insModSliceCompute;
    }

    public void setSliceComputeList(List<ModSliceCompute> sliceComputeList) {
        this.sliceComputeList = sliceComputeList;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setShardField(String shardField) {
        this.shardField = shardField;
    }

    public String getTableRule() {
        return tableRule;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public String getShardField() {
        return shardField;
    }

    public List<ModSliceCompute> getSliceComputeList() {
        return sliceComputeList;
    }

    public ModSliceCompute getInsModSliceCompute() {
        return insModSliceCompute;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getShardingType() {
        return shardingType;
    }

    public void setShardingType(String shardingType) {
        this.shardingType = shardingType;
    }
}
