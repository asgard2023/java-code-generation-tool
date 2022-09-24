package org.ccs.opendfl.mysql.dflsystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ccs.opendfl.base.BaseController;
import org.ccs.opendfl.base.MyPageInfo;
import org.ccs.opendfl.base.PageResult;
import org.ccs.opendfl.base.PageVO;
import org.ccs.opendfl.exception.ResultData;
import org.ccs.opendfl.exception.ValidateUtils;
import org.ccs.opendfl.mysql.dflsystem.biz.IDflResourceBiz;
import org.ccs.opendfl.mysql.dflsystem.po.DflResourcePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenjh
 * @Version V1.0
 * @Description: 菜单资源管理 Controller
 * @Date: 2022-8-6 23:03:15
 * @Company: opendfl
 * @Copyright: 2022 opendfl Inc. All rights reserved.
 */
@Api(tags = "菜单资源管理接口")
@RestController
@RequestMapping("dflsystem/dflResource")
public class DflResourceController extends BaseController {

    static Logger logger = LoggerFactory.getLogger(DflResourceController.class);

    @Autowired
    private IDflResourceBiz dflResourceBiz;

    /**
     * 菜单资源管理列表查询
     *
     * @param request  请求req
     * @param entity   菜单资源管理对象
     * @param pageInfo 翻页对象
     * @return MyPageInfo 带翻页的数据集
     * @author Generator
     * @date 2022-8-6 23:03:15
     */
    @ApiOperation(value = "菜单资源管理列表", notes = "菜单资源管理列表翻页查询")
    @RequestMapping(value = "list", method = {RequestMethod.POST, RequestMethod.GET})
    public MyPageInfo<DflResourcePo> queryPage(HttpServletRequest request, DflResourcePo entity, MyPageInfo<DflResourcePo> pageInfo) {
        if (entity == null) {
            entity = new DflResourcePo();
        }
        entity.setIfDel(0);
        if (pageInfo.getPageSize() == 0) {
            pageInfo.setPageSize(getPageSize());
        }
        pageInfo = dflResourceBiz.findPageBy(entity, pageInfo, this.createAllParams(request));
        return pageInfo;
    }

    @ApiOperation(value = "菜单资源管理列表(easyui)", notes = "菜单资源管理列表翻页查询，用于兼容easyui的rows方式")
    @RequestMapping(value = "/list2", method = {RequestMethod.POST, RequestMethod.GET})
    public PageVO<DflResourcePo> findByPage(HttpServletRequest request, DflResourcePo entity, MyPageInfo<DflResourcePo> pageInfo) {
        logger.debug("-------findByPage-------");
        this.pageSortBy(pageInfo);
        pageInfo = queryPage(request, entity, pageInfo);
        return new PageVO(pageInfo);
    }

    @ApiOperation(value = "菜单资源管理列表(layui)", notes = "菜单资源管理列表翻页查询，用于兼容easyui的rows方式")
    @RequestMapping(value = "/list3", method = {RequestMethod.POST, RequestMethod.GET})
    public PageResult<DflResourcePo> findByLayuiPage(HttpServletRequest request, DflResourcePo entity, MyPageInfo<DflResourcePo> pageInfo) {
        logger.debug("-------findByLayuiPage-------");
        this.pageSortBy(pageInfo);
        pageInfo = queryPage(request, entity, pageInfo);
        return new PageResult(pageInfo);
    }

    /**
     * 菜单资源管理 新增
     *
     * @param request 请求req
     * @param entity  菜单资源管理对象
     * @return ResultData 返回数据
     * @author Generator
     * @date 2022-8-6 23:03:15
     */
    @ApiOperation(value = "添加菜单资源管理", notes = "添加一个菜单资源管理")
    @RequestMapping(value = "save", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData edit(DflResourcePo entity, HttpServletRequest request) {
        if (entity.getId() != null && entity.getId() > 0) {
            return update(entity, request);
        }
        entity.setModifyUser(getCurrentUserId());
        entity.setCreateUser(getCurrentUserId());
        dflResourceBiz.saveDflResource(entity);
        return ResultData.success(entity.getId());
    }

    /**
     * 菜单资源管理 更新
     *
     * @param request 请求req
     * @param entity  菜单资源管理对象
     * @return ResultData 返回数据
     * @author Generator
     * @date 2022-8-6 23:03:15
     */
    @ApiOperation(value = "修改菜单资源管理", notes = "根据传入的角色信息修改")
    @RequestMapping(value = "update", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData update(DflResourcePo entity, HttpServletRequest request) {
        entity.setModifyUser(getCurrentUserId());
        int v = dflResourceBiz.updateDflResource(entity);
        return ResultData.success(entity.getId());
    }

    /**
     * 菜单资源管理 删除
     *
     * @param request 请求req
     * @param id      数据id
     * @return ResultData 返回数据
     * @author Generator
     * @date 2022-8-6 23:03:15
     */
    @ApiOperation(value = "删除菜单资源管理 ", notes = "根据传入id进行删除状态修改(即软删除)")
    @RequestMapping(value = "delete", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData delete(@RequestParam(name = "id", required = false) Integer id, HttpServletRequest request) {
        ValidateUtils.notNull(id, "id不能为空");
        String remark = request.getParameter("remark");
        int v = dflResourceBiz.deleteDflResource(id, this.getCurrentUserId(), remark);
        return ResultData.success(v);
    }

    /**
     * 前往编辑页面
     *
     * @return String
     */
    @RequestMapping("/view")
    public ResultData view(int id) {
        return ResultData.success(this.dflResourceBiz.getDataById(id));
    }

}