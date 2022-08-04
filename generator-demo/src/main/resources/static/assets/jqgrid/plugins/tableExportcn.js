//20170722修正以下问题
//1.解决jqgrid无法获取表头问题;2.jqgrid有多余空行问题乱码问题;3.部分导出无扩展名问题;4.但xml中文还是乱码,ppt输出不对；
//5.注意参数大小写问题，必须一致,因此将所有参数名改成了小写
//csv,txt
//sql
//json
//xml ,中文还有乱码
//excel,doc,powerpoint
//png
//pdf
//onclick="$('#DataGrid').tableexport({type:'txt',escape:'false',consolelog:'true',tablename:'用户帐号表',jqgrid:'true'});"
(function($){
	$.fn.extend({
			tableexport: function(options) {
      var defaults = {
      separator: ',',
      ignorecolumn: [],
      tablename:'yourtablename',
      type:'csv',
      pdffontsize:14,
      pdfleftmargin:20,
      escape:'true',
      htmlcontent:'false',
      consolelog:'false',
      jqgrid:'false'
    };

    var options = $.extend(defaults, options);
    var el = this;
		//如果是jqgrid表就要用特殊ID找表头
		(defaults.jqgrid=='true')?headerid="#gview_"+$(el).attr("id"):headerid=el;
		//如果是jqgrid表就要去掉第一个空行
		(defaults.jqgrid=='true')?tbtr="tr:gt(0)":tbtr="tr";

    if(defaults.type == 'csv' || defaults.type == 'txt'){
     // Header
     if(defaults.type == 'csv'){
		var fext=".csv";     
	 }else if(defaults.type == 'txt'){
		var fext=".txt";
	 }
     var tdData ="";
     $(headerid).find('thead').find('tr').each(function() {
     tdData += "\n";
      $(this).filter(':visible').find('th').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         tdData += '"' + parseString($(this)) + '"' + defaults.separator;
        }
       }

      });
      tdData = $.trim(tdData);
      tdData = $.trim(tdData).substring(0, tdData.length -1);
     });

     // Row vs Column
     $(el).find('tbody').find(tbtr).each(function() {
     tdData += "\n";
      $(this).filter(':visible').find('td').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         tdData += '"'+ parseString($(this)) + '"'+ defaults.separator;
        }
       }
      });
      //tdData = $.trim(tdData);
      tdData = $.trim(tdData).substring(0, tdData.length -1);
     });

     //output
     if(defaults.consolelog == 'true'){
      console.log(tdData);
     }
     var base64data = "base64," + $.base64({data:tdData});
     //window.open('data:application/'+defaults.type+';filename=exportData;' + base64data);
	$('<a style="display:none" href="data:application/'+defaults.type+';filename=exportData;'+base64data+'" download="'+defaults.tablename.toString()+fext+'"><span></span></a>').appendTo(document.body).find('span').trigger("click").parent().remove();
    }else if(defaults.type == 'sql'){

     // Header
     var tdData ="INSERT INTO `"+defaults.tablename+"` (";
     $(headerid).find('thead').find('tr').each(function() {

      $(this).filter(':visible').find('th').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         tdData += '`' + parseString($(this)) + '`,' ;
        }
       }

      });
      tdData = $.trim(tdData);
      tdData = $.trim(tdData).substring(0, tdData.length -1);
     });
     tdData += ") VALUES ";
     // Row vs Column
     $(el).find('tbody').find(tbtr).each(function() {
     tdData += "(";
      $(this).filter(':visible').find('td').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         tdData += '"'+ parseString($(this)) + '",';
        }
       }
      });

      tdData = $.trim(tdData).substring(0, tdData.length -1);
      tdData += "),";
     });
     tdData = $.trim(tdData).substring(0, tdData.length -1);
     tdData += ";";

     //output
     //console.log(tdData);

     if(defaults.consolelog == 'true'){
      console.log(tdData);
     }

     var base64data = "base64," + $.base64({data:tdData});
     //window.open('data:application/sql;filename=exportData;' + base64data);
	$('<a style="display:none" href="data:application/sql;filename=exportData;'+base64data+'" download="'+defaults.tablename.toString()+'.sql'+'"><span></span></a>').appendTo(document.body).find('span').trigger("click").parent().remove();


    }else if(defaults.type == 'json'){

     var jsonHeaderArray = [];
     $(headerid).find('thead').find('tr').each(function() {
      var tdData ="";
      var jsonArrayTd = [];

      $(this).filter(':visible').find('th').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         jsonArrayTd.push(parseString($(this)));
        }
       }
      });
      jsonHeaderArray.push(jsonArrayTd);

     });

     var jsonArray = [];
     $(el).find('tbody').find(tbtr).each(function() {
      var tdData ="";
      var jsonArrayTd = [];

      $(this).filter(':visible').find('td').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         jsonArrayTd.push(parseString($(this)));
        }
       }
      });
      jsonArray.push(jsonArrayTd);

     });

     var jsonExportArray =[];
     jsonExportArray.push({header:jsonHeaderArray,data:jsonArray});

     //Return as JSON
     //console.log(JSON.stringify(jsonExportArray));

     //Return as Array
     //console.log(jsonExportArray);
     if(defaults.consolelog == 'true'){
      console.log(JSON.stringify(jsonExportArray));
     }
     var base64data = "base64," + $.base64({data:JSON.stringify(jsonExportArray)});
     //window.open('data:application/json;filename=exportData;' + base64data);
	$('<a style="display:none" href="data:application/json;filename=exportData;'+base64data+'" download="'+defaults.tablename.toString()+'.json'+'"><span></span></a>').appendTo(document.body).find('span').trigger("click").parent().remove();
    }else if(defaults.type == 'xml'){

     var xml = '<?xml version="1.0" encoding="utf-8"?>';
     xml += '<tabledata><fields>';
     // Header
     $(headerid).find('thead').find('tr').each(function() {
      $(this).filter(':visible').find('th').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         xml += "<field>" + parseString($(this)) + "</field>";
        }
       }
      });
     });
     xml += '</fields><data>';

     // Row Vs Column
     var rowCount=1;
     $(el).find('tbody').find(tbtr).each(function() {
      xml += '<row id="'+rowCount+'">';
      var colCount=0;
      $(this).filter(':visible').find('td').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         xml += "<column-"+colCount+">"+parseString($(this))+"</column-"+colCount+">";
        }
       }
       colCount++;
      });
      rowCount++;
      xml += '</row>';
     });
     xml += '</data></tabledata>'

     if(defaults.consolelog == 'true'){
      console.log(xml);
     }

     var base64data = "base64," + $.base64({data:xml});
     //window.open('data:application/xml;filename=exportData;' + base64data);
	$('<a style="display:none" href="data:application/xml;filename=exportData;'+base64data+'" download="'+defaults.tablename.toString()+'.xml'+'"><span></span></a>').appendTo(document.body).find('span').trigger("click").parent().remove();
    }else if(defaults.type == 'excel' || defaults.type == 'word'|| defaults.type == 'powerpoint'  ){
     //console.log($(this).html());
     var excel="<table>";
     // Header
     $(headerid).find('thead').find('tr').each(function() {
      excel += "<tr>";
      $(this).filter(':visible').find('th').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         excel += "<td>" + parseString($(this))+ "</td>";
        }
       }
      });
      excel += '</tr>';

     });


     // Row Vs Column
     var rowCount=1;
     $(el).find('tbody').find(tbtr).each(function() {
      excel += "<tr>";
      var colCount=0;
      $(this).filter(':visible').find('td').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         excel += "<td>"+parseString($(this))+"</td>";
        }
       }
       colCount++;
      });
      rowCount++;
      excel += '</tr>';
     });
     excel += '</table>'

     if(defaults.consolelog == 'true'){
      console.log(excel);
     }

     if(defaults.type == 'excel'){
	var fext=".xls";
     var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:"+defaults.type+"' xmlns='http://www.w3.org/TR/REC-html40'>";
     excelFile += "<head>";
     excelFile += "<!--[if gte mso 9]>";
     excelFile += "<xml>";
     excelFile += "<x:ExcelWorkbook>";
     excelFile += "<x:ExcelWorksheets>";
     excelFile += "<x:ExcelWorksheet>";
     excelFile += "<x:Name>";
     excelFile += "{worksheet}";
     excelFile += "</x:Name>";
     excelFile += "<x:WorksheetOptions>";
     excelFile += "<x:DisplayGridlines/>";
     excelFile += "</x:WorksheetOptions>";
     excelFile += "</x:ExcelWorksheet>";
     excelFile += "</x:ExcelWorksheets>";
     excelFile += "</x:ExcelWorkbook>";
     excelFile += "</xml>";
     excelFile += "<![endif]-->";
     excelFile += "</head>";
	 }else if(defaults.type == 'word'){
	var fext=".doc";
     var excelFile = "<html xmlns:v='urn:schemas-microsoft-com:vml' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:"+defaults.type+"' xmlns='http://www.w3.org/TR/REC-html40'>";
	 }else if(defaults.type == 'powerpoint'){
	var fext=".ppt";
     var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:"+defaults.type+"' xmlns='http://www.w3.org/TR/REC-html40'>";
	 }
     excelFile += "<body>";
     excelFile += excel;
     excelFile += "</body>";
     excelFile += "</html>";
     var base64data = "base64," + $.base64({ data: excelFile, type: 0 });
     //window.open('data:application/vnd.ms-'+defaults.type+';filename=exportData.doc;' + base64data);
	$('<a style="display:none" href="data:application/vnd.ms-'+defaults.type+';filename=exportData.doc;'+base64data+'" download="'+defaults.tablename.toString()+fext+'"><span></span></a>').appendTo(document.body).find('span').trigger("click").parent().remove();
    }else if(defaults.type == 'png'){
     html2canvas($(el), {
      onrendered: function(canvas) {
       var img = canvas.toDataURL("image/png");
       window.open(img);


      }
     });
    }else if(defaults.type == 'pdf'){

     var doc = new jsPDF('p','pt', 'a4', true);
     doc.setFontSize(defaults.pdffontsize);

     // Header
     var startColPosition=defaults.pdfleftmargin;
     $(headerid).find('thead').find('tr').each(function() {
      $(this).filter(':visible').find('th').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         var colPosition = startColPosition+ (index * 50);
         doc.text(colPosition,20, parseString($(this)));
        }
       }
      });
     });


     // Row Vs Column
     var startRowPosition = 20; var page =1;var rowPosition=0;
     $(el).find('tbody').find(tbtr).each(function(index,data) {
      rowCalc = index+1;

     if (rowCalc % 26 == 0){
      doc.addPage();
      page++;
      startRowPosition=startRowPosition+10;
     }
     rowPosition=(startRowPosition + (rowCalc * 10)) - ((page -1) * 280);

      $(this).filter(':visible').find('td').each(function(index,data) {
       if ($(this).css('display') != 'none'){
        if(defaults.ignorecolumn.indexOf(index) == -1){
         var colPosition = startColPosition+ (index * 50);
         doc.text(colPosition,rowPosition, parseString($(this)));
        }
       }

      });

     });

     // Output as Data URI
     doc.output('datauri');

    }


    function parseString(data){

     if(defaults.htmlcontent == 'true'){
      content_data = data.html().trim();
     }else{
      content_data = data.text().trim();
     }

     if(defaults.escape == 'true'){
      content_data = escape(content_data);
     }



     return content_data;
    }

   }
        });
    })(jQuery);