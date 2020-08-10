package com.ruizhi.data.event;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruizhi.data.commons.exception.BizException;
import com.ruizhi.data.constant.ResultCode;
import com.ruizhi.data.constant.TaskStatusCode;
import com.ruizhi.data.dal.entitys.DataSourceInfo;
import com.ruizhi.data.dal.entitys.FldCltRst;
import com.ruizhi.data.dal.entitys.FlwInfo;
import com.ruizhi.data.dal.entitys.RtlFlwDb;
import com.ruizhi.data.dal.entitys.RtlFlwDbTbl;
import com.ruizhi.data.dto.flwInfo.ForeignKeyDTO;
import com.ruizhi.data.dto.flwInfo.IndexInfoDTO;
import com.ruizhi.data.dto.rtlFlwDBTblInfo.TableAndFieldDTO;
import com.ruizhi.data.service.DataSourceInfoService;
import com.ruizhi.data.service.FlwInfoService;
import com.ruizhi.data.service.RtlFlwDbService;
import com.ruizhi.data.service.RtlFlwDbTblService;
import com.ruizhi.data.utils.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 数据采集事件
 * Created by lvjie on 2020/8/9
 */
@Slf4j
public class DataCollectionEvent extends ApplicationEvent {

    /**
     * 作业ID
     */
    private Integer flwInfoId;

    public DataCollectionEvent(Object source, Integer flwInfoId) {
        super(source);
        this.flwInfoId = flwInfoId;
    }

    public Integer getFlwInfoId() {
        return flwInfoId;
    }

    /**
     * 采集数据处理
     */
    public void insertDataHandle() {
        //1.查询配置数据源数据信息
        RtlFlwDbService rtlFlwDbService = ApplicationContextProvider.getBean(RtlFlwDbService.class);
        DataSourceInfoService dataSourceInfoService = ApplicationContextProvider.getBean(DataSourceInfoService.class);
        FlwInfoService flwInfoService = ApplicationContextProvider.getBean(FlwInfoService.class);
        FlwInfo flwInfo = flwInfoService.getById(this.flwInfoId);
        QueryWrapper<RtlFlwDb> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RtlFlwDb::getFlwId,this.flwInfoId);
        List<RtlFlwDb> rtlFlwDbList = rtlFlwDbService.list(queryWrapper);
        for (RtlFlwDb rtlFlwDb : rtlFlwDbList) {
            // 查询数据源信息
            Integer srcId = rtlFlwDb.getSrcId();
            Integer id = rtlFlwDb.getId();
            DataSourceInfo dataSourceInfo = dataSourceInfoService.getById(srcId);
            if (Objects.isNull(dataSourceInfo)) {
                log.error("数据源数据不存在，数据源ID为：{}",srcId);
                break;
            }
            List<TableAndFieldDTO> tableAndFieldDTOList = this.getTableAndFieldData(dataSourceInfo,rtlFlwDb,flwInfo);
            // 2.插入表数据、字段数据、采集数据
            RtlFlwDbTblService rtlFlwDbTblService = ApplicationContextProvider.getBean(RtlFlwDbTblService.class);
            rtlFlwDbTblService.insertDataConnection(tableAndFieldDTOList,flwInfo);
            // 3.修改采集表数据为已完成
            flwInfo.setFlwColTyp(TaskStatusCode.COMPLETE.getCode());
            flwInfoService.updateById(flwInfo);
        }

    }

    /**
     * 返回数据库表和数据库表字段信息
     * @param dataSourceInfo
     * @param rtlFlwDb
     * @param flwInfo
     * @return
     */
    private List<TableAndFieldDTO> getTableAndFieldData(DataSourceInfo dataSourceInfo,RtlFlwDb rtlFlwDb,FlwInfo flwInfo) {
        List<TableAndFieldDTO> tableAndFieldDTOList = new ArrayList<>();
        DBUtil dbUtil = new DBUtil(dataSourceInfo.getSrcTyp(), dataSourceInfo.getIpAdr(), dataSourceInfo.getPrt(), dataSourceInfo.getUsr(), dataSourceInfo.getPwd(), dataSourceInfo.getIstNam());
        try {
            Connection conn = dbUtil.getConnection();
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet rs = dbMetaData.getTables(null, null, null, new String[]{"TABLE"});
            List<String> tables = new ArrayList<String>();
            while (rs.next()) {
                TableAndFieldDTO tableAndFieldDTO = new TableAndFieldDTO();
                // 获取表名
                String table_name = rs.getString("TABLE_NAME");
                // 获取表注释
                String table_comment = this.getTableComment(table_name,conn);
                // 获取表所有的主键列
                ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(null, null, table_name);
                List<String> primaryColumnList = this.getPrimaryColumns(primaryKeyResultSet);
                // 获取表的外键信息
                ResultSet foreignKeyResultSet = dbMetaData.getImportedKeys(null, null, table_name);
                List<ForeignKeyDTO> foreignKeyDTOList = this.getForeignKeyInfo(foreignKeyResultSet,conn);
                // 获取表的索引信息
                ResultSet indexResultSet = dbMetaData.getIndexInfo(null, null, table_name, false, true);
                List<IndexInfoDTO> indexInfoDTOList = this.getIndexInfo(indexResultSet);
                // 通过表名获取所有字段名
                ResultSet columns = dbMetaData.getColumns(null, null, table_name, "%");
                List<FldCltRst> fldCltRstList = this.getColumnInfo(columns, primaryColumnList, foreignKeyDTOList, indexInfoDTOList,table_name,Integer.parseInt(flwInfo.getSmpNum()),conn);

                RtlFlwDbTbl rtlFlwDbTbl = new RtlFlwDbTbl();
                rtlFlwDbTbl.setSrcId(rtlFlwDb.getId());
                rtlFlwDbTbl.setDbName(dataSourceInfo.getIstNam());
                rtlFlwDbTbl.setDbType(dataSourceInfo.getSrcTyp());
                rtlFlwDbTbl.setTblName(table_name);
                rtlFlwDbTbl.setTblCmt(table_comment);
                rtlFlwDbTbl.setFldNum(String.valueOf(fldCltRstList.size()));
                rtlFlwDbTbl.setDataNum(String.valueOf(this.getTableRows(table_name,conn)));
                rtlFlwDbTbl.setPsySize(this.getTablePhysicsSize(table_name,dataSourceInfo.getIstNam(),conn));
                this.tableInfoHandler(rtlFlwDbTbl,fldCltRstList);
                tableAndFieldDTO.setFldCltRstList(fldCltRstList);
                tableAndFieldDTO.setRtlFlwDbTbl(rtlFlwDbTbl);
                tableAndFieldDTOList.add(tableAndFieldDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableAndFieldDTOList;
    }

    /**
     * 获取列信息
     * @param columns
     * @param primaryColumnList
     * @param foreignKeyDTOList
     * @param indexInfoDTOList
     * @return
     * @throws SQLException
     */
    private List<FldCltRst> getColumnInfo(ResultSet columns, List<String> primaryColumnList, List<ForeignKeyDTO> foreignKeyDTOList, List<IndexInfoDTO> indexInfoDTOList,
                                          String tableName,int num,Connection conn) throws SQLException {
        List<FldCltRst> fldCltRstList = new ArrayList<>();
        while (columns.next()) {
            FldCltRst fldCltRst = new FldCltRst();
            //获得字段名
            String column_name = columns.getString("COLUMN_NAME");
            //获得字段类型
            String type_name = columns.getString("TYPE_NAME");
            //获得字段注释
            String remarks = columns.getString("REMARKS");
            //获得列的长度
            Integer column_size = Integer.parseInt(columns.getString("COLUMN_SIZE"));
            fldCltRst.setFldNamEn(column_name);
            fldCltRst.setFldTyp(type_name);
            fldCltRst.setFldCmt(remarks);
            fldCltRst.setFldLen(column_size);
            //列位置
            fldCltRst.setFldPoi(columns.getRow());
            fldCltRst.setIsTime(isTimeColumn(type_name));
            //是否列主键
            String isPky = isPrimary(primaryColumnList, column_name);
            fldCltRst.setIsPky(isPky);
            if ("是".equals(isPky)) {
                fldCltRst.setIsUnique("是");
            } else {
                fldCltRst.setIsUnique(this.isUniqueColumn(indexInfoDTOList,column_name));
            }
            // 是否外键列
            String isForeignColumn = "否";
            String foreignColumn = "0";
            for(ForeignKeyDTO foreignKeyDTO : foreignKeyDTOList) {
                if (foreignKeyDTO.equals(column_name)) {
                    isForeignColumn = "是";
                    foreignColumn = foreignKeyDTO.getIndex().toString();
                }
                // 外键表名注释
                fldCltRst.setForKeyTbl(foreignKeyDTO.getFkTableNameComment());
            }
            fldCltRst.setIsForKey(isForeignColumn);
            fldCltRst.setForKeyPoi(foreignColumn);
            // 是否索引列
            fldCltRst.setIsInd(this.isIndexColumn(indexInfoDTOList,column_name));
            // 获取结果集数据
            List<String> dataList = new ArrayList<>();
            // 查询采集数据条数
            ResultSet dbDataResultSet = this.getDbData(tableName,column_name,num,conn);
            while (dbDataResultSet.next()) {
                String dataValue = dbDataResultSet.getString(1);
                dataList.add(dataValue);
            }
            fldCltRst.setResultData(dataList);
            fldCltRstList.add(fldCltRst);
        }
        return fldCltRstList;
    }

    /**
     * 是否是时间类型
     *
     * @param type_name
     * @return
     */
    private String isTimeColumn(String type_name) {
        return "否";
    }

    /**
     * 是否是主键列
     *
     * @param primaryColumnList
     * @param columnName
     * @return
     */
    private String isPrimary(List<String> primaryColumnList, String columnName) {
        if (primaryColumnList.contains(columnName)) {
            return "是";
        }
        return "否";
    }

    /**
     * 获取表的主键列
     *
     * @param primaryKeyResultSet
     * @return
     * @throws SQLException
     */
    private List<String> getPrimaryColumns(ResultSet primaryKeyResultSet) throws SQLException {
        List<String> primaryColumnList = new ArrayList<>();
        while (primaryKeyResultSet.next()) {
            String primaryColumn = primaryKeyResultSet.getString("COLUMN_NAME");
            primaryColumnList.add(primaryColumn);
        }
        return primaryColumnList;
    }

    /**
     * 获取表的外键信息
     *
     * @param foreignKeyResultSet
     * @return
     * @throws SQLException
     */
    private List<ForeignKeyDTO> getForeignKeyInfo(ResultSet foreignKeyResultSet,Connection conn) throws SQLException {
        List<ForeignKeyDTO> foreignKeyDTOList = new ArrayList<>();
        int i = 1;
        while (foreignKeyResultSet.next()) {
            // 外键列名称
            String fkColumnName = foreignKeyResultSet.getString("FKCOLUMN_NAME");
            // 外键关联表名
            String fkTableName = foreignKeyResultSet.getString("PKTABLE_NAME");
            // 外键关联表列名称
            String pkColumnName = foreignKeyResultSet.getString("PKCOLUMN_NAME");
            ForeignKeyDTO foreignKeyDTO = new ForeignKeyDTO();
            foreignKeyDTO.setFkColumnName(fkColumnName);
            foreignKeyDTO.setFkTableName(fkTableName);
            foreignKeyDTO.setPkColumnName(pkColumnName);
            foreignKeyDTO.setIndex(i);
            // 获取关联外键表的表名
            String tableComment = this.getTableComment(fkTableName,conn);
            foreignKeyDTO.setFkTableNameComment(tableComment);
            foreignKeyDTOList.add(foreignKeyDTO);
            i++;
        }
        return foreignKeyDTOList;
    }

    /**
     * 返回索引信息
     *
     * @param indexResultSet
     * @return
     * @throws SQLException
     */
    private List<IndexInfoDTO> getIndexInfo(ResultSet indexResultSet) throws SQLException {
        List<IndexInfoDTO> indexInfoDTOList = new ArrayList<>();
        // 字段联合索引，返回的结果集是按每一个字段返回，可能存在某在是唯一索引，在联合索引中不是唯一索引
        while (indexResultSet.next()) {
            // 索引列名
            String columnName = indexResultSet.getString("COLUMN_NAME");
            // 如果为真则说明索引值不唯一，为假则说明索引值必须唯一
            String nonUnique = indexResultSet.getString("NON_UNIQUE");
            if (indexInfoDTOList.size() == 0) {
                IndexInfoDTO indexInfoDTO = new IndexInfoDTO();
                indexInfoDTO.setColumnName(columnName);
                indexInfoDTO.setNonUnique(nonUnique);
                indexInfoDTOList.add(indexInfoDTO);
            } else {
                // 存在唯一索引的列覆盖不是唯一索引的列
                boolean isExist = false;
                for (IndexInfoDTO indexInfoDTO : indexInfoDTOList) {
                    if (columnName.equals(indexInfoDTO.getColumnName())) {
                        isExist = true;
                        if ("false".equals(nonUnique)) {
                            indexInfoDTO.setNonUnique(nonUnique);
                        }
                    }
                }
                if (!isExist) {
                    IndexInfoDTO indexInfoDTO = new IndexInfoDTO();
                    indexInfoDTO.setColumnName(columnName);
                    indexInfoDTO.setNonUnique(nonUnique);
                    indexInfoDTOList.add(indexInfoDTO);
                }
            }
        }
        return indexInfoDTOList;
    }

    /**
     * 是否是索引列
     * @param indexInfoDTOList
     * @param columnName
     * @return
     */
    private String isIndexColumn(List<IndexInfoDTO> indexInfoDTOList,String columnName) {
        AtomicReference<String> isIndexColumn = new AtomicReference<>("否");
        indexInfoDTOList.stream().forEach(indexInfoDTO -> {
            if (indexInfoDTO.getColumnName().equals(columnName)) {
                isIndexColumn.set("是");
            }
        });
        return isIndexColumn.get();
    }

    /**
     * 是否唯一列
     * @param indexInfoDTOList
     * @param columnName
     * @return
     */
    private String isUniqueColumn(List<IndexInfoDTO> indexInfoDTOList,String columnName) {
        AtomicReference<String> isUniqueColumn = new AtomicReference<>("否");
        indexInfoDTOList.stream().forEach(indexInfoDTO -> {
            if (indexInfoDTO.getColumnName().equals(columnName)) {
                if ("false".equals(indexInfoDTO.getNonUnique())) {
                    isUniqueColumn.set("是");
                }
            }
        });
        return isUniqueColumn.get();
    }

    /**
     * 处理表信息
     * @param rtlFlwDbTbl
     * @param fldCltRstList
     */
    private void tableInfoHandler(RtlFlwDbTbl rtlFlwDbTbl,List<FldCltRst> fldCltRstList) {
        int timeColumn = 0;
        int timeColumnIndex = 0;
        int varcharColumn = 0;
        int numberColumn = 0;
        int num = 0;
        // 唯一键
        int uniqueNumber = 0;
        // 主键最大列数
        int primaryKeyNumber = 0;
        // 外键数量
        int foreignKeyNumber = 0;
        for (FldCltRst fldCltRst : fldCltRstList) {
            num++;
            String fldTyp = fldCltRst.getFldTyp();
            // 处理时间列
            if (this.isTimeType(fldTyp)) {
                timeColumn++;
                if (timeColumnIndex == 0) {
                    timeColumnIndex = num;
                }
            }
            // 处理字符串列
            if (this.isVarcharType(fldTyp)) {
                varcharColumn++;
            }
            // 处理数字列
            if (this.isNumberType(fldTyp)) {
                numberColumn++;
            }
            // 主键数量
            String isPky = fldCltRst.getIsPky();
            if ("是".equals(isPky)) {
                primaryKeyNumber++;
            }
            // 唯一键
            String isUnique = fldCltRst.getIsUnique();
            if ("是".equals(isUnique)) {
                uniqueNumber++;
            }
            // 外键列
            String isForKey = fldCltRst.getIsForKey();
            if ("是".equals(isForKey)) {
                foreignKeyNumber++;
            }
            fldCltRst.setFromTbl(rtlFlwDbTbl.getTblCmt());
        }
        rtlFlwDbTbl.setFldTimeNum(String.valueOf(timeColumn));
        rtlFlwDbTbl.setFrtTime(String.valueOf(timeColumnIndex));
        rtlFlwDbTbl.setFldStrNum(String.valueOf(varcharColumn));
        rtlFlwDbTbl.setFldNmlNum(String.valueOf(numberColumn));
        rtlFlwDbTbl.setFldPkNum(String.valueOf(uniqueNumber));
        rtlFlwDbTbl.setFldCpkNum(String.valueOf(primaryKeyNumber));
        rtlFlwDbTbl.setFldFkNum(String.valueOf(foreignKeyNumber));
        if (foreignKeyNumber >0) {
            rtlFlwDbTbl.setFldFpkNum("包含");
        } else {
            rtlFlwDbTbl.setFldFpkNum("不包含");
        }
    }

    /**
     * 是否是时间列
     * @param fldTyp
     * @return
     */
    private boolean isTimeType(String fldTyp) {
        String[] strings = new String[]{"DATE","TIME","YEAR","DATETIME","TIMESTAMP"};
        for (int i=0;i<strings.length;i++) {
            if (strings[i].equalsIgnoreCase(fldTyp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否字符串列
     * @param fldTyp
     * @return
     */
    private boolean isVarcharType(String fldTyp) {
        String[] strings = new String[]{"CHAR","VARCHAR","TINYBLOB","TINYTEXT","BLOB",
                "TEXT","MEDIUMBLOB","MEDIUMTEXT","LONGBLOB","LONGTEXT"};
        for (int i=0;i<strings.length;i++) {
            if (strings[i].equalsIgnoreCase(fldTyp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否数值类型
     * @param fldTyp
     * @return
     */
    private boolean isNumberType(String fldTyp) {
        String[] strings = new String[]{"TINYINT","SMALLINT","MEDIUMINT","INT","INTEGER",
                "BIGINT","FLOAT","DOUBLE","DECIMAL"};
        for (int i=0;i<strings.length;i++) {
            if (strings[i].equalsIgnoreCase(fldTyp)) {
                return true;
            }
        }
        return false;
    }

    private static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }

    /**
     * 获取表名的注释
     * @param tableName
     * @param conn
     * @return
     * @throws SQLException
     */
    private String getTableComment(String tableName,Connection conn) throws SQLException {
        String table_comment = "";
        Statement stmt = conn.createStatement();
        ResultSet createTableResultSet = stmt.executeQuery("SHOW CREATE TABLE "+tableName);
        if (createTableResultSet != null && createTableResultSet.next()) {
            String createDDL = createTableResultSet.getString(2);
            table_comment = parse(createDDL);
        }
        return table_comment;
    }

    /**
     * 获取表格行数
     * @param tableName
     * @param conn
     * @return
     */
    private Integer getTableRows(String tableName,Connection conn) throws SQLException {
        String sql = "select count(0) from "+tableName;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int rowCount = 0;
        if(rs.next()) {
            rowCount=rs.getInt(1);
        }
        return rowCount;
    }

    /**
     * 获取表格物理大小
     * @param tableName
     * @param table_schema
     * @param conn
     * @return
     */
    private String getTablePhysicsSize(String tableName,String table_schema,Connection conn) throws SQLException {
        String sql = "SELECT CONCAT(ROUND(SUM(DATA_LENGTH/1024/1024),2),'M') FROM information_schema.tables WHERE table_schema='"+ table_schema + "' AND table_name = '"+tableName+"'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        String physicsSize = "0M";
        if(rs.next()) {
            physicsSize=rs.getString(1);
        }
        return physicsSize;
    }

    /**
     * 抽取数据
     * @param tableName
     * @param num
     * @param conn
     * @return
     */
    private ResultSet getDbData(String tableName,String columnName,int num,Connection conn) throws SQLException {
        String sql = "select "+columnName +" from "+tableName +" limit 0,"+num;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }


}
