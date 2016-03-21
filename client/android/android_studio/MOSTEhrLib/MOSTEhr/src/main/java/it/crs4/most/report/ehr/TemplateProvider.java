/**
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr;

import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * This class represents a visual Archetypes Template, according to the OpenEHR specifications.
 * A template is an ordered list of {@link WidgetProvider} , each of them contains the layout of a specific archetype of the template- 
 */
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
	
	/**
	 * Creates the template, building all the archetypes specified in the provided json schemas.
	 * @param ctx the application Context
	 * @param templateSchema the json schema of the template
	 * @param archetypeSchemaProvider the archetype schema provider
	 * @param language the default ontology language
	 * @throws JSONException if an error occurred during the parsing of the json schemas
	 */
	public TemplateProvider(Context ctx, String templateSchema, ArchetypeSchemaProvider archetypeSchemaProvider, String language) throws JSONException
	{
		this.ctx = ctx;
		this.templateSchema = new JSONObject(templateSchema);
		
		this.archetypeSchemaProvider = archetypeSchemaProvider;
		this.language = language;
		
		this.buildtemplate();
	}
	
	/**
	 * Get the list of the widget providers of this template, one for each archetype
	 * @return
	 */
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
