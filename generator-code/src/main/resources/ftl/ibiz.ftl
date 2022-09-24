package ${module}.biz;

import ${module}.po.${entityName}Po;

import org.ccs.opendfl.base.IBaseService;

/**
 *
 * @Version V1.0
 *  ${comment} 业务接口
 * @author: ${author}
 * @Date: ${.now}
 * @Company: ${company}
 * @Copyright: ${copyright}
*/
public interface I${entityName}Biz extends IBaseService<${entityName}Po> {
    public ${entityName}Po getDataById(${idJavaType} id);

    public ${entityName}Po getDataById(${idJavaType} id, String ignoreFields);

    /**
     * ${comment} 保存
     * @author ${author}
     * @date ${.now}
     * @param entity
     * @return Integer
    */
    Integer save${entityName}(${entityName}Po entity);

    /**
     * ${comment} 更新
     * @param entity
     * @return Integer
     * @author ${author}
     * @date ${.now}
    */
    Integer update${entityName}(${entityName}Po entity);

    /**
     * ${comment} 删除
     * @param id 主键ID
     * @param operUser 操作人
     * @param remark 备注
     * @return Integer
     * @author ${author}
     * @date ${.now}
    */
    Integer delete${entityName}(${idJavaType} id, Integer operUser, String remark);
}