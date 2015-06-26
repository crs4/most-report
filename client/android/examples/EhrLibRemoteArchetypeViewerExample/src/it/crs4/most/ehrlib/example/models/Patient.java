package it.crs4.most.ehrlib.example.models;

public class Patient {
	
	private String uuid = null;
	private String demographicUuid = null;
	private String ehrUuid = null;
	
	
    public Patient(String uuid, String demographicUuid, String ehrUuid)
    {
    	this.uuid = uuid;
    	this.demographicUuid = demographicUuid;
    	this.ehrUuid = ehrUuid;
    }


	public String getUuid() {
		return uuid;
	}


	public String getDemographicUuid() {
		return demographicUuid;
	}


	public String getEhrUuid() {
		return ehrUuid;
	}
}
