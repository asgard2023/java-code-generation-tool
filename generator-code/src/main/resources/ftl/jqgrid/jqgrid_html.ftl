<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${comment}列表</title>
    <meta name="keywords" content="${comment}列表">
    <meta name="description" content="${comment}列表">
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="../../assets/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="../../assets/sweetalert/sweetalert.css">
    <link rel="stylesheet" href="../../assets/layer_v3/skin/default/layer.css" media="all">

    <link rel="stylesheet" href="../../assets/jqgrid5/css/ui.jqgrid-bootstrap4.css">
</head>
<body class="gray-bg">
<form action="" id="searchForm" class="form-inline">
    <div class="form-group">
        <input placeholder="开始时间" width="100px" class="laydate-icon form-control layer-date" id="startTime" name="startTime">
        <input placeholder="结束时间" width="100px" class="laydate-icon form-control layer-date" id="endTime" name="endTime">
    </div>
    <#list columns as column>
        <#if column.equalsSearch >
    <div class="input-group">
        <input id="query_${column.fieldName}" name="${column.fieldName}" type="text" placeholder="${column.comment}" class="input-sm form-control">
    </div>
        </#if>
    </#list>
    <div class="input-group" style="width: 300px;margin: 20px;">
        <span class="input-group-btn"><button type="button" id="btnSearch" class="btn btn-sm btn-primary"> 搜索</button></span>
    </div>
</form>
<div class="ibox-content">
    <div class="jqGrid_wrapper">
        <table id="table_list_1"></table>
        <div id="pager_list_1"></div>
    </div>
</div>
<script src="../../assets/jquery/jquery.min.js?v=2.1.4"></script>
<script src="../../assets/bootstrap/js/bootstrap.min.js?v=3.3.6"></script>
<script src="../../assets/sweetalert/sweetalert.min.js"></script>
<script src="../../assets/layer/laydate/laydate.js"></script>

<script src="../../assets/jqgrid5/js/i18n/grid.locale-cn.js"></script>
<script src="../../assets/jqgrid5/js/jquery.jqGrid.min.js"></script>

<script src="../../js/jqgrid/common.js"></script>
<script src="../../js/jqgrid/comm.dict.js"></script>
<script src="${entityName?uncap_first}.js"></script>
</body>
</html>