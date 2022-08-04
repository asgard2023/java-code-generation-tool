package org.ccs.opendfl.mysql.dflsystem.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import org.ccs.opendfl.base.BaseService;
import org.ccs.opendfl.base.ISelfInject;
import org.ccs.opendfl.base.MyPageInfo;

import com.github.pagehelper.PageHelper;
import org.ccs.opendfl.mysql.dflsystem.biz.IDflUserBiz;
import org.ccs.opendfl.mysql.dflsystem.mapper.DflUserMapper;
import org.ccs.opendfl.mysql.dflsystem.po.DflUserPo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;

/**
 * @Version V1.0
 * @Title: DflUserBiz
 * @Description:  业务实现
 * @Author: Created by Generator
 * @Date: 2022-8-1 22:41:43
 * @Company: opendfl
 * @Copyright: 2022 opendfl Inc. All rights reserved.
*/
@Service(value = "dflUserBiz")
public class DflUserBiz extends BaseService<DflUserPo> implements IDflUserBiz {
	@Resource
	private DflUserMapper mapper;
	
	static Logger logger = LoggerFactory.getLogger(DflUserBiz.class);

	@Override
	public Mapper<DflUserPo> getMapper() {
		return mapper;
	}

	@Override
	public Example createConditions(DflUserPo entity, Map<String, Object> otherParams) {
		Example example = new Example(entity.getClass());
		Example.Criteria criteria=example.createCriteria();
		searchCondition(entity, otherParams, criteria);
		addFilters(criteria, otherParams);
		Object keywords = otherParams.get("keywords");
		if(keywords != null && StringUtil.isNotEmpty(keywords.toString())) {
			//Example.Criteria criteria=example.createCriteria();
			//criteria.andCondition("(user_id='"+keywords+"' or telphone='"+keywords+"' or username like '%"+keywords+"%')");
		} else {
			//Example.Criteria criteria = example.createCriteria();
		}
		return example;
	}

	private void searchCondition(DflUserPo entity, Map<String, Object> otherParams, Example.Criteria criteria){
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
		this.addEqualByKey(criteria, "username", otherParams);
		this.addEqualByKey(criteria, "telephone", otherParams);
		this.addEqualByKey(criteria, "email", otherParams);
	}

	@Override
	public MyPageInfo<DflUserPo> findPageBy(DflUserPo entity, MyPageInfo<DflUserPo> pageInfo, Map<String, Object> otherParams) {
		if(entity == null) {
			entity = new DflUserPo();
		}
		Example example = createConditions(entity, otherParams);
		if(StringUtil.isNotEmpty(pageInfo.getOrderBy()) && StringUtil.isNotEmpty(pageInfo.getOrder())) {
			example.setOrderByClause(StringUtil.camelhumpToUnderline(pageInfo.getOrderBy()) + " " + pageInfo.getOrder());
		}
		PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
		List<DflUserPo> list = this.getMapper().selectByExample(example);
		return new MyPageInfo<>(list);
	}

	@Override
	public Integer saveDflUser(DflUserPo entity){
		if (entity.getCreateTime() == null) {
			entity.setCreateTime(new Date());
		}
		entity.setModifyTime(new Date());
		if (entity.getIfDel() == null) {
			entity.setIfDel(0);
		}
		return this.mapper.insert(entity);
	}

	@Override
	public Integer updateDflUser(DflUserPo entity){
		entity.setModifyTime(new Date());
		if (entity.getIfDel() == null) {
			entity.setIfDel(0);
		}
		return this.updateByPrimaryKeySelective(entity);

	}

	@Override
	public	Integer deleteDflUser(Integer id, Integer operUser, String remark){
		DflUserPo po = new DflUserPo();
		po.setId(id);
		po.setIfDel(1); // 0未删除,1已删除
		po.setModifyUser(operUser);
		po.setRemark(remark);
		po.setModifyTime(new Date());
		return this.updateByPrimaryKeySelective(po);
	}
}