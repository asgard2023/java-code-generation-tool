package org.ccs.generator.util;

import org.ccs.generator.bean.Column;
import org.ccs.generator.bean.Table;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlHelper implements DBHelper {
    DataSource dataSource = null;
    List<Table> tables = null;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 查找所有的表
     *
     * @return
     */
    private List<Table> getAllTables() {
        Connection conn = dataSource.getConnection();
        List<Table> result = new ArrayList<>();
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, dataSource.getUsername().toUpperCase(), "%", new String[]{"TABLE", "VIEW"});
            String commentStr = "";
            while (rs.next()) {
                String tableName = rs.getString(3);
                // oracle中的垃圾表都以BIN$开头
                if (tableName.startsWith("BIN$"))
                    continue;// 跳过垃圾表
                Table t = new Table();
                t.setTableName(rs.getString(3));
                t.setTableType(rs.getString(4));
                commentStr = rs.getString("REMARKS");
                if (commentStr != null) {
                    commentStr = commentStr.replaceAll("\r|\n", "");
                }
                t.setComment(commentStr);
                if (t.getComment() == null || "".equals(t.getComment())) {
                    t.setComment(t.getTableName());
                }
                t.setEnglish(t.getEntityName());
                result.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConn(conn);
        }
        return result;
    }

    /**
     * 获取指定表的所有字段信息
     *
     * @param tableName
     * @return
     */
    private List<Column> getAllColumns(String tableName) {
        Connection conn = dataSource.getConnection();
        List<Column> result = new ArrayList<>();
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getColumns(null, dataSource.getUsername().toUpperCase(), tableName, "%");
            while (rs.next()) {
                Column c = new Column();
                c.setColumnName(rs.getString(4));
                c.setFieldName(DbFieldTypeUtil.getFieldName(c.getColumnName()));
                String typeName = rs.getString(6);
                int columnSize = rs.getInt(7);
                int precision = rs.getInt(9);
                c.setNullable("YES".equalsIgnoreCase(rs.getString(18)));
                c.setDbType(DbFieldTypeUtil.getDbType(typeName, columnSize, precision));
                c.setJavaType(DbFieldTypeUtil.getJavaType(typeName, columnSize, precision));
                if (columnSize == 0 && ("NUMBER".equals(typeName) || "INT".equals(typeName)))
                    c.setColumnSize(10);// 默认给10
                else
                    c.setColumnSize(columnSize);
                c.setPrecision(precision + "");
                c.setEnglish(DbFieldTypeUtil.getDefaultEnglishName(c.getColumnName()));
                c.setComment(rs.getString("REMARKS"));
                if (StringUtils.isEmpty(c.getComment())) {
                    c.setComment(c.getColumnName());
                }
                // 查找主键
                String primaryName = getPrimaryKey(tableName);
                c.setPrimary(false);
                if (c.getColumnName().equals(primaryName)) {
                    c.setPrimary(true);
                }
                if (!result.stream().anyMatch(t -> t.getColumnName().equals(c.getColumnName()))) {
                    result.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConn(conn);
        }
        return result;
    }

    // 获取表主键
    public String getPrimaryKey(String tableName) {
        Connection conn = dataSource.getConnection();
        String primary = null;
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getPrimaryKeys(null, dataSource.getUsername().toUpperCase(), tableName);
            while (rs.next()) {
                primary = rs.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConn(conn);
        }
        return primary;
    }

    /**
     * 按表名返回表结构
     *
     * @tableName
     */
    public Table getTable(String tableName) {
        if (tables == null) {
            tables = getAllTables();
        }
        for (Table t : tables) {
            if (t.getTableName().equals(tableName)) {
                t.setColumns(getAllColumns(tableName));
                for(Column c : t.getColumns()){
                    if(c.isPrimary()){
                        t.setIdJavaType(c.getJavaType());
                    }
                    if(c.getFieldName().equals("updateUser")||c.getFieldName().equals("modifyUser")){
                        t.setModifyUserField(c.getFieldName());
                    }
                    if(c.getFieldName().equals("updateTime")||c.getFieldName().equals("modifyTime")||c.getFieldName().equals("modifyDate")){
                        t.setModifyTimeField(c.getFieldName());
                    }
                }
                return t;
            }
        }
        return null;
    }

}
