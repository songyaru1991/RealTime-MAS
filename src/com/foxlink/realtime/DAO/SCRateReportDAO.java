package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.SCRateReport;
import com.foxlink.realtime.model.objectMapper.SCRateReportMapper;

public class SCRateReportDAO  extends DAO<SCRateReport> {
private static Logger logger=Logger.getLogger(SCRateReportDAO.class);	
	
	public SCRateReportDAO(){
		super();
	}

	@Override
	public boolean AddNewRecord(SCRateReport newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(SCRateReport updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SCRateReport> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<SCRateReport> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	public List<SCRateReport> FindSCRateReport(String userDataCostId,String costId,String startDate,String endDate) {
		// TODO Auto-generated method stub
		List<SCRateReport> searchSCRateReport = null;
		String sSQL = "SELECT nvl(aa.costid,'合计'), sum(nvl(aa.onDutyORoffDutySUM,0)) AS offDutyORonDutyCount,"
				+ "sum(nvl(aa.onDutyANDoffDutySUM,0)) AS offDutyANDonDutyCount, "
				+ "sum(nvl(bb.overTimeCount,0)) AS isOverTimeCount FROM ("
				+ "SELECT a.costId, SUM(a.onDutyORoffDutySUM) AS onDutyORoffDutySUM,"
				+ " SUM(a.onDutyANDoffDutySUM) AS onDutyANDoffDutySUM "
				+ "FROM ("
				+ "SELECT costId, "
				+ "SUM(CASE WHEN ts.swipecardtime IS NOT NULL OR ts.swipecardtime2 IS NOT NULL THEN 1 ELSE 0 END) AS onDutyORoffDutySUM,"
				+ " SUM(CASE WHEN ts.swipecardtime IS NOT NULL AND ts.swipecardtime2 IS NOT NULL THEN 1 ELSE 0 END) AS onDutyANDoffDutySUM "
				+ "FROM csr_employee te, csr_swipecardtime ts "
				+ "WHERE te.id = ts.emp_id "
				+ "AND  to_date(ts.swipe_date,'yyyy-mm-dd') "
				+ "between(to_date(?,'yyyy-mm-dd')) and (to_date(?,'yyyy-mm-dd')) "
				+ "AND te.costid IN (SELECT depid FROM a2_dept) "
				+ "GROUP BY costId, swipe_date) a GROUP BY a.costId) aa "
				+ "LEFT JOIN (SELECT costId, COUNT(*) AS overTimeCount "
				+ "FROM notes_overtime_state WHERE to_date(overtimedate,'yyyy-mm-dd')"
				+ " between(to_date(?,'yyyy-mm-dd')) and (to_date(?,'yyyy-mm-dd')) "
				+ "AND costID IN (SELECT depid FROM a2_dept) AND NOTESSTATES = '1' "
				+ "GROUP BY costId) bb ON aa.costId = bb.costId Where 1=1 ";
		
		try {	
			 List <Object> queryList=new  ArrayList<Object>();  
			
			if(!startDate.equals("") && !endDate.equals("")){
				queryList.add(startDate);
				queryList.add(endDate);
				queryList.add(startDate);
				queryList.add(endDate);
			}			
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSQL += " and aa.costId in(";
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
				sSQL+=" and aa.costId in(";  
				  String [] costIdArray = costId.split(",");
		            for(int i=0;i<costIdArray.length;i++){
		            	sSQL+="'"+costIdArray[i]+"'";
		                if(costIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			}
									
				sSQL += "  group by rollup(aa.costId) ORDER BY aa.costId";
	  
				searchSCRateReport = jdbcTemplate.query(sSQL,  queryList.toArray(), new SCRateReportMapper());			    
		
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search SCRateReport Record is failed",ex);
		}
		return searchSCRateReport;
	}

	@Override
	public List<SCRateReport> FindRecord(String userDataCostId, int currentPage, int totalRecord, SCRateReport t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SCRateReport> FindRecords(SCRateReport t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, SCRateReport t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
