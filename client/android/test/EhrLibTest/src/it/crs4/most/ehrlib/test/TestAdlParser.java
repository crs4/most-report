/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */


package it.crs4.most.ehrlib.test;

import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.parser.AdlParser;
import it.crs4.most.ehrlib.parser.AdlStructure;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class TestAdlParser extends InstrumentationTestCase {
	
	private static final String TAG = "TestAdlParser";
    
	private Context context = null;

	private String content="";

	private String content_wm;
	
	protected void setUp() {
		 Log.d(TAG,"Running testCase");
		 context = getInstrumentation().getTargetContext().getApplicationContext(); 
		 assertNotNull(context);
		 content = WidgetProvider.parseFileToString(context, "blood_pressures/011984cc9bc2460dab67dffd18010871.json");
		 content_wm = WidgetProvider.parseFileToString(context, "blood_pressures/write_mode_schema_blood_pressure_v1.json");
		 assertNotNull(content);
	}
	
	public void testGetItemsContainer()
	{
	
		String systolicPressurePath = "data[at0001]/events[at0006]/data[at0003]/items[at0004]";
		try {
			AdlParser parser = new AdlParser(new JSONObject(content).getJSONObject("archetype_details"));
			AdlStructure adlStructure = parser.getItemsContainer(systolicPressurePath);
			Log.d(TAG, "CARDINALITY: " + adlStructure.count());
			assertEquals(1,adlStructure.count());
			assertNotNull(adlStructure.getStructure(0));
			Log.d(TAG, "STRUCTURE: " + adlStructure.getStructure(0));
			assertEquals(119, adlStructure.getStructure(0).getJSONObject("items").getJSONObject("at0004").getJSONObject("value").getInt("magnitude"));
			Log.d(TAG, "FIRST ITEM: " + adlStructure.getStructure(0));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	public void testGetStructureByPath()
	{
		String systolicPressurePath = "data[at0001]/events[at0006]/data[at0003]/items[at0004]";
		Log.d(TAG, "PATH:" + systolicPressurePath.substring(0, systolicPressurePath.lastIndexOf("/")));
		try {
			AdlParser parser = new AdlParser(new JSONObject(content).getJSONObject("archetype_details"));
			AdlStructure adlStructure = parser.getStructureByPath(systolicPressurePath);
			Log.d(TAG, "CARDINALITY: " + adlStructure.count());
			assertEquals(1,adlStructure.count());
			assertNotNull(adlStructure.getStructure(0));
			Log.d(TAG, "STRUCTURE: " + adlStructure.getStructure(0));
			assertEquals(119, adlStructure.getStructure(0).getJSONObject("value").getInt("magnitude"));
			Log.d(TAG, "FIRST ITEM: " + adlStructure.getStructure(0));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	 
	
	
	public void testuUdateContent()
	{
		String systolicPressurePath = "data[at0001]/events[at0006]/data[at0003]/items[at0004]";
		JSONObject jsonSrc;
		try {
			jsonSrc = new JSONObject(content_wm).getJSONObject("archetype_details");
			Log.d(TAG, "Original JSON:" + jsonSrc);
			
			JSONObject newMagnitude = new JSONObject();
			newMagnitude.put("value", new JSONObject().put("magnitude", 500));
			Log.d(TAG, "New value to replace:" + newMagnitude);
			AdlParser parser = new AdlParser(jsonSrc);
			assertFalse(newMagnitude.toString().equals(parser.getStructureByPath(systolicPressurePath).getOriginalObject().toString()));
			
			parser.replaceContent(systolicPressurePath, 0, newMagnitude);
			Log.d(TAG, "Updated JSON:" + jsonSrc);
			
			assertEquals(newMagnitude.toString(), parser.getStructureByPath(systolicPressurePath).getStructure(0).toString());
			assertTrue(newMagnitude.toString().equals(parser.getStructureByPath(systolicPressurePath).getOriginalObject().toString()));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
