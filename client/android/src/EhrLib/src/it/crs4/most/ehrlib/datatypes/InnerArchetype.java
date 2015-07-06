package it.crs4.most.ehrlib.datatypes;

import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

import org.json.JSONException;
import org.json.JSONObject;

public class InnerArchetype extends EhrDatatype {

	private String archetypeClass;
	private WidgetProvider wp;
	
	 

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
