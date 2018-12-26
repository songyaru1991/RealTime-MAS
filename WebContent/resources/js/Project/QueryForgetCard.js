$(document).ready(function () {
	//獲取session中的費用代碼放入select下拉框中
	
	if($("#hideCostid").val()=='ALL'){
		 $.ajax({
	            type: "post",
	            url: 'GetCostId.show',
	            async: false,
	            data: {},
	            error: function (XMLHttpRequest, textStatus,
	                             errorThrown) {
	                alert(XMLHttpRequest.status);
	                alert(XMLHttpRequest.readyState);
	                alert(textStatus);
	            },
	            success: function (data) {
	                if(data!=null && data!=''){	
	                	if (data.length > 0) {	                    
	                		//$("#depid").find("option").not(":first").remove();
	                		$("#costid").find("option").remove();
	                		//$("#costid").append("<option value='ALL'>"+"ALL"+"</option>");	   
	                		for (var i = 0; i < data.length; i++) {
	                			$("#costid").append("<option value='"+data[i]+"'>"+data[i]+"</option>");	                    	
	                		}
	                		                 
	                	}
	                }
	            }
	            
	        });
	}else{
		var sessionCostid = $("#hideCostid").val().split("*");
		for(var i=0;i<sessionCostid.length;i++){
		$("#costid").append("<option value='"+sessionCostid[i]+"'>"+sessionCostid[i]+"</option>");
		};
	}
	$("#depid").change(function(){
		$('#forgetCardInfoPagination').empty();
	});
	//二级联动，根据费用代码查询数据库对应的部门代码
	$("#costid").change(function(){   	
		 $.ajax({
	            type: "post",
	            url: 'GetDepid.show',
	            async: false,
	            data: $("#form1").serialize(),
	            error: function (XMLHttpRequest, textStatus,
	                             errorThrown) {
	                alert(XMLHttpRequest.status);
	                alert(XMLHttpRequest.readyState);
	                alert(textStatus);
	            },
	            success: function (data) {
	            	$("#depid").append("");
	                if(data!=null && data!=''){	
	                	if (data.length > 0) {	                    
	                		//$("#depid").find("option").not(":first").remove();
	                		$("#depid").find("option").remove();   
	                		for (var i = 0; i < data.length; i++) {
	                			$("#depid").append("<option value='"+data[i].depid+"'>"+data[i].depid+"</option>");	                    	
	                		}
							$('#depid').selectpicker('refresh');                  
	                	}
	                }
	            }
	            
	        });
	})
	/*//调用multiselect()方法，
	$('#depid').multiselect();
	$('#costid').multiselect();*/
	
	   $('.selectpicker').selectpicker({
	        'selectedText': 'cat'
	    });
	
// $('.selectpicker').selectpicker('hide');
					var curPage = 1;
	//查詢全部記錄，方便導出
					$("#showAll").click(function(){
						//删除查询全部时显示的页数
						$('#forgetCardInfoPagination').empty();
						
						 var ccostid = $("#costid").val();
					        var cdepid = $("#depid").val();
					        if (ccostid == '' || ccostid ==null) {
					            alert("請選擇所要查詢的費用代碼！");
					            return;
					        }
					        if (cdepid =='' || cdepid ==null) {
					            alert("請選擇所要查詢的部門代碼！");          
					            return;
					        }
						
						/*if(deptValue==""||deptValue=null){
							alert("請選擇部門代碼");
							
						}*/
				        var ssDate = $("#dpick1").val();
				        //var eeDate = $("#dpick2").val();
				        if (ssDate == "") {
				            alert("請選擇日期")
				            return;
				        }
					
						$.ajax({
				            type: "post",
				            url: 'CheckFCJsonAll.show',
				            // dataType:"json",
				            async: false,
				            data: $("#form1").serialize(),
				            error: function (XMLHttpRequest, textStatus,
				                             errorThrown) {
				                alert(XMLHttpRequest.status);
				                alert(XMLHttpRequest.readyState);
				                alert(textStatus);
				            },
				            success: function (data) {
				                if(data!=null && data!=''){	
				                	if (data.length > 0) {
				                		var sumdata = data.length;
				                		$("#sumData").text(sumdata);
				                		var tableBodyElement = '';

				                		$('#ForgetCardStatus tbody').empty();
				                		for (var i = 0; i < data.length; i++) {
				                			tableBodyElement += '<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
				                				+ data[i].id + '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
				                				+ data[i].name + '</td>'
				                				+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + data[i].depid
				                				+ '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
				                				+ data[i].costid + '</td>'
				                				+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + data[i].isonwork + '</td>'
				                				+ '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + data[i].fcDate
				                				+ '</td></tr>';
				                		}

				                		$('#ForgetCardStatus tbody').append(tableBodyElement);
				                	}else {
				                		alert("查詢數據為空");
				                	}
				                }else{
				                	alert("查詢數據為空");
				                }
							}
						});

			});				  

    //清空查询
    $("#clearTable").click(function () {
    	//$("#costid").val("");
    	$("#dpick1").val("");
    	//$("#depid").val("");
    	//$("#depid").find("option").not(":first").remove();
    	
        $("#ForgetCardStatus tbody").html("");
        
    })
    $("#submit").click(function () {
     
    
        var ccostid = $("#costid").val();
        var cdepid = $("#depid").val();
        if (ccostid == '' || ccostid ==null) {
            alert("請選擇所要查詢的費用代碼！");
            return;
        }
        if (cdepid == '' || cdepid==null) {
            alert("請選擇所要查詢的部門代碼！");          
            return;
        }
        var ssDate = $("#dpick1").val();
        //var eeDate = $("#dpick2").val();
        if (ssDate == "") {
            alert("請選擇日期")
            // $("#dpick1").focus();
            // $("#dpick2").focus();
            return;
        }
        getAsyncAccountInfo();
    });
    //导出加班数据
    	$("#exportData").click(
        function () {

            $("#ForgetCardStatus").table2excel(
                {
                    exclude: ".noExl", // 过滤位置的 css 类名
                    filename: "員工忘卡數據"
                    + new Date().getFullYear()
                    + new Date().getMonth()
                    + new Date().getDate()
                    + new Date().getHours()
                    + new Date().getMinutes(), // 文件名称
                    name: "Excel Document Name.xlsx",
                    exclude_img: true,
                    exclude_links: true,
                    exclude_inputs: true

                });
        });
//分页查询
    function refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize){
		$('#forgetCardInfoPagination').empty();
		var paginationElement='頁次：'+currentPage+'/'+totalPage +'&nbsp;每页:&nbsp;'+pageSize+'&nbsp;共&nbsp;'+totalRecord+'&nbsp;條&nbsp;';
		if(currentPage==1)
			paginationElement+='<a href="#">首页</a>';		  
		else
			paginationElement+='<a class="firstPage">首页</a>';

		if(currentPage>1)
			paginationElement+='&nbsp;<a class="previousPage">上一頁</a>';
		else
			paginationElement+='&nbsp;<a href="#">上一頁</a>';
		
	   /* for(var i=1;i <= totalPage;i++){
	    	paginationElement+='&nbsp;<a class="numPage">' + i +'</a>&nbsp;';	    	
	    }*/
		if(currentPage<totalPage)
			paginationElement+='<a class="nextPage">下一頁</a>';
		else
			paginationElement+='<a href="#">下一頁</a>';
		
		$('#forgetCardInfoPagination').append(paginationElement);
		
		$('.firstPage').click(function(){
			curPage=1;
			getAsyncAccountInfo(curPage,$('#form1').serialize());				
		});
		
		$('.previousPage').click(function(){
			curPage=currentPage-1;
			getAsyncAccountInfo(curPage,$('#form1').serialize());				
		});	
		
		$('.nextPage').click(function(){
			curPage=currentPage+1;
			getAsyncAccountInfo(curPage,$('#form1').serialize());				
		});	
		
		$('.numPage').click(function(){
			var curPage=$(this).text();
    		getAsyncAccountInfo(curPage,$('#form1').serialize());				
    		});
		
	}
	function getAsyncAccountInfo(){
		var data1 = $.param({'curPage': curPage}) + '&' + $('#form1').serialize();
		$.ajax({
			type : "post",
			url : 'CheckFCJson.show',
			// dataType:"json",
			async : false,
			//data : {curPage:curPage,$("#form1").serialize()},
			data : data1,
			error:function(e){
				alert('找不到資料');
			},
			success:function(rawData){	
				 if(rawData!=null && rawData!=''){	
					 var executeResult=rawData["list"];
					 var errorResponse=executeResult.ErrorMessage;
					 if(errorResponse!=null){
						 alert(errorResponse);
					 }
					 else{
						 var numOfRecords=executeResult.length;
						 if(numOfRecords>0)
							 ShowAllAccountTable(rawData);
						 else
							 alert('找不到資料');
					 }
				 }else{
					 alert('找不到資料');
				 }
			}
		});
		
	}
	function ShowAllAccountTable(data){
		var currentPage=data.currentPage;
		var totalRecord=data.totalRecord;
		var totalPage=data.totalPage;
		var pageSize=data.pageSize;
		var queryResult=data['list'];
		if (queryResult.length > 0) {
			var sumdata = queryResult.length;
			$("#sumData").text(totalRecord);
			var tableBodyElement = '';

			$('#ForgetCardStatus tbody').empty();
			for (var i = 0; i < queryResult.length; i++) {
				tableBodyElement += '<tr><td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
                    + queryResult[i].id + '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
                    + queryResult[i].name + '</td>'
                    + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].depid
                    + '</td>' + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'
                    + queryResult[i].costid + '</td>'
                    + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].isonwork + '</td>'
                    + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + queryResult[i].fcDate
                    + '</td></tr>';
			}

			$('#ForgetCardStatus tbody').append(
					tableBodyElement);
		}	
		refreshUserInfoPagination(currentPage,totalRecord,totalPage,pageSize);
	}
});
function Save1(name){
	var filename = name+ new Date().getFullYear() + "-"
	+ (new Date().getMonth()+1) + "-"
	+ new Date().getDate() + " "
	+ new Date().getHours() + ":"
	+ new Date().getMinutes();
	$('#ForgetCardStatus').tableExport({type:'excel'},filename);
};