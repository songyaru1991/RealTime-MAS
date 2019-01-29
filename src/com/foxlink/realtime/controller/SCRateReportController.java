package com.foxlink.realtime.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.foxlink.realtime.model.SCRateReport;
import com.foxlink.realtime.service.SCRateReportService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/SwipeCardRateReport")
public class SCRateReportController {
	
	private static Logger logger=Logger.getLogger(SCRateReportController.class);
	private SCRateReportService scRateReportService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView showMainPage(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.addObject("username", session.getAttribute("username"));
		
	    model.setViewName("index");
	    return model;
	}

	@RequestMapping(value="/ShowSCRateReport",method=RequestMethod.GET)
	public String ShowSwipeCardRate(){
		return "SwipeCardRateReport";
	}
	
	@RequestMapping(value = "/SearchSwipeCardRateReport.show", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SearchSwipeCardRate(HttpSession session,@RequestParam("costId")String costId,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate){
		String jsonResults="";
		try{
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if (userDataCostId != null && userDataCostId != "") {

				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				scRateReportService = (SCRateReportService) context.getBean("SCRateReportService");
				Gson gson = new GsonBuilder().serializeNulls().create();

				List<SCRateReport> list = scRateReportService.FindSearchSCRateReport(userDataCostId, costId,startDate, endDate);
				jsonResults = gson.toJson(list);
			}
			else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢上下班刷卡超15分鐘報表的權限！");
				jsonResults=costIdJson.toString();
			}
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得上下班刷卡超15分鐘報表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
		
}
