package org.ccs.opendfl.mysql.dflsystem.biz;

import org.ccs.opendfl.base.IBaseService;
import org.ccs.opendfl.mysql.dflsystem.po.DflUserPo;

/**
 * @Version V1.0
 * @Description: dfl_user 业务接口
 * @author chenjh
 * @Date: 2022-8-6 6:06:51
 * @Company: opendfl
 * @Copyright: 2022 opendfl Inc. All rights reserved.
 */
public interface IDflUserBiz extends IBaseService<DflUserPo> {
    public DflUserPo getDataById(Integer id);

    public DflUserPo getDataById(Integer id, String ignoreFields);

    /**
     * dfl_user 保存
     *
     * @param entity
     * @return Integer
     * @author Generator
     * @date 2022-8-6 6:06:51
     */
    Integer saveDflUser(DflUserPo entity);

    /**
     * dfl_user 更新
     *
     * @param entity
     * @return Integer
     * @author Generator
     * @date 2022-8-6 6:06:51
     */
    Integer updateDflUser(DflUserPo entity);

    /**
     * dfl_user 删除
     *
     * @param id       主键ID
     * @param operUser 操作人
     * @param remark   备注
     * @return Integer
     * @author Generator
     * @date 2022-8-6 6:06:51
     */
    Integer deleteDflUser(Integer id, Integer operUser, String remark);
}