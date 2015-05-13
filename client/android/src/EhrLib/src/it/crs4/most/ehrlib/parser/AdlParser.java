package it.crs4.most.ehrlib.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


// TODO: Auto-generated Javadoc
/**
 * The Class AdlParser.
 */
public class AdlParser {
	
	
	/** The json data. */
	private JSONObject jsonData;


	/**
	 * Instantiates a new adl parser.
	 *
	 * @param jsonData the json data
	 */
	public AdlParser(JSONObject jsonData)
	{
		this.jsonData = jsonData;
	}
	
	
	/** The Constant TAG. */
	private static final String TAG = "TestAdlParser";

	
	/**
	 * Gets the paths array.
	 *
	 * @param path the path
	 * @return the paths array
	 */
	private String [] getPathsArray(String path)
	{
		ArrayList<String> pathsA = new ArrayList<String>(Arrays.asList(path.split("[\\[\\]/]")));
	
		Iterator<String> iter = pathsA.iterator();
		while(iter.hasNext()){
		    if(iter.next().equalsIgnoreCase(""))
		        iter.remove();
		}
		return  pathsA.toArray(new String[0]);
	}

	/**
	 * retrieve the json substructure (or primitive object) corresponding to the provided path
	 * Note that, if during the path navigation a JSon Array is found, only the first item of this array is considered.
	 *
	 * @param path the path
	 * @param json the json structure where to retrieve the substructure from
	 * @param numLevelsUp the num levels up
	 * @return the json substructure (or primitive object) corresponding to the provided path
	 */
	private Object getObjectByPath(String path, JSONObject json, int numLevelsUp) {
		
		String [] paths = getPathsArray(path);
		return this.getObjectByPath(paths, json, numLevelsUp);
	}
	
	
	/**
	 * retrieve the json substructure (or primitive object) corresponding to the provided path
	 * Note that, if during the path navigation a JSon Array is found, only the first item of this array is considered.
	 *
	 * @param paths the array containing the sequence of elements path
	 * @param json the json structure where to retrieve the substructure from
	 * @param numLevelsUp the num levels up
	 * @return the json substructure (or primitive object) corresponding to the provided path
	 */
	private Object getObjectByPath(String [] paths, JSONObject json, int numLevelsUp)
	{
		Log.d(TAG, "JSON IN getObjectByPath:" + json);
		//Log.d(TAG, "LEVELS UP:" + numLevelsUp);
		
		//for (String s : paths) Log.d(TAG, "Path:" +s);
		
		int lastIndex = paths.length-1 +numLevelsUp;
			
		try {	
		for (int i=0;i<lastIndex;i++)
		{  
			String s = paths[i];
			//Log.d(TAG, "getting:" +s);
			if (s.equals("")) continue;
			//Log.d(TAG, "NEXT OBJECT TO GET:" +json.get(s));
			if (json.get(s) instanceof JSONObject)
			{
				json = json.getJSONObject(s);
			}
			else if(json.get(s) instanceof JSONArray)
			{
				json = json.getJSONArray(s).getJSONObject(0);
				//Log.d(TAG, "JSON ARRAY FOUND:" + json);
			}
		}  // end for
			
		 
		
		//Log.d(TAG, "Returning Object by path:" + json.get(paths[lastIndex]));
		
				return json.get(paths[lastIndex]);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG,"Exception getting object by path: " + e.getMessage());
			}
		return null;
	}
	
	

	
	/**
	 * Get an ItemsContainer including all the instances of an archetype, given the path of one of its items).
	 *
	 * @param path the path of an item
	 * @return the AdlStructure
	 */ 
	public AdlStructure getItemsContainer(String path)
	{
		//e.g: path = data[at0001]/events[at0006]/data[at0003]/items[at0004]
		//Object obj = this.getObjectByPath(path.substring(0, path.lastIndexOf("/")), jsonData);
		Object obj = this.getObjectByPath(path, jsonData, -2);
		return new AdlStructure(obj);
	}
	
	/**
	 * Get the structure corresponding to the specified path.
	 *
	 * @param path the path of an item
	 * @return the AdlStructure
	 */ 
	public AdlStructure getStructureByPath(String path)
	{
		//e.g: path = data[at0001]/events[at0006]/data[at0003]/items[at0004]
		Object obj = this.getObjectByPath(path,jsonData,0);
		return new AdlStructure(obj);
	}
	
	/**
	 * Replace the content of a json structure.
	 *
	 * @param path the path of the json substructure to be replaced
	 * @param index the index of the json instance
	 * @param newContent the json structure containing the new json content
	 * @throws JSONException the JSON exception
	 */
	public void replaceContent(String path, int index, JSONObject newContent) throws JSONException
	{
		//e.g: path = data[at0001]/events[at0006]/data[at0003]/items[at0004]
		//Log.d(TAG, "PATH TO UPDATE:" + path);
		String [] paths = getPathsArray(path);
		
		AdlStructure jsonAdl = new AdlStructure(getObjectByPath(path, jsonData,-1));
		//Log.d(TAG, "updateJsonStructure::::" + jsonAdl);
		JSONObject json = jsonAdl.getStructure(index);
		Log.d(TAG, "STRUCTURE TO UPDATE:" +json.toString(2));
		json.put(paths[paths.length-1], newContent);
		Log.d(TAG, "UPDATED STRUCTURE:" +json.toString(2));
		
		// TODO 04-17 19:54:18.446: D/TestAdlParser(14709): updateJsonStructure:ADL Structure [0:  ]

		// fix the bug if there is no structure yet....example
		// Structure: -> {"value":{"magnitude":120,"units":"mm[Hg]"}} // Ok it is a JSonObject
		// Structure: -> DATA_NODE::DV_QUANTITY (Strung)  // Error: it is just a String!! FIX ME!
		/*
		JSONArray names = newContent.names();
		for (int i=0;i<names.length();i++)
		{
			json.put(names.getString(i), newContent.get(names.getString(i)));
		}
		*/
		
	}

}
