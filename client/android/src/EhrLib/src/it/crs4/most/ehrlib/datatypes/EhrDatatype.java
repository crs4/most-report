package it.crs4.most.ehrlib.datatypes;

import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

import org.json.JSONException;
import org.json.JSONObject;


// TODO: Auto-generated Javadoc
/**
 * Defines a generic openEHR datatype.
 *
 * @author smonni
 */
public abstract class EhrDatatype {

	
	/** The datatype change listener. */
	protected EhrDatatypeChangeListener datatypeChangeListener;
	
	/** The path. */
	protected String path;
	
	/**
	 * Sets the datatype change listener.
	 *
	 * @param datatypeChangeListener the new datatype change listener
	 */
	public void setDatatypeChangeListener(EhrDatatypeChangeListener datatypeChangeListener)
	{
		this.datatypeChangeListener = datatypeChangeListener;
	}
	
	public boolean isCluster()
	{
		return false;
	}
	
	/**
	 * Sets the attributes.
	 *
	 * @param attributes the new attributes
	 * @throws JSONException the JSON exception
	 */
	protected abstract void setAttributes(JSONObject attributes) throws JSONException;
	
	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	protected void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath()
	{
		return this.path;
	}
 
	/**
	 * From json.
	 *
	 * @param data the data
	 * @throws JSONException the JSON exception
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	public abstract void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException;
	
	/**
	 * To json.
	 *
	 * @return the JSON object
	 */
	public abstract JSONObject toJSON();
}
