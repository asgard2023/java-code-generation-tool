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
		url: "/${simpleModule}/${entityName?uncap_first}/update",
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
		url:  '/${simpleModule}/${entityName?uncap_first}/list',
        mtype: 'POST',
		datatype: "json",
		styleUI: 'Bootstrap',
		postData: queryObj,
		colNames: [<#list columns as column><#if column_index==0>'${column.comment}'<#else>, '${column.comment}'</#if></#list>],
        colModel: [<#list columns as column><#if column_index!=0>,
            </#if><#if column.primary >{name: '${column.fieldName}', index: '${column.fieldName}', editable:true, hidden: true,key:true}<#else>{name: '${column.fieldName}', index: '${column.fieldName}',editable:true, width: 100<#if column.hasDict>, formatter: 'select',
		formatoptions: {value: ${column.dictKey}},
		edittype: 'select',
		editoptions: {value: ${column.dictKey}}</#if><#if !column.nullable>, editrules:{required:true}</#if> }</#if></#list>
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
			url: "/${simpleModule}/${entityName?uncap_first}/save",
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
			url:"/${simpleModule}/${entityName?uncap_first}/delete",
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