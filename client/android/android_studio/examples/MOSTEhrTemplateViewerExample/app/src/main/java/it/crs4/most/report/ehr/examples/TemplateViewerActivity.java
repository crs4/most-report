/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.examples;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import it.crs4.most.report.ehr.ArchetypeFragment;
import it.crs4.most.report.ehr.ArchetypeSchemaProvider;
import it.crs4.most.report.ehr.TemplateProvider;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class TemplateViewerActivity extends ActionBarActivity {
    private final String LANGUAGE = "en";
	private TemplateProvider tp = null;
	private List<ArchetypeFragment> archetypeFragments;
	private static final String TAG = "TemplateViewerActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			ArchetypeSchemaProvider asp = new ArchetypeSchemaProvider(getApplicationContext(), "archetypes.properties", "archetypes");
			this.tp = new TemplateProvider(getApplicationContext(),WidgetProvider.parseFileToString(getApplicationContext(), "ecg_bp_template.json"), asp, LANGUAGE);
		    this.buildArchetypeFragments();
		    this.setupButtonsListener();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setupButtonsListener()
	{
		Button butSave =  (Button) findViewById(R.id.butSave);
		butSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitTemplate();
				
			}
		});
		
		Button butReset =  (Button) findViewById(R.id.butReset);
		butReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resetTemplate();
				
			}
		});
	}
	
	private void updateOntologies(String lang)
	{
		for (WidgetProvider wp : this.tp.getWidgetProviders())
		{
			wp.updateOntologyLanguage(lang);  
		}
	}
	
	private void resetTemplate()
	{
		for (ArchetypeFragment af : this.archetypeFragments)
		{
		 af.getFormContainer().resetAllWidgets();	 
		}
	}
	
	private void submitTemplate()
	{
		boolean error = false;
		for (ArchetypeFragment af : this.archetypeFragments)
		{
			try {
				af.getFormContainer().submitAllWidgets();
			} catch (InvalidDatatypeException e) {
				Log.e(TAG, "Error submitting forms:" + e.getMessage());
				e.printStackTrace();
				Toast.makeText(this, "Error Validating Template:" + e.getMessage(), Toast.LENGTH_LONG).show();
				error=true;
			}
		}
		
		if (!error)
			Toast.makeText(this, "Template content successfully saved." , Toast.LENGTH_LONG).show();
		else
			Toast.makeText(this, "Template content was not successfully saved." , Toast.LENGTH_LONG).show();
	}
	
	private void buildArchetypeFragments()
	{
		this.archetypeFragments = new ArrayList<ArchetypeFragment>();
		
		List<WidgetProvider> wps = this.tp.getWidgetProviders();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		for (WidgetProvider wp : wps )
		{ 
			  ArchetypeFragment af = new ArchetypeFragment(wp);
			  this.archetypeFragments.add(af);
		      ft.add(R.id.container, af);
		}
		
	    ft.commit();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_template, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch (item.getItemId()) {
         case R.id.menu_spain:
                 updateOntologies("es-ar");
                 return true;
         case R.id.menu_english:
        	 updateOntologies("en");
             return true;
         case R.id.action_quit:
        	 finish();
        	 return true;
         }
         return super.onOptionsItemSelected(item);
	}
}
