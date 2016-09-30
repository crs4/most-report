/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.widgets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.DvCodedText;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;


/**
 * This class represents a visual widget mapped on a  {@link DvCodedText} datatype. It renders all the options of the {@link DvCodedText} datatype in a ListView.
 */
public class DvCodedTextAsListWidget extends DatatypeWidget<DvCodedText> {

    private static String TAG = "DvCodedTextAsListWidget";
    private int mCurrentSelectionIndex = 0;
    private ListView mListView;
    private ImageView mHelp;
    private ArrayAdapter<String> mAdapter;
    private TextView mTxtTitle;
    private ToolTipRelativeLayout mToolTipLayout;
    private ToolTipView mToolTipView;

    /**
     * Instantiates a new {@link DvCodedTextAsListWidget}
     *
     * @param provider    the widget provider
     * @param name        the name of this widget
     * @param path        the path of the {@link DvCodedText} mapped on this widget
     * @param attributes  the attributes of the {@link DvCodedText} mapped on this widget
     * @param parentIndex the parent index
     */
    public DvCodedTextAsListWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex) {
        super(provider, name, new DvCodedText(path, attributes), parentIndex);

        Log.d(TAG, "istanziato DvCodedTextAsListWidget");

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dv_coded_text_listview, null);
        mRootView = view;


        mListView = (ListView) mRootView.findViewById(R.id.list_coded_text);

        mTxtTitle = (TextView) mRootView.findViewById(R.id.txt_title);

        updateLabelsContent();

        mHelp = (ImageView) mRootView.findViewById(R.id.image_help);

        mToolTipLayout = (ToolTipRelativeLayout) mRootView.findViewById(R.id.activity_main_tooltipRelativeLayout);

        mHelp.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                if (mToolTipView == null) {
                    mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mHelp);
                    //mToolTipView.setOnToolTipViewClickedListener(DvTextWidget.this);
                }
                else {
                    mToolTipView.remove();
                    mToolTipView = null;

                }
            }
        });

//		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.dv_coded_text_listview_header, mListView, false);
//		TextView txtView = (TextView) header.findViewById(R.id.txt_header);
//		txtView.setText(name);
//		Log.d(TAG, "Header creato");
//		mListView.addHeaderView(header, null, false);
//		Log.d(TAG, "Header aggiunto");

        setupListViewAdapter();
    }


    /**
     * Gets the options.
     *
     * @return the options
     */
    private ArrayList<String> getOptions() {
        String[] options = getDatatype().getOptions();

        ArrayList<String> lOptions = new ArrayList<String>();

        for (int i = 0; i < options.length; i++) {
            try {
                lOptions.add(mOntology.getJSONObject(options[i]).getString("text"));
            }
            catch (JSONException e) {
                lOptions.add(options[i]);
                e.printStackTrace();
            }
        }
        return lOptions;
    }

    /**
     * Setup list view adapter.
     */
    private void setupListViewAdapter() {
        mAdapter = new ArrayAdapter<String>(
            mContext,
            R.layout.dv_coded_text_listview_row,
            R.id.txt_row,
            getOptions()
        );

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCurrentSelectionIndex = position;
                mListView.setItemChecked(getDatatype().getSelectedOptionIndex(), false);
                mListView.setItemChecked(mCurrentSelectionIndex, true);
            }
        });


        mListView.setSelection(0);
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#save()
     */
    @Override
    public void save() throws InvalidDatatypeException {
        Log.d(TAG, "SAVING CUSTOM WIDGET WITH INDEX SELECTED:" + mCurrentSelectionIndex);
        getDatatype().setSelectedOptionIndex(mCurrentSelectionIndex);
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
     */
    @Override
    public void reset() {
        mListView.setItemChecked(mCurrentSelectionIndex, false);
        mListView.setItemChecked(getDatatype().getSelectedOptionIndex(), true);
    }

    /**
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
     */
    @Override
    public void onEhrDatatypeChanged(DvCodedText datatype) {
        reset();
    }


    /**
     * @see {@link it.crs4.most.report.ehr.widgets.DatatypeWidget#replaceTooltip(com.nhaarman.supertooltips.ToolTip)}
     */
    @Override
    protected void replaceTooltip(ToolTip tooltip) {
        if (mToolTipView != null) {
            mToolTipView.remove();
            mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mHelp);
        }

    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#updateLabelsContent()
     */
    @Override
    protected void updateLabelsContent() {
        mTxtTitle.setText(getDisplayTitle());
        mAdapter.clear();
        mAdapter.addAll(getOptions());
        mAdapter.notifyDataSetChanged();
    }

}
