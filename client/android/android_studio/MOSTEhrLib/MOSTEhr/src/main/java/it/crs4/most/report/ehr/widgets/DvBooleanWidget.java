/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */


package it.crs4.most.report.ehr.widgets;

import org.json.JSONObject;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.DvBoolean;
import it.crs4.most.report.ehr.datatypes.DvQuantity;
import it.crs4.most.report.ehr.datatypes.DvText;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;


/**
 * This class represents a visual widget mapped on a  {@link DvBoolean} datatype.
 */
public class DvBooleanWidget extends DatatypeWidget<DvBoolean> implements ToolTipView.OnToolTipViewClickedListener {

	/** The checkbox. */
	private CheckBox _chkBoolean;
	
	
	
	/** The tag. */
	private String TAG= "DvTextWidget";
	

	/** The _help. */
	private ImageView _help;
	
	/** The my tool tip view. */
	private ToolTipView myToolTipView;
	
	/** The tool tip relative layout. */
	private ToolTipRelativeLayout toolTipRelativeLayout;

	
	/**
	 * Instantiates a new {@link DvBooleanWidget}
	 *
	 * @param provider the widget provider
	 * @param name the name of this widget
	 * @param path the path of the {@link DvBoolean} mapped on this widget
	 * @param attributes the attributes of the {@link DvBoolean} mapped on this widget
	 * @param parentIndex the parent index
	 */
	public DvBooleanWidget(WidgetProvider provider,String name, String path, JSONObject attributes, int parentIndex)
	       {
		super(provider, name, new DvBoolean(path, attributes), parentIndex);
		

		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dv_boolean, null);
		
		_root_view = view;
	
		_chkBoolean = (CheckBox) _root_view.findViewById(R.id.check_is_selected);
		
		this.updateLabelsContent();
		
		_help = (ImageView) _root_view.findViewById(R.id.image_help);
		
		toolTipRelativeLayout = (ToolTipRelativeLayout) _root_view.findViewById(R.id.activity_main_tooltipRelativeLayout);
		
	    _help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (myToolTipView==null)
				{
					 myToolTipView = toolTipRelativeLayout.showToolTipForView(toolTip, _help);
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
	 * Update widget contents.
	 */
	private void updateWidgetContents()
	{  
		Log.d(TAG, "CALLED updateWidgetContents with DV BOOLEAN VALUE: " + datatype.getValue());
		
		if (_context instanceof Activity)
		{
			((Activity)_context).runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					_chkBoolean.setChecked(datatype.getValue());
				}
			});
		}
		else
		{    
			_chkBoolean.setChecked(datatype.getValue());
		}
		
	}
	
	/**
	 * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
	 */
	@Override
	public void onEhrDatatypeChanged(DvBoolean datatype) {
		updateWidgetContents();
	}

	/**
	 * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#save()
	 */
	@Override
	public void save() throws InvalidDatatypeException {
		boolean value = _chkBoolean.isChecked();
		this.datatype.setValue(value);
	}

	/**
	 * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
	 */
	@Override
	public void reset() {
		updateWidgetContents();
	}


	/**
	 * @see com.nhaarman.supertooltips.ToolTipView.OnToolTipViewClickedListener#onToolTipViewClicked(com.nhaarman.supertooltips.ToolTipView)
	 */
	@Override
	public void onToolTipViewClicked(ToolTipView arg0) {
		
	}

	/**
	 * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#replaceTooltip(com.nhaarman.supertooltips.ToolTip)
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
	 * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#updateLabelsContent()
	 */
	@Override
	protected void updateLabelsContent() {
		_chkBoolean.setText(getDisplayTitle());
	}

}
