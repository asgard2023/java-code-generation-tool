package org.ccs.opendfl.mysql.dflsystem.biz;

import org.ccs.opendfl.base.IBaseService;
import org.ccs.opendfl.mysql.dflsystem.po.DflRolePo;

import java.util.List;
import java.util.Map;


/**
 * @Version V1.0
 * @Description: 角色表 业务接口
 * @Author: Created by chenjh
 * @Date: 2022-5-3 20:25:42
 */
public interface IDflRoleBiz extends IBaseService<DflRolePo> {
    public Map<Integer, DflRolePo> getRoleMapByIds(List<Integer> roleIds);

    public DflRolePo getDataById(Integer id);

    public DflRolePo getDataById(Integer id, String ignoreFields);

    /**
     * 角色表 保存
     *
     * @param entity
     * @return Integer
     * @author chenjh
     * @date 2022-5-3 20:25:42
     */
    Integer saveDflRole(DflRolePo entity);

    /**
     * 角色表 更新
     *
     * @param entity
     * @return Integer
     * @author chenjh
     * @date 2022-5-3 20:25:42
     */
    Integer updateDflRole(DflRolePo entity);

    /**
     * 角色表 删除
     *
     * @param id       主键ID
     * @param operUser 操作人
     * @param remark   备注
     * @return Integer
     * @author chenjh
     * @date 2022-5-3 20:25:42
     */
    Integer deleteDflRole(Integer id, Integer operUser, String remark);
}