/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */


package it.crs4.most.ehrlib.example;

import org.json.JSONException;

import it.crs4.ehrlib.example.R;
import it.crs4.most.ehrlib.FormContainer;
import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.app.Dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




/**
 * The Class ArchetypeViewerActivityExample.
 */
public class ArchetypeViewerActivityExample extends ActionBarActivity{

	/** The Constant LANGUAGE. */
	public static final String LANGUAGE = "es-ar"; // "es-ar"; //
	
	/** The Constant TAG. */
	public static final String TAG = null;
	
	/** The json schema. */
	private static String JSON_SCHEMA_DATATYPES = "ecg/schema_datatypes_ECG_recording_v1.json";
	
	/** The json layout schema. */
	private static String JSON_SCHEMA_LAYOUT = "ecg/schema_layout_ECG_recording_v1.json";
	
	/** The json ontology. */
	private static String JSON_SCHEMA_ONTOLOGY = "ecg/schema_ontology_ECG_recording_v1.json";
	//private static String JSON_INSTANCES =  "reason_for_encounters/011984cc9bc2460dab67dffd18010871.json";
	/** The json instances. */
	private static String JSON_SCHEMA_INSTANCE =  "ecg/schema_adl_void_instance_ECG_recording_v1.json";
    
	/** The spain view. */
	private ImageView spainView = null;
	
    /* (non-Javadoc)
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_blood_pressure);
     
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ArchetypeViewerFragment())
                    .commit();
        }
        
    }
    
   
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blood_pressure, menu);
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_quit) {
        	finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A fragment containing a form for Blood Pressure handling.
     */
    public static class ArchetypeViewerFragment extends Fragment {

    	 /* (non-Javadoc)
 	     * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
 	     */
 	    @Override
         public void onCreateOptionsMenu( Menu menu, MenuInflater inflater) {   
            inflater.inflate(R.menu.action_fragment, menu);
         }
    	 
    	 /* (non-Javadoc)
 	     * @see android.support.v4.app.Fragment#onOptionsItemSelected(android.view.MenuItem)
 	     */
 	    @Override
         public boolean onOptionsItemSelected(MenuItem item) {
                 switch (item.getItemId()) {
                 case R.id.menu_spain:
                         widgetProvider.updateOntologyLanguage("es-ar");
                         return true;
                 case R.id.menu_english:
                    
                     widgetProvider.updateOntologyLanguage("en");
                     return true;


                 }
                 return super.onOptionsItemSelected(item);
         }
    	 
        /* (non-Javadoc)
         * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
         */
        @Override
        public void onCreate (Bundle savedInstanceState)
        {
        	super.onCreate(savedInstanceState);
        	setHasOptionsMenu(true);
        }
        
    	/** The form container. */
	    private FormContainer formContainer = null;
		
		/** The widget provider. */
		private WidgetProvider widgetProvider;
    	
        /**
         * Instantiates a new blood pressure fragment.
         */
        public ArchetypeViewerFragment() {
        }

        /* (non-Javadoc)
         * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_blood_pressure, container, false);
            
            //View rootView = WidgetProvider.getDatatypeWidget(getActivity().getApplicationContext(), "DV_QUANTITY", null).getView();

			try {
				widgetProvider = new WidgetProvider(getActivity(), 
													WidgetProvider.parseFileToString(getActivity().getApplicationContext(),JSON_SCHEMA_DATATYPES),
													WidgetProvider.parseFileToString(getActivity().getApplicationContext(),JSON_SCHEMA_ONTOLOGY), 
													WidgetProvider.parseFileToString(getActivity().getApplicationContext(),JSON_SCHEMA_INSTANCE),
													WidgetProvider.parseFileToString(getActivity().getApplicationContext(),JSON_SCHEMA_LAYOUT),
													LANGUAGE);
				
				this.formContainer = widgetProvider.buildFormView(0);
			
				
			} catch (JSONException e1) {
				Log.e(TAG, "Error reading json data: " + e1);
				e1.printStackTrace();
			} catch (InvalidDatatypeException e) {
				Log.e(TAG, "Error building widgets: " + e);
				e.printStackTrace();
			}
            
           
            
            // Buttons Panel
    		View buttonsPanel = inflater.inflate(R.layout.datatype_form_buttons, null);
    		ViewGroup rootView = formContainer.getLayout();
    		
    		// Actionbar ImageViews
    		
    		
    		
    	    rootView.addView(buttonsPanel);
    	    
            rootView.setBackgroundColor(Color.BLACK);
            setupButtonsListeners(buttonsPanel);
            return rootView;
        }
        
        
        /**
         * Show info dialog.
         *
         * @param content the content
         */
        private void showInfoDialog(String content)
        { 
        	final Dialog dialog = new Dialog(getActivity());
        	dialog.setTitle("Json Adl Structure");
        	dialog.setContentView(R.layout.custom_dialog);
        	TextView dialogText = (TextView) dialog.findViewById(R.id.dialogText);
        	dialogText.setMovementMethod(new ScrollingMovementMethod());
        	dialogText.setText(content);
        	
        	Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        	dialogButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					
				}
			});
        	
        	dialog.show();
        }
        
        /**
         * Sets the up buttons listeners.
         *
         * @param buttonsPanel the new up buttons listeners
         */
        private void setupButtonsListeners(View buttonsPanel)
        {
        	Button butJson = (Button) buttonsPanel.findViewById(R.id.butJson);
        	butJson.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String content;
					try {
						content = widgetProvider.toJson().toString(2);
						showInfoDialog(content);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						showInfoDialog(String.format("Error Parsing Json Content: \n\n %s" , e.getMessage()));
					}
					
				}
			});
        	
        	
        	
        	Button butReset = (Button) buttonsPanel.findViewById(R.id.butReset);
        	butReset.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					formContainer.resetAllWidgets();
					
				}
			});
        	
        	Button butSave = (Button) buttonsPanel.findViewById(R.id.butSave);
        	butSave.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try {
						
						formContainer.submitAllWidgets();
						widgetProvider.updateSectionsJsonContent(0);
						
					} catch (InvalidDatatypeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e(TAG, "Invalid datatype:" + e.getMessage());
						Toast.makeText(getActivity(), "Invalid Input value:" + e.getMessage(), Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e(TAG, "Invalid JSON PARSING:" + e.getMessage());
					}				
				}
			});
        }
    }
}
