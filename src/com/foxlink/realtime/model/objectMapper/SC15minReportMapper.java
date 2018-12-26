package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.SC15minReport;

public class SC15minReportMapper  implements RowMapper<SC15minReport> {
	@Override
	public SC15minReport mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		SC15minReport sc15minReport =new SC15minReport();
		sc15minReport.setCostId(rs.getString(1));
		sc15minReport.setOndutySCSum(rs.getString(2));
		sc15minReport.setOndutySC15min(rs.getString(3));
		sc15minReport.setOndutySCNO15min(rs.getString(4));
		sc15minReport.setOffdutySCSum(rs.getString(5));
		sc15minReport.setOffdutySC15min(rs.getString(6));
		sc15minReport.setOffdutySCNO15min(rs.getString(7));
		return sc15minReport;
	}
}
