/**
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.ehrlib;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import it.crs4.most.ehrlib.datatypes.EhrDatatype;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;
import it.crs4.most.ehrlib.parser.AdlParser;
import it.crs4.most.ehrlib.parser.AdlStructure;
import it.crs4.most.ehrlib.widgets.*;


/**
 * A WidgetProvider lets you build a set of visual and interactive widgets corresponding to a specific OpenEHR Archetype.
 * The Archetype description is specified by a set of json structures (to be provided to the class constructor). 
 */
public class WidgetProvider {
	
	/** The tag. */
	private static String TAG = "WidgetProvider";
	
	/** The ehr widgets map. */
	private static Map<String,String[]> ehrWidgetsMap = new HashMap<String, String[]>()
	
{
	
	private static final long serialVersionUID = 1L;

	{
        put("DV_QUANTITY", new String[] {"it.crs4.most.ehrlib.datatypes.DvQuantity", "it.crs4.most.ehrlib.widgets.DvQuantityWidget"});
        put("DV_TEXT", new String[] {"it.crs4.most.ehrlib.datatypes.DvText", "it.crs4.most.ehrlib.widgets.DvTextWidget"});
        //put("DV_CODED_TEXT", new String[] {"it.crs4.most.ehrlib.datatypes.DvCodedText", "it.crs4.most.ehrlib.widgets.DvCodedTextWidget"});
        put("DV_CODED_TEXT", new String[] {"it.crs4.most.ehrlib.datatypes.DvCodedText", "it.crs4.most.ehrlib.widgets.DvCodedTextWidget"});
        put("DV_BOOLEAN", new String[] {"it.crs4.most.ehrlib.datatypes.DvBoolean", "it.crs4.most.ehrlib.widgets.DvBooleanWidget"});
        put("DV_CLUSTER", new String[] {"it.crs4.most.ehrlib.datatypes.DvCluster", "it.crs4.most.ehrlib.widgets.DvClusterWidget"});
        put("ARCHETYPE", new String[] {"it.crs4.most.ehrlib.datatypes.InnerArchetype", "it.crs4.most.ehrlib.widgets.InnerArchetypeWidget"});
    }
};

/** The Constant defaultLayoutParams. */
public static final LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);



// - data map, with the "section" archetype value as key
/** The section widgets map. */
protected Map<String, List<DatatypeWidget<EhrDatatype>>> sectionWidgetsMap;


//- data map, with the "cluster" archetype value as key
/** The cluster widgets map. */
protected Map<String, List<DatatypeWidget<EhrDatatype>>> clusterWidgetsMap;


// -- widgets
/** The _container. */
protected LinearLayout _container;

/** The _layout. */
protected LinearLayout _layout;

/** The _viewport. */
protected ScrollView   _viewport;

/** The context. */
private Context context;


/** The datatypes schema. */
private JSONObject datatypesSchema;

/** The ontology. */
private JSONObject ontology;

/** The json archetype. */
private JSONObject jsonArchetype;

/** The archetype instances. */
private JSONObject archetypeInstances;
 
/** The archetype adl parser. */
private AdlParser archetypeAdlParser = null;

/** The json layout schema. */
private JSONObject jsonLayoutSchema;

/** The json ontology. */
private String jsonOntology;

/** The title view. */
private TextView titleView;

private List<String> excludeArray = new ArrayList<String>();
private ArchetypeSchemaProvider asp = null;

