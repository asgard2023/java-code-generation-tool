package org.ccs.generator.util;

import org.ccs.generator.bean.Table;

public interface DBHelper {
    public String getPrimaryKey(String tableName);

    public Table getTable(String tableName);
}
