<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="UTF-8">
    <title>添加</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="../../assets/layui/css/layui.css" rel="stylesheet">
    <link href="../../assets/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
</head>
<body>
<div style="padding: 15px;">
    <form class="layui-form" action="" lay-filter="fm">
        <#list columns as column>
            <#if !column.showInGrid>
                <input type="hidden" name="${column.fieldName}">
            <#else>
                <div class="fitem">
                    <div class="layui-form-item">
                        <label class="layui-form-label">${column.comment}:</label>
                        <div class="layui-input-block">
                    <#if column.hasDict>
                        <select name="${column.fieldName}" lay-filter="${column.fieldName}">
                        </select>
                    <#else>
                        <input name="${column.fieldName}" class="layui-input" <#if !column.nullable> lay-verify="required"</#if>>
                    </#if>
                        </div>
                    </div>
                </div>
            </#if>
        </#list>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="reset" class="layui-btn layui-btn-primary"
                        style="height: 33px;line-height: 33px;float: right;">重置
                </button>
                <button class="layui-btn" lay-submit="" lay-filter="btnSubmit"
                        style="height: 33px;line-height: 33px;background-color: #627aad;float: right;margin-right: 10px;">
                    立即提交
                </button>
            </div>
        </div>
    </form>
</div>
<script src="../../assets/layui/layui.js"></script>
<script src="../../js/layui/common.js"></script>
<script src="../../js/layui/comm.dict.js"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form;
        var $ = layui.$;
        var id=getQueryString('id');
        if(id){
            $.post("/${simpleModule}/${entityName?uncap_first}/view", {id: id}, function (data) {
                if(data.success){
                    form.val('fm', data.data);
                }
                else{
                    layer.msg(data.errorMsg);
                    layer.close(index);
                }
            });
        }
        form.on('submit(btnSubmit)', function (data) {
            let index = layer.load();
            $.post("/${simpleModule}/${entityName?uncap_first}/save", data.field, function (data) {
                if (data.success) {
                    layer.msg("更新成功", {icon: 6});
                    setTimeout(function () {
                        window.parent.location.reload();
                    }, 1000);
                } else {
                    layer.msg(data.errorMsg);
                }
                layer.close(index);
            })
            return false;
        });
    })
</script>
</body>
</html>