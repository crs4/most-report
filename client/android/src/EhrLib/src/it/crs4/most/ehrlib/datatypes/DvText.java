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

// TODO: Auto-generated Javadoc
/**
 * The Class DvText.
 */
public class DvText extends EhrDatatype {

	/** The text. */
	private String text = "";
	
	/** The tag. */
	private String TAG = "DvText";
	
	/**
	 * Instantiates a new dv text.
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
	
	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatype#setAttributes(org.json.JSONObject)
	 */
	@Override
	protected void setAttributes(JSONObject attributes) throws JSONException {
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatype#fromJSON(org.json.JSONObject)
	 */
	@Override
	public void fromJSON(JSONObject data) throws JSONException,
			InvalidDatatypeException {
		 
		if (!data.isNull("text"))
			this.setText(data.getString("text"));
		
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatype#toJSON()
	 */
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
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
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
