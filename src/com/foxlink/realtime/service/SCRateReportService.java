package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.SCRateReportDAO;
import com.foxlink.realtime.model.SCRateReport;

public class SCRateReportService  extends Service<SCRateReport> {
	private static Logger logger=Logger.getLogger(SCRateReportService.class);  
	private SCRateReportDAO scRateReportDAO;
	public void setSCRateReportDAO(SCRateReportDAO scRateReportDAO ) {
	      this.scRateReportDAO = scRateReportDAO;
	      }
	@Override
	public boolean AddNewRecord(SCRateReport t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(SCRateReport t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SCRateReport> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SCRateReport> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<SCRateReport> FindSearchSCRateReport(String userDataCostId,String costId,String startDate,String endDate) {
		// TODO Auto-generated method stub
		List<SCRateReport> searchDGsubsidy=null;
		try {     
			searchDGsubsidy = scRateReportDAO.FindSCRateReport(userDataCostId,costId,startDate,endDate);					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Search SCRateReport Record is failed ",e);
		}
		return searchDGsubsidy;
	}
	
	@Override
	public List<SCRateReport> FindRecord(SCRateReport t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<SCRateReport> FindQueryRecord(String userDataCostId, int currentPage, SCRateReport t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<SCRateReport> FindQueryRecords(String userDataCostId, SCRateReport t) {
		// TODO Auto-generated method stub
		return null;
	}


}
