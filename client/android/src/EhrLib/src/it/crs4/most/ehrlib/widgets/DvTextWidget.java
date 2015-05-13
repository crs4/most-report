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

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import it.crs4.most.ehrlib.R;
import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.datatypes.DvText;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

// TODO: Auto-generated Javadoc
/**
 * The Class DvTextWidget.
 */
public class DvTextWidget extends DatatypeWidget<DvText> implements ToolTipView.OnToolTipViewClickedListener {

	/** The _title. */
	private TextView _title;
	
	/** The _input. */
	private EditText _input;
	
	/** The tag. */
	private String TAG= "DvTextWidget";
	
	/** The _txtvalidity. */
	private TextView _txtvalidity;
	
	/** The _help. */
	private ImageView _help;
	
	/** The my tool tip view. */
	private ToolTipView myToolTipView;
	
	/** The tool tip relative layout. */
	private ToolTipRelativeLayout toolTipRelativeLayout;

	
	/**
	 * Instantiates a new dv text widget.
	 *
	 * @param context the context
	 * @param name the name
	 * @param path the path
	 * @param attributes the attributes
	 * @param ontology the ontology
	 * @param parentIndex the parent index
	 */
	public DvTextWidget(WidgetProvider provider,String name, String path, JSONObject attributes, int parentIndex)
	       {
		super(provider, name, new DvText(path, attributes), parentIndex);
		

		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dv_text, null);
		
		_root_view = view;
	
		_title = (TextView) _root_view.findViewById(R.id.txt_title);
		_txtvalidity = (TextView) _root_view.findViewById(R.id.txt_validity);
		_input = (EditText) _root_view.findViewById(R.id.txt_text);
		
		_input.setMovementMethod(new ScrollingMovementMethod());
		
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
	    
	}

	
	/**
	 * Update widget contents.
	 */
	private void updateWidgetContents()
	{  
		Log.d(TAG, "CALLED updateWidgetContents with DV TEXT VALUE: " + datatype.getText());
		
		if (_context instanceof Activity)
		{
			((Activity)_context).runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					_input.setText(String.valueOf(datatype.getText()));
				}
			});
		}
		else
		{
			_input.setText(String.valueOf(datatype.getText()));
		}
		
	}
	
	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)
	 */
	@Override
	public void onEhrDatatypeChanged(DvText datatype) {
		updateWidgetContents();
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#save()
	 */
	@Override
	public void save() throws InvalidDatatypeException {
		String text = _input.getText().toString().trim();
		
		this.datatype.setText(text);
		
		
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#reset()
	 */
	@Override
	public void reset() {
		updateWidgetContents();
	}


	/* (non-Javadoc)
	 * @see com.nhaarman.supertooltips.ToolTipView.OnToolTipViewClickedListener#onToolTipViewClicked(com.nhaarman.supertooltips.ToolTipView)
	 */
	@Override
	public void onToolTipViewClicked(ToolTipView arg0) {
		// TODO Auto-generated method stub
		
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

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#updateLabelsContent()
	 */
	@Override
	protected void updateLabelsContent() {
		_title.setText(getDisplayTitle());
	}

}
