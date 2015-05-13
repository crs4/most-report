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
 * The Class DvQuantity.
 */
public class DvQuantity extends EhrDatatype{
	
	/** The Constant TAG. */
	private static final String TAG = "DvQuantity";
	
	/** The magnitude. */
	private double magnitude = 0;
	
	/** The units. */
	private String units = "????";
	
	/** The precision. */
	private int precision = 0;
	
	/** The min. */
	private Integer min = null;
	
	/** The max. */
	private Integer max = null;
	
	
	
	/**
	 * Instantiates a new dv quantity.
	 *
	 * @param path the path
	 * @param attributes the attributes
	 */
	public DvQuantity(String path, JSONObject attributes) {
		
		Log.d(TAG, "ISTANZIATA DV QUANTITY con path:" + path);
		this.setPath(path);
		
		try {
			setAttributes(attributes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatype#setAttributes(org.json.JSONObject)
	 */
	@Override
	protected void setAttributes(JSONObject attributes) throws JSONException
	{
		Log.d(TAG, String.format("Setting datatype attributes: %s" , attributes));
		
		/*
		 *  "attributes" : {
	                  			"unit_of_measure": "mm[Hg]",
	   				  			"precision" : 2,
	   			       			"range" : {"min" : 10 , "max" :180 }
	                  			}
		 */
		
		 
			this.setUnits(attributes.getString("unit_of_measure"));
			Log.d(TAG,"Set unity:" + this.units);
			Log.d(TAG,String.format("Calling get unit from %s return: %s" , this, this.getUnits()));
			this.precision = attributes.getInt("precision");
			if (attributes.has("range"))
					{
				       if(attributes.getJSONObject("range").has("min"))
				       {
				    	   this.setMin(attributes.getJSONObject("range").getInt("min"));
				       }
				       
				       if(attributes.getJSONObject("range").has("max"))
				       {
				    	   this.setMax(attributes.getJSONObject("range").getInt("max"));
				       }
				
					}
			
	}

	/**
	 * Checks if is valid.
	 *
	 * @param value the value
	 * @return true, if is valid
	 */
	public boolean isValid(double value)
	{
		return ((this.min==null || value>=this.min) && (this.max==null || value<=this.max)); // && getPrecision(value)<=this.precision);
	}
	
	/**
	 * Gets the precision.
	 *
	 * @param value the value
	 * @return the precision
	 */
	private int getPrecision(double value)
	{
		String sval = String.valueOf(value);
		
		int decimalIndexOf = sval.indexOf(".");
		if (decimalIndexOf<0 || (value-(int)value == 0)) return 0;
		else
		{
			return sval.substring(decimalIndexOf).length()-1;
		}
	}
	
	/**
	 * Gets the validity message.
	 *
	 * @param value the value
	 * @return the validity message
	 */
	public String getValidityMessage(double value)
	{  
		if (this.min!=null && value<this.min)
			return String.format("Value too low: %s The MIN value must be %s", value, this.min);
		
		else if (this.max!=null && value>this.max)
			return String.format("Value too high: %s The MAX value must be %s", value, this.max);
		
		else if (getPrecision(value)>this.precision)
		{
			return String.format("Too many decimal digits for %s:%s (max:%s)", value, getPrecision(value), this.precision);
		}
		else return String.format("Ok %s" , this.getConstraintsInfo());
	}
	
	/**
	 * Gets the constraints info.
	 *
	 * @return the constraints info
	 */
	public String getConstraintsInfo()
	{   
		if (this.min!=null && this.max!=null) return String.format("(Valid Range: [%s, %s] - Max Precision: %s)" , this.min, this.max, this.precision);
		else if (this.min==null && this.max==null) return  String.format("Max Precision: %s", this.precision);
		else if (this.min!=null) return  String.format("(Min Value: %s - Max Precision: %s)" , this.min, this.precision);
		else return String.format("(Max Value: %s - Max Precision: %s)" , this.max, this.precision);
		
	}
	
	/**
	 * Gets the magnitude.
	 *
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return this.magnitude;
	}

	/**
	 * Sets the magnitude.
	 *
	 * @param magnitude the new magnitude
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	public void setMagnitude(double magnitude) throws InvalidDatatypeException  {
		if (this.isValid(magnitude))
		{  
			Log.d(TAG, "Magnitude to save:" + magnitude);
			double factor = Math.pow(10, this.precision);
			this.magnitude = Math.round(magnitude*factor)/factor;
			Log.d(TAG, "Magnitude to save after:" +this.magnitude  + " [" + this + "]");
			//
			if (this.datatypeChangeListener!=null)
				this.datatypeChangeListener.onEhrDatatypeChanged(this);
		}
		else throw new InvalidDatatypeException(this.getValidityMessage(magnitude));
	}

	/**
	 * Gets the units.
	 *
	 * @return the units
	 */
	public String getUnits() {
		Log.d(TAG,String.format("Calling get unity() from %s return: %s" , this, this.units));
		return this.units;
	}

	/**
	 * Sets the units.
	 *
	 * @param units the new units
	 */
	public void setUnits(String units) {
		this.units = units;
	}

	/**
	 * Gets the max precision.
	 *
	 * @return the max precision
	 */
	public int getMaxPrecision() {
		return this.precision;
	}

	/**
	 * Sets the max precision.
	 *
	 * @param precision the new max precision
	 */
	public void setMaxPrecision(int precision) {
		this.precision = precision;
	}

	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public int getMin() {
		return this.min;
	}

	/**
	 * Sets the min.
	 *
	 * @param min the new min
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public int getMax() {
		return this.max;
	}

	/**
	 * Sets the max.
	 *
	 * @param max the new max
	 */
	public void setMax(int max) {
		this.max = max;
	}

	 
	
	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatype#fromJSON(org.json.JSONObject)
	 */
	@Override
	public void fromJSON(JSONObject content) throws JSONException, InvalidDatatypeException {
		
			if (!content.isNull("magnitude"))
				{
				Log.d(TAG,"VALUE OF MAGNITUDE from json:" + content.getDouble("magnitude"));
					this.setMagnitude(content.getDouble("magnitude"));
				}
		
			if (!content.isNull("units"))
				{
					this.setUnits(content.getString("units"));
					Log.d(TAG,"VALUE OF UNITS from json:" + units);
				}
			
		if (this.datatypeChangeListener!=null)
		{
			Log.d(TAG,"Notifying datatype changes to the widget with current value:" + getMagnitude());
			this.datatypeChangeListener.onEhrDatatypeChanged(this);
		}
		else
			Log.d(TAG,"No Listener for notifying datatype changes to the widget");
			
		
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatype#toJSON()
	 */
	@Override
	public JSONObject toJSON() {
		JSONObject jsonMagnitude = new JSONObject();
		try {
			String value = String.format("{ \"value\": { \"magnitude\" : %s , \"units\":\"%s\"}}", this.magnitude, this.units);
			Log.d(TAG , "To be converted in JSON:" + value);
			Log.d(TAG , "MAGNITUDE To be converted in JSON:" + this.magnitude + " [" + this + "]");
			jsonMagnitude = new JSONObject(value);
			
			return jsonMagnitude;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
