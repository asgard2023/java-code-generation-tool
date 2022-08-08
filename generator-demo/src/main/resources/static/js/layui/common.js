/*
 * 获取URL后的参数bond
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return decodeURIComponent((r[2]));
    return null;
}

function tableInitResponseType(table){
    table.init('dataList', {
        limit: 10,
        response: {
            statusName: 'state',
            statusCode: 200,
            msgName: 'msg',
            countName: 'total',
            dataName: 'data'
        }
    });
}

function initStartEndTime(beforeDay){
    var laydate = layui.laydate;
    //7天内
    var recentlyDate=new Date(new Date().getTime()-beforeDay*24*3600*1000);
    //日期
    laydate.render({
        elem: '#startTime'
        ,type: 'datetime'
        ,value: recentlyDate
    });
    laydate.render({
        elem: '#endTime'
        ,type: 'datetime'
    });
}


function selectOptions(layui, data, id){
    var $ = layui.$;
    console.log(id);
    $("#"+id).html("");
    $.each(data, function(key, val) {
        var option1 = $("<option>").val(val.id).text(val.name);
        //通过LayUI.jQuery添加列表项
        $("#"+id).append(option1);
    });
}