package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.SC15minReport;
import com.foxlink.realtime.model.objectMapper.SC15minReportMapper;

public class SC15minReportDAO  extends DAO<SC15minReport> {
private static Logger logger=Logger.getLogger(SC15minReportDAO.class);	
	
	public SC15minReportDAO(){
		super();
	}

	@Override
	public boolean AddNewRecord(SC15minReport newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(SC15minReport updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SC15minReport> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<SC15minReport> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	public List<SC15minReport> FindSC15minReport(String userDataCostId,String costId,String startDate,String endDate) {
		// TODO Auto-generated method stub
		List<SC15minReport> searchSC15minReport = null;
		String sSQL = "SELECT a.costid, SUM(a.ONDUTYSCSUM) AS ONDUTYSCSUM, SUM(a.ONDUTYSC15MIN) AS ONDUTYSC15MIN, "
				+ "SUM(a.ONDUTYSCNO15MIN) AS ONDUTYSCNO15MIN, SUM(a.OFFDUTYSCSUM) AS OFFDUTYSCSUM, "
				+ "SUM(a.OFFDUTYSC15MIN) AS OFFDUTYSC15MIN, SUM(a.OFFDUTYSCNO15MIN) AS OFFDUTYSCNO15MIN "
				+ "FROM ("
				+ "SELECT ce.costid, cs.swipe_date, SUM(CASE "
				+ "WHEN cs.swipecardtime IS NOT NULL THEN 1 ELSE 0 END) AS ONDUTYSCSUM, "
				+ "SUM(CASE WHEN (TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.class_start, 1, 2) || ':' || SUBSTR(c.class_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 1 / 96 - cs.swipecardtime) * 24 * 3600 >= 1 "
				+ "THEN 1 ELSE 0 END) AS ONDUTYSC15MIN, "
				+ "SUM(CASE WHEN (TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.class_start, 1, 2) || ':' || SUBSTR(c.class_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 1 / 96 - cs.swipecardtime) * 24 * 3600 < 1 THEN 1 "
				+ "ELSE 0 END) AS ONDUTYSCNO15MIN, "
				+ "SUM(CASE WHEN cs.swipecardtime2 IS NOT NULL THEN 1 ELSE 0 END) AS OFFDUTYSCSUM, "
				+ "SUM(CASE WHEN ((cs.swipecardtime2 - TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600 >= 1 "
				+ "AND cs.shift = 'D') "
				+ "OR ((cs.swipecardtime2 - TO_DATE(TO_CHAR(TO_DATE(cs.swipe_date, 'yyyy-mm-dd') + 1, 'yyyy-mm-dd') || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600 >= 1 "
				+ ""
				+ "AND cs.shift = 'N') THEN 1 ELSE 0 END) AS OFFDUTYSC15MIN, "
				+ "SUM(CASE WHEN ((cs.swipecardtime2 - TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600 < 1 "
				+ "AND cs.shift = 'D') "
				+ "OR ((cs.swipecardtime2 - TO_DATE(TO_CHAR(TO_DATE(cs.swipe_date, 'yyyy-mm-dd') + 1, 'yyyy-mm-dd') || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600 < 1 "
				+ "AND cs.shift = 'N') THEN 1 ELSE 0 END) AS OFFDUTYSCNO15MIN "
				+ "FROM swipe.csr_swipecardtime cs, swipe.csr_employee ce, swipe.classno c "
				+ "WHERE ce.id = cs.emp_id "
				+ "AND cs.class_no = c.class_no ";
		
		try {	
			 List <Object> queryList=new  ArrayList<Object>();  
			
			if(!startDate.equals("") && !endDate.equals("")){
				sSQL+=" and cs.swipe_date >= ? and cs.swipe_date <= ?";  
				queryList.add(startDate);
				queryList.add(endDate);
			}			
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSQL += " and ce.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sSQL += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sSQL += ",";
						else
							sSQL += ") ";
					}
				}
			}else{
				sSQL += " and ce.costId in('')";
			}			
			
			if(!costId.equals("")){
				sSQL+=" and ce.costId in(";  
				  String [] costIdArray = costId.split(",");
		            for(int i=0;i<costIdArray.length;i++){
		            	sSQL+="'"+costIdArray[i]+"'";
		                if(costIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			}
									
				sSQL += "GROUP BY ce.costid, swipe_date) A GROUP BY a.costid";
	  
				searchSC15minReport = jdbcTemplate.query(sSQL,  queryList.toArray(), new SC15minReportMapper());			    
		
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search SC15minReport Record is failed",ex);
		}
		return searchSC15minReport;
	}

	@Override
	public List<SC15minReport> FindRecord(String userDataCostId, int currentPage, int totalRecord, SC15minReport t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SC15minReport> FindRecords(SC15minReport t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, SC15minReport t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
