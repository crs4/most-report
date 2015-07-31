/**
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.ehrlib;

import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class TemplateProvider {

	private Context ctx;
	private JSONObject templateSchema;
	private ArchetypeSchemaProvider archetypeSchemaProvider;
	private List<WidgetProvider> wproviders; 
	private String language;
	 
	
	private static final String TAG = "TemplateProvider";
	private String name;
	
	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	private String id;
	
	public TemplateProvider(Context ctx, String templateSchema, ArchetypeSchemaProvider archetypeSchemaProvider, String language) throws JSONException
	{
		this.ctx = ctx;
		this.templateSchema = new JSONObject(templateSchema);
		
		this.archetypeSchemaProvider = archetypeSchemaProvider;
		this.language = language;
		
		this.buildtemplate();
	}
	
	public List<WidgetProvider> getWidgetProviders()
	{
		return this.wproviders;
	}
	
	private void buildtemplate()
	{
		try {
			
			this.id = this.templateSchema.getString("id");
			this.name = this.templateSchema.getString("name");
			
			JSONArray archetype_classes = this.templateSchema.getJSONArray("definition");
			
			this.wproviders = new ArrayList<WidgetProvider>();
			
			for (int i=0;i < archetype_classes.length();i++) {
				String archetypeClass =  archetype_classes.getJSONObject(i).getString("archetype_class");
				String jsonExclude = archetype_classes.getJSONObject(i).getJSONArray("exclude").toString();
				try {
					WidgetProvider wp = new WidgetProvider(ctx, archetypeSchemaProvider, archetypeClass, language, jsonExclude);
					
					this.wproviders.add(wp);
					
				} catch (InvalidDatatypeException e) {
					Log.e(TAG, "InvalidDatatypeException:" + e.getMessage());
					e.printStackTrace();
					continue;
				}
			}
		} catch (JSONException e) {
			Log.e(TAG, "JSON Exception:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
