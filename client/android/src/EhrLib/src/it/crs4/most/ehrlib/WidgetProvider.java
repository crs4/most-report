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

// TODO: Auto-generated Javadoc
/**
 * The Class WidgetProvider.
 *
 * @author smonni
 * 
 * A WidgetProvider is a utility class that builds a set of visual and iteractive widgets corresponding to a specific Archetype.
 * The Archetype description is specified by a set of json structures, to be provided to the class constructor. 
 */
public class WidgetProvider {
	
	/** The tag. */
	private static String TAG = "WidgetProvider";
	
	/** The ehr widgets map. */
	private static Map<String,String[]> ehrWidgetsMap = new HashMap<String, String[]>()
	
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	{
        put("DV_QUANTITY", new String[] {"it.crs4.most.ehrlib.datatypes.DvQuantity", "it.crs4.most.ehrlib.widgets.DvQuantityWidget"});
        put("DV_TEXT", new String[] {"it.crs4.most.ehrlib.datatypes.DvText", "it.crs4.most.ehrlib.widgets.DvTextWidget"});
        //put("DV_CODED_TEXT", new String[] {"it.crs4.most.ehrlib.datatypes.DvCodedText", "it.crs4.most.ehrlib.widgets.DvCodedTextWidget"});
        put("DV_CODED_TEXT", new String[] {"it.crs4.most.ehrlib.datatypes.DvCodedText", "it.crs4.most.ehrlib.widgets.DvCodedTextWidget"});
        put("DV_CLUSTER", new String[] {"it.crs4.most.ehrlib.datatypes.DvCluster", "it.crs4.most.ehrlib.widgets.DvClusterWidget"});
    }
};

/** The Constant defaultLayoutParams. */
public static final LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);



