<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<c:url value="/resources/css/icons.css" var="iconsCSS"></c:url>
<c:url value="/resources/css/main.css" var="mainCSS"></c:url>
<c:url value="/resources/css/plugins.css" var="pluginsCSS"></c:url>
<c:url value="/resources/css/bootstrap/bootstrap.min.css" var="bootstrapCSS"></c:url>
<c:url value="/resources/My97DatePicker/WdatePicker.js" var="WdatePicker"></c:url>
<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" var="jqueryJS"></c:url>
<c:url value="/resources/js/Project/QueryEmpStatus.js?version=${resourceVersion}" var="QueryEmpStatusJS"></c:url>
<c:url value="/resources/js/Project/jquery.table2excel.js" var="table2excelJS"></c:url>
<c:url value="/resources/js/Project/AjaxCheckSession.js?version=${resourceVersion}" var="AjaxCheckSessionJS"/> 
<c:url value="/resources/js/Project/Common.Utils.js?version=${resourceVersion}" var="CommonUtilsJS"></c:url>
<c:url value="/resources/js/Project/tableToExcel.js?version=${resourceVersion}" var="tableToExcel" />
<c:url value="/resources/js/Project/testTableExcel.js?version=${resourceVersion}" var="testTableExcel" />

<script src="${WdatePicker }" type="text/javascript"></script>
<script src="${jqueryJS }" type="text/javascript"></script>
<script type="text/javascript" src='${AjaxCheckSessionJS}'></script>
<script src="${table2excelJS }" type="text/javascript"></script>
<script src="${CommonUtilsJS }" type="text/javascript"></script>
<script src="${QueryEmpStatusJS }" type="text/javascript"></script>
<script src="${tableToExcel}" type="text/javascript"></script>
<script src="${testTableExcel}" type="text/javascript"></script>
<link href="${iconsCSS }" rel="stylesheet" rel="stylesheet" />
<link href="${mainCSS }" rel="stylesheet" rel="stylesheet" />
<link href="${pluginsCSS }" rel="stylesheet" rel="stylesheet" />
<link href="${bootstrapCSS }" rel="stylesheet" rel="stylesheet" />
</head>
<body>

	<div id="header" class="header-fixed">
		<div class="navbar">
			<a class="navbar-brand" href="Login.html"> <!-- <i
				class="im-windows8 text-logo-element animated bounceIn"></i> --> <span
				class="text-logo" style="margin-left: 40px">FOX</span><span
				class="text-slogan">LINK</span>
			</a>
			<span style="color:red;font-size:20px;">根據人資規定，所有刷卡記錄只允許查詢近三個月的記錄</span>
		</div>
		<!-- Start .header-inner -->
	</div>
	<div style="position: absolute; top: 55px; margin-left: 10px">
		<div class="panel-body" style="border: 1px solid #e1e3e6;">

			<div class="col-sm-12 col-sm-12"
				style="margin-left: -15px; width: 1000PX">

				<form id="form1" enctype="multipart/form-data">
					員工ID號: <input type="text" id="empno" name="id" autocomplete="off" /> <input
						type="checkbox" id="chk">&nbsp; <span
						class="btn btn-primary fileinput-button" disabled="true"
						id="span1"> <span>導入員工ID號</span> <input type="file"
						disabled="true" id="fu1" name="uploadId" accept="text/plain">
					</span> &nbsp;此次共導入：<span id="sumId">0</span>個ID<br> 費用代碼: <input
						type="text" id="costid" name="costId" autocomplete="off" />&nbsp;<input
						type="checkbox" id="chk1">&nbsp; <span
						class="btn btn-primary fileinput-button" disabled="true"
						id="span2"> <span>導入費用代碼</span> <input type="file"
						disabled="true" id="fu2">
					</span> &nbsp;此次共導入：<span id="sumCostid">0</span>個費用代碼<br> <br>開始日期: <input
						id="dpick1" class="Wdate" type="text" name="OVERTIMEDATE"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'%y-\#{%M-2}-01',maxDate:'#F{$dp.$D(\'dpick2\')}'})" autocomplete="off" />  結束日期:
					<input id="dpick2" class="Wdate" type="text" name="OVERTIMEDATEEnd"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'dpick1\')}'})" autocomplete="off" /> <br><br> <input
						type="button" class="btn btn-primary" id="submit" value="查詢" />
						 <input
						type="button" class="btn btn-primary" id="showAll" value="显示全部" /> 
						<a href="#" class="easyui-linkbutton" id="exportExcel" data-options="iconCls:'icon-search'"></a>
						<input
						type="button" class="btn btn-primary" value="導出" onclick="javascript:Save1('員工加班詳情')" />&nbsp;
					<input type="button" class="btn btn-primary" id="clearTable"
						value="清空" />&nbsp; <br> 此次共查詢出：<span
						id="sumData">0</span>條數據
					<div id="overTimeInfoPagination" align="right"
						style="height: 10; float: right"></div>
				</form>

			</div>

		</div>

		<div class="row"
			style="position: fixed; margin-top: 10px; margin-left: 0px; border: 1px solid #C0C0C0;overflow: auto;height: 500px">
			<table id="OverTimeStatus"
				class="table table-striped" style="height: 200px">
				<thead>
					<tr>
						<th>員工號</th>
						<th>姓名</th>
						<th>部門代碼</th>
						<th>費用代碼</th>
						<th>直間接</th>
						<th>日期</th>
						<th>班別</th>
						<th>加班內容</th>
						<th>加班時數</th>
						<th>加班類型</th>
						<th>加班時段</th>
						<th>助理姓名</th>
						<th>助理工號</th>
						<th>NOTES狀態</th>
						<th>NOTES回饋原因</th>
						<th>NOTES回饋時間</th>
						<th>車間</th>
						<th></th>
					</tr>
				</thead>
				<tbody></tbody>
				
			</table>

		</div>

	</div>



</body>
</html>