package it.crs4.most.report.ehr.datatypes;

import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents an Archetype item included in another one.
 */
public class InnerArchetype extends EhrDatatype {

	private String archetypeClass;
	private WidgetProvider wp;
	
	 
    /**
     * Instantiates a new Archetype item.
     * @param wp the Widget Provider of the archetype
     * @param path the absolute path of the archetype inside the json structure
     * @param attributes
     */
	public InnerArchetype(WidgetProvider wp, String path, JSONObject attributes) {
		this.wp = wp;
		this.setPath(path);
		
		
		try {
			setAttributes(attributes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WidgetProvider getWidgetProvider() {
		return wp;
	}

	@Override
	protected void setAttributes(JSONObject attributes) throws JSONException {
		this.archetypeClass = attributes.getString("archetype_class");
	}

	/**
	 * 
	 * @return the name of the archetype class
	 */
	public String getArchetypeClass()
	{
		return this.archetypeClass;
	}
	
	@Override
	public void fromJSON(JSONObject data) throws JSONException,
			InvalidDatatypeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject toJSON() {
	 
		return this.wp.toJson();
	}
	
	/**
	 * 
	 * @return {@code True} if this datatype is an archetype itself  {@code False} otherwise
	 */
	@Override
	public boolean isInnerArchetype()
	{
		return true;
	}

}
