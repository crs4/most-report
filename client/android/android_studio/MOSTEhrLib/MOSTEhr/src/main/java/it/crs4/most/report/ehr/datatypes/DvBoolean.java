/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.datatypes;

import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/**
 * This class represents a DV_BOOLEAN item, according to the definition provided by the OpenEHR Data Type Information Model
 */
public class DvBoolean extends EhrDatatype {

	/** The text. */
	private boolean value = false;
	
	/** The tag. */
	private String TAG = "DvBoolean";
	
	/**
	 * Instantiates a new DV_BOOLEAN item.
	 *
	 * @param path the path
	 * @param attributes the attributes
	 */
	public DvBoolean(String path, JSONObject attributes)
	{
		this.setPath(path);
		try {
			this.setAttributes(attributes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void setAttributes(JSONObject attributes) throws JSONException {
	}

	
	@Override
	public void fromJSON(JSONObject data) throws JSONException,
			InvalidDatatypeException {
		 
		if (!data.isNull("selected"))
			this.setValue(data.getBoolean("selected"));
		
	}

	
	@Override
	public JSONObject toJSON() {
		Log.d(TAG, String.format("DV_BOOLEAN:%s Called toJson  for DV_BOOLEAN:%s" , this, String.valueOf(this.value)));
		JSONObject jsonText = new JSONObject();
		try {
			String convValue = String.format("{\"value\": { \"selected\" : %s}}",this.value);
			Log.d(TAG , "To be converted in JSON:" + convValue);
			jsonText = new JSONObject(convValue);
			
			return jsonText;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Gets the current value of this DV_BOOLEAN item
	 *
	 * @return the current text of this DV_TEXT item
	 */
	public boolean getValue() {
		return this.value;
	}

	/**
	 * Sets the text of this DV_BOOLEAN item
	 *
	 * @param text the new text
	 */
	public void setValue(boolean value) {
		Log.d(TAG, String.format("DV_BOOLEAN:%s Called  setValue for DV_BOOLEAN: %s" , this, String.valueOf(value)));
		this.value = value;
	
	
	if (this.datatypeChangeListener!=null)
	{
		Log.d(TAG ,"Notifying datatype changes to the DvBooleanWidget with current value:" + getValue());
		this.datatypeChangeListener.onEhrDatatypeChanged(this);
	}
	else
		Log.d(TAG,"No Listener for notifying datatype changes to the widget");
	}
}
