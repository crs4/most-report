/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.ehrlib.widgets;


import org.json.JSONException;
import org.json.JSONObject;

import com.nhaarman.supertooltips.ToolTip;

import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.datatypes.EhrDatatype;
import it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

/**
 * This the base class for all DatatypeWidgets. A {@link DatatypeWidget}  is a visual and interactive widget mapped on a specific {@link EhrDatatype}.
 * A user can instantiate a {@link DatatypeWidget}  for reading, editing and saving the content of the {@link EhrDatatype} handled by it.
 *
 * @param <T> the generic {@link EhrDatatype}
 */
public abstract class DatatypeWidget <T extends EhrDatatype> implements EhrDatatypeChangeListener<T>
{
	
	/** The _view. */
	protected View 			_view;
	
	/** The _name. */
	protected String 		_name;
	
	/** The _priority. */
	protected int 	 		_priority;
	
	/** The _parent_index. */
	protected int			_parent_index;
	
	/** The _root_view. */
	protected View 	_root_view;
	
	/** The _context. */
	protected Context _context;
	
	/** The datatype. */
	protected T datatype;
	
	/** The _ontology. */
	protected JSONObject _ontology = null;
    
	/** The description. */
	protected String description = "N.A";
	
	/** The display title. */
	protected String displayTitle;
	
	/** The tool tip. */
	protected ToolTip toolTip;
	
	/** The tag. */
	private String TAG = "DatatypeWidget";

	protected WidgetProvider _widget_provider;
	
	/**
	 * Update the content of the labels of the widget, according to the current ontology language.
	 */
	protected abstract void updateLabelsContent();
	
	/**
	 * Replace tooltip.
	 *
	 * @param tooltip the tooltip
	 */
	protected abstract void replaceTooltip(ToolTip tooltip);
	
	// Nedeed for InnerArchetypeWidget that does not call the super constructor.
	protected DatatypeWidget() {}
	
	/**
	 * Instantiates a new {@link DatatypeWidget} widget.
	 *
	 * @param context the context
	 * @param name the name of this widget
	 * @param datatype the {@link EhrDatatype} to be handled by this widget
	 * @param ontology the ontology used 
	 * @param parent_index the parent_index 
	 */
	public DatatypeWidget( WidgetProvider provider, String name, T datatype, int parent_index)
	{
		_widget_provider = provider;
		_name 		= name;
	    _context 	= provider.getContext();
		_ontology   = provider.getOntology();
		_parent_index = parent_index;
		
		this.datatype = datatype;
		this.datatype.setDatatypeChangeListener(this);
		
		this.setupDisplaytitle();
		this.setupDescription();
		this.setupTooltip();
	}
	
	
	public WidgetProvider getWidgetProvider()
	{
		return this._widget_provider;
	}
	
	/**
	 * Setup tooltip.
	 */
	protected void setupTooltip() {
		
		//if (this.toolTip!=null) {}
			
		 this.toolTip = new ToolTip()
         .withText(this.getDescription())
         .withColor(Color.GREEN)
         .withShadow()
         .withTextColor(Color.BLUE)
         .withAnimationType(ToolTip.AnimationType.FROM_TOP);
		 
		 this.replaceTooltip(this.toolTip);
		
	}
	
	/**
	 * Gets the tool tip.
	 *
	 * @return the tool tip
	 */
	public ToolTip getToolTip()
	{
		return this.toolTip;
	}

	/**
	 * Sets the ontology.
	 *
	 * @param ontology the new ontology
	 * @param lang the language of the new loaded ontology
	 */
	public void setOntology(JSONObject ontology, String lang)
	{
		this._ontology = ontology;
		this.setupDescription();
		this.setupDisplaytitle();
		this.setupTooltip();
		
		this.updateLabelsContent();
	}
	
	/**
	 * Setup displaytitle.
	 */
	private void setupDisplaytitle() {
		try {
			displayTitle = _ontology.getJSONObject(_name).getString("text");
		} catch (JSONException e) {
			Log.e(TAG, String.format("Key %s not found for datatype with ontology: %s", _name, _ontology));
			displayTitle = _name;
		}
	}
	
	/**
	 * Gets the display title.
	 *
	 * @return the display title
	 */
	public String getDisplayTitle()
	{
		return this.displayTitle;
	}

	/**
	 * Setup description.
	 */
	private void setupDescription()
	{
		try {
			this.description = _ontology.getJSONObject(this._name).getString("description");
		} catch (JSONException e) {
			Log.e(TAG , "Problems during DESCRIPTION SETUP:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return this.description;
	}
	
	
	/**
	 * Gets the {@link EhrDatatype} handled by this widget
	 *
	 * @return the {@link EhrDatatype}
	 */
	public T getDatatype()
	{
		return this.datatype;
	}
	
	
	/**
	 * Update the value of the underlying datatype according to the current value of the fields of this widget.
	 *
	 * @throws InvalidDatatypeException if the current value of the fields cannot be converted to a datatype value
	 */
	public abstract void save() throws InvalidDatatypeException;
	
	
	/**
	 * Reset all fields of this widget according to the current underlying datatype value.
	 */
	public abstract void reset();
	
	// -----------------------------------------------
	//
	// view
	//
	// -----------------------------------------------
	/**
	 * get the Root View containing this widget's view elements.
	 *
	 * @return the view
	 */
	public View getView() {
		return _root_view;
	}
	
	
	/**
	 * Gets the parent index.
	 *
	 * @return the parent index
	 */
	public int getParentIndex()
	{
		return this._parent_index;
	}
	
	/**
	 * set the visibility of this widget.
	 *
	 * @param value the new visibility
	 */
	public void setVisibility( int value ){
		_root_view.setVisibility( value );
	}
	
	
	
	// -----------------------------------------------
	//
	// set / get priority
	//
	// -----------------------------------------------
	
	/**
	 * sets the visual priority of this widget
	 * essentially this means it's physical location in the form.
	 *
	 * @param value the new priority
	 */
	public void setPriority( int value ) {
		_priority = value;
	}
	
	/**
	 * returns the visual priority of this widget (essentially this means it's physical location in the form).
	 *
	 * @return the priority
	 */
	public int getPriority() {
		return _priority;
	}
	
	
	/**
	 * returns the name of this widget
	 *
	 * @return the name
	 */
	public String getName(){
		return _name;
	}
}
