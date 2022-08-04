package org.ccs.generator.bean;

import lombok.Data;
import org.ccs.generator.exceptions.DataNotExistException;

import java.util.List;

/**
 * 表信息
 */
@Data
public class Table {
    //用户名
    private String author;
    //表名
    private String tableName;
    //表类型
    private String tableType;
    //实体名
    private String entityName;
    //所属项目
    private String project;
    //所属模块,例如com.sf.module.ssv
    private String module;
    //模块简称,ssv
    private String simpleModule;
    //类注释
    private String comment;
    private String english;
    //excel模板名
    private String excelTpl;
    //主键序列
    private String sequence;
    //表名前缀
    private String prefix;
    //是否存在外键关联
    private boolean hasFK;
    //表结构
    private List<Column> columns;

    private String company = "test";
    private String copyright = "test";

    /**
     * id类型，String,Integer,Long
     */
    private String idJavaType = "Integer";

    /**
     * 修改用户属性名modifyUser,updateUser
     */
    private String modifyUserField = "updateUser";
    /**
     * 修改用户属性名modifyTime,updateTime
     */
    private String modifyTimeField = "updateTime";

    public void setEqualsSearchColumns(String columnsStr) {
        if (columnsStr == null || columnsStr.trim().equals("")) {
            return;
        }
        columnsStr = columnsStr.trim();
        //只要判断fieldName是否在columnsStr中即可
        columnsStr += "," + columnsStr + ",";
        for (Column column : this.columns) {
            if (columnsStr.indexOf("," + column.getFieldName() + ",") != -1) {
                column.setEqualsSearch(true);
            }
        }
    }

    public void setLikeSearchColumns(String columnsStr) {
        if (columnsStr == null || columnsStr.trim().equals("")) {
            return;
        }
        columnsStr = columnsStr.trim();
        //只要判断fieldName是否在columnsStr中即可
        columnsStr += "," + columnsStr + ",";
        for (Column column : this.columns) {
            if (columnsStr.indexOf("," + column.getFieldName() + ",") != -1) {
                column.setLikeSearch(true);
            }
        }
    }

    /**
     * 指定字段不在grid中显示
     *
     * @param columnsStr
     */
    public void setHideGridColumns(String columnsStr) {
        if (columnsStr == null || columnsStr.trim().equals("")) {
            return;
        }
        columnsStr = columnsStr.trim();
        //只要判断fieldName是否在columnsStr中即可
        columnsStr += "," + columnsStr + ",";
        for (Column column : this.columns) {
            if (columnsStr.indexOf("," + column.getFieldName() + ",") != -1) {
                column.setShowInGrid(false);
            }
        }
    }

    /**
     * 设置属性
     *
     * @param columnsStr
     * @param type
     * @throws Exception
     */
    private void setRenderOrDictColumns(String columnsStr, String type) throws Exception {
        if (columnsStr == null || columnsStr.trim().equals("")) {
            return;
        }
        columnsStr = columnsStr.trim();
        String[] strs = columnsStr.split(",");
        String fieldName;
        String key;
        String[] subStrs;
        Column c;
        for (String str : strs) {
            subStrs = str.split(":");
            fieldName = subStrs[0];
            key = subStrs[1];
            c = getColumnByFieldName(fieldName);
            if (c == null) {
                throw new DataNotExistException("column", type + "栏位:" + fieldName + "，不存在:");
            } else {
                if (type.equals("render")) {
                    c.setHasRender(true);
                    c.setRenderKey(key);
                } else if (type.equals("dict")) {
                    c.setHasDict(true);
                    c.setDictKey(key);
                } else if (type.equals("fk")) {
                    this.setHasFK(true);
                    c.setFK(true);
                    c.setFkRelField(key);
                }
            }
        }

    }

    /**
     * 设置栏位的code转换成text的render
     *
     * @param columnsStr
     * @throws Exception
     */
    public void setRenderColumns(String columnsStr) throws Exception {
        setRenderOrDictColumns(columnsStr, "render");
    }

    /**
     * 设置栏位的下拉
     *
     * @param columnsStr
     */
    public void setDictColumns(String columnsStr) throws Exception {
        setRenderOrDictColumns(columnsStr, "dict");
    }

    /**
     * 指定字段是否为FK
     *
     * @param columnsStr
     * @throws Exception
     */
    public void setFKColumns(String columnsStr) throws Exception {
        setRenderOrDictColumns(columnsStr, "fk");
    }

    /**
     * 通过fieldName获取栏位对象
     *
     * @param fieldName
     * @return
     */
    public Column getColumnByFieldName(String fieldName) {
        for (Column column : this.columns) {
            if (column.getFieldName().equals(fieldName)) {
                return column;
            }
        }
        return null;
    }

    public String getPrefix() {
        if (prefix == null || prefix.trim().equals("")) {
            return tableName.split("_")[0].toLowerCase();
        }
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
