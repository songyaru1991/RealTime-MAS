	var _menus = {
		"menus" : [ {
			"menuid" : "1",
			"icon" : "icon-sys",
			"menuname" : "加班單管理",
			"menus" : [ {
				"menuname" : "加班單審核",
				"icon" : "icon-nav",
				"url" : "Overtime/OT.show"
			},/*{
				"menuname" : "加班單-富強線長",
				"icon" : "icon-nav",
				"url" : "Overtime/OTByLL.show"
			},*/{
				"menuname" : "員工加班詳情",
				"icon" : "icon-nav",
				"url" : "ShowCheckOverTimeStatus"
			}, {
				"menuname" : "上下班刷卡記錄查詢",
				"icon" : "icon-nav",
				"url" : "ShowCheckSwipeCardRecords"
			}, {
				"menuname" : "上下班刷卡超15分鐘",
				"icon" : "icon-nav",
				"url" : "ShowCheckOverTime15min"
			},{
				"menuname" : "忘卡人员查询",
				"icon" : "icon-nav",
				"url" : "ShowCheckForgetCard"
			},{
				"menuname" : "班别信息查询",
				"icon" : "icon-nav",
				"url" : "ShowCheckShiftStatus"
			},{
				"menuname" : "原始刷卡記錄查詢",
				"icon" : "icon-nav",
				"url" : "RawRecord/ShowRawRecord"
			},{
				"menuname" : "崗位津貼查詢",
				"icon" : "icon-nav",
				"url" : "DGsubsidy/ShowDGsubsidy"
			},{
				"menuname" : "員工職位維護",
				"icon" : "icon-nav",
				"url" : "JobTitle/ShowAllJobTitle"
			},{
				"menuname" : "線別維護",
				"icon" : "icon-nav",
				"url" : "LineMapping/ShowAllLineMapping"
			}]
		}, {
			"menuid" : "2",
			"icon" : "icon-sys",
			"menuname" : "報表管理",
			"menus" : [ {
				"menuname" : "刷卡超15分鐘&未超15分鐘報表",
				"icon" : "icon-nav",
				"url" : "SC15minReport/ShowSC15minReport"
			},
			{
				"menuname" : "刷卡人數統計報表",
				"icon" : "icon-nav",
				"url" : "SwipeCardRateReport/ShowSCRateReport"
			}]
		},
		{
			"menuid" : "2",
			"icon" : "icon-sys",
			"menuname" : "系統信息管理",
			"menus" : [ {
				"menuname" : "助理信息管理",
				"icon" : "icon-nav",
				"url" : "Assistant/ShowAllAssistant"
			}, {
				"menuname" : "賬號管理",
				"icon" : "icon-nav",
				"url" : "Account/ShowAllAccount"
			}, {
				"menuname" : "車間管理",
				"icon" : "icon-nav",
				"url" : "WorkShop/ShowAllWorkShop"
			}]
		} ]
	};

	$(function() {

		$('#loginOut').click(function() {
			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
				if (r) {
					location.href = 'Login?logout';
				}
			});

		})

	});
	
	$(document).ajaxComplete(function(event,obj,settings){
        if (obj.responseText == 'timeout') { //超时标识
        	alert("session timeOut!!!!");
        	top.location.href='/Login'; //跳转到登录页面
        }
    });