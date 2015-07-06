/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.ehrlib.widgets;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import it.crs4.most.ehrlib.FormContainer;
import it.crs4.most.ehrlib.R;
import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.datatypes.DvCluster;
import it.crs4.most.ehrlib.datatypes.EhrDatatype;
import it.crs4.most.ehrlib.datatypes.InnerArchetype;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;


/**
 * This class represents a visual widget mapped on a  {@link InnerArchetype} datatype.
 *
 */
public class InnerArchetypeWidget extends DatatypeWidget<InnerArchetype>{

	private static final String TAG = "InnerArchetypeWidget";
	private List<DatatypeWidget<EhrDatatype>> archetypeWidgets;
	private TextView _title;
	private ToolTipRelativeLayout toolTipRelativeLayout;
	private ImageView _help;
	protected ToolTipView myToolTipView;
	private LinearLayout _archetype_layout;
   
	private boolean areWidgetsVisible = false;
	private ImageView addRemWidgets;
	private FormContainer formContainer = null;
	
	/**
	 * Instantiate a new InnerArchetypeWidget
	 * 
	 * @param provider the widget provider
	 * @param name the name of this widget
	 * @param path  the path of the {@link InnerArchetype} mapped on this widget
	 * @param attributes the attributes of the {@link InnerArchetype} mapped on this widget
	 * @param parentIndex the parent index of this widget
	 */
	public InnerArchetypeWidget( WidgetProvider provider,  String name , String path, JSONObject attributes, int parentIndex) {
		_widget_provider = provider;
		_name 		= name;
	    _context 	= provider.getContext();
		_ontology   = provider.getOntology();
		_parent_index = parentIndex;
		
		this.datatype = new InnerArchetype(path, attributes);
		this.datatype.setDatatypeChangeListener(this);
		
		this.setupDisplaytitle();
		this.setupDescription();
		super.setupTooltip();
		
		buildInnerArchetypeView();
		updateLabelsContent();
		
		_help = (ImageView) _root_view.findViewById(R.id.image_help);
		
		toolTipRelativeLayout = (ToolTipRelativeLayout) _root_view.findViewById(R.id.activity_main_tooltipRelativeLayout);
		
	    _help.setOnClickListener(new OnClickListener() {
			
	  
          
			@Override
			public void onClick(View v) {
				if (myToolTipView==null)
				{
					 myToolTipView = toolTipRelativeLayout.showToolTipForView(toolTip, _help);
					 //myToolTipView.setOnToolTipViewClickedListener(DvTextWidget.this);
				}
				else
				{
					myToolTipView.remove();
					myToolTipView=null;
					
				}
			}
		});
	}
	
	
	/**
	 * Sets the ontology.
	 *
	 * @param ontology the new ontology
	 */
	@Override
	public void setOntology(JSONObject ontology, String language)
	{
		this._widget_provider.updateOntologyLanguage(language);
		this._ontology = this._widget_provider.getOntology();
		this.setupDescription();
		this.setupDisplaytitle();
		
		this.setupTooltip();
		this.updateLabelsContent();
		
	}
	
	/**
	 * Setup description.
	 */
	private void setupDescription()
	{
		try {
			this.description = _ontology.getJSONObject(_widget_provider.getDatatypesSchema().getString("title")).getString("description"); 
		} catch (JSONException e) {
			Log.e(TAG , "Problems during DESCRIPTION SETUP:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Setup displaytitle.
	 */
	private void setupDisplaytitle() {
		try {
			
			displayTitle = _ontology.getJSONObject(_widget_provider.getDatatypesSchema().getString("title")).getString("text"); 
			//displayTitle = _ontology.getJSONObject(_name).getString("text");
		} catch (JSONException e) {
			displayTitle = _name;
		}
	}
	
	private void buildInnerArchetypeView()
	{
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.inner_archetype, null);
	
		_root_view = view;
	
		 _archetype_layout = (LinearLayout) _root_view.findViewById(R.id.archetype_container);
				
		_title = (TextView) _root_view.findViewById(R.id.txt_archetype_title);
		
	    this.formContainer = this._widget_provider.buildFormView(0);
	 
	    archetypeWidgets =   this.formContainer.getWidgets();
		
	
	 
	 addRemWidgets = (ImageView) _root_view.findViewById(R.id.image_toggle_widgets);
	 addRemWidgets.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			if (areWidgetsVisible)
				removeWidgets();
			else
				addWidgets();
			
		}
	});
			 
	}

	private void addWidgets()
	{   
		 _archetype_layout.addView(this.formContainer.getLayout());
		 this.areWidgetsVisible = true;
		 this.addRemWidgets.setImageDrawable(_context.getResources().getDrawable(android.R.drawable.ic_menu_revert));
	}
	
	private void removeWidgets()
	{
		_archetype_layout.removeAllViews();
		this.areWidgetsVisible = false;
	    this.addRemWidgets.setImageDrawable(_context.getResources().getDrawable(android.R.drawable.ic_menu_more));
	}
	
	
	/**
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)
	 */
	@Override
	public void onEhrDatatypeChanged(InnerArchetype datatype) {
		
	}

	@Override
	protected void updateLabelsContent() {
		_title.setText(getDisplayTitle());
	}

	@Override
	protected void replaceTooltip(ToolTip tooltip) {
		if (myToolTipView!=null)
		{
			myToolTipView.remove();
			myToolTipView = toolTipRelativeLayout.showToolTipForView(toolTip, _help);
		}
	}

	
	/**
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#save()
	 */
	@Override
	public void save() throws InvalidDatatypeException {
		 for( int i = 0; i < archetypeWidgets.size(); i++ ) {
			  archetypeWidgets.get(i).save();
	        }
		
	}

	/**
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#reset()
	 */
	@Override
	public void reset() {
		for( int i = 0; i < archetypeWidgets.size(); i++ ) {
			  archetypeWidgets.get(i).reset();
	        }
	}

}
