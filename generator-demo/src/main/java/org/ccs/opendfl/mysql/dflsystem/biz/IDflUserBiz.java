package org.ccs.opendfl.mysql.dflsystem.biz;

import org.ccs.opendfl.mysql.dflsystem.po.DflUserPo;

import org.ccs.opendfl.base.IBaseService;

/**
 *
 *
 * @Version V1.0
 * @Title: IDflUserBiz
 * @Description:  业务接口
 * @Author: Created by Generator
 * @Date: 2022-8-1 7:02:19
 * @Company: opendfl
 * @Copyright: 2022 opendfl Inc. All rights reserved.

*/
public interface IDflUserBiz extends IBaseService<DflUserPo> {

    /**
     *  保存
     * @author Generator
     * @date 2022-8-1 7:02:19
     * @param entity
     * @return Integer
    */
    Integer saveDflUser(DflUserPo entity);

    /**
     *  更新
     * @author Generator
     * @date 2022-8-1 7:02:19
     * @param entity
     * @return Integer
    */
    Integer updateDflUser(DflUserPo entity);

    /**
     *  删除
     * @author Generator
     * @date 2022-8-1 7:02:19
     * @param id 主键ID
     * @param operUser 操作人
     * @param remark 备注
     * @return Integer
    */
    Integer deleteDflUser(Integer id, Integer operUser, String remark);
}