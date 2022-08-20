package ${module}.po;

import java.io.Serializable;
<#list columns as column>
<#if column.javaType=="Date">
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
<#break>
</#if>
</#list>

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

<#list columns as column>
<#if column.javaType=="Date">
import com.fasterxml.jackson.annotation.JsonFormat;
<#break>
</#if>
</#list>
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 *
 * @Version V1.0
 * @Title: ${entityName}Po
 * @Description: ${comment} 实体
 * @Author: Created by ${author}
 * @Date: ${.now}
 * @Company: ${company}
 * @Copyright: ${copyright}

*/
@Data
@Table(name = "${tableName}")
@JsonInclude(Include.ALWAYS)
public class ${entityName}Po implements Serializable {

<#list columns as column>
	/**
     * ${column.comment}
     */
    <#if column.primary==true>
    @Id
    </#if>
    @Column(name = "${column.columnName}")
    <#if column.javaType=="Date">
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    <#elseif column.javaType=="String">
        <#if !column.nullable>
            @NotBlank(message ="${column.fieldName}不能为空")
        </#if>
        @Length(message = "${column.fieldName}超出最大长度${column.columnSize}限制", max = ${column.columnSize})
    </#if>
    private ${column.javaType} ${column.fieldName};
	<#if column.FK>
	/**
     * ${column.fieldName}外键关联值
     */
    @Transient
    private String ${column.fkRelField}Name;
    </#if>
</#list>
}