package it.crs4.most.ehrlib.datatypes;

import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

import org.json.JSONException;
import org.json.JSONObject;

public class InnerArchetype extends EhrDatatype {

	private String archetypeClass;
	
	 

	public InnerArchetype(String path, JSONObject attributes) {
		this.setPath(path);
		
		try {
			setAttributes(attributes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		// TODO Auto-generated method stub
		return null;
	}

}
