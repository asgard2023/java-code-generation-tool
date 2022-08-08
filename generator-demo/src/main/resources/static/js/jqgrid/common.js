function loadI18n(){
    var defaultLang = "zh";
    try{


    var cookieLang=getCookie('i18n_lang');
    if(cookieLang && cookieLang!=''){
        defaultLang=cookieLang;
    }
    $("[i18n]").i18n({
        defaultLang: defaultLang,
        filePath: "../statics/js/i18n/",
        filePrefix: "i18n_",
        fileSuffix: "",
        forever: true,
        callback: function () {
            console.log("i18n has been completed.");
        }
    });
    }catch (e) {
        console.log(e);
    }
    return defaultLang;
}

function getCookie(name) {
    var arr = document.cookie.split('; ');
    for (var i = 0; i < arr.length; i++) {
        var arr1 = arr[i].split('=');
        if (arr1[0] == name) {
            return arr1[1];
        }
    }
    return '';
}

function setCookie(name, value, myDay) {
    if(!myDay){
        myDay=1;
    }
    var oDate = new Date();
    oDate.setDate(oDate.getDate() + myDay);
    document.cookie = name + '=' + value + '; expires=' + oDate;
}


function formToJson(formId) {
	var queryArray = $("#"+formId).serializeArray();
    var jsonString= '{';
    for (var i = 0; i < queryArray.length; i++) {
        jsonString+= JSON.stringify(queryArray[i].name) + ':' + JSON.stringify(queryArray[i].value) + ',';
    }
    jsonString= jsonString.substring(0, (jsonString.length - 1));
    jsonString+= '}';
    return JSON.parse(jsonString);
};

function layerAlert(text, yesAction) {
	var index = layer.open({
	    type: 1
	    ,area: '300px'
	    ,offset: 'auto'
	    ,id: 'lay_demo' //防止重复弹出
	    ,content: '<div style="padding: 20px 20px;word-wrap:break-word;">'+text+'</div>'
	    ,btn: '确定'
	    ,btnAlign: 'c' //按钮居中
	    ,shade: 0 //不显示遮罩
	    ,yes: function(){
	    	if(yesAction) {
	    		yesAction();
	    	} else {
	    		layer.close(index);
	    	}
	    }
	  });
};

function formatterDict(dict, value){
    var obj=value;
    jQuery.each(dict, function(key, val) {
        if(key==value){
            obj=val;
            return obj;
        }
    });

    return obj;
}

function showAttr(obj) {
    var b = "", c;
    for (c in obj) {
        b = b + c + ",\n";
    }
    alert(b)
}

function objToString(obj, callBack){
    var i=0;
    var str='';
    var t='';
    for (c in obj) {
        t=obj[c];
        if(callBack){
            t=callBack(c);
            // console.log(t);
        }
        if(i>0){
            str+=';';
        }
        str+=(c+':'+t);
        i++;
    }
    return str;
}

function objToObject(obj, callBack){
    var i=0;
    var objs={};//如果objs={'':''}; 可支持在前面插入为空的选项
    return appendToObject(objs, obj, callBack);
}

function appendToObject(objs, obj, callBack){
    var i=0;
    var t='';
    for (c in obj) {
        t=obj[c];
        if(callBack){
            t=callBack(c);
            // console.log(t);
        }
        // if(i>0){
        //     str+=';';
        // }
        // str+=(c+':'+t);
        objs[c]=t;
        i++;
    }
    return objs;
}

function objToSelect(obj, callBack){
    var i=0;
    var str='';
    var t='';
    for (c in obj) {
        t=obj[c];
        if(callBack){
            t=callBack(c);
        }
        str += "<option value='"+c+"'>"+t+"</option>";
        i++;
    }
    return str;
}

/**
 * 只留下包含的
 * @param obj
 * @param contains
 * @returns {string}
 */
function objToSelectContains(obj, contains){
    var i=0;
    var str='';
    var t='';
    for (c in obj) {
        t=obj[c];
        if(!(contains.indexOf(c+',')>=0)){
            continue;
        }
        str += "<option value='"+c+"'>"+t+"</option>";
        i++;
    }
    return str;
}

/**
 * 排除要过滤的
 * @param obj
 * @param filters
 * @returns {string}
 */
function objToSelectFilter(obj, filters){
    var i=0;
    var str='';
    var t='';
    for (c in obj) {
        t=obj[c];
        if((filters.indexOf(c+',')>=0)){
            continue;
        }
        str += "<option value='"+c+"'>"+t+"</option>";
        i++;
    }
    return str;
}

var thumImageDomains;
function getPicThumbnail(img, w, h) {
    if(!img || ''==img){
        return '';
    }

    if(!thumImageDomains){
        var datas=getSystemConfigs('picCdnDomainThumbnail');
        console.log(datas);
        if(datas && datas.length>=1){
            thumImageDomains=datas[0].configValue;
            console.log('----thumImageDomains='+thumImageDomains);
        }
    }

    var domains=thumImageDomains.split(',');
    for(var i=0; i<domains.length; i++){
        var domain=domains[i];
        // console.log('domain='+domain);
        if(img.indexOf(domain)<0){
            continue;
        }
        // console.log('domain='+domain);
        var imgView;
        var imgView2='imageView2/0/w/'+w+'/h/'+h;
        if(img.indexOf('?')>0){
            imgView=img+'&';
        }
        else{
            imgView=img+'?';
        }
        return imgView;
    }
    return img;
}


