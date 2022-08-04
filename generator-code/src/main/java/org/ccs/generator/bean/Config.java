package org.ccs.generator.bean;

import lombok.Data;

@Data
public class Config {
    // button checkbox radio date file hidden image email number color datetime
    /**
     * 表名，exp:ts_user)
     */
    private String tableName;
    /**
     * 实体名: exp: User
     */
    private String entityName;
    /**
     * 模块名
     */
    private String modelName = "demo";
    /**
     * 包名
     */
    private String pkg = "org.ccs.demo";
    /**
     * 作者
     */
    private String author = "author";

    /**
     * 是否支持生成swagger文档
     */
    private boolean swagger;

    /**
     * 精确匹配查询，多个以","隔开
     */
    private String equalsSearchColumns;
    /**
     * 模糊查询条件，多个以","隔开
     */
    private String likeSearchColumns;
    /**
     * 定义哪些属性在jsp grid中显示或不显示，多个以","隔开
     */
    private String hideGridColumns;

    private String renderColumns;
    /**
     * 编辑时绑定下拉数据, exp: status:dict_tf_status
     */
    private String dictColumns;
    /**
     * 外键关联，查询关联的名称, exp: systemInfoId:systemInfo,intfInfoId:intfInfo
     */
    private String fkColumns;
    /**
     * 版权声明
     */
    private String copyright = "copyright";
    /**
     * 公司
     */
    private String company = "company";

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
