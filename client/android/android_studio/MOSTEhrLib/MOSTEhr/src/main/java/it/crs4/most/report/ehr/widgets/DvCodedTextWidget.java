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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
 * This class represents a visual widget mapped on a  {@link DvCodedText} datatype. It renders all the options of the {@link DvCodedText} datatype in a Combobox.
 */
public class DvCodedTextWidget extends DatatypeWidget<DvCodedText> {

    private Spinner mSpinner = null;
    private int mCurrentSelectionIndex = 0;
//    private ImageView mHelp;
    private ArrayAdapter<String> mAdapter;
    private TextView mTxtTitle;
    private ToolTipRelativeLayout mToolTipLayout;
    private ToolTipView mToolTipView;

    /**
     * Instantiates a new {@link DvCodedTextWidget}
     *
     * @param provider    the widget provider
     * @param name        the name of this widget
     * @param path        the path of the {@link DvCodedText} mapped on this widget
     * @param attributes  the attributes of the {@link DvCodedText} mapped on this widget
     * @param parentIndex the parent index
     */
    public DvCodedTextWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex) {
        super(provider, name, new DvCodedText(path, attributes), parentIndex);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.dv_coded_text, null);

        mTxtTitle = (TextView) mRootView.findViewById(R.id.txt_title);
        this.setupSpinner();

        this.updateLabelsContent();

//        mHelp = (ImageView) mRootView.findViewById(R.id.image_help);

        mToolTipLayout = (ToolTipRelativeLayout) mRootView.findViewById(R.id.activity_main_tooltipRelativeLayout);

        mTxtTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToolTipView == null) {
                    mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mTxtTitle);
                    //mToolTipView.setOnToolTipViewClickedListener(DvTextWidget.this);
                }
                else {
                    mToolTipView.remove();
                    mToolTipView = null;
                }
            }
        });
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
     * Setup spinner.
     */
    private void setupSpinner() {

        mSpinner = (Spinner) mRootView.findViewById(R.id.spinnerState);
        mAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, getOptions());
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentSelectionIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#save()
     */
    @Override
    public void save() throws InvalidDatatypeException {
        getDatatype().setSelectedOptionIndex(mCurrentSelectionIndex);

    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
     */
    @Override
    public void reset() {
        mSpinner.setSelection(getDatatype().getSelectedOptionIndex());

    }

    /**
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
     */
    @Override
    public void onEhrDatatypeChanged(DvCodedText datatype) {
        reset();
    }


    /* (non-Javadoc)
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#updateLabelsContent()
     */
    @Override
    protected void updateLabelsContent() {
        mTxtTitle.setText(String.format("%s: ", getDisplayTitle()));
        mAdapter.clear();
        mAdapter.addAll(getOptions());
        mAdapter.notifyDataSetChanged();
    }

    /* (non-Javadoc)
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#replaceTooltip(com.nhaarman.supertooltips.ToolTip)
     */
    @Override
    protected void replaceTooltip(ToolTip tooltip) {
        if (mToolTipView != null) {
            mToolTipView.remove();
            mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mTxtTitle);
        }
    }
}
