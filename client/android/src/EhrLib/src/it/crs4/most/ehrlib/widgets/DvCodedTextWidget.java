package it.crs4.most.ehrlib.widgets;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import it.crs4.most.ehrlib.R;
import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.datatypes.DvCodedText;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

// TODO: Auto-generated Javadoc
/**
 * The Class DvCodedTextWidget.
 */
public class DvCodedTextWidget extends DatatypeWidget<DvCodedText>{

	/** The spinner. */
	private Spinner spinner = null;
	
	/** The current selection index. */
	private int currentSelectionIndex = 0;
	
	/** The _help. */
	private ImageView _help;
	
	/** The adapter. */
	private ArrayAdapter<String> adapter;
	
	/** The txt title. */
	private TextView txtTitle;
	
	/** The tool tip relative layout. */
	private ToolTipRelativeLayout toolTipRelativeLayout;
	
	/** The my tool tip view. */
	private ToolTipView myToolTipView;
	
	/**
	 * Instantiates a new dv coded text widget.
	 *
	 * @param context the context
	 * @param name the name
	 * @param path the path
	 * @param attributes the attributes
	 * @param ontology the ontology
	 * @param parentIndex the parent index
	 */
	public DvCodedTextWidget(WidgetProvider provider, String name , String path, JSONObject attributes, int parentIndex) {
		super(provider, name, new DvCodedText(path, attributes), parentIndex);
		
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dv_coded_text, null);
		_root_view = view;
		
		txtTitle = (TextView) _root_view.findViewById(R.id.txt_title);
		this.setupSpinner();
		
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
	 * Gets the options.
	 *
	 * @return the options
	 */
	private ArrayList<String>  getOptions()
	{
		String [] options = getDatatype().getOptions();
		
		ArrayList<String> lOptions = new ArrayList<String>();
		
		for (int i=0;i<options.length;i++)
		{
			try {
				lOptions.add(_ontology.getJSONObject(options[i]).getString("text"));
			} catch (JSONException e) {
				lOptions.add(options[i]);
				e.printStackTrace();
			}
		}
		return lOptions;
	}
	
	
	/**
	 * Setup spinner.
	 */
	private void setupSpinner()
	{
		
		this.spinner = (Spinner) _root_view.findViewById(R.id.spinnerState);
	
		adapter = new ArrayAdapter<String>(
        		_context,
        		R.layout.spinner_item,
        		getOptions()
        		);
		
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				currentSelectionIndex = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#save()
	 */
	@Override
	public void save() throws InvalidDatatypeException {
		getDatatype().setSelectedOptionIndex(this.currentSelectionIndex);
		
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#reset()
	 */
	@Override
	public void reset() {
		spinner.setSelection(getDatatype().getSelectedOptionIndex());
		
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)
	 */
	@Override
	public void onEhrDatatypeChanged(DvCodedText datatype) {
		reset();
	}



	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#updateLabelsContent()
	 */
	@Override
	protected void updateLabelsContent() {
		this.txtTitle.setText(String.format("%s: " , getDisplayTitle()));
		this.adapter.clear();
		this.adapter.addAll(getOptions());
		this.adapter.notifyDataSetChanged();
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

}