/**
 * 加载json的数据到页面的表单中，以name为唯一标示符加载
 * @param {String} jsonStr json表单数据
 */
function loadJsonDataToForm(json){
    try{
        //var obj = eval("("+json+")");
        var obj = json;
        var key,value,tagName,type,arr;
        for(x in obj){
            key = x;
            value = obj[x];

            $("[name='"+key+"'],[name='"+key+"[]']").each(function(){
                tagName = $(this)[0].tagName;
                type = $(this).attr('type');
                if(tagName=='INPUT'){
                    if(type=='radio'){
                        $(this).attr('checked',$(this).val()==value);
                    }else if(type=='checkbox'){
                        arr = value.split(',');
                        for(var i =0;i<arr.length;i++){
                            if($(this).val()==arr[i]){
                                $(this).attr('checked',true);
                                break;
                            }
                        }
                    }else{
                        $(this).val(value);
                    }
                }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                    $(this).val(value);
                }

            });
        }
    }catch(e){
        alert("加载表单："+e.message+",数据内容"+JSON.stringify(json));
    }
}


$.fn.loadForm = function (jsonValue) {
    var obj = this;
    $.each(jsonValue, function (name, ival) {
        var $oinput = obj.find("input[name=" + name + "]");
        if ($oinput.attr("type") == "checkbox") {
            if (ival !== null) {
                var checkboxObj = $("[name=" + name + "]");
                var checkArray = ival.split(";");
                for (var i = 0; i < checkboxObj.length; i++) {
                    for (var j = 0; j < checkArray.length; j++) {
                        if (checkboxObj[i].value == checkArray[j]) {
                            checkboxObj[i].click();
                        }
                    }
                }
            }
        }
        else if ($oinput.attr("type") == "radio") {
            $oinput.each(function () {
                var radioObj = $("[name=" + name + "]");
                for (var i = 0; i < radioObj.length; i++) {
                    if (radioObj[i].value == ival) {
                        radioObj[i].click();
                    }
                }
            });
        }
        else if ($oinput.attr("type") == "textarea") {
            obj.find("[name=" + name + "]").html(ival);
        }
        else {
            obj.find("[name=" + name + "]").val(ival);
        }
    });
};

//获取当前用户
function getCurrentUser() {
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
    var url=localhostPaht+'/cms/asUser/currentUser';
    var datas=null;
    $.ajax({
        url: url,
        type: 'GET',
        async: false,
        cache: false,
        contentType: false,
        error: function (){
            swal({title:"消息提示",text:"调用失败"});
        },
        success: function (res) {
            datas=res;
        },
        error: function (returndata) {
            swal({title:"消息提示",text:JSON.stringify(returndata)});
        }
    });
    return datas;
}


function getChooseTypes(data,...chooseStr){
	var map = {}
	var len = chooseStr.length;
	for(var i=0;i<len;i++){
		if(data[chooseStr[i]] != null){
			map[chooseStr[i]] = data[chooseStr[i]];
		}
    }
    return map;
}

function itemToSelect(objs){
    var i=0;
    var str='';
    for (var i=0; i<objs.length; i++) {
        var obj=objs[i];
        str += "<option value='"+obj.code+"'>"+obj.name+"</option>";
    }
    return str;
}

function itemToOptions(objs) {
    var i=0;
    var str='';
    for (var i=0; i<objs.length; i++) {
        var obj=objs[i];
        str += obj.code+':'+obj.name+';';
    }
    if(str.length>0){
        str=str.substring(0, str.length-1);
    }
    return str;
}

function doPostAsync(url, data, callback) {
	$.ajax({
        url: url,
        type: 'POST',
        data: data,
        async: true,
        dataType: 'json',
        error: function (){
        	swal({title:"消息提示",text:"调用失败"});
        },
        success: function (res) {
      	  if(res.success) {
      		  if(callback) {
      			callback(res.data);
      		  }

          } else {
              swal({title:"消息提示",text:res.msg}, function(){
            		//doSearch();
            	});
            }
        },
        error: function (returndata) {
            swal({title:"消息提示",text:JSON.stringify(returndata)});
        }
   });
}

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

function isAndroid() {
	var u = navigator.userAgent;
	return u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端

}

function isIOS() {
	var u = navigator.userAgent;
	return !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
}

function isMobile() {
	return isAndroid() || isIOS();
}

function dateFormat(fmt, date) {
    let ret;
    const opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        };
    };
    return fmt;
}

function initStartEndTime(beforeDay) {
    var start = {
        elem: '#startTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        max: laydate.now(),
        istime: true,
        istoday: false,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    var end = {
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        max: laydate.now(),
        istime: true, //是否开启时间选择
        istoday: false,
        isclear: true, //是否显示清空
        issure: true, //是否显示确认
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(start);
    laydate(end);
    //给input赋值
    $('#startTime').val(laydate.now(-beforeDay, 'YYYY-MM-DD hh:mm:ss'));
    //$('#endTime').val(laydate.now(0, 'YYYY-MM-DD hh:mm:ss'));
}
