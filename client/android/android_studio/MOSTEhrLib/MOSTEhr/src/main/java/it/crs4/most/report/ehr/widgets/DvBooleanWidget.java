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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import org.json.JSONObject;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.DvBoolean;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;


/**
 * This class represents a visual widget mapped on a  {@link DvBoolean} datatype.
 */
public class DvBooleanWidget extends DatatypeWidget<DvBoolean> implements ToolTipView.OnToolTipViewClickedListener {

    private static final String TAG = "DvTextWidget";
    private TextView mTitleText;
    private CheckBox mChkBoolean;
    private ToolTipView mToolTipView;
    private ToolTipRelativeLayout mToolTipLayout;

    /**
     * Instantiates a new {@link DvBooleanWidget}
     *
     * @param provider    the widget provider
     * @param name        the name of this widget
     * @param path        the path of the {@link DvBoolean} mapped on this widget
     * @param attributes  the attributes of the {@link DvBoolean} mapped on this widget
     * @param parentIndex the parent index
     */
    public DvBooleanWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex) {
        super(provider, name, new DvBoolean(path, attributes), parentIndex);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.dv_boolean, null);
        mTitleText = (TextView) mRootView.findViewById(R.id.txt_title);
        mChkBoolean = (CheckBox) mRootView.findViewById(R.id.check_is_selected);
        updateLabelsContent();

//        mHelp = (ImageView) mRootView.findViewById(R.id.image_help);
        mToolTipLayout = (ToolTipRelativeLayout) mRootView.findViewById(R.id.activity_main_tooltipRelativeLayout);
        mTitleText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToolTipView == null) {
                    mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mTitleText);
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
        if (mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mChkBoolean.setChecked(mDatatype.getValue());
                }
            });
        }
        else {
            mChkBoolean.setChecked(mDatatype.getValue());
        }
    }

    /**
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
     */
    @Override
    public void onEhrDatatypeChanged(DvBoolean datatype) {
        updateWidgetContents();
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#save()
     */
    @Override
    public void save() throws InvalidDatatypeException {
        boolean value = mChkBoolean.isChecked();
        mDatatype.setValue(value);
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
            mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mTitleText);
        }
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#updateLabelsContent()
     */
    @Override
    protected void updateLabelsContent() {
        mTitleText.setText(getDisplayTitle());
    }

}
