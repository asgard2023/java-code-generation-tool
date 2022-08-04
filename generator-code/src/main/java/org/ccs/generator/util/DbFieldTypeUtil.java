package org.ccs.generator.util;

public class DbFieldTypeUtil {
    private DbFieldTypeUtil(){

    }


    public static final String ROOT = "/ftl";

    /**
     * 首字母小写
     *
     * @param value
     * @return
     */
    public static String toLowerCaseFirstChar(String value) {
        if (value == null || value.trim().equals("")) {
            return value;
        } else if (value.length() == 1) {
            return value.toLowerCase();
        }
        return value.substring(0, 1).toLowerCase() + value.substring(1);

    }

    /**
     * 生成驼峰命名的属性名
     *
     * @param columnName
     * @return
     */
    public static String getFieldName(String columnName) {
        String[] array = columnName.split("_+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                result.append(array[i].toLowerCase());
            } else {
                result.append(array[i].substring(0, 1).toUpperCase());
                result.append(array[i].substring(1).toLowerCase());
            }

        }
        return result.toString();
    }

    /**
     * 栏位转成英文，如user_name:User Name
     *
     * @param columnName
     * @return
     */
    public static String getDefaultEnglishName(String columnName) {
        String[] array = columnName.split("_+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            result.append(array[i].substring(0, 1).toUpperCase());
            result.append(array[i].substring(1).toLowerCase());
            if (i != array.length - 1)
                result.append(" ");

        }
        return result.toString();
    }

    /**
     * 获取数据库类型
     *
     * @param typeName   类型类名,如 VARCHAR2,NUMBER...
     * @param columnSize 类型大小
     * @param precision  精度
     * @return
     */
    public static String getDbType(String typeName, int columnSize, int precision) {
        if ("DATE".equals(typeName) || "TIMESTAMP".equals(typeName) || "DATETIME".equals(typeName)
                || "DOUBLE".equals(typeName) || "FLOAT".equals(typeName))
            return typeName;
        else if ("NUMBER".equals(typeName)) {
            if (columnSize == 0)
                return typeName;
            return typeName + "(" + columnSize + "," + precision + ")";
        } else if ("INT".equals(typeName)) {
            return typeName + "(" + columnSize + ")";
        } else {
            return typeName + "(" + columnSize + ")";
        }
    }

    /**
     * 获取属性类型
     *
     * @param typeName
     * @param columnSize
     * @param precision
     * @return
     */
    public static String getJavaType(String typeName, int columnSize, int precision) {
        if (typeName.startsWith("VARCHAR") || typeName.startsWith("CHAR")) {
            return "String";
        } else if (typeName.startsWith("DECIMAL") || typeName.startsWith("NUMBER") || typeName.startsWith("TINYINT") || typeName.startsWith("INT") || typeName.startsWith("BIGINT")) {
            if (precision != 0)
                return "Double";
            else if (columnSize < 3)
                //Byte
                return "Integer";
            else if (columnSize < 5)
                //Short
                return "Integer";
            else if (columnSize < 12)
                return "Integer";
            else
                return "Long";
        } else if ("DOUBLE".equals(typeName)) {
            return "Double";
        } else if ("FLOAT".equals(typeName)) {
            return "Float";
        } else if ("DATE".equals(typeName) || "TIMESTAMP".equals(typeName) || "DATETIME".equals(typeName)) {
            return "Date";
        } else if ("DECIMAL".equals(typeName)) {
            return "Long";
        } else if ("TEXT".equals(typeName)) {
            return "String";
        } else {
            return typeName;
        }
    }

    /**
     * sort java type to full java type
     *
     * @param javaType
     * @return
     */
    public static String getFullJavaType(String javaType) {
        if ("Date".equals(javaType))
            return "java.util.Date";
        else
            return "java.lang." + javaType;
    }



}
