package it.crs4.most.ehrlib.widgets;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import it.crs4.most.ehrlib.R;
import it.crs4.most.ehrlib.WidgetProvider;
import it.crs4.most.ehrlib.datatypes.DvCodedText;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;

// TODO: Auto-generated Javadoc
/**
 * The Class DvCodedTextAsListWidget.
 */
public class DvCodedTextAsListWidget extends DatatypeWidget<DvCodedText>{
    
	/** The tag. */
	private static String TAG = "DvCodedTextAsListWidget";
	
	/** The current selection index. */
	private int currentSelectionIndex = 0;
	
	/** The list. */
	private ListView list;

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
	 * Instantiates a new dv coded text as list widget.
	 *
	 * @param context the context
	 * @param name the name
	 * @param path the path
	 * @param attributes the attributes
	 * @param ontology the ontology
	 * @param parentIndex the parent index
	 */
	public DvCodedTextAsListWidget( WidgetProvider provider, String name , String path, JSONObject attributes,JSONObject ontology, int parentIndex) {
		super(provider, name, new DvCodedText(path, attributes), parentIndex);
		
		Log.d(TAG, "istanziato DvCodedTextAsListWidget");
		
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dv_coded_text_listview, null);
		Log.d(TAG, "Root view recuperata");
		_root_view = view;
		
	
		list = (ListView) _root_view.findViewById(R.id.list_coded_text);
		 Log.d(TAG, "LIST istanziata");
		 
		 txtTitle = (TextView) _root_view.findViewById(R.id.txt_title);
		 
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
		 
//		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.dv_coded_text_listview_header, list, false);
//		TextView txtView = (TextView) header.findViewById(R.id.txt_header);
//		txtView.setText(name);
//		Log.d(TAG, "Header creato");
//		list.addHeaderView(header, null, false);
//		Log.d(TAG, "Header aggiunto");
		 
	   this.setupListViewAdapter();
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
	 * Setup list view adapter.
	 */
	private void setupListViewAdapter()
	{
		Log.d(TAG, "Creazione adapter");
		this.adapter = new ArrayAdapter<String>(
        		_context,
        		R.layout.dv_coded_text_listview_row,
        		R.id.txt_row,
        		getOptions()
        		);
		
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG,"CLICKING ON:" + position);
				currentSelectionIndex = position;
				list.setItemChecked(getDatatype().getSelectedOptionIndex(), false);
				list.setItemChecked(currentSelectionIndex, true);
			}
		});
		
		
		list.setSelection(0);
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#save()
	 */
	@Override
	public void save() throws InvalidDatatypeException {
		Log.d(TAG, "SAVING CUSTOM WIDGET WITH INDEX SELECTED:" + this.currentSelectionIndex);
		getDatatype().setSelectedOptionIndex(this.currentSelectionIndex);
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.widgets.DatatypeWidget#reset()
	 */
	@Override
	public void reset() {
		list.setItemChecked(currentSelectionIndex, false);
		list.setItemChecked(getDatatype().getSelectedOptionIndex(), true);
	}

	/* (non-Javadoc)
	 * @see it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)
	 */
	@Override
	public void onEhrDatatypeChanged(DvCodedText datatype) {
		reset();
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
		this.txtTitle.setText(getDisplayTitle());
		this.adapter.clear();
		this.adapter.addAll(getOptions());
		this.adapter.notifyDataSetChanged();
	}

}
