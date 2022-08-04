package ${module}.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;

import org.ccs.opendfl.base.BaseController;
import org.ccs.opendfl.base.MyPageInfo;
import org.ccs.opendfl.base.PageVO;
import org.ccs.opendfl.exception.ResultData;
import org.ccs.opendfl.exception.ValidateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ${module}.biz.I${entityName}Biz;
import ${module}.po.${entityName}Po;

/**
 * @Version V1.0
 * @Title: ${entityName}controller
 * @Package: ${module}.controller
 * @Description: ${comment} Controller
 * @Author: Created by ${author}
 * @Date: ${.now}
 * @Company: ${company}
 * @Copyright: ${copyright}
*/
@Api(tags = "${comment}接口")
@RestController
@RequestMapping("${simpleModule}/${entityName?uncap_first}")
public class ${entityName}Controller extends BaseController {

	static Logger logger = LoggerFactory.getLogger(${entityName}Controller.class);
	
	@Autowired
	private I${entityName}Biz ${entityName?uncap_first}Biz;

	@RequestMapping(value = {"index"}, method = RequestMethod.GET)
	public String index() {
		return "${simpleModule}/${entityName?uncap_first}";
	}

	/**
	 * ${comment}列表查询
	 * @author ${author}
	 * @date ${.now}
	 * @param request
	 * @param entity
	 * @param pageInfo
	 * @return java.lang.Object
	*/
	@ApiOperation(value = "${comment}列表", notes = "${comment}列表翻页查询")
	@RequestMapping(value = "list", method = {RequestMethod.POST, RequestMethod.GET})
	public MyPageInfo<${entityName}Po> queryPage(HttpServletRequest request, ${entityName}Po entity, MyPageInfo<${entityName}Po> pageInfo){
		if(entity == null) {
			entity = new ${entityName}Po();
		}
		if (pageInfo.getPageSize() == 0) {
			pageInfo.setPageSize(getPageSize());
		}
		pageInfo = ${entityName?uncap_first}Biz.findPageBy(entity, pageInfo, this.createAllParams(request));
		return pageInfo;
	}

	@ApiOperation(value = "${comment}列表(easyui)", notes = "${comment}列表翻页查询，用于兼容easyui的rows方式")
	@RequestMapping(value = "/list2", method = {RequestMethod.POST, RequestMethod.GET})
	public PageVO<${entityName}Po> findByPage(HttpServletRequest request, ${entityName}Po entity, MyPageInfo<${entityName}Po> pageInfo) {
		logger.debug("-------findByPage-------");
		this.pageSortBy(pageInfo);
		pageInfo = queryPage(request, entity, pageInfo);
		return new PageVO(pageInfo);
	}

	/**
	 * ${comment} 新增
	 * @author ${author}
	 * @date ${.now}
	 * @param request
	 * @param entity
	 * @return ResultData
	*/
	@ApiOperation(value = "添加${comment}", notes = "添加一个${comment}")
	@RequestMapping(value = "save", method = {RequestMethod.POST, RequestMethod.GET})
	public ResultData edit(${entityName}Po entity, HttpServletRequest request){
		if (entity.getId() != null && entity.getId() > 0) {
			return update(entity, request);
		}
		entity.set${modifyUserField?cap_first}(getCurrentUserId());
		entity.setCreateUser(getCurrentUserId());
		${entityName?uncap_first}Biz.save${entityName}(entity);
		return ResultData.success();
	}

	/**
	 * ${comment} 更新
	 * @author ${author}
	 * @date ${.now}
	 * @param request
	 * @param entity
	 * @return ResultData
	*/
	@ApiOperation(value = "修改${comment}", notes = "根据传入的角色信息修改")
	@RequestMapping(value = "update", method = {RequestMethod.POST, RequestMethod.GET})
	public ResultData update(${entityName}Po entity, HttpServletRequest request){
		entity.set${modifyUserField?cap_first}(getCurrentUserId());
		int v=${entityName?uncap_first}Biz.update${entityName}(entity);
		return ResultData.success(v);
	}

	/**
	 * ${comment} 删除
	 * @author ${author}
	 * @date ${.now}
	 * @param request
	 * @param ${entityName?uncap_first}
	 * @return ResultData
	*/
	@ApiOperation(value = "删除${comment} ", notes = "根据传入id进行删除状态修改(即软删除)")
	@RequestMapping(value = "delete", method = {RequestMethod.POST, RequestMethod.GET})
	public ResultData delete(${entityName}Po ${entityName?uncap_first}, HttpServletRequest request){
		String id = request.getParameter("id");
		ValidateUtils.notNull(id,"id不能为空");
		String remark = request.getParameter("remark");
			<#if idJavaType=='Integer'>
		int v=${entityName?uncap_first}Biz.delete${entityName}(Integer.parseInt(id), this.getCurrentUserId(), remark);
			<#elseif idJavaType=='Long'>
		int v=${entityName?uncap_first}Biz.delete${entityName}(Long.parseLong(id), this.getCurrentUserId(), remark);
			<#else>
		int v=${entityName?uncap_first}Biz.delete${entityName}(id, this.getCurrentUserId(), remark);
			</#if>
		return ResultData.success(v);
	}
}