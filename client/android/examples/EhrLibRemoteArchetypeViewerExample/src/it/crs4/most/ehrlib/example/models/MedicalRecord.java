package it.crs4.most.ehrlib.example.models;

import org.json.JSONException;
import org.json.JSONObject;

public class MedicalRecord {
	private Patient patient = null;
	private JSONObject jRecord = null;
	
	/**
	 * @param patient
	 * @param jRecord
	 */
	public MedicalRecord(Patient patient, JSONObject jRecord)
	{
		this.patient = patient;
		this.jRecord = jRecord;
	}

	public Patient getPatient() {
		return patient;
	}

	public JSONObject getMedicalRecord() {
		try {
			return jRecord.getJSONObject("ehr_data");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public String getRecordId()
	{
		try {
			return jRecord.getString("record_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "RECORD_ID NOT SPECIFIED";
	}

}
