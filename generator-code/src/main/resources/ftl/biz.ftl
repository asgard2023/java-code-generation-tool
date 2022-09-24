package ${module}.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import org.ccs.opendfl.base.BaseService;
import org.ccs.opendfl.base.BeanUtils;
import org.ccs.opendfl.base.ISelfInject;
import org.ccs.opendfl.base.MyPageInfo;

import com.github.pagehelper.PageHelper;
import ${module}.biz.I${entityName}Biz;
import ${module}.mapper.${entityName}Mapper;
import ${module}.po.${entityName}Po;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;

/**
 * @Version V1.0
 * ${comment} 业务实现
 * @author: ${author}
 * @Date: ${.now}
 * @Company: ${company}
 * @Copyright: ${copyright}
*/
@Service(value = "${entityName?uncap_first}Biz")
public class ${entityName}Biz extends BaseService<${entityName}Po> implements I${entityName}Biz {
	@Resource
	private ${entityName}Mapper mapper;
	
	static Logger logger = LoggerFactory.getLogger(${entityName}Biz.class);

	@Override
	public Mapper<${entityName}Po> getMapper() {
		return mapper;
	}

	public ${entityName}Po getDataById(${idJavaType} id) {
		return getDataById(id, null);
	}
	/**
		* 按ID查数据
		*
		* @param id           数据id
		* @param ignoreFields 支持忽略属性，例如：ignoreFields=ifDel,createTime,createUser将不返回这些属性
		* @return 数据
	*/
	public ${entityName}Po getDataById(${idJavaType} id, String ignoreFields) {
		if (id == null || id == 0) {
			return null;
		}
		Example example = new Example(${entityName}Po.class);
		if(StringUtils.isNotBlank(ignoreFields)){
			String props = BeanUtils.getAllProperties(${entityName}Po.class, ignoreFields);
			example.selectProperties(props.split(","));
		}
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", id);
		return this.mapper.selectOneByExample(example);
	}

	@Override
	public Example createConditions(${entityName}Po entity, Map<String, Object> otherParams) {
		Example example = new Example(entity.getClass());
		Example.Criteria criteria=example.createCriteria();
		searchCondition(entity, otherParams, criteria);
		addFilters(criteria, otherParams);
		return example;
	}

	private void searchCondition(${entityName}Po entity, Map<String, Object> otherParams, Example.Criteria criteria){
		String startTime = (String)otherParams.get("startTime");
		if (StringUtil.isNotEmpty(startTime)) {
			criteria.andGreaterThanOrEqualTo("createTime", startTime);
		}
		String endTime = (String)otherParams.get("endTime");
		if (StringUtil.isNotEmpty(endTime)) {
			criteria.andLessThanOrEqualTo("createTime", endTime);
		}

		if (entity.getIfDel() != null) {
			criteria.andEqualTo("ifDel", entity.getIfDel());
		}
		this.addEqualByKey(criteria, "id", otherParams);
		<#list columns as column>
		<#if column.equalsSearch && !column.primary >
		this.addEqualByKey(criteria, "${column.fieldName}", otherParams);
		</#if>
		</#list>
	}

	@Override
	public MyPageInfo<${entityName}Po> findPageBy(${entityName}Po entity, MyPageInfo<${entityName}Po> pageInfo, Map<String, Object> otherParams) {
		if(entity == null) {
			entity = new ${entityName}Po();
		}
		Example example = createConditions(entity, otherParams);
		if(StringUtil.isNotEmpty(pageInfo.getOrderBy()) && StringUtil.isNotEmpty(pageInfo.getOrder())) {
			example.setOrderByClause(StringUtil.camelhumpToUnderline(pageInfo.getOrderBy()) + " " + pageInfo.getOrder());
		}
		PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
		List<${entityName}Po> list = this.getMapper().selectByExample(example);
		return new MyPageInfo<>(list);
	}

	@Override
	public Integer save${entityName}(${entityName}Po entity){
		if (entity.getCreateTime() == null) {
			entity.setCreateTime(new Date());
		}
		entity.set${modifyTimeField?cap_first}(new Date());
		if (entity.getIfDel() == null) {
			entity.setIfDel(0);
		}
		return this.mapper.insert(entity);
	}

	@Override
	public Integer update${entityName}(${entityName}Po entity){
		entity.set${modifyTimeField?cap_first}(new Date());
		if (entity.getIfDel() == null) {
			entity.setIfDel(0);
		}
		return this.updateByPrimaryKeySelective(entity);

	}

	@Override
	public	Integer delete${entityName}(${idJavaType} id, Integer operUser, String remark){
		${entityName}Po po = new ${entityName}Po();
		po.setId(id);
		po.setIfDel(1); // 0未删除,1已删除
		po.set${modifyUserField?cap_first}(operUser);
		po.setRemark(remark);
		po.set${modifyTimeField?cap_first}(new Date());
		return this.updateByPrimaryKeySelective(po);
	}
}