<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>菜单资源管理</title>
    <link href="../../assets/layui/css/layui.css" rel="stylesheet">
    <link href="../../assets/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
    <style>
        .addPeers {
            background-color: #627aad;
        }

        .layui-laypage .layui-laypage-curr .layui-laypage-em {
            background-color: #627aad;
        }
    </style>
</head>
<body>
<div class="layui-container">
    <form action="" id="search-form" lay-filter="searchForm" class="layui-form">
        <div class="layui-inline">
            <input id="startTime" name="startTime" placeholder="开始时间" width="100px" lay-verify="startTime"
                   placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-inline">
            <input id="endTime" name="endTime" placeholder="结束时间" width="100px" lay-verify="endTime"
                   placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-inline">
            <input id="query_name" name="name" type="text" placeholder="name" class="layui-input">
        </div>
        <div class="layui-inline">
            <button type="button" class="layui-btn" lay-submit lay-filter="btnSearch">查询</button>
        </div>
    </form>
</div>
<div class="layui-container" style="width: 100%;padding: 0">
    <div class="layui-row">
        <div class="layui-col-md12" style="padding:5px;">
            <div class="layui-card">
                <div class="layui-card-body">
                    <table class="layui-table"
                           lay-data="{url:'/${simpleModule}/${entityName?uncap_first}/list3', page:true, id:'dataList',toolbar:'#toolbar'}"
                           lay-filter="dataList">
                        <thead>
                        <tr>
                            <#list columns as column>
                            <th lay-data="{field:'${column.fieldName}', sort: true, align:'center'<#if !column.showInGrid>, hide:true</#if><#if column.hasDict>, templet: function(d,row){
                                return formatterCombobox(${column.dictKey}, d.${column.fieldName});
                                }</#if>}">${column.comment}</th>
                            </#list>
                            <th lay-data="{field:'right',toolbar: '#rightBar',width:150,align:'center'}">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm addData" lay-event="addData">添加</button>
    </div>
</script>
<script type="text/html" id="createTimeTpl">
    <div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>
</script>
<script type="text/html" id="rightBar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script src="../../assets/layui/layui.js"></script>
<script src="../../js/layui/common.js"></script>
<script src="../../js/layui/comm.dict.js"></script>
<script>
    layui.use(['form', 'table', 'laydate'], function () {
        var $ = layui.$;
        var table = layui.table;
        var form=layui.form;
        tableInitResponseType(table);

        initStartEndTime(7);

        form.on('submit(btnSearch)', function (data) {
            var jsonParam = form.val('searchForm');
            table.reload('dataList', {
                where: jsonParam
            });
        });

        //监听行工具事件
        table.on('tool(dataList)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('确定要删除该数据吗?', function (index) {
                    $.post("/${simpleModule}/${entityName?uncap_first}/delete", {"id": obj.data.id}, function (data) {
                        if (data.code === 200) {
                            obj.del();
                            layer.msg("删除成功");
                        } else {
                            layer.msg(data.msg);
                        }
                    })
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.open({
                    type: 2,
                    area: ['650px', '400px'],
                    title: '编辑',
                    shadeClose: true,
                    maxmin: true,
                    content: '${entityName?uncap_first}Edit.html?id=' + obj.data.id
                });
            }
        });

        //头工具栏事件
        table.on('toolbar(dataList)', function (obj) {
            switch (obj.event) {
                case 'addData':
                    layer.open({
                        type: 2,
                        area: ['650px', '400px'],
                        title: '添加',
                        shadeClose: true,
                        maxmin: true,
                        content: '${entityName?uncap_first}Edit.html'
                    });
                    break;
            };
        });
    })
</script>
</body>
</html>