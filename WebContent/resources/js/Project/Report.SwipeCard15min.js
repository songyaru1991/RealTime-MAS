$(document).ready(function(){
	var costId=null,startDate=null,endDate=null;
	
	$('#costIdcheck').click(function(){
    	$('#searchCostId').val('');
    	$('#importCostIdSum').text('0');
	    if($(this).prop("checked")==true){
	        //当前为选中状态	    
	    	$("#searchCostId").attr("disabled", true); 	    	
	    	$("#importCostIdSearchBtn").attr("disabled", false); 	
	    }else{
	        //当前为不选中状态
	    	$("#searchCostId").attr("disabled", false);
	    	$("#importCostIdSearchBtn").attr("disabled", true); 
	    }
	});
	
	$('#resetBtn').click(function(){
    	$('#searchCostId').val('');
    	$('#startDate').val('');
    	$('#endDate').val('');
    	$("#costIdcheck").attr("checked",false);
	    $("#searchCostId").attr("disabled", false);
	    $("#importCostIdSearchBtn").attr("disabled", true); 
	
	});
	
	$('#uploadCostIdFile').change(function(){  	
		$('#searchCostId').val('');		
		 getFileContent(this, function (str) {
			 var exportCostId=str.replace(/\s+/g,',').replace(/,$/,"");
			 $('#searchCostId').val(exportCostId);
			 $('#importCostIdSum').text(exportCostId.split(",").length);
          });	   
		 $('#uploadCostIdFile').val(''); 
	});

    $("#exportSwipeCard15minBtn").click(function() {
		
		$("#SwipeCard15minTable").table2excel(
				{
					exclude : ".noExl", // 过滤位置的 css 类名
					filename : "A2刷卡數據統計(超15分鐘&未超15分鐘)"
							+ new Date().getFullYear() + "-"
							+ new Date().getMonth()+1 + "-"
							+ new Date().getDate() + " "
							+ new Date().getHours() + "："
							+ new Date().getMinutes(), 
					name : "Excel Document Name.xlsx",
					exclude_img : true,
					exclude_links : true,
					exclude_inputs : true

				});
	});
	
	$('#searchSwipeCard15minBtn').click(function(){
		SearchSC15minReport();		
	});

	
	function SearchSC15minReport(){			
		costId=$('#searchCostId').val();
		startDate = $("#startDate").val();
		endDate = $("#endDate").val();
		
		var recordStatusArray =[],errorMessage='';
		
		if(startDate==="null" || startDate=='')
			errorMessage+='請選擇查詢的起始時間!\n';
		
		if(endDate==="null" || endDate=='')
			errorMessage+='請選擇查詢的結束時間!\n';
		
		
		if(errorMessage==''){
			$.ajax({
				type:'POST',
				url:'../SC15minReport/SearchSC15minReport.show',
				data:{costId:costId,startDate:startDate,endDate:endDate},
				error:function(e){
					alert('找不到資料');
				},
				success:function(executeResult){
					$('#SwipeCard15minTable tbody').empty();
											if (executeResult != null && executeResult != "") {
												var errorResponse = executeResult.ErrorMessage;
												if (errorResponse != null) {
													alert(errorResponse);
												} else {													
													if (executeResult.length > 0)
														ShowSearchSC15minReportTable(executeResult);
													else
														alert('找不到資料');
												}
											}
				}
			});	
		}else{
			if(errorMessage.length>0 ||errorMessage!='' ){
	    		alert(errorMessage);		
	    		event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
	    		}
		}
	}
	
	function ShowSearchSC15minReportTable(executeResult){
		var OndutySCNO15minCount=0,OndutySC15minCount=0,OndutySCSumCount=0,OffdutySCNO15minCount=0,OffdutySC15minCount=0,OffdutySCSumCount=0;
		var	tableContents='';
		for(var i=0;i<executeResult.length;i++){
			tableContents += '<tr><td>'+executeResult[i]["CostId"]+'</td>'+
			'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["OndutySCNO15min"]+'</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["OndutySC15min"]+'</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["OndutySCSum"]+'</td>';
			   if(executeResult[i]["OndutySCSum"]==0 || executeResult[i]["OndutySC15min"]==0)			
				    tableContents += '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+ 0+'</td>';
			   else
				   tableContents += '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+ Math.round(executeResult[i]["OndutySC15min"]/executeResult[i]["OndutySCSum"]* 10000)/ 100.00+"%"+'</td>';
			   
			   tableContents +=   
				   '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+ executeResult[i]["OffdutySCNO15min"] + '</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + executeResult[i]["OffdutySC15min"] + '</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + executeResult[i]["OffdutySCSum"] + '</td>';
				if(executeResult[i]["OffdutySCSum"]==0 || executeResult[i]["OffdutySC15min"]==0)	
					 tableContents +='<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+ 0 +'</td></tr>';
				else
					 tableContents +='<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+ Math.round(executeResult[i]["OffdutySC15min"]/executeResult[i]["OffdutySCSum"]* 10000)/ 100.00+"%" +'</td></tr>';
				
				OndutySCNO15minCount = Number(OndutySCNO15minCount) + Number(executeResult[i]["OndutySCNO15min"]);
				OndutySC15minCount = Number(OndutySC15minCount) + Number(executeResult[i]["OndutySC15min"]);
				OndutySCSumCount = Number(OndutySCSumCount) + Number(executeResult[i]["OndutySCSum"]),
				OffdutySCNO15minCount = Number(OffdutySCNO15minCount) + Number(executeResult[i]["OffdutySCNO15min"]),
				OffdutySC15minCount = Number(OffdutySC15minCount) + Number(executeResult[i]["OffdutySC15min"]),
				OffdutySCSumCount = Number(OffdutySCSumCount) + Number(executeResult[i]["OffdutySCSum"]);
				
		}	
		  tableContents +='<tr><td>'+ '合计' + '</td>' 
				   				 + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + OndutySCNO15minCount + '</td>'
				                + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + OndutySC15minCount + ' </td>' 
				                + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + OndutySCSumCount + ' </td>';
				          if(OndutySC15minCount==0 || OndutySCSumCount==0)	
				        	  tableContents += '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + 0 + ' </td>';
				          else
				        	  tableContents += '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + Math.round(OndutySC15minCount/OndutySCSumCount* 10000)/ 100.00+"%" + ' </td>';
				                
				          tableContents +=  '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + OffdutySCNO15minCount + ' </td>'
				                + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + OffdutySC15minCount + ' </td>'
				                + '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + OffdutySCSumCount + ' </td>'; 	

                         if(OffdutySC15minCount==0 || OffdutySCSumCount==0)
                        	 tableContents += '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + 0 + ' </td></tr>';
                         else
                        	 tableContents += '<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">' + Math.round(OffdutySC15minCount/OffdutySCSumCount* 10000)/ 100.00+"%" + ' </td></tr>';
		  
		$('#SwipeCard15minTable tbody').append(tableContents);
		
		
	}
	
});

function SaveExcel(name){
	startDate = $("#startDate").val();
	endDate = $("#endDate").val();
	var filename='';
	if(startDate==endDate)
		filename=name+ new Date(startDate).getFullYear()
		+ (new Date(startDate).getMonth()+1)
		+ new Date(startDate).getDate();
	else
		filename = name+ new Date(startDate).getFullYear()
		+ (new Date(startDate).getMonth()+1)
		+ new Date(startDate).getDate() + "-"
		+ new Date(endDate).getFullYear()
		+ (new Date(endDate).getMonth()+1)
		+ new Date(endDate).getDate();
	$('#SwipeCard15minTable').tableExport({type:'excel'},filename);
};