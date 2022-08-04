$(function() {
	var beforeDay=7;
	initStartEndTime(beforeDay);
	pageInit();
});
function doSearch() {
	var queryObj = formToJson('searchForm');
    $("#table_list_1").jqGrid("setGridParam", { postData:  queryObj, page: 1}).trigger("reloadGrid");
}
var editOption =
	{
		url: "/dflsystem/dflUser/update",
		recreateForm: true,
		beforeSubmit: function( postdata, form , oper) {
			return [true,''];
		},
		closeAfterEdit: true,
		errorTextFormat: function (data) {
			return 'Error: ' + data.responseText
		},
		afterSubmit: function(response, postdata)  {
			var result = eval('(' + response.responseText + ')');
			if(result.success==true) {
				swal({title:"消息提示",text:"修改成功"});
				return [true,''];
			} else {
				return [false, '修改失败，' + result.errorMsg];
			}
		}
	};
function pageInit() {
	var queryObj = formToJson('searchForm');
	$("#table_list_1").jqGrid({
		url:  '/dflsystem/dflUser/list',
        mtype: 'POST',
		datatype: "json",
		styleUI: 'Bootstrap',
		postData: queryObj,
		colNames: ['id', '昵称', '用户名', '电话', '邮箱', '密码', '是否删除', '状态:是否有效0无效，1有效', 'remark', 'create_time', 'modify_time', 'create_user', 'modify_user', 'register_ip', 'sys_type'],
        colModel: [{name: 'id', index: 'id', editable:true, hidden: true,key:true},
            {name: 'nickname', index: 'nickname',editable:true, width: 100, editrules:{required:true} },
            {name: 'username', index: 'username',editable:true, width: 100, editrules:{required:true} },
            {name: 'telephone', index: 'telephone',editable:true, width: 100 },
            {name: 'email', index: 'email',editable:true, width: 100 },
            {name: 'pwd', index: 'pwd',editable:true, width: 100 },
            {name: 'ifDel', index: 'ifDel',editable:true, width: 100, editrules:{required:true} },
            {name: 'status', index: 'status',editable:true, width: 100, formatter: 'select',
		formatoptions: {value: dict_status},
		edittype: 'select',
		editoptions: {value: dict_status}, editrules:{required:true} },
            {name: 'remark', index: 'remark',editable:true, width: 100 },
            {name: 'createTime', index: 'createTime',editable:true, width: 100 },
            {name: 'modifyTime', index: 'modifyTime',editable:true, width: 100 },
            {name: 'createUser', index: 'createUser',editable:true, width: 100 },
            {name: 'modifyUser', index: 'modifyUser',editable:true, width: 100 },
            {name: 'registerIp', index: 'registerIp',editable:true, width: 100 },
            {name: 'sysType', index: 'sysType',editable:true, width: 100 }
	    ],
		rowNum: 10,
		rows: 'abc',
		rowList:[10,50,100],
		prmNames: {
			rows: 'pageSize',
			page: 'pageNum',
			sort: 'orderBy',
			order: 'order'
		},
		jsonReader: {
			root: "list",    // json中代表实际模型数据的入口
			page: "pageNum",    // json中代表当前页码的数据
			total: "pages",    // json中代表页码总数的数据
			records: "total", // json中代表数据行总数的数据
		 },
		autowidth:true,
		shrinkToFit:true,
		pager: '#pager_list_1',
		//sortname: '',
		viewrecords: true,
		sortorder: "desc",
		hidegrid: false,
		onSortCol: function(name, index) {
		  //alert("Column Name: " + name + " Column Index: " + index);
		},
		ondblClickRow: function(rowid) {
			$("#table_list_1").editGridRow(rowid,editOption);
		},
		caption: "",
		//height: 400,
		height: 'auto',
		editOptions: {
			top: 50, left: "100", width: 800
			,closeOnEscape: true, afterSubmit: function(response, postData) {
				alert(123);

			}
		}
	});
	$("#table_list_1").jqGrid('navGrid', "#pager_list_1",
		{
		  edit: true,
		  add: true,
		  del: true,
		  search: true,
		  refresh: false
		},
		editOption,
		{
			//add
			url: "/dflsystem/dflUser/save",
			closeAfterAdd: true,
			recreateForm: true,
			errorTextFormat: function (data) {
				return 'Error: ' + data.responseText
			},
			afterSubmit: function(response, postdata)  {
				var result = eval('(' + response.responseText + ')');
				if(result.success==true) {
					swal({title:"消息提示",text:"添加成功"});
					return [true,''];
				} else {
					return [false, '添加失败，' + result.errorMsg];
				}
			}
		},
		{
			//delete
			url:"/dflsystem/dflUser/delete",
			afterSubmit: function(response, postdata)  {
				var result = eval('(' + response.responseText + ')');
				if(result.success==true) {
					swal({title:"消息提示",text:"删除成功"});
					return [true,''];
				} else {
					return [false, '删除失败，' + result.errorMsg];
				}
			}
		},
		{
			//search
			closeAfterSearch: true,
			multipleSearch: true
		}
	);

	$("#btnSearch").click(function(){
	  doSearch();
	});
}