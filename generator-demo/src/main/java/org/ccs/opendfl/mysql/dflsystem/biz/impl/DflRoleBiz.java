package org.ccs.opendfl.mysql.dflsystem.biz.impl;

import com.github.pagehelper.page.PageMethod;
import org.apache.commons.lang3.StringUtils;
import org.ccs.opendfl.base.BaseService;
import org.ccs.opendfl.base.BeanUtils;
import org.ccs.opendfl.base.MyPageInfo;
import org.ccs.opendfl.mysql.dflsystem.biz.IDflRoleBiz;
import org.ccs.opendfl.mysql.dflsystem.mapper.DflRoleMapper;
import org.ccs.opendfl.mysql.dflsystem.po.DflRolePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * @Version V1.0
 * @Description: 角色表 业务实现
 * @Author: Created by chenjh
 * @Date: 2022-5-3 20:25:42
 */
@Service(value = "dflRoleBiz")
public class DflRoleBiz extends BaseService<DflRolePo> implements IDflRoleBiz {
    @Autowired
    private DflRoleMapper mapper;

    static Logger logger = LoggerFactory.getLogger(DflRoleBiz.class);

    @Override
    public Mapper<DflRolePo> getMapper() {
        return mapper;
    }


    public DflRolePo getDataById(Integer id) {
        return getDataById(id, null);
    }

    /**
     * 按ID查数据
     *
     * @param id           数据id
     * @param ignoreFields 支持忽略属性，例如：ignoreFields=ifDel,createTime,createUser将不返回这些属性
     * @return 数据
     */
    public DflRolePo getDataById(Integer id, String ignoreFields) {
        if (id == null) {
            return null;
        }
        Example example = new Example(DflRolePo.class);
        if (StringUtils.isNotBlank(ignoreFields)) {
            String props = BeanUtils.getAllProperties(DflRolePo.class, ignoreFields);
            example.selectProperties(props.split(","));
        }
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        return this.mapper.selectOneByExample(example);
    }

    @Override
    public Example createConditions(DflRolePo entity, Map<String, Object> otherParams) {
        Example example = new Example(entity.getClass());
        Example.Criteria criteria = example.createCriteria();
        searchCondition(entity, otherParams, criteria);
        addFilters(criteria, otherParams);
        return example;
    }

    private void searchCondition(DflRolePo entity, Map<String, Object> otherParams, Example.Criteria criteria) {
        String startTime = (String) otherParams.get("startTime");
        if (StringUtil.isNotEmpty(startTime)) {
            criteria.andGreaterThanOrEqualTo("createTime", startTime);
        }
        String endTime = (String) otherParams.get("endTime");
        if (StringUtil.isNotEmpty(endTime)) {
            criteria.andLessThanOrEqualTo("createTime", endTime);
        }

        if (entity.getIfDel() != null) {
            criteria.andEqualTo("ifDel", entity.getIfDel());
        }
        this.addEqualByKey(criteria, "id", otherParams);
        this.addEqualByKey(criteria, "status", otherParams);
    }

    @Override
    public MyPageInfo<DflRolePo> findPageBy(DflRolePo entity, MyPageInfo<DflRolePo> pageInfo, Map<String, Object> otherParams) {
        if (entity == null) {
            entity = new DflRolePo();
        }
        Example example = createConditions(entity, otherParams);
        if (StringUtil.isNotEmpty(pageInfo.getOrderBy()) && StringUtil.isNotEmpty(pageInfo.getOrder())) {
            example.setOrderByClause(StringUtil.camelhumpToUnderline(pageInfo.getOrderBy()) + " " + pageInfo.getOrder());
        }
        PageMethod.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<DflRolePo> list = this.getMapper().selectByExample(example);
        return new MyPageInfo<>(list);
    }

    @Override
    public Map<Integer, DflRolePo> getRoleMapByIds(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyMap();
        }
        Example example = new Example(DflRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", roleIds);
        List<DflRolePo> roleList = this.mapper.selectByExample(example);
        Map<Integer, DflRolePo> rolePoMap = new HashMap<>();
        for (DflRolePo rolePo : roleList) {
            rolePoMap.put(rolePo.getId(), rolePo);
        }
        return rolePoMap;
    }

    @Override
    public Integer saveDflRole(DflRolePo entity) {
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(new Date());
        }
        entity.setModifyTime(new Date());
        if (entity.getIfDel() == null) {
            entity.setIfDel(0);
        }
        int v = this.mapper.insertUseGeneratedKeys(entity);
        return v;
    }

    @Override
    public Integer updateDflRole(DflRolePo entity) {
        entity.setModifyTime(new Date());
        int v = this.updateByPrimaryKeySelective(entity);
        return v;
    }

    @Override
    public Integer deleteDflRole(Integer id, Integer operUser, String remark) {
        DflRolePo po = new DflRolePo();
        po.setId(id);
        po.setIfDel(1); // 0未删除,1已删除
        po.setModifyUser(operUser);
        po.setRemark(remark);
        po.setModifyTime(new Date());
        int v = this.updateByPrimaryKeySelective(po);
        return v;
    }
}