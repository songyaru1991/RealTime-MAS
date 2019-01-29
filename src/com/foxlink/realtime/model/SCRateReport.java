package com.foxlink.realtime.model;

public class SCRateReport {
	
	private String CostId;
	private String offDutyORonDutyCount;
	private String offDutyANDonDutyCount;
	private String overTimeCount;
	
	public String getCostId() {
		return CostId;
	}
	public void setCostId(String costId) {
		CostId = costId;
	}
	public String getOffDutyORonDutyCount() {
		return offDutyORonDutyCount;
	}
	public void setOffDutyORonDutyCount(String offDutyORonDutyCount) {
		this.offDutyORonDutyCount = offDutyORonDutyCount;
	}
	public String getOffDutyANDonDutyCount() {
		return offDutyANDonDutyCount;
	}
	public void setOffDutyANDonDutyCount(String offDutyANDonDutyCount) {
		this.offDutyANDonDutyCount = offDutyANDonDutyCount;
	}
	public String getOverTimeCount() {
		return overTimeCount;
	}
	public void setOverTimeCount(String overTimeCount) {
		this.overTimeCount = overTimeCount;
	}


}
