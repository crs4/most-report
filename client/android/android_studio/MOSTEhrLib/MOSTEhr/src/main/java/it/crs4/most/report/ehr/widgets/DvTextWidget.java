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
import android.text.method.ScrollingMovementMethod;
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
import it.crs4.most.report.ehr.datatypes.DvText;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;


/**
 * This class represents a visual widget mapped on a  {@link DvText} datatype.
 */
public class DvTextWidget extends DatatypeWidget<DvText> implements ToolTipView.OnToolTipViewClickedListener {

    private String TAG = "DvTextWidget";
    private TextView mTxtTitle;
    private EditText mInput;
    private TextView mTxtValidity;
    private ImageView mHelp;
    private ToolTipView mToolTipView;
    private ToolTipRelativeLayout mToolTipLayout;

    /**
     * Instantiates a new {@link DvTextWidget}
     *
     * @param provider    the widget provider
     * @param name        the name of this widget
     * @param path        the path of the {@link DvText} mapped on this widget
     * @param attributes  the attributes of the {@link DvText} mapped on this widget
     * @param parentIndex the parent index
     */
    public DvTextWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex) {
        super(provider, name, new DvText(path, attributes), parentIndex);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mRootView = inflater.inflate(R.layout.dv_text, null);
        mTxtTitle = (TextView) mRootView.findViewById(R.id.txt_title);
        mTxtValidity = (TextView) mRootView.findViewById(R.id.txt_validity);
        mInput = (EditText) mRootView.findViewById(R.id.txt_text);
        mInput.setMovementMethod(new ScrollingMovementMethod());

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
    }


    /**
     * Update widget contents.
     */
    private void updateWidgetContents() {
        Log.d(TAG, "CALLED updateWidgetContents with DV TEXT VALUE: " + mDatatype.getText());

        if (mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mInput.setText(String.valueOf(mDatatype.getText()));
                }
            });
        }
        else {
            mInput.setText(String.valueOf(mDatatype.getText()));
        }
    }

    /**
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
     */
    @Override
    public void onEhrDatatypeChanged(DvText datatype) {
        updateWidgetContents();
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#save()
     */
    @Override
    public void save() throws InvalidDatatypeException {
        String text = mInput.getText().toString().trim();
        mDatatype.setText(text);
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
    }
}
