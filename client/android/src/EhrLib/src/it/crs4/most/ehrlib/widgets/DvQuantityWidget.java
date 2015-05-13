/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.ehrlib.widgets;


import org.json.JSONObject;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import it.crs4.most.ehrlib.R;
import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.datatypes.DvQuantity;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;
import it.crs4.most.ehrlib.widgets.filters.DvQuantityFilter;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


// TODO: Auto-generated Javadoc
/**
 * The Class DvQuantityWidget.
 */
public class DvQuantityWidget extends DatatypeWidget<DvQuantity>
{
	
	/** The _lab unity. */
	protected TextView _labUnity;
	
	/** The _input. */
	protected EditText _input;
	
	/** The _title. */
	protected TextView _title;
	
	/** The _txtvalidity. */
	protected TextView _txtvalidity;
	
	/** The _help. */
	private View _help;
	
	/** The tag. */
	private static String TAG = "DvQuantityWidget";
	
	/** The my tool tip view. */
	private ToolTipView myToolTipView;
	
	/** The tool tip relative layout. */
	private ToolTipRelativeLayout toolTipRelativeLayout;

	/**
	 * Instantiates a new dv quantity widget.
	 *
	 * @param context the context
	 * @param name the name
	 * @param path the path
	 * @param attributes the attributes
	 * @param ontology the ontology
	 * @param parentIndex the parent index
	 */
	public DvQuantityWidget(WidgetProvider provider, String name , String path, JSONObject attributes, int parentIndex)
	{
		super( provider, name , new DvQuantity(path, attributes), parentIndex);
		
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dv_quantity, null);
		
		_root_view = view;
		_labUnity = (TextView) _root_view.findViewById(R.id.txt_units);
		_title = (TextView) _root_view.findViewById(R.id.txt_title);
		_txtvalidity = (TextView) _root_view.findViewById(R.id.txt_validity);
		_input = (EditText) _root_view.findViewById(R.id.txt_magnitude);
		
		this.updateLabelsContent();
		
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
       
		Log.d(TAG, "DV QUANTITY VALUE: " + datatype.getMagnitude());
		setupEditingFilters();
		updateWidgetContents();
	}
	
	
	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#replaceTooltip(com.nhaarman.supertooltips.ToolTip)
	 */
	@Override
	protected void replaceTooltip(ToolTip tooltip) {
		if (myToolTipView!=null)
		{
			myToolTipView.remove();
			myToolTipView = toolTipRelativeLayout.showToolTipForView(toolTip, _help);
		}
		
	}
	 
	
	/**
	 * Update widget contents.
	 */
	private void updateWidgetContents()
	{  
		Log.d(TAG, "CALLED updateWidgetContents with DV QUANTITY VALUE: " + datatype.getMagnitude());
		
		if (_context instanceof Activity)
		{
			((Activity)_context).runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					_input.setText(String.valueOf(datatype.getMagnitude()));
					_labUnity.setText(String.format("(%s)",datatype.getUnits()));
				}
			});
		}
		else
		{
			_input.setText(String.valueOf(datatype.getMagnitude()));
			_labUnity.setText(String.format("(%s)",datatype.getUnits()));
		}
		
	}
	
	/**
	 * Setup editing filters.
	 */
	private void setupEditingFilters()
	{
		DvQuantityFilter filter = new DvQuantityFilter(_input, _txtvalidity, datatype);
	}

	

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#reset()
	 */
	@Override
	public void reset() {
		this.updateWidgetContents();
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)
	 */
	@Override
	public void onEhrDatatypeChanged(DvQuantity datatype) {
		Log.d(TAG,"Updating datatype: " + datatype.getMagnitude());
		updateWidgetContents();
		
	}



	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#save()
	 */
	@Override
	public void save() throws InvalidDatatypeException {
		String magnitude = _input.getText().toString().trim();
		try{
		this.datatype.setMagnitude(Double.valueOf(magnitude));
		} catch (NumberFormatException ex)
		{
			ex.printStackTrace();
			throw new InvalidDatatypeException("No Double Number specified for magnitude:"+ magnitude);
		}
		
	}



	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#updateLabelsContent()
	 */
	@Override
	protected void updateLabelsContent() {
		
		_title.setText(getDisplayTitle());
		_labUnity.setText(String.format("(%s)",datatype.getUnits()));
		
	}



	

}
