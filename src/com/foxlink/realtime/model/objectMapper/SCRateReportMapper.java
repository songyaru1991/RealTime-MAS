package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.SCRateReport;

public class SCRateReportMapper  implements RowMapper<SCRateReport> {
	@Override
	public SCRateReport mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		SCRateReport scRateReport =new SCRateReport();
		scRateReport.setCostId(rs.getString(1));
		scRateReport.setOffDutyORonDutyCount(rs.getString(2));
		scRateReport.setOffDutyANDonDutyCount(rs.getString(3));
		scRateReport.setOverTimeCount(rs.getString(4));
		return scRateReport;
	}
}
