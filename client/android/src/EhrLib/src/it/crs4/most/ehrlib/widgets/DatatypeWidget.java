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

// TODO: Auto-generated Javadoc
/**
 * The Class DatatypeWidget.
 *
 * @param <T> the generic type
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
	private String description = "N.A";
	
	/** The display title. */
	private String displayTitle;
	
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
	
	/**
	 * Instantiates a new datatype widget.
	 *
	 * @param context the context
	 * @param name the name
	 * @param datatype the datatype
	 * @param ontology the ontology
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
	
	/**
	 * Setup tooltip.
	 */
	private void setupTooltip() {
		
		//if (this.toolTip!=null) {}
			
		 this.toolTip = new ToolTip()
         .withText(this.getDescription())
         .withColor(Color.GREEN)
         .withShadow()
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
	 */
	public void setOntology(JSONObject ontology)
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
	 * Sets the datatype.
	 *
	 * @param datatype the new datatype
	 */
	public void setDatatype(T datatype)
	{
		this.datatype = datatype;
	}
	
	/**
	 * Gets the datatype.
	 *
	 * @return the datatype
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
	 * return LinearLayout containing this widget's view elements.
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
	 * toggles the visibility of this widget.
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
	 * returns visual priority.
	 *
	 * @return the priority
	 */
	public int getPriority() {
		return _priority;
	}
	
	
	/**
	 * returns the un-modified name of the property this widget represents.
	 *
	 * @return the name
	 */
	public String getName(){
		return _name;
	}
}
