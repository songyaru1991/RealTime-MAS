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

import com.foxlink.realtime.model.SC15minReport;
import com.foxlink.realtime.service.SC15minReportService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/SwipeCard15min")
public class SC15minReportController {
	
	private static Logger logger=Logger.getLogger(SC15minReportController.class);
	private SC15minReportService sc15minReportService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView showMainPage(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.addObject("username", session.getAttribute("username"));
		
	    model.setViewName("index");
	    return model;
	}

	@RequestMapping(value="/ShowSwipeCard15min",method=RequestMethod.GET)
	public String ShowSwipeCard15min(){
		return "SwipeCard15minReport";
	}
	
	@RequestMapping(value = "/SearchSwipeCard15min.show", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SearchSwipeCard15min(HttpSession session,@RequestParam("costId")String costId,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate){
		String jsonResults="";
		try{
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if (userDataCostId != null && userDataCostId != "") {

				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				sc15minReportService = (SC15minReportService) context.getBean("SC15minReportService");
				Gson gson = new GsonBuilder().serializeNulls().create();

				List<SC15minReport> list = sc15minReportService.FindSearchSC15minReport(userDataCostId, costId,startDate, endDate);
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