private String language;

	/**
	 * Setup a Widget provider representing a specific archetype, according to the specified Archetype Schema Provider and archetype class name
	 * 
	 * @param context get application context
	 * @param asp the Archetype Schema Provider
	 * @param archetypeClassName the name of the archetype class to be built (e.g: openEHR-EHR-OBSERVATION.blood_pressure.v1)
	 * @param language the default ontology language
	 * @param jsonExclude the json array containing a list of item ids to be excluded from the archetype 
	 * @throws JSONException
	 * @throws InvalidDatatypeException
	 */
	public WidgetProvider(Context context, ArchetypeSchemaProvider asp, String archetypeClassName, String language , String jsonExclude) throws JSONException, InvalidDatatypeException
	{
		this.asp = asp;
		this.context = context;
		this.language = language;
		this.datatypesSchema = new JSONObject(asp.getDatatypesSchema(archetypeClassName));
		Log.d(TAG, "Loading ontology for archetype class:" + archetypeClassName);
		this.jsonOntology = asp.getOntologySchema(archetypeClassName);
		this.ontology = getOntology(this.jsonOntology, language);
		
		if (asp.getLayoutSchema(archetypeClassName)!=null)
			this.jsonLayoutSchema = new JSONObject(asp.getLayoutSchema(archetypeClassName));
		
		if (jsonExclude!=null)
		{
			this.buildExcludeArray(new JSONArray(jsonExclude));
		}
		
		// Archetype structure instance
		this.jsonArchetype = new JSONObject(asp.getAdlStructureSchema(archetypeClassName));
		this.archetypeInstances =  this.jsonArchetype.getJSONObject("archetype_details");
		this.archetypeAdlParser = new AdlParser(this.archetypeInstances);
		
	}

	public JSONObject getDatatypesSchema() {
		return datatypesSchema;
	}

	/**
	 * Setup a Widget provider representing a specific archetype, according to the specified json datatypes schema , json archetype structure and json ontology.
	 *
	 * @param context the application context
	 * @param jsonDatatypes - the json description of all datatypes used by this archetype, subdivided in sections
	 * @param jsonOntology - the json ontology (it includes a textual description of each item of the archetype)
	 * @param jsonAdlStructure - the initial json structure of the archetype (optionally including initial values)
	 * @param jsonLayoutSchema (optional, it can be null) the layout schema containing informations about visual rendering (sections, custom widgets, priorities..)
	 * @param language -  the default language code (any language code included in the ontology json schema) 
	 * @throws JSONException the JSON exception
	 * @throws InvalidDatatypeException 
	 */
	public WidgetProvider(Context context, String jsonDatatypes, String jsonOntology, String jsonAdlStructure, String jsonLayoutSchema, String language) throws JSONException, InvalidDatatypeException 
	{ 
		this(context,jsonDatatypes,  jsonOntology, jsonAdlStructure, jsonLayoutSchema,language, null);
	}

	/**
	 * Setup a Widget provider representing a specific archetype, according to the specified json datatypes schema , json archetype structure and json ontology.
	 *
	 * @param context the application context
	 * @param jsonDatatypes - the json description of all datatypes used by this archetype, subdivided in sections
	 * @param jsonOntology - the json ontology (it includes a textual description of each item of the archetype)
	 * @param jsonAdlStructure - the initial json structure of the archetype (optionally including initial values)
	 * @param jsonLayoutSchema (optional, it can be null) the layout schema containing informations about visual rendering (sections, custom widgets, priorities..)
	 * @param jsonExclude (optional, it can be null) the list of archetype items (i.e their id , like "at0004") to exclude from the viewer
	 * @param language - the default language code (any language code included in the ontology json schema) 
	 * @throws JSONException - if an error occurred during the parsing of the json schemas
	 * @throws InvalidDatatypeException 
	 */
	public WidgetProvider(Context context, String jsonDatatypes, String jsonOntology, String jsonAdlStructure, String jsonLayoutSchema, String language, String jsonExclude) throws JSONException, InvalidDatatypeException {
		    
		    this.context = context;
			this.datatypesSchema = new JSONObject(jsonDatatypes);
			this.jsonOntology = jsonOntology;
			this.ontology = getOntology(jsonOntology, language);
			
			if (jsonLayoutSchema!=null)
				this.jsonLayoutSchema = new JSONObject(jsonLayoutSchema);
			
			if (jsonExclude!=null)
			{
				this.buildExcludeArray(new JSONArray(jsonExclude));
			}
			
			// Archetype structure instance
			this.jsonArchetype = new JSONObject(jsonAdlStructure);
			this.archetypeInstances =  this.jsonArchetype.getJSONObject("archetype_details");
			this.archetypeAdlParser = new AdlParser(this.archetypeInstances);
			
	}
	
	
	private void buildExcludeArray(JSONArray jsonExclude)  
	{
		for (int i=0;i<jsonExclude.length();i++)
		{
			try {
				excludeArray.add(jsonExclude.getString(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * Get the application context
	 * @return the application context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Get the json schema containing the ontology of this archetype
	 * @return the ontology json schema
	 */
	public JSONObject getOntology()
	{
		return this.ontology;
	}
	
	/**
	 * Update the ontology of all {@link DatatypeWidget} widgets.
	 *
	 * @param lang the language code (ISO 639-1:2002)
	 */
	public void updateOntologyLanguage(String lang)
	{
		this.ontology = getOntology(this.jsonOntology, lang);
		
		this.updateTitleView();
		
		String [] sections = sectionWidgetsMap.keySet().toArray(new String[0]);
		
		for (String section : sections)
		{
			List<DatatypeWidget<EhrDatatype>> sectionWidgets = sectionWidgetsMap.get(section);
			for (DatatypeWidget<EhrDatatype> w : sectionWidgets)
				w.setOntology(this.ontology, lang);
		}
		
		String [] clusters = clusterWidgetsMap.keySet().toArray(new String[0]);
		
		for (String cluster : clusters)
		{
			List<DatatypeWidget<EhrDatatype>> clusterWidgets = clusterWidgetsMap.get(cluster);
			for (DatatypeWidget<EhrDatatype> w : clusterWidgets)
				w.setOntology(this.ontology, lang);
		}
		
	}

	/**
	 * Get a widget mapped to a specific openEHR datatype, or null if no widget was found for the specified datatype
	 *
	 * @param context the context where the widget will be rendered (e.g the activity)
	 * @param datatype the name of the datatype
	 * @param path the unique path of the datatype
	 * @param title the title 
	 * @param attributes the attributes
	 * @param parentIndex the parent index
	 * @return the widget or null if no widget was found for the specified datatype
	 */
	private DatatypeWidget<EhrDatatype> buildDatatypeWidget(String datatype, String path, String title,  JSONObject attributes, int parentIndex) 
	{
		String widgetClassName = null;
		
		if (this.jsonLayoutSchema!=null)
			{
			  try {
				 
				widgetClassName =  this.jsonLayoutSchema.getJSONObject(path).isNull("widget") ? null :  this.jsonLayoutSchema.getJSONObject(path).getString("widget");
			} catch (JSONException e) {
				}
			} 
				
		if (widgetClassName==null)
		{
			String [] classNames = ehrWidgetsMap.get(datatype);
			if (classNames==null)
			{
				Log.e(TAG, "Datatype Widget not found for: " + datatype);
				return null;
			}
				
			widgetClassName= classNames[1];
		}

		Log.d(TAG, String.format("Found Datatype Widget: %s" , widgetClassName));
		Class<?> clazz;
		try {
			clazz = Class.forName(widgetClassName);
			Constructor<?> ctor = clazz.getConstructor(WidgetProvider.class, String.class, String.class, JSONObject.class, int.class);
			Log.d(TAG, String.format("Instancing widget %s for datatype: %s parentIndex: %s" , widgetClassName, datatype,  parentIndex));
			
			// A datatype of type ARCHETYPE MUST BE HANDLED BY ITS OWN WIDGET PROVIDER
			WidgetProvider wp = this;
			
			if (datatype.equals("ARCHETYPE")) {
				Log.d(TAG,"Found Innser archetype!");
				String archetypeName = attributes.getString("archetype_class");
				Log.d(TAG,"Inner Arechetype Class Name:" + archetypeName);
				
				wp = new WidgetProvider(this.context, this.asp, archetypeName , language, null);
			}
			
			DatatypeWidget<EhrDatatype> widget =(DatatypeWidget<EhrDatatype>) ctor.newInstance(new Object[] {wp, title, path, attributes, parentIndex });
			return  widget;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDatatypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	     //String className = datatype.getClass().getName(); // it.crs4.most.ehrlib.datatypes.DvQuantity
	     //Log.d(TAG,"DataType is:" + className); 
		return null;
	}
	
	/**
	 * Get the sections of this archetype structure.
	 *
	 * @return the sections
	 */
	public String [] getSections()
	{
		try {
			JSONArray jsonSections = this.datatypesSchema.getJSONObject("datatypes").names();
			String [] sections = new String[jsonSections.length()];
			for (int i=0;i<sections.length;i++){
				sections[i] = jsonSections.getString(i);
				Log.d(TAG, "Found section:" + sections[i]);  
			}
			return sections;
		} catch (JSONException e) {
	        Log.e(TAG, "Error retrieving sections: %s" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get the clusters of this archetype structure or null if no cluster is found
	 *
	 * @return the clusters
	 */
	private String [] getClusters()
	{
		try {
			JSONArray jsonClusters = this.datatypesSchema.getJSONObject("clusters").names();
			String [] clusters = new String[jsonClusters.length()];
			
			for (int i=0;i<clusters.length;i++){
				clusters[i] = jsonClusters.getString(i);
				Log.d(TAG, "Found cluster:" + clusters[i]);  
			}
			return clusters;
		} catch (JSONException e) {
	        Log.e(TAG, "Error retrieving clusters from json" + this.datatypesSchema);
			e.printStackTrace();
		} catch (NullPointerException e)
		{
			 Log.w(TAG, "No clusters found in the current archetype:" + e.getMessage());
			return null;
		}
		return null;
	}
	
	/**
	 * Builds a Map of widgets, according to the provided archetype json schema, where each key is an archetype section and the corresponding values are an ordered list of widgets corresponding to the underlying  EHR datatypes.
	 *
	 * @param sections the array of sections to build (if null is provided, all sections of the provided archetype are built)
	 * @return the map of widgets
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	private Map<String, List<DatatypeWidget<EhrDatatype>>> buildSectionWidgetsMap(String [] sections) throws InvalidDatatypeException
	{
		if (sections==null)
			sections = getSections();
		
		this.sectionWidgetsMap = new HashMap<String, List<DatatypeWidget<EhrDatatype>>>();
		 for (String section : sections)
		 {
			 List<DatatypeWidget<EhrDatatype>> sectionWidgets = buildSectionWidgets(section, 0);
			 this.sectionWidgetsMap.put(section, sectionWidgets);
		 }
		 
		 return  this.sectionWidgetsMap;
	}
	
	
	/**
	 * Builds a Map of widgets, according to the provided archetype json schema, where each key is an archetype section and the corresponding values are an ordered list of widgets corresponding to the underlying  EHR datatypes.
	 *
	 * @param clusters the array of clusters to build (if null is provided, all clusters of the provided archetype are built)
	 * @return the map of widgets
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	private Map<String, List<DatatypeWidget<EhrDatatype>>> buildClusterWidgetsMap(String [] clusters) throws InvalidDatatypeException
	{
		if (clusters==null)
			clusters = getClusters();
		
		this.clusterWidgetsMap = new HashMap<String, List<DatatypeWidget<EhrDatatype>>>();
		
		// No Clusters
		if (clusters==null)
			return this.clusterWidgetsMap;
		
		for (String cluster : clusters)
		 {
			 List<DatatypeWidget<EhrDatatype>> clusterWidgets = buildClusterWidgets(cluster, 0);
			 this.clusterWidgetsMap.put(cluster, clusterWidgets);
		 }
		 
		 return  this.clusterWidgetsMap;
	}
	
 
	
	/**
	 * build a view containing all widgets according to the json archetype structure, layout and ontology, All widgets are rendered in a vertical layout, 
	 * optionally ordered by section and/or item priority (if specified in the layout json schema) 
	 *
	 * @param index the index of this Form Container
	 * @return the FormContainer containing all widgets, ordered by section and item priority in a vertical layout
	 */
	public FormContainer buildFormView(int index) {
		
		/*
		
		 {
   			"title" : "at0000",
   			
   			"datatypes" : {
		     "at0004" : {
                  "path": "/data[at0001]/events[at002]",
                  "priority" : "0",
                  "type" : "DV_QUANTITY",
                  "attributes" : {
                  			"unit_of_measure": "mm[Hg]",
   				  			"precision" : 2,
   			       			"range" : {"min" : 10 , "max" :180 }
                  			}
                },
                
                 "at0005" : {
                  "path": "/data[at0001]/events[at002]",
                  "priority" : "1",
                  "type" : "DV_QUANTITY",
                  "attributes" : {
                  			"unit_of_measure": "mm[Hg]",
   				  			"precision" : 2,
   			       			"range" : {"min" : 10 , "max" :180 }
                  			}
                }
            }
		 */
		
		// Build cluster widgets, needed for sections including them
		try {
			buildClusterWidgetsMap(null);
		} catch (InvalidDatatypeException e2) {
			Log.e(TAG,String.format("Error building cluster Widget Map:%s", e2.getMessage()));
			e2.printStackTrace();
			
		}
		
		String [] sections = null;
		
		if (this.jsonLayoutSchema!=null)
		{
			JSONArray jsonSections;
			try {
				jsonSections = this.jsonLayoutSchema.getJSONArray("sections");
				sections = new String[jsonSections.length()];
				
				for (int i=0;i<sections.length;i++)
					sections[i] = jsonSections.getString(i);
				
				buildSectionWidgetsMap(sections);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (InvalidDatatypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else
			try {
				buildSectionWidgetsMap(null);
			} catch (InvalidDatatypeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
		if (sections==null)
			sections = getSections();
		
		Log.d(TAG, "Generating form....");
		String formTitle = "Unknown Form";
		ArrayList<DatatypeWidget<EhrDatatype>> _allWidgets = new ArrayList<DatatypeWidget<EhrDatatype>>();
		
		try {
			
			//formTitle = String.format("%s [%s]", ontology.getJSONObject(datatypesSchema.getString("title")).getString("text"), index);   
			formTitle = ontology.getJSONObject(datatypesSchema.getString("title")).getString("text"); 
			Log.d(TAG, "FORM TITLE: " + formTitle);
			
			for (String section : sections)
			{
				List<DatatypeWidget<EhrDatatype>> sectionWidgets  = this.sectionWidgetsMap.get(section);
				Log.d(TAG, String.format("SectionWidgets for section %s: %s" , section, sectionWidgets));  
				
				if (this.jsonLayoutSchema!=null)
				{
					Collections.sort(sectionWidgets, new PriorityComparison(this.jsonLayoutSchema) );	
				}
				
				_allWidgets.addAll(sectionWidgets);
	
			}
			
		} catch (JSONException e) {
			Log.e(TAG, "Error parsing input json string:" + e);
			e.printStackTrace();
		}
		
		
		
        _container = new LinearLayout( context );
        _container.setOrientation( LinearLayout.VERTICAL );
        _container.setLayoutParams( defaultLayoutParams );

        _viewport  = new ScrollView( context );
        _viewport.setLayoutParams(defaultLayoutParams );

        _layout = new LinearLayout( context );
        _layout.setOrientation( LinearLayout.VERTICAL );
        _layout.setLayoutParams( defaultLayoutParams );
        
      
        View titleView = buildTitleView(context, formTitle);
        _layout.addView(titleView);
        for( int i = 0; i < _allWidgets.size(); i++ ) {
        	_layout.addView( ( View ) _allWidgets.get(i).getView() );
        }
       
        _viewport.addView( _layout );
        _container.addView( _viewport );
        
        
		FormContainer fc =  new FormContainer((ViewGroup) _container, _allWidgets, index);
		Log.d(TAG, "Resetting all widgets...");
		//fc.resetAllWidgets();
		return fc;
	}
	
	/**
	 * Builds the title view.
	 *
	 * @param context the context
	 * @param title the title
	 * @return the view
	 */
	private View buildTitleView(Context context, String title) {
		titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextColor(Color.WHITE);
        titleView.setTextSize(22);
        titleView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        titleView.setTypeface(null, Typeface.BOLD); 
        titleView.setGravity(Gravity.CENTER);
		
        return titleView;
	}
	

	/**
	 * Update title view.
	 */
	private void updateTitleView()
	{
		if (titleView!=null)
		{
			 try {
				String title = ontology.getJSONObject(datatypesSchema.getString("title")).getString("text");
				titleView.setText(title);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	
	
	public List<DatatypeWidget<EhrDatatype>> getSectionWidgets(String section, int itemIndex){
		List<DatatypeWidget<EhrDatatype>> widgets = sectionWidgetsMap.get(section);
		if (widgets==null)
		{
			try {
				widgets = buildSectionWidgets(section, itemIndex);
				sectionWidgetsMap.put(section, widgets);
			} catch (InvalidDatatypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return widgets;
	}
	
	public List<DatatypeWidget<EhrDatatype>> getClusterWidgets(String cluster, int itemIndex){
		List<DatatypeWidget<EhrDatatype>> widgets = clusterWidgetsMap.get(cluster);
		if (widgets==null)
		{
			try {
				widgets = buildClusterWidgets(cluster, itemIndex);
				clusterWidgetsMap.put(cluster, widgets);
			} catch (InvalidDatatypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return widgets;
	}
	
	/**
	 * Builds a list of widgets of a specific section and item index.
	 *
	 * @param section the section
	 * @param itemIndex the index of the archetype instance to be created
	 * @return a list of datatype widgets of the "data" substructure
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	private List<DatatypeWidget<EhrDatatype>> buildSectionWidgets(String section, int itemIndex) throws InvalidDatatypeException
	{
					try {
						JSONObject sectionDatatypes = this.datatypesSchema.getJSONObject("datatypes").getJSONObject(section);
						// Retrieve a path of a datatype used for getting the structure of the "section" structure in an archetype instance
						String sectionDatapath = sectionDatatypes.getJSONObject(sectionDatatypes.names().getString(0)).getString("path");
						Log.d(TAG, "Building SECTION:" + section);
						Log.d(TAG, "item data path:" + sectionDatapath);
						// It contains the structure of the "data" item instances of this archetype
						AdlStructure sectionStructure = this.archetypeAdlParser.getItemsContainer(sectionDatapath);
						return buildDatatypeWidgets(sectionStructure, sectionDatatypes, itemIndex);
					} catch (JSONException e) {
						e.printStackTrace();
						Log.e(TAG,"Error building section widgets:" + e.getMessage());
					}
	
					return null;
	}
	
	/**
	 * Builds a list of widgets of a specific cluster and item index.
	 *
	 * @param cluster the cluster
	 * @param itemIndex the index of the archetype instance to be created
	 * @return a list of datatype widgets of the "data" substructure
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	private List<DatatypeWidget<EhrDatatype>> buildClusterWidgets(String cluster, int itemIndex) throws InvalidDatatypeException
	{
					try {
						JSONObject sectionDatatypes = this.datatypesSchema.getJSONObject("clusters").getJSONObject(cluster);
						// Retrieve a path of a datatype used for getting the structure of the "cluster" structure in an archetype instance
						String sectionDatapath = sectionDatatypes.getJSONObject(sectionDatatypes.names().getString(0)).getString("path");
						Log.d(TAG, "Building CLUSTER:" + cluster);
						Log.d(TAG, "item data path:" + sectionDatapath);
						// It contains the structure of the "data" item instances of this archetype
						AdlStructure sectionStructure = this.archetypeAdlParser.getItemsContainer(sectionDatapath);
						return buildDatatypeWidgets(sectionStructure, sectionDatatypes, itemIndex);
					} catch (JSONException e) {
						e.printStackTrace();
						Log.e(TAG,"Error building  cluster widgets:" + e.getMessage());
					}
	
					return null;
	}
	
	
	
	
	/**
	 * Builds a list of widgets from a specific item index, according to the provided schema, ontology and language
	 *
	 * @param dataStructure the data structure
	 * @param datatypes the datatype structures
	 * @param itemIndex the index of the archetype instance to be created
	 * @return a list of datatype widgets of the "data" substructure
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	private List<DatatypeWidget<EhrDatatype>> buildDatatypeWidgets(AdlStructure dataStructure, JSONObject datatypes, int itemIndex) throws InvalidDatatypeException
	{
		Log.d(TAG, "creating datatype widgets...");
		
		ArrayList<DatatypeWidget<EhrDatatype>> widgets = new ArrayList<DatatypeWidget<EhrDatatype>>();
		
		//_map = new HashMap<String, DatatypeWidget>();
		
		
		JSONObject items_structure;
		
		try {
			// item_id and datatype
			
			//items_structure = (JSONObject) datatypes.getJSONArray("items").get(itemIndex);
			items_structure = dataStructure.getStructure(itemIndex).getJSONObject("items");
			Log.d(TAG, "ITEM STRUCTURE NAMES:" +  items_structure.names());
			for( int i= 0; i < items_structure.names().length(); i++ ) 
			{
				String itemTitle  = items_structure.names().getString(i);  // es: at0004
				// Check if this id is in the list of items to be excluded
				if (this.excludeArray.contains(itemTitle))
					continue;
				
				//String displayTitle = ontology.getJSONObject(itemTitle).getString("text");
				
				JSONObject itemStructureInfo = datatypes.getJSONObject(itemTitle);
			 	
			 	String itemType =  itemStructureInfo.getString("type"); //  from the datatypes structure Es: DV_TEXT, ARCHETYPE
			 	String itemPath =  itemStructureInfo.getString("path"); //  from the datatypes structure
			 	
			 	JSONObject itemAttributes = itemStructureInfo.getJSONObject("attributes");
			 	Log.d(TAG, String.format("Trying to building widget for %s with path %s", itemTitle, itemPath));
			 	DatatypeWidget<EhrDatatype> widget = buildDatatypeWidget(itemType, itemPath, itemTitle, itemAttributes, itemIndex);
			 	
			 	// Fill the field of the widget reading the content from the json data, if existing
			 	// The following key exists only if this structure is an item instance, not an item structure
			 	
			 	JSONObject itemContentInfo = items_structure.optJSONObject(itemTitle);
			
			 	
			 	if (itemContentInfo!=null)
			 	{
			 		JSONObject itemContent =  itemContentInfo.optJSONObject("value");   
				 	Log.d(TAG, String.format("ITEM CONTENT FOR ITEM %s: %s" ,i, itemContent));
				 	if (itemContent!=null)
				 	{
				 		widget.getDatatype().fromJSON(itemContent);
				 	}
			 	}
			 	Log.d(TAG, String.format("Adding widget %s for %s" , widget, itemTitle));
			 	widgets.add( widget );
			 	
			 	// Ordering cluster widgets
			 	if (this.jsonLayoutSchema!=null)
				{
					Collections.sort(widgets, new PriorityComparison(this.jsonLayoutSchema) );	
				}
			}
			
		} catch (JSONException e) {
			Log.e(TAG,"Error build a datatype widget: " +e);
			e.printStackTrace();
		} 

		return widgets;
	}
	
	/**
	 * Update the json structure according to the current value of the datatype widgets belonging to this form.
	 *
	 * @param index the form index
	 * @return the updated json structure
	 * @throws JSONException the JSON exception
	 */
	public void updateSectionsJsonContent(int index) throws JSONException
	{
		updateMapJsonContent(sectionWidgetsMap, index);
		updateMapJsonContent(clusterWidgetsMap, index);
	}
	
	/**
	 * Update the json structure according to the current value of the datatype widgets included the specified Map
	 *
	 * @param index the form index
	 * @return the updated json structure
	 * @throws JSONException the JSON exception
	 */
	private void updateMapJsonContent(Map<String, List<DatatypeWidget<EhrDatatype>>> widgetsMap , int index) throws JSONException
	{
		if (widgetsMap==null)
			return;
		
		
		String [] sections = widgetsMap.keySet().toArray(new String[0]);
		
		for (String section : sections)
		{
			List<DatatypeWidget<EhrDatatype>> sectionWidgets = widgetsMap.get(section);
			
			for (DatatypeWidget<EhrDatatype> widget : sectionWidgets)
			{
				if (widget.getDatatype().isCluster()) continue;
				if (widget.getDatatype().isInnerArchetype())
				{
					widget.getWidgetProvider().updateSectionsJsonContent(index);
				}
									
				String path = widget.getDatatype().getPath();
				Log.d(TAG, "\nPATH TO UPDATE: " + path);
				try{
					this.archetypeAdlParser.replaceContent(path, index, widget.getDatatype().toJSON());
				}catch(JSONException je) {
					Log.e(TAG, "Exception replacing content:" + je.getMessage());
					je.printStackTrace();
				}
				
			}
			
		}
	}
	
	/**
	 * get a Json representation of the current state of this archetype.
	 *
	 * @return the JSON object
	 */
	public JSONObject toJson()
	{
		return this.jsonArchetype;
	}
	
	/**
	 * Parses the file to string.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @return the string
	 */
	public static String parseFileToString( Context context, String filename )
	{
		try
		{
			InputStream stream = context.getAssets().open( filename );
			int size = stream.available();
			
			byte[] bytes = new byte[size];
			stream.read(bytes);
			stream.close();
			
			return new String( bytes );
			
		} catch ( IOException e ) {
			Log.i(TAG, "IOException: " + e.getMessage() );
		}
		return null;
	}
	
	/**
	 * Get the ontology .
	 *
	 * @param data the ontology schema (including all available languages)
	 * @param language the selected language
	 * @return a json object containing the ontology of the specified language
	 */
	public static JSONObject getOntology(String data, String language)
	{
		JSONObject ontology;
		try {
			Log.d(TAG,"Reading ontology: " + data);
			ontology = new JSONObject(data);
			Log.d(TAG,"Ontology read ok ");
			return ontology.getJSONObject(language);
		} catch (JSONException e) {
			Log.e(TAG,"Error reading ontology:" + e);
			e.printStackTrace();
		}
		return null;
	}
}