package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryForgetCard;
import com.foxlink.realtime.model.objectMapper.QueryFCMapper;

public class CheckFCDao extends DAO<QueryForgetCard> {

	@Override
	public boolean AddNewRecord(QueryForgetCard newRecord) {
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryForgetCard updateRecord) {
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		return false;
	}

	@Override
	public List<QueryForgetCard> FindAllRecords() {
		return null;
	}

	@Override
	public List<QueryForgetCard> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		return null;
	}

	@Override
	public List<QueryForgetCard> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			QueryForgetCard queryForgetCard) {
		String sdate = queryForgetCard.getStartDate();
		String sql = "select id ,name,depid,costid,  isOnWork, fcDate  from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from ("
				+ "select id ,name,depid,costid,isOnWork,'"+ sdate + "' fcDate from swipe.csr_employee "
				+ "where isOnWork = 0 AND id NOT IN (SELECT distinct(emp_id) FROM swipe.csr_swipecardtime st WHERE "
				+ "st.emp_id IN (SELECT id FROM  swipe.csr_employee) and "
				+ "st.swipe_date =? ) ";
				/*+ "select id ,name,depid,costid,  isOnWork,  '"
				+ sdate + "' fcDate  from swipe.csr_employee where 1=1";*/
		List<QueryForgetCard> forgetCards = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			
			queryList.add(sdate);
			
			if ( queryForgetCard.getDepid() != null){			
				sql+=" AND depid in(";  
				 String [] depidArray = queryForgetCard.getDepid().split(",");
		            for(int i=0;i<depidArray.length;i++){
		            	sql+="'"+depidArray[i].trim()+"'";
		                if(depidArray.length-1!=i)
		                	sql+=",";
		                else
		                	sql+=") ";				                
		               }
			}
			
			if ( queryForgetCard.getCostid() != null){				
				sql+=" AND costid in(";  
				 String [] costIdArray = queryForgetCard.getCostid().split(",");
		            for(int i=0;i<costIdArray.length;i++){
		            	sql+="'"+costIdArray[i].trim()+"'";
		                if(costIdArray.length-1!=i)
		                	sql+=",";
		                else
		                	sql+=") ";				                
		               }		            
			}
						
			//queryList.add(queryForgetCard.getStartDate());
			
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sql += ",";
						else
							sql += ") ";
					}
				}
			} else {
				sql += " and costId in('')";
			}
			Page page = new Page(currentPage, totalRecord);
			int endIndex = page.getStartIndex() + page.getPageSize();
			sql += " order by isOnWork,depid,ID) A ) where rnum > " + page.getStartIndex() + " and rnum <= " + endIndex;
			forgetCards = super.jdbcTemplate.query(sql, queryList.toArray(), new QueryFCMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return forgetCards;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		return 0;
	}

	// 获取总记录数
	@Override
	public int getTotalRecords(String userDataCostId, QueryForgetCard queryForgetCard) {
		int totalRecord = -1;
		String sql ="select count(*) from swipe.csr_employee "
				+ "where isOnWork = 0 AND id NOT IN (SELECT distinct(emp_id) FROM swipe.csr_swipecardtime st WHERE "
				+ "st.emp_id IN (SELECT id FROM  swipe.csr_employee) and "
				+ "st.swipe_date =? ) ";
	
		try {
			String sdate = queryForgetCard.getStartDate();
			List<Object> queryList = new ArrayList<Object>();
			queryList.add(sdate);
			
			if ( queryForgetCard.getDepid() != null){			
				sql+=" AND depid in(";  
				 String [] depidArray = queryForgetCard.getDepid().split(",");
		            for(int i=0;i<depidArray.length;i++){
		            	sql+="'"+depidArray[i].trim()+"'";
		                if(depidArray.length-1!=i)
		                	sql+=",";
		                else
		                	sql+=") ";				                
		               }
			}
			
			if ( queryForgetCard.getCostid() != null){				
				sql+=" AND costid in(";  
				 String [] costIdArray = queryForgetCard.getCostid().split(",");
		            for(int i=0;i<costIdArray.length;i++){
		            	sql+="'"+costIdArray[i].trim()+"'";
		                if(costIdArray.length-1!=i)
		                	sql+=",";
		                else
		                	sql+=") ";				                
		               }		            
			}
			
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sql += ",";
						else
							sql += ") ";
					}
				}
			} else {
				sql += " and costId in('')";
			}
			totalRecord = jdbcTemplate.queryForObject(sql, queryList.toArray(), Integer.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return totalRecord;
	}

	// 查詢所有記錄
	public List<QueryForgetCard> FindRecords(String userDataCostId, QueryForgetCard queryForgetCard) {
		String sdate = queryForgetCard.getStartDate();
		String sql = "select id ,name,depid,costid,isOnWork,'"+ sdate + "' fcDate from swipe.csr_employee "
				+ "where isOnWork = 0 AND id NOT IN (SELECT distinct(emp_id) FROM swipe.csr_swipecardtime st WHERE "
				+ "st.emp_id IN (SELECT id FROM  swipe.csr_employee) and "
				+ "st.swipe_date =? ) ";
				/*+ "select id ,name,depid,costid,  isOnWork,  '"
				+ sdate + "' fcDate  from swipe.csr_employee where 1=1";*/
		List<QueryForgetCard> forgetCards = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			
			queryList.add(sdate);
			
			if ( queryForgetCard.getDepid() != null && queryForgetCard.getDepid() != ""){				
				sql+=" AND depid in(";  
				 String [] depidArray = queryForgetCard.getDepid().split(",");
		            for(int i=0;i<depidArray.length;i++){
		            	sql+="'"+depidArray[i].trim()+"'";
		                if(depidArray.length-1!=i)
		                	sql+=",";
		                else
		                	sql+=") ";				                
		               }
			}
			
			if ( queryForgetCard.getCostid() != null && queryForgetCard.getCostid() != ""){
					sql+=" AND costid in(";  
					String [] costIdArray = queryForgetCard.getCostid().split(",");
						for(int i=0;i<costIdArray.length;i++){
							sql+="'"+costIdArray[i].trim()+"'";
							if(costIdArray.length-1!=i)
								sql+=",";
							else
								sql+=") ";				                
				}
			}
						
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sql += ",";
						else
							sql += ") ";
					}
				}
			} else {
				sql += " and costId in('')";
			}
			sql += " order by isOnWork,depid,ID";
			
			forgetCards = jdbcTemplate.query(sql, queryList.toArray(), new QueryFCMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return forgetCards;
	}

	@Override
	public List<QueryForgetCard> FindRecords(QueryForgetCard t) {
		// TODO Auto-generated method stub
		return null;
	}
}
