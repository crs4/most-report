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
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.lang.ref.WeakReference;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.DvQuantity;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;


/**
 * This class represents a visual widget mapped on a  {@link DvQuantity} datatype.
 */
public class DvQuantityWidget extends SimpleDatatypeWidget<DvQuantity> {

    private static String TAG = "DvQuantityWidget";
    private TextView mLabUnityText;
    private EditText mMagnitudeText;

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

        mMagnitudeText = (EditText) mRootView.findViewById(R.id.txt_magnitude);
        mMagnitudeText.setHint(String.format("%s-%s", mDatatype.getMin(), mDatatype.getMax()));
        mMagnitudeText.addTextChangedListener(new MagnitudeTextWatcher(this));

        mLabUnityText = (TextView) mRootView.findViewById(R.id.txt_units);
        mLabUnityText.setText(String.format("(%s)", mDatatype.getUnits()));

        mToolTipLayout = (ToolTipRelativeLayout) mRootView.findViewById(R.id.activity_main_tooltipRelativeLayout);

        updateWidgetContents();
    }

    /**
     * Update widget contents.
     */
    private void updateWidgetContents() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mDatatype.getMagnitude() != null) {
                        mMagnitudeText.setText(String.valueOf(mDatatype.getMagnitude()));
                    }
                    mLabUnityText.setText(String.format("(%s)", mDatatype.getUnits()));
                }
            });
        }
        else {
            if (mDatatype.getMagnitude() != null) {
                mMagnitudeText.setText(String.valueOf(mDatatype.getMagnitude()));
            }
            mLabUnityText.setText(String.format("(%s)", mDatatype.getUnits()));
        }
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
     */
    @Override
    public void reset() {
        updateWidgetContents();
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
        String magnitude = mMagnitudeText.getText().toString().trim();
        try {
            mDatatype.setMagnitude(Double.valueOf(magnitude));
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
        mTitleText.setText(getDisplayTitle());
        mLabUnityText.setText(String.format("(%s)", mDatatype.getUnits()));
    }

    @Override
    public int getLayoutResource() {
        return R.layout.dv_quantity;
    }

    private static class MagnitudeTextWatcher implements TextWatcher {

        WeakReference<DvQuantityWidget> outerRef;

        public MagnitudeTextWatcher(DvQuantityWidget widget) {
            outerRef = new WeakReference<>(widget);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            double value;
            boolean isValid = true;
            try {
                value = Double.parseDouble(outerRef.get().mMagnitudeText.getText().toString());
                isValid = outerRef.get().mDatatype.isValid(value);
            }
            catch (NumberFormatException e) {
                isValid = false;
            }

            if (isValid) {
                outerRef.get().mMagnitudeText.setTextColor(Color.BLACK);
            }
            else {
                outerRef.get().mMagnitudeText.setTextColor(Color.RED);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
