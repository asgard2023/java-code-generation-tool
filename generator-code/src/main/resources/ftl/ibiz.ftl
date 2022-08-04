package ${module}.biz;

import ${module}.po.${entityName}Po;

import org.ccs.opendfl.base.IBaseService;

/**
 *
 *
 * @Version V1.0
 * @Title: I${entityName}Biz
 * @Description: ${comment} 业务接口
 * @Author: Created by ${author}
 * @Date: ${.now}
 * @Company: ${company}
 * @Copyright: ${copyright}

*/
public interface I${entityName}Biz extends IBaseService<${entityName}Po> {

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
     * @author ${author}
     * @date ${.now}
     * @param entity
     * @return Integer
    */
    Integer update${entityName}(${entityName}Po entity);

    /**
     * ${comment} 删除
     * @author ${author}
     * @date ${.now}
     * @param id 主键ID
     * @param operUser 操作人
     * @param remark 备注
     * @return Integer
    */
    <#if idJavaType=='Integer'>
    Integer delete${entityName}(Integer id, Integer operUser, String remark);
    <#elseif idJavaType=='Long'>
    Integer delete${entityName}(Long id, Integer operUser, String remark);
    <#else>
    Integer delete${entityName}(String id, Integer operUser, String remark);
    </#if>
}