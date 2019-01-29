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

    $("#exportSwipeCardRateBtn").click(function() {
		
		$("#SwipeCardRateTable").table2excel(
				{
					exclude : ".noExl", // 过滤位置的 css 类名
					filename : "A2部门刷卡率統計"
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
	
	$('#searchSwipeCardRateBtn').click(function(){
		SearchSwipeCardRate();		
	});

	
	function SearchSwipeCardRate(){			
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
				url:'../SwipeCardRateReport/SearchSwipeCardRateReport.show',
				data:{costId:costId,startDate:startDate,endDate:endDate},
				error:function(e){
					alert('找不到資料');
				},
				success:function(executeResult){
					$('#SwipeCardRateTable tbody').empty();
											if (executeResult != null && executeResult != "") {
												var errorResponse = executeResult.ErrorMessage;
												if (errorResponse != null) {
													alert(errorResponse);
												} else {													
													if (executeResult.length > 0)
														ShowSearchSwipeCardRateTable(executeResult);
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
	
	function ShowSearchSwipeCardRateTable(executeResult){
		var	tableContents='';
		for(var i=0;i<executeResult.length;i++){
			tableContents += '<tr><td>'+executeResult[i]["CostId"]+'</td>'+
			'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["offDutyORonDutyCount"]+'</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["offDutyANDonDutyCount"]+'</td>'+
					'<td style="mso-number-format:\'\@\';" ng-bind="data.paySerialNo">'+executeResult[i]["overTimeCount"]+'</td>';								
		}	
		 
		  
		$('#SwipeCardRateTable tbody').append(tableContents);
		
		
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
	$('#SwipeCardRateTable').tableExport({type:'excel'},filename);
};