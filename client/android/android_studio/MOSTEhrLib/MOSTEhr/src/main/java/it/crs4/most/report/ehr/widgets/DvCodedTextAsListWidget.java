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
public class DvCodedTextAsListWidget extends SimpleDatatypeWidget<DvCodedText> {

    private static String TAG = "DvCodedTextAsListWidget";
    private int mCurrentSelectionIndex = 0;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
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

        mListView = (ListView) mRootView.findViewById(R.id.list_coded_text);
        mAdapter = new ArrayAdapter<>(mContext, R.layout.dv_coded_text_listview_row, R.id.txt_row, getOptions());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentSelectionIndex = position;
                mListView.setItemChecked(getDatatype().getSelectedOptionIndex(), false);
                mListView.setItemChecked(mCurrentSelectionIndex, true);
            }
        });
        mListView.setSelection(0);

        updateLabelsContent();
    }

    /**
     * Gets the options.
     *
     * @return the options
     */
    private ArrayList<String> getOptions() {
        String[] options = getDatatype().getOptions();
        ArrayList<String> lOptions = new ArrayList<>();

        for(String option : options) {
            try {
                lOptions.add(mOntology.getJSONObject(option).getString("text"));
            }
            catch (JSONException e) {
                lOptions.add(option);
                e.printStackTrace();
            }
        }
        return lOptions;
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
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#updateLabelsContent()
     */
    @Override
    protected void updateLabelsContent() {
        mTitleText.setText(getDisplayTitle());
        mAdapter.clear();
        mAdapter.addAll(getOptions());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.dv_coded_text_listview;
    }
}