// - data map, with the "section" archetype value as key
/** The section widgets map. */
protected Map<String, List<DatatypeWidget<EhrDatatype>>> sectionWidgetsMap;

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


	/**
	 * Setup a Widget provider representing a specific archetype, according to the specified json archetype datatypes, structure and ontology.
	 *
	 * @param context the application context
	 * @param jsonDatatypes - the json description of all datatypes used by this archetype, subdivided in sections
	 * @param jsonOntology - the json ontology (it includes a textual description of each item of the archetype)
	 * @param jsonInstances - the initial json structure of the archetype (optionally including initial values)
	 * @param jsonLayoutSchema (optional, it can be null) the layout schema containing informations about visual rendering (sections, custom widgets, priorities..)
	 * @param language - the ontology language
	 * @throws JSONException the JSON exception
	 * @throws InvalidDatatypeException 
	 */
	public WidgetProvider(Context context, String jsonDatatypes, String jsonOntology, String jsonInstances, String jsonLayoutSchema, String language) throws JSONException, InvalidDatatypeException {
		    
		    this.context = context;
			this.datatypesSchema = new JSONObject(jsonDatatypes);
			this.jsonOntology = jsonOntology;
			this.ontology = getOntology(jsonOntology, language);
			
			if (jsonLayoutSchema!=null)
				this.jsonLayoutSchema = new JSONObject(jsonLayoutSchema);
			
			
			// Archetype structure instance
			this.jsonArchetype = new JSONObject(jsonInstances);
			this.archetypeInstances =  this.jsonArchetype.getJSONObject("archetype_details");
			this.archetypeAdlParser = new AdlParser(this.archetypeInstances);
			
			// build all sections because we must build also widgets referred by clusters
			this.buildSectionWidgetsMap(null);
	}
	
	public Context getContext() {
		return context;
	}

	public JSONObject getOntology()
	{
		return this.ontology;
	}
	
	/**
	 * Update the ontology on all datatype widgets.
	 *
	 * @param lang the language code
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
				w.setOntology(this.ontology);
		}
	}

	/**
	 * Get a widget mapped to a specific openEHR datatype.
	 *
	 * @param context the context where the widget will be rendered (e.g the activity)
	 * @param datatype the name of the datatype
	 * @param path the unique path
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
				return null;
			widgetClassName= classNames[1];
		}

		Log.d(TAG, String.format("Found Form Widget: %s" , widgetClassName));
		Class<?> clazz;
		try {
			clazz = Class.forName(widgetClassName);
			Constructor<?> ctor = clazz.getConstructor(WidgetProvider.class, String.class, String.class, JSONObject.class, int.class);
			Log.d(TAG, String.format("Instancing widget %s for datatype: %s parentIndex: %s" , widgetClassName, datatype,  parentIndex));
			DatatypeWidget<EhrDatatype> widget =(DatatypeWidget<EhrDatatype>) ctor.newInstance(new Object[] {this, title, path, attributes, parentIndex });
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
			for (int i=0;i<sections.length;i++)
				sections[i] = jsonSections.getString(i);
			return sections;
		} catch (JSONException e) {
	        Log.e(TAG, "Error retrieving sections: %s" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Builds a Map of widgets, according to the provided archetype json schema, where each key is an archetype section and the corresponding values are an oredered list of widgets corresponding to the underluying  EHR datatypes.
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
	 * build a view containing all widgets according to the json archetype structure, layout and ontology, All widgets are rendered  in a vertical layout, 
	 * optionally ordered by section and/or item priority (if specified in the layout json schema) 
	 * (This method first  calls the {@link #buildSectionWidgetsMap(String[])} methods on all archetype sections (or on the sections provided by the layout schema , if provided).
	 *
	 * @param index the index
	 * @return the FormContainer containing all widgets, ordered by section and item priority in a vertical layout
	 * @throws InvalidDatatypeException the invalid datatype exception
	 */
	public FormContainer buildFormView(int index) throws InvalidDatatypeException {
		
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
		
		
		String [] sections = null;
		
		if (this.jsonLayoutSchema!=null)
		{
			JSONArray jsonSections;
			try {
				jsonSections = this.jsonLayoutSchema.getJSONArray("sections");
				sections = new String[jsonSections.length()];
				for (int i=0;i<sections.length;i++)
					sections[i] = jsonSections.getString(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		if (sections==null)
			sections = getSections();
		
		
		
		Log.d(TAG, "Generating form....");
		String formTitle = "Unknown Form";
		ArrayList<DatatypeWidget<EhrDatatype>> _allWidgets = new ArrayList<DatatypeWidget<EhrDatatype>>();
		
		try {
			
			//formTitle = String.format("%s [%s]", ontology.getJSONObject(datatypesSchema.getString("title")).getString("text"), index);   
			formTitle = ontology.getJSONObject(datatypesSchema.getString("title")).getString("text"); 

			for (String section : sections)
			{
				List<DatatypeWidget<EhrDatatype>>  sectionWidgets = this.sectionWidgetsMap.get(section);
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
		fc.resetAllWidgets();
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
		return sectionWidgetsMap.get(section);
	}
	/**
	 * Builds a list of widgets from a specific section and item index.
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
						Log.d(TAG, "item data path:" + sectionDatapath);
						// It contains the structure of the "data" item instances of this archetype
						AdlStructure sectionStructure = this.archetypeAdlParser.getItemsContainer(sectionDatapath);
						return buildDatatypeWidgets(sectionStructure, sectionDatatypes, itemIndex);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
			
			for( int i= 0; i < items_structure.names().length(); i++ ) 
			{
				String itemTitle  = items_structure.names().getString(i);  // es: at0004
				//String displayTitle = ontology.getJSONObject(itemTitle).getString("text");
				
				JSONObject itemStructureInfo = datatypes.getJSONObject(itemTitle);
			 	
			 	String itemType =  itemStructureInfo.getString("type"); //  from the datatypes structure
			 	String itemPath =  itemStructureInfo.getString("path"); //  from the datatypes structure
			 	
			 	JSONObject itemAttributes = itemStructureInfo.getJSONObject("attributes");
		
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
			 	
			 	widgets.add( widget );
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
		String [] sections = sectionWidgetsMap.keySet().toArray(new String[0]);
		
		for (String section : sections)
		{
			List<DatatypeWidget<EhrDatatype>> sectionWidgets = sectionWidgetsMap.get(section);
			
			for (DatatypeWidget<EhrDatatype> widget : sectionWidgets)
			{
				if (widget.getDatatype().isCluster()) continue;
									
				String path = widget.getDatatype().getPath();
				Log.d(TAG, "\nPATH TO UPDATE: " + path);
				this.archetypeAdlParser.replaceContent(path, index, widget.getDatatype().toJSON());
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
