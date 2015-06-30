package com.example.ehrtemplateviewerexample;

import java.util.List;

import org.json.JSONException;


import it.crs4.most.ehrlib.ArchetypeSchemaProvider;
import it.crs4.most.ehrlib.TemplateProvider;
import it.crs4.most.ehrlib.WidgetProvider;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TemplateViewerActivity extends ActionBarActivity {
    private final String LANGUAGE = "en";
	private TemplateProvider tp = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			ArchetypeSchemaProvider asp = new ArchetypeSchemaProvider(getApplicationContext(), "archetypes.properties", "archetypes");
			this.tp = new TemplateProvider(getApplicationContext(),WidgetProvider.parseFileToString(getApplicationContext(), "ecg_bp_template.json"), asp, LANGUAGE);
		    this.buildArchetypeFragments();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void buildArchetypeFragments()
	{
		List<WidgetProvider> wps = this.tp.getWidgetProviders();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		for (WidgetProvider wp : wps )
		{ 
			  ArchetypeFragment af = new ArchetypeFragment(wp);
		      ft.add(R.id.container, af);
		}
		
	    ft.commit();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.template_viewer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
