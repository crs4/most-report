/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.widgets;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import org.json.JSONObject;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.DvQuantity;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;
import it.crs4.most.report.ehr.widgets.filters.DvQuantityFilter;


/**
 * This class represents a visual widget mapped on a  {@link DvQuantity} datatype.
 */
public class DvQuantityWidget extends DatatypeWidget<DvQuantity> {

    private static String TAG = "DvQuantityWidget";
    protected TextView mLabUnity;
    protected EditText mInput;
    protected TextView mTitle;
    protected TextView mTxtValidity;
    private View mHelp;
    private ToolTipView mToolTipView;
    private ToolTipRelativeLayout mToolTipLayout;

    /**
     * Instantiates a new {@link DvQuantityWidget}
     *
     * @param provider    the widget provider
     * @param name        the name of this widget
     * @param path        the path of the {@link DvQuantity} mapped on this widget
     * @param attributes  the attributes of the {@link DvQuantity} mapped on this widget
     * @param parentIndex the parent index
     */
    public DvQuantityWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex) {
        super(provider, name, new DvQuantity(path, attributes), parentIndex);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dv_quantity, null);

        mRootView = view;
        mLabUnity = (TextView) mRootView.findViewById(R.id.txt_units);
        mTitle = (TextView) mRootView.findViewById(R.id.txt_title);
        mTxtValidity = (TextView) mRootView.findViewById(R.id.txt_validity);
        mInput = (EditText) mRootView.findViewById(R.id.txt_magnitude);

        this.updateLabelsContent();

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

        Log.d(TAG, "DV QUANTITY VALUE: " + mDatatype.getMagnitude());
        setupEditingFilters();
        updateWidgetContents();
    }


    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#replaceTooltip(com.nhaarman.supertooltips.ToolTip)
     */
    @Override
    protected void replaceTooltip(ToolTip tooltip) {
        if (mToolTipView != null) {
            mToolTipView.remove();
            mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mHelp);
        }

    }


    /**
     * Update widget contents.
     */
    private void updateWidgetContents() {
        Log.d(TAG, "CALLED updateWidgetContents with DV QUANTITY VALUE: " + mDatatype.getMagnitude());

        if (mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mInput.setText(String.valueOf(mDatatype.getMagnitude()));
                    mLabUnity.setText(String.format("(%s)", mDatatype.getUnits()));
                }
            });
        }
        else {
            mInput.setText(String.valueOf(mDatatype.getMagnitude()));
            mLabUnity.setText(String.format("(%s)", mDatatype.getUnits()));
        }

    }

    /**
     * Setup editing filters.
     */
    private void setupEditingFilters() {
        DvQuantityFilter filter = new DvQuantityFilter(mInput, mTxtValidity, mDatatype);
    }


    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
     */
    @Override
    public void reset() {
        this.updateWidgetContents();
    }

    /**
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
     */
    @Override
    public void onEhrDatatypeChanged(DvQuantity datatype) {
        Log.d(TAG, "Updating datatype: " + datatype.getMagnitude());
        updateWidgetContents();

    }


    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#save()
     */
    @Override
    public void save() throws InvalidDatatypeException {
        String magnitude = mInput.getText().toString().trim();
        try {
            this.mDatatype.setMagnitude(Double.valueOf(magnitude));
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
            throw new InvalidDatatypeException("No Double Number specified for magnitude:" + magnitude);
        }

    }


    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#updateLabelsContent()
     */
    @Override
    protected void updateLabelsContent() {

        mTitle.setText(getDisplayTitle());
        mLabUnity.setText(String.format("(%s)", mDatatype.getUnits()));

    }
}
