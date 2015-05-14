/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.ehrlib.datatypes;

import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/**
 * This class represents a DV_CODED_TEXT item, according to the definition provided by the OpenEHR Data Type Information Model
 */
public class DvText extends EhrDatatype {

	/** The text. */
	private String text = "";
	
	/** The tag. */
	private String TAG = "DvText";
	
	/**
	 * Instantiates a new DV_TEXT item.
	 *
	 * @param path the path
	 * @param attributes the attributes
	 */
	public DvText(String path, JSONObject attributes)
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
		 
		if (!data.isNull("text"))
			this.setText(data.getString("text"));
		
	}

	
	@Override
	public JSONObject toJSON() {
		JSONObject jsonText = new JSONObject();
		try {
			String value = String.format("{\"value\": { \"text\" : \"%s\"}}", this.text);
			Log.d(TAG , "To be converted in JSON:" + value);
			jsonText = new JSONObject(value);
			
			return jsonText;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Gets the current text of this DV_TEXT item
	 *
	 * @returnc the current text of this DV_TEXT item
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of this DV_TEXT item
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	
	
	if (this.datatypeChangeListener!=null)
	{
		Log.d(TAG ,"Notifying datatype changes to the DvTextWidget with current value:" + getText());
		this.datatypeChangeListener.onEhrDatatypeChanged(this);
	}
	else
		Log.d(TAG,"No Listener for notifying datatype changes to the widget");
	}
}
