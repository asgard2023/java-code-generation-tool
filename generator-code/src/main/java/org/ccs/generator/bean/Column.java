package org.ccs.generator.bean;

import lombok.Data;

/**
 * 列信息
 *
 * @author chenjh
 */
@Data
public class Column {
    //列名
    private String columnName;
    //属性名
    private String fieldName;
    //数据库类型
    private String dbType;
    //字段长度,hibernate配置需要
    private Integer columnSize;
    //小数位,hibernate配置需要
    private String precision;
    //属性类型,例如Long
    private String javaType;
    //属性类全名,例如,java.lang.Long
    private String fullJavaType;
    //注释
    private String comment;
    //英文名
    private String english;
    //是否主键
    private boolean primary;
    //是否表单查询
    private boolean equalsSearch;
    //是否表单查询
    private boolean likeSearch;

    private boolean nullable;
    //是否在grid中显示
    private boolean showInGrid = true;
    //是否有rander
    private boolean hasRender = false;
    //rander对应的关键字
    private String renderKey = "";
    //是否有dict
    private boolean hasDict = false;
    //dict对应的关键字
    private String dictKey = "";
    //fk外键标识
    private boolean isFK;
    //外键需要关联的数据
    private String fkRelField;

    public String getJdbcType() {
        if ("String".equals(this.getJavaType()) || "Character".equals(this.getJavaType()))
            return "VARCHAR";
        else if ("Date".equals(this.getJavaType())) {
            if ("DATE".equalsIgnoreCase(this.getDbType()))
                return "DATE";
            else
                return "TIMESTAMP";
        } else if ("Boolean".equals(this.getJavaType())) {
            return "BOOLEAN";
        } else if ("Long".equals(this.getJavaType())) {
            return "DECIMAL";
        } else if ("Integer".equals(this.getJavaType())) {
            return "INTEGER";
        } else
            return "NUMERIC";
    }

}
