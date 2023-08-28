package com.starship.warpdrive;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {

	private String serial;
	private String status;
	private String version;
	private int tProLicenses;
	private int pmLicenses;
	private int webLicenses;
	private int otherLicenses;
	private Date accessDate;
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int gettProLicenses() {
		return tProLicenses;
	}

	public void settProLicenses(int tProLicenses) {
		this.tProLicenses = tProLicenses;
	}

	public int getPmLicenses() {
		return pmLicenses;
	}

	public void setPmLicenses(int pmLicenses) {
		this.pmLicenses = pmLicenses;
	}

	public int getWebLicenses() {
		return webLicenses;
	}

	public void setWebLicenses(int webLicenses) {
		this.webLicenses = webLicenses;
	}

	public int getOtherLicenses() {
		return otherLicenses;
	}

	public void setOtherLicenses(int otherLicenses) {
		this.otherLicenses = otherLicenses;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	@Override
	public String toString() {
		return serial + "," + version + "," + tProLicenses + "," + pmLicenses + "," + webLicenses + "," + otherLicenses
				+ "," + sf.format(accessDate);
	}

}
