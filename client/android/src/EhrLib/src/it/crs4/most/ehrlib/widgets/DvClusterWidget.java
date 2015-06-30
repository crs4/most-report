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

import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import it.crs4.most.ehrlib.R;
import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.datatypes.DvCluster;
import it.crs4.most.ehrlib.datatypes.EhrDatatype;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;


/**
 * This class represents a visual widget mapped on a  {@link DvCluster} datatype.
 *
 */
public class DvClusterWidget extends DatatypeWidget<DvCluster>{

	private List<DatatypeWidget<EhrDatatype>> clusterWidgets;
	private TextView _title;
	private ToolTipRelativeLayout toolTipRelativeLayout;
	private ImageView _help;
	protected ToolTipView myToolTipView;
	private LinearLayout _clusterLayout;
   
	private boolean areWidgetsVisible = false;
	private ImageView addRemWidgets;
	
	/**
	 * Instantiate a new DvClusterWidget
	 * 
	 * @param provider the widget provider
	 * @param name the name of this widget
	 * @param path  the path of the {@link DvCluster} mapped on this widget
	 * @param attributes the attributes of the {@link DvCluster} mapped on this widget
	 * @param parentIndex the parent index of this widget
	 */
	public DvClusterWidget( WidgetProvider provider,  String name , String path, JSONObject attributes, int parentIndex) {
		super(provider, name, new DvCluster(path, attributes), parentIndex);
		buildClusterView();
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
	
	
	
	private void buildClusterView()
	{
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dv_cluster, null);
	
		_root_view = view;
	
		 _clusterLayout = (LinearLayout) _root_view.findViewById(R.id.cluster_container);
				
		_title = (TextView) _root_view.findViewById(R.id.txt_title);
		
		  
	 String sectionRef = this.datatype.getSectionName();
	 
	 clusterWidgets =   this._widget_provider.getSectionWidgets(sectionRef, 0);
		 
	
	 
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
		 for( int i = 0; i < clusterWidgets.size(); i++ ) {
			 _clusterLayout.addView( ( View ) clusterWidgets.get(i).getView() );
	        }
		 this.areWidgetsVisible = true;
		 this.addRemWidgets.setImageDrawable(_context.getResources().getDrawable(android.R.drawable.ic_menu_revert));
	}
	
	private void removeWidgets()
	{
		_clusterLayout.removeAllViews();
		this.areWidgetsVisible = false;
	    this.addRemWidgets.setImageDrawable(_context.getResources().getDrawable(android.R.drawable.ic_menu_more));
	}
	
	
	/**
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)
	 */
	@Override
	public void onEhrDatatypeChanged(DvCluster datatype) {
		
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
		 for( int i = 0; i < clusterWidgets.size(); i++ ) {
			  clusterWidgets.get(i).save();
	        }
		
	}

	/**
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#reset()
	 */
	@Override
	public void reset() {
		for( int i = 0; i < clusterWidgets.size(); i++ ) {
			  clusterWidgets.get(i).reset();
	        }
	}

}
