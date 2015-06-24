package it.crs4.most.ehrlib.example;
/*
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */



import java.util.HashMap;
import java.util.Map;
import android.provider.Settings.Secure;

import org.json.JSONException;
import org.json.JSONObject;



import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * 
 * curl -X POST -d "client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&grant_type=password&username=YOUR_USERNAME&password=YOUR_PASSWORD" http://localhost:8000/oauth2/access_token/
 * 
 * curl -X POST -d "client_id=1f2138b9c94c388503fb&client_secret=fda712b6456c498c4e826e2942e30175d9a3c682&grant_type=password&username=admin&password=12345" http://localhost:8001/oauth2/access_token/
 *                            1f2138b9c94c388503fb               fda712b6456c498c4e826e2942e30175d9a3c682
 * curl -X POST -d "client_id=d67a0f2868956edece1a&client_secret=29df85c27354579d87f026cb33007f350398a491&grant_type=password&username=admin&password=admin" http://localhost:8001/oauth2/access_token/
 * 
 * curl -X POST -d "client_id=d67a0f2868956edece1a&client_secret=29df85c27354579d87f026cb33007f350398a491&grant_type=pincode&username=admin&pincode=12345" http://localhost:8001/oauth2/access_token/
 * 
 * client id d67a0f2868956edece1a
 * client secret: 29df85c27354579d87f026cb33007f350398a491
 */

public class RemotePyEHRConnector {
	
	private static final String TAG = "REMOTE_CONFIG_READER";

	private RequestQueue rq = null;
	
	String serverIp = null;
	int serverPort = -1;
	Context ctx = null;
	String accessToken = null;
	String deviceID = null;
	private String urlPrefix = "";
	

	public RemotePyEHRConnector(Context ctx, String serverIp, int serverPort)
	{
		this.ctx = ctx;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.deviceID = Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID); 
		this.urlPrefix = "http://" + this.serverIp + ":" + String.valueOf(this.serverPort) + "/";
		this.rq = Volley.newRequestQueue(this.ctx);
	}
	
	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}
	
	
	public void getAccessToken(final String clientId, final String clientSecret, final String username, final String password,  final String taskgroup,
							 Response.Listener<String> listener, Response.ErrorListener errorListener)
	{
		String uri = this.urlPrefix + "oauth2/access_token/";
		Log.d(TAG, "Called getAccessToken() on uri: " + uri);
		Log.d(TAG, "client id: " + clientId);
		Log.d(TAG, "client secret: " + clientSecret);
		Log.d(TAG, "username: " + username);
		Log.d(TAG, "password: " + password);
		Log.d(TAG, "taskgroup:" + taskgroup);
		
		StringRequest postReq = new StringRequest(Request.Method.POST, uri , listener, errorListener)
			{
	 			@Override
	 		    protected Map<String, String> getParams() 
	 		    {  
		            Map<String, String>  params = new HashMap<String, String>();  
		            params.put("client_id", clientId);
	                params.put("client_secret", clientSecret); 
	                params.put("grant_type", "password");
	                params.put("username", username);
	                params.put("password", password);
	                params.put("taskgroup", taskgroup);
		            return params;  
	 		    }
			};

			this.rq.add(postReq);
			Log.d(TAG, "Request added to the queue");
	}
	
	
	
	/**
	 * return the list a medical record of a patient
	 * @param patientId the patient uuid
	 */
	public void getPatientMedicalRecords(String patientId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		String uri = String.format("%smedicalrecords/ehr/%s/?access_token=%s", this.urlPrefix, patientId, accessToken);
		JsonObjectRequest postReq = new JsonObjectRequest(uri, null, listener, errorListener);
		this.rq.add(postReq);	 
	}
	 
	public void getPatientMedicalRecord(String patientId, String medicalRecordId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		String uri = String.format("%smedicalrecords/ehr/%s/records/%s/?access_token=%s", this.urlPrefix, patientId,medicalRecordId, accessToken);
		JsonObjectRequest postReq = new JsonObjectRequest(uri, null, listener, errorListener);
		this.rq.add(postReq);	 
	}
	
	
	// --------------------------------------------------------------------------------------------------------------- //
	// --------------------------------------------------------------------------------------------------------------- //
	
	
	
	public void doTestJsonRequest() 
	{
		//RequestQueue rq = Volley.newRequestQueue(this.ctx);
		
		JsonObjectRequest postReq = new JsonObjectRequest( this.urlPrefix + "accounts/", null, new Response.Listener<JSONObject>(){
		    @Override
		    public void onResponse(JSONObject response) {
		    	Log.d("most_example", "Query Response::" + response);
		    	try {
					Log.d("most_example", "First Account data:" + response.getJSONObject("data").getJSONArray("accounts").get(0));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("most_example", "error:" + e);
				}
		    	
		    }
		}, new Response.ErrorListener() {
		    @Override
		    public void onErrorResponse(VolleyError error) {
		    	Log.d("most_example", "Error ["+error+"]");
	
		    }
		});

	this.rq.add(postReq);
	
	Log.d("most_example", "Click button 2");
	}
	

	/**
	 * return the task groups associated to this specific device (i.e by its internal device id)
	 * Note that the used device must be registered on the remote server
	 */
	public void getTaskgroups(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		String uri = String.format("%steleconsultation/taskgroups/%s/", this.urlPrefix, this.deviceID);
		JsonObjectRequest postReq = new JsonObjectRequest(uri, null, listener, errorListener);
		this.rq.add(postReq);	 
	}
	
	public void getTeleconsultationsByTaskgroup(String taskgroupId, String accessToken, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		String uri = String.format("%steleconsultation/list/?access_token=%s", this.urlPrefix, accessToken);
		JsonObjectRequest postReq = new JsonObjectRequest(uri, null, listener, errorListener);
		this.rq.add(postReq);	 
	}
	
	/**
	 * Retrieve the users associated to the specified TaskGroup ID
	 * @param taskgroupId the id of the taskgroup
	 * @param listener the listener where to receive the Taskgroup user(s)
	 * @param errorListener the listener used for error responses
	 */
	public void getUsersByTaskgroup(String taskgroupId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		String uri = String.format("%steleconsultation/applicants/%s/", this.urlPrefix, taskgroupId);
		JsonObjectRequest postReq = new JsonObjectRequest(uri, null, listener, errorListener);
		this.rq.add(postReq);	 
	}
	

	public void  getAccounts(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		JsonObjectRequest postReq = new JsonObjectRequest( this.urlPrefix + "accounts/?access_token=" + this.accessToken, null, listener, errorListener);
		this.rq.add(postReq);	 
		Log.d("most_example", "getAccountsRequest Sent");
	}


	public void getAccount(int accountId , Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		JsonObjectRequest postReq = new JsonObjectRequest( this.urlPrefix + "accounts/" + String.valueOf(accountId)+"/?access_token=" + this.accessToken, null, listener, errorListener);
		this.rq.add(postReq);
	}

	
	public void  getBuddies(int accountId , Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		JsonObjectRequest postReq = new JsonObjectRequest( this.urlPrefix + "buddies/" + String.valueOf(accountId)+"/?access_token=" + this.accessToken, null, listener, errorListener);
		this.rq.add(postReq);
	}

}