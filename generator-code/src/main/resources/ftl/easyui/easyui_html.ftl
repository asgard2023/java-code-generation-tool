<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${comment}列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="../../assets/easyui/easyui1.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="../../assets/easyui/easyui1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../assets/easyui/easyui1.7.0/easyui.comm.valid.js"></script>
    <script type="text/javascript" src="../../assets/easyui/easyui1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../../assets/easyui/easyui.grid.hide.js"></script>
    <script type="text/javascript" src="../../js/easyui/Common.js"></script>
    <script type="text/javascript" src="../../js/easyui/comm.dict.js"></script>
    <script type="text/javascript" src="../../js/easyui/DateUtils.js"></script>
    <link rel="stylesheet" type="text/css" href="../../easyui/easyui1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../easyui/easyui1.7.0/themes/icon.css">

    <style type="text/css">
        #fm {
            margin: 0;
            padding: 10px 20px;
        }

        .ftitle {
            font-size: 14px;
            font-weight: bold;
            padding: 5px 0;
            margin-bottom: 10px;
            border-bottom: 1px solid #ccc;
        }

        .fitem {
            margin-bottom: 5px;
        }

        .fitem label {
            display: inline-block;
            width: 60px;
        }

        .input_query {
            width: 80px;
        }

        input, textarea {
            width: 160px;
            border: 1px solid #ccc;
            padding: 2px;
        }
    </style>
</head>

<body>

<div class="easyui-layout" data-options="fit:true">
    <!-- 查询条件  -->
    <div data-options="region:'north', title:'${comment}-条件'"
         style="height: 80px; padding: 5px 10px;display:none;">
        <form id="search-form" class="form-inline" style="float:left">
            <div class="form">
                <div class="input-group">
                    <input name="startTime" id="query_startTime" data-options="prompt:'开始时间'" class="easyui-datetimebox" style="width:160px" formatter="Common.DateFormatter">
                    <input name="endTime" id="query_endTime" data-options="prompt:'结束时间'" class="easyui-datetimebox" style="width:160px" formatter="Common.DateFormatter">
                    <#list columns as column>
                        <#if column.equalsSearch >
                            <#if column.hasDict>
                                <input class="easyui-combobox" name="${column.fieldName}" id="query_${column.fieldName}" data-options="panelHeight:'auto',
                                        valueField: 'id',
                                        textField: 'name',
                                        data: getDictData(${column.dictKey},'all')" />
                            <#else>
                                <input type="text" id="query_${column.fieldName}" name="${column.fieldName}" placeholder="${column.comment}" data-options="prompt:'${column.comment}'" <#if column.javaType == 'Date'>class="input_query"<#else>class="easyui-validatebox"</#if>>
                            </#if>
                        </#if>
                    </#list>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="search-btn">查询</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-reload'" id="reset-btn">重置</a>
                </div>
            </div>
        </form>
    </div>

    <!-- 查询结果 center -->
    <div data-options="region:'center', title:'查询结果'">
        <div id="toolbar" style="display:none;">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="onAdd()">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="onEdit()">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
               onclick="onDestroy()">删除</a>
        </div>
        <table id="dg" class="easyui-datagrid"
               data-options="onDblClickRow:onDblClick, onClickRow:onClick, onLoadError:loadError, onLoadSuccess:function(){$('.datagrid-btable').find('div.datagrid-cell').css('text-align','left');}"
               toolbar="#toolbar" pagination="true" sortName="createTime" sortOrder="desc"
               rownumbers="true" fitColumns="true" singleSelect="true" style="display:none;">
            <thead>
            <tr>
                <#list columns as column>
                    <th align="center" field="${column.fieldName}" width="50" sortable="true"<#if !column.showInGrid> hidden="true"</#if><#if column.hasDict> data-options="formatter: function(value,row,index){
                                return formatterCombobox(${column.dictKey}, value, false);
                            }"</#if>>${column.comment}</th>
                    <#if column.FK>
                        <th align="center" field="${column.fkRelField}Name"<#if !column.showInGrid> hidden="true"</#if> width="50" sortable="true">${column.comment}</th>
                    </#if>
                </#list>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:500px;padding:5px 10px;display:none;"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post" novalidate>
        <#list columns as column>
            <#if !column.showInGrid>
                <input type="hidden" name="${column.fieldName}">
            <#else>
            <div class="fitem">
                <label>${column.comment}:</label>
                <#if column.hasDict>
                    <input class="easyui-combobox" name="${column.fieldName}" id="${entityName?uncap_first}_${column.fieldName}" <#if !column.nullable> required="true"</#if> data-options="panelHeight:'auto',
                                valueField: 'id',
                                textField: 'name',
                                //multiple:true, //多选增加此项
                                data: ${column.dictKey}" />
                <#else>
                    <input name="${column.fieldName}" <#if column.javaType == 'Date'>class="easyui-datetimebox"<#else>class="easyui-validatebox"</#if> <#if !column.nullable> required="true"</#if>>
                </#if>
            </div>
            </#if>
        </#list>
    </form>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="onSave()">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
    </div>
</div>

<script type="text/javascript" src="${entityName?uncap_first}.js"></script>
</body>
</html>