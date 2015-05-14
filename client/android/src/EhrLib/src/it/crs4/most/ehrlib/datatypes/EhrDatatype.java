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



/**
 * This is the base class for all data types included in a generic Archetype, as defined by the OpenEHR ADL structure
 *
 */
public abstract class EhrDatatype {

	
	/** The datatype change listener. */
	protected EhrDatatypeChangeListener datatypeChangeListener;
	
	/** The path of the datatype */
	protected String path;
	
	/**
	 * Sets the Event listener interface for 'change' events. 
	 *
	 * @param datatypeChangeListener the Listener where to notify any content modification of this datatype
	 */
	public void setDatatypeChangeListener(EhrDatatypeChangeListener datatypeChangeListener)
	{
		this.datatypeChangeListener = datatypeChangeListener;
	}
	
	/**
	 * 
	 * @return {@code True} if this datatype is a container for other datatypes, {@code False} otherwise
	 */
	public boolean isCluster()
	{
		return false;
	}
	
	/**
	 * Sets the attributes for this datatype. Generally, different datatypes have different attributes.
	 *
	 * @param attributes the json structure containing all the attributes of this datatype.
	 * @throws JSONException if a malformed json structure was provided
	 */
	protected abstract void setAttributes(JSONObject attributes) throws JSONException;
	
	/**
	 * Sets the path of this datatype.
	 *
	 * @param path the new path
	 */
	protected void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Gets the path of this datatype
	 *
	 * @return the path
	 */
	public String getPath()
	{
		return this.path;
	}
 
	/**
	 * Load the new content of this datatype from a json schema.
	 *
	 * @param data the json structure representing this datatype
	 * @throws JSONException if a malformed json structure was provided
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	public abstract void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException;
	
	/**
	 * Get the json structure representing the current state of this datatype.
	 *
	 * @return the JSON structure representing the current state of this datatype
	 */
	public abstract JSONObject toJSON();
}
