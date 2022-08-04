<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${module}.mapper.${entityName}Mapper">
    <resultMap id="${entityName?uncap_first}Map" type="${module}.po.${entityName}Po">
    	<#list columns as column>
    	<#if column.primary >
		<id column="${column.columnName}" property="${column.fieldName}" jdbcType="${column.jdbcType}" />
		</#if>
		</#list>
		<#list columns as column>
		<#if !column.primary >
		<result column="${column.columnName}" property="${column.fieldName}" jdbcType="${column.jdbcType}" />
		</#if>
		</#list>
    </resultMap>
	
	<sql id="field">
		<#list columns as column><#if column_index==0>${column.columnName}<#else>, 
		${column.columnName}</#if></#list>
	</sql>
	
	<sql id="findByPage_where">
		<where>
            <trim suffixOverrides="and | or">
            <#list columns as column>
				<if test="${column.fieldName} != null">${column.columnName} = #${'{'}${column.fieldName}, jdbcType = ${column.jdbcType}} and</if>
			</#list>
            <if test="keywords != null and keywords != ''">
				and (
					<#list columns as column><#if column_index==0>${column.columnName} like concat(concat('%', #${'{'}keywords}),'%')<#else>
					or ${column.columnName} like concat(concat('%', #${'{'}keywords}),'%')</#if></#list>
				)
			</if>
            </trim>
        </where>
	</sql>
    
    <select id="findByPageXML" parameterType="java.util.Map" resultMap="${entityName?uncap_first}Map">
        select
        <include refid="field"></include>
        from ${tableName}
        <include refid="findByPage_where"></include>
        <choose>
	        <when test="sidx != null">
	        	order by ${'$'}{sidx} ${'$'}{sord}, <#list columns as column><#if column.primary >${column.columnName}</#if></#list> desc
	        </when>
	        <otherwise>
	        	order by <#list columns as column><#if column.primary >${column.columnName}</#if></#list> desc
	        </otherwise>
        </choose>
        <if test="page != null and rows != null">
			limit ${'$'}{startRows}, ${'$'}{rows}      
        </if>
    </select>
    
    <select id="findCountXML" parameterType="java.util.Map" resultType="java.lang.Integer">
        select
        count(1)
        from ${tableName}
        <include refid="findByPage_where"></include>
    </select>
    
    <select id="findAllXML" parameterType="java.util.Map" resultMap="${entityName?uncap_first}Map">
        select
        <include refid="field"></include>
        from ${tableName}
        <where>
            <trim suffixOverrides="and | or">
                <#list columns as column>
                <if test="${column.fieldName} != null">${column.columnName} = #${'{'}${column.fieldName}, jdbcType = ${column.jdbcType}} and</if>
				</#list>
            </trim>
        </where>
    </select>
    
    <select id="findByIdXML" parameterType="java.lang.String" resultMap="${entityName?uncap_first}Map">
        select
        <include refid="field"></include>
        from ${tableName}
        <where>
            <#list columns as column><#if column.primary >${column.columnName}</#if></#list> = #${'{'}id}
        </where>
    </select>

    <insert id="insertXML" parameterType="${module}.po.${entityName}Po" useGeneratedKeys="true" keyProperty="<#list columns as column><#if column.primary >${column.columnName}</#if></#list>" flushCache="true">
        insert ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        	<#list columns as column>
        	<if test="${column.fieldName} != null">${column.columnName},</if>
        	</#list>
        </trim>
        values
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list columns as column>
        	<if test="${column.fieldName} != null">#${'{'}${column.fieldName}, jdbcType = ${column.jdbcType}},</if>
        	</#list>
        </trim>
    </insert>

    <update id="updateXML" parameterType="${module}.po.${entityName}Po" keyProperty="<#list columns as column><#if column.primary >${column.columnName}</#if></#list>">
        update ${tableName} 
        <set>
            <trim suffixOverrides=",">
            	<#list columns as column>
        		<if test="${column.fieldName} != null">${column.columnName} = #${'{'}${column.fieldName}},</if>
        		</#list>
            </trim>
        </set>
        <where>
            <#list columns as column><#if column.primary >${column.columnName}</#if></#list> = #${'{'}<#list columns as column><#if column.primary >${column.fieldName}</#if></#list>}
        </where>
    </update>

    <delete id="deleteXML" parameterType="java.lang.String">
        delete from ${tableName}
        <where>
            <#list columns as column><#if column.primary >${column.columnName}</#if></#list> = #${'{'}id}
        </where>
    </delete>
</mapper>