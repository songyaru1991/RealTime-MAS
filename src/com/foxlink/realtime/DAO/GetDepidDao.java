package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.objectMapper.GetDepidMapper;

public class GetDepidDao extends DAO<GetDepid>{
	private static Logger logger=Logger.getLogger(GetDepidDao.class);	

	@Override
	public boolean AddNewRecord(GetDepid newRecord) {
		return false;
	}

	@Override
	public boolean UpdateRecord(GetDepid updateRecord) {
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		return false;
	}

	@Override
	public List<GetDepid> FindAllRecords() {
		return null;
	}

	@Override
	public List<GetDepid> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		return null;
	}

	
	public List<GetDepid> FindRecord(GetDepid getDepid) {
		List<GetDepid> getDepids=null;
		try {
			String sql="select distinct(depid) from swipe.csr_employee  ";;
			if(getDepid.getCostid().equals("ALL") || getDepid.getCostid().equals("")){								
				getDepids=jdbcTemplate.query(sql,new GetDepidMapper());
			}
			else{
				sql+=" where costid in(";  
				 String [] costIdArray = getDepid.getCostid().split(",");
		            for(int i=0;i<costIdArray.length;i++){
		            	sql+="'"+costIdArray[i].trim()+"'";
		                if(costIdArray.length-1!=i)
		                	sql+=",";
		                else
		                	sql+=") ";				                
		               }
				
				//List<Object> queryList = new ArrayList<Object>();
				//queryList.add(getDepid.getCostid());
				getDepids=jdbcTemplate.query(sql,new GetDepidMapper());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search getDepids Record is failed",ex);
		}
		return getDepids;
	}
	
	public List<String> GetCostId() {
		List<String> getCostIds=null;
		try {	
			String sql="select distinct(costid) from swipe.csr_employee ";
			getCostIds=jdbcTemplate.queryForList(sql,String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search GetCostId Record is failed",ex);
		}
		return getCostIds;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		return 0;
	}

	


	@Override
	public List<GetDepid> FindRecords(GetDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GetDepid> FindRecord(String userDataCostId, int currentPage, int totalRecord, GetDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, GetDepid t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
