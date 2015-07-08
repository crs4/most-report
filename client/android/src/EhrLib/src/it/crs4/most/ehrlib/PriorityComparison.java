package it.crs4.most.ehrlib;

import it.crs4.most.ehrlib.datatypes.EhrDatatype;
import it.crs4.most.ehrlib.widgets.DatatypeWidget;

import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Helper class for sorting widgets by priority.
 */
public class PriorityComparison implements Comparator< DatatypeWidget<EhrDatatype>>
{
	
	/** The schema. */
	private JSONObject schema = null;
	
	/**
	 * Instantiates a new priority comparison.
	 *
	 * @param layoutSchema the layout schema
	 */
	public PriorityComparison(JSONObject layoutSchema)
	{
		this.schema = layoutSchema;
	}
	
	
	public int compare(  DatatypeWidget<EhrDatatype>item1, DatatypeWidget<EhrDatatype> item2 ) {
		
		if (this.schema==null || !this.schema.has("items")) return 1;
		else
		{
			String path1 = item1.getDatatype().getPath();
			String path2 = item2.getDatatype().getPath();
			
			try {
				return this.schema.getJSONObject("items").getJSONObject(path1).getInt("priority") > this.schema.getJSONObject("items").getJSONObject(path2).getInt("priority") ? 1: -1;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 1;
			}
		}
	}
}