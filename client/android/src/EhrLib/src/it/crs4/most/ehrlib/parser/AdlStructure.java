package it.crs4.most.ehrlib.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class AdlStructure.
 */
public class AdlStructure {
	
	/** The num items. */
	private int numItems = 0;
	
	/** The item. */
	private JSONObject item = null;
	
	/** The items. */
	private JSONArray items = null;
	
	/** The original item. */
	private Object originalItem = null;
	
	/** The cardinality. */
	private StructureCardinality cardinality = StructureCardinality.UNKNOWN;
	
	/**
	 * Instantiates a new adl structure.
	 *
	 * @param item the item
	 */
	public AdlStructure(Object item)
	{
		this.originalItem = item;
		
		 if (item==null)
		 {   
			 this.item = null;
			 this.items = null;
			 this.numItems = 0;
			 this.cardinality = StructureCardinality.UNKNOWN;
		 }
	
			else if (item instanceof JSONObject)
			{
				this.item = (JSONObject) item;
				this.items = null;
				this.numItems = 1;
				this.cardinality = StructureCardinality.UNIQUE;
			}
	 
			else if (item instanceof JSONArray)
			{
				this.item = null;
				this.items = (JSONArray) item;
				this.numItems = this.items.length();
				this.cardinality = StructureCardinality.MULTIPLE;
			}
			else
			{
				this.item = null;
				this.items = null;
				this.numItems = 0;
				this.cardinality = StructureCardinality.UNKNOWN;
			}
	}
	
	/**
	 * Get the amount of instances of this structure.
	 *
	 * @return the int
	 */
	public int count()
	{
		return this.numItems;
	}
	
	/**
	 * Gets the cardinality.
	 *
	 * @return the cardinality
	 */
	public StructureCardinality getCardinality() {
		return this.cardinality;
	}
	
	/**
	 * Get the index-th occurrence of this ADL structure.
	 *
	 * @param index the index
	 * @return the adl structure
	 * @throws JSONException the JSON exception
	 */
	public JSONObject getStructure(int index) throws JSONException
	{
		if (index==0 && this.item!=null) return this.item;
		else
			return this.items.getJSONObject(index);
	
	}
     
	/**
	 * Gets the original object.
	 *
	 * @return the original object
	 */
	public Object getOriginalObject()
	{
	  return this.originalItem;	
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String structure = "NULL";
		try {
			structure = this.getStructure(0).toString();
		} catch (Exception e) {
		}
		
		return String.format("ADL Structure (%s items)] First -> %s ]", this.count() , structure);
		
	}
} 
