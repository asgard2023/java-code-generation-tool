package org.ccs.opendfl.mysql.dflsystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ccs.opendfl.base.BaseController;
import org.ccs.opendfl.base.MyPageInfo;
import org.ccs.opendfl.base.PageVO;
import org.ccs.opendfl.exception.ResultData;
import org.ccs.opendfl.exception.ValidateUtils;
import org.ccs.opendfl.mysql.dflsystem.biz.IDflUserBiz;
import org.ccs.opendfl.mysql.dflsystem.po.DflUserPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Version V1.0
 * @Title: DflUsercontroller
 * @Package: org.ccs.opendfl.mysql.dflsystem.controller
 * @Description: dfl_user Controller
 * @Author: Created by Generator
 * @Date: 2022-8-5 6:52:11
 * @Company: opendfl
 * @Copyright: 2022 opendfl Inc. All rights reserved.
 */
@Api(tags = "dfl_user接口")
@RestController
@RequestMapping("dflsystem/dflUser")
public class DflUserController extends BaseController {

    static Logger logger = LoggerFactory.getLogger(DflUserController.class);

    @Autowired
    private IDflUserBiz dflUserBiz;

    @RequestMapping(value = {"index"}, method = RequestMethod.GET)
    public String index() {
        return "dflsystem/dflUser";
    }

    /**
     * dfl_user列表查询
     *
     * @param request
     * @param entity
     * @param pageInfo
     * @return java.lang.Object
     * @author Generator
     * @date 2022-8-5 6:52:11
     */
    @ApiOperation(value = "dfl_user列表", notes = "dfl_user列表翻页查询")
    @RequestMapping(value = "list", method = {RequestMethod.POST, RequestMethod.GET})
    public MyPageInfo<DflUserPo> queryPage(HttpServletRequest request, DflUserPo entity, MyPageInfo<DflUserPo> pageInfo) {
        if (entity == null) {
            entity = new DflUserPo();
        }
        if (pageInfo.getPageSize() == 0) {
            pageInfo.setPageSize(getPageSize());
        }
        pageInfo = dflUserBiz.findPageBy(entity, pageInfo, this.createAllParams(request));
        return pageInfo;
    }

    @ApiOperation(value = "dfl_user列表(easyui)", notes = "dfl_user列表翻页查询，用于兼容easyui的rows方式")
    @RequestMapping(value = "/list2", method = {RequestMethod.POST, RequestMethod.GET})
    public PageVO<DflUserPo> findByPage(HttpServletRequest request, DflUserPo entity, MyPageInfo<DflUserPo> pageInfo) {
        logger.debug("-------findByPage-------");
        this.pageSortBy(pageInfo);
        pageInfo = queryPage(request, entity, pageInfo);
        return new PageVO(pageInfo);
    }

    /**
     * dfl_user 新增
     *
     * @param request
     * @param entity
     * @return ResultData
     * @author Generator
     * @date 2022-8-5 6:52:11
     */
    @ApiOperation(value = "添加dfl_user", notes = "添加一个dfl_user")
    @RequestMapping(value = "save", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData edit(DflUserPo entity, HttpServletRequest request) {
        if (entity.getId() != null && entity.getId() > 0) {
            return update(entity, request);
        }
        entity.setModifyUser(getCurrentUserId());
        entity.setCreateUser(getCurrentUserId());
        dflUserBiz.saveDflUser(entity);
        return ResultData.success();
    }

    /**
     * dfl_user 更新
     *
     * @param request
     * @param entity
     * @return ResultData
     * @author Generator
     * @date 2022-8-5 6:52:11
     */
    @ApiOperation(value = "修改dfl_user", notes = "根据传入的角色信息修改")
    @RequestMapping(value = "update", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData update(DflUserPo entity, HttpServletRequest request) {
        entity.setModifyUser(getCurrentUserId());
        int v = dflUserBiz.updateDflUser(entity);
        return ResultData.success(v);
    }

    /**
     * dfl_user 删除
     *
     * @param request
     * @param dflUser
     * @return ResultData
     * @author Generator
     * @date 2022-8-5 6:52:11
     */
    @ApiOperation(value = "删除dfl_user ", notes = "根据传入id进行删除状态修改(即软删除)")
    @RequestMapping(value = "delete", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData delete(DflUserPo dflUser, HttpServletRequest request) {
        String id = request.getParameter("id");
        ValidateUtils.notNull(id, "id不能为空");
        String remark = request.getParameter("remark");
        int v = dflUserBiz.deleteDflUser(Integer.parseInt(id), this.getCurrentUserId(), remark);
        return ResultData.success(v);
    }
}