package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.SC15minReportDAO;
import com.foxlink.realtime.model.SC15minReport;

public class SC15minReportService  extends Service<SC15minReport> {
	private static Logger logger=Logger.getLogger(SC15minReportService.class);  
	private SC15minReportDAO sc15minReportDAO;
	public void setSC15minReportDAO(SC15minReportDAO sc15minReportDAO ) {
	      this.sc15minReportDAO = sc15minReportDAO;
	      }
	@Override
	public boolean AddNewRecord(SC15minReport t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(SC15minReport t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SC15minReport> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SC15minReport> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<SC15minReport> FindSearchSC15minReport(String userDataCostId,String costId,String startDate,String endDate) {
		// TODO Auto-generated method stub
		List<SC15minReport> searchDGsubsidy=null;
		try {     
			searchDGsubsidy = sc15minReportDAO.FindSC15minReport(userDataCostId,costId,startDate,endDate);					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Search SC15minReport Record is failed ",e);
		}
		return searchDGsubsidy;
	}
	
	@Override
	public List<SC15minReport> FindRecord(SC15minReport t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<SC15minReport> FindQueryRecord(String userDataCostId, int currentPage, SC15minReport t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<SC15minReport> FindQueryRecords(String userDataCostId, SC15minReport t) {
		// TODO Auto-generated method stub
		return null;
	}


}
