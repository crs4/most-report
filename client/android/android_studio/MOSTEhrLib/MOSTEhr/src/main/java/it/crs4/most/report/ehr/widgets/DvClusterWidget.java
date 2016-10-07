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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import org.json.JSONObject;

import java.util.List;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.DvCluster;
import it.crs4.most.report.ehr.datatypes.EhrDatatype;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;


/**
 * This class represents a visual widget mapped on a  {@link DvCluster} datatype.
 */
public class DvClusterWidget extends DatatypeWidget<DvCluster> {

    private static final String TAG = "DvClusterWidget";
    private boolean mWidgetsVisible = false;
    private List<DatatypeWidget<EhrDatatype>> mClusterWidgets;
    private TextView mTitleText;
    private ToolTipRelativeLayout mToolTipLayout;
    private ToolTipView mToolTipView;
    private LinearLayout mClusterLayout;

    /**
     * Instantiate a new DvClusterWidget
     *
     * @param provider    the widget provider
     * @param name        the name of this widget
     * @param path        the path of the {@link DvCluster} mapped on this widget
     * @param attributes  the attributes of the {@link DvCluster} mapped on this widget
     * @param parentIndex the parent index of this widget
     */
    public DvClusterWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex) {
        super(provider, name, new DvCluster(path, attributes), parentIndex);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.dv_cluster, null);
        mClusterLayout = (LinearLayout) mRootView.findViewById(R.id.cluster_container);
        mTitleText = (TextView) mRootView.findViewById(R.id.txt_title);
        mTitleText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWidgetsVisible) {
                    removeWidgets();
                }
                else {
                    addWidgets();
                }
            }
        });

        String sectionRef = mDatatype.getSectionName();
        mClusterWidgets = mWidgetProvider.getClusterWidgets(sectionRef, 0);

        mToolTipLayout = (ToolTipRelativeLayout) mRootView.findViewById(R.id.activity_main_tooltipRelativeLayout);
        ImageView info = (ImageView) mRootView.findViewById(R.id.image_info);
        info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToolTipView == null) {
                    mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mTitleText);
                    //mToolTipView.setOnToolTipViewClickedListener(DvTextWidget.this);
                }
                else {
                    mToolTipView.remove();
                    mToolTipView = null;
                }
            }
        });
        updateLabelsContent();
    }

    private void addWidgets() {
        for (int i = 0; i < mClusterWidgets.size(); i++) {
            mClusterLayout.addView(mClusterWidgets.get(i).getView());
        }
        mWidgetsVisible = true;
        mTitleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_less_white, 0);
    }

    private void removeWidgets() {
        mClusterLayout.removeAllViews();
        mWidgetsVisible = false;
        mTitleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_white, 0);
    }

    /**
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
     */
    @Override
    public void onEhrDatatypeChanged(DvCluster datatype) {
    }

    @Override
    protected void updateLabelsContent() {
        mTitleText.setText(getDisplayTitle());
    }

    @Override
    protected void replaceTooltip(ToolTip tooltip) {
        if (mToolTipView != null) {
            mToolTipView.remove();
            mToolTipView = mToolTipLayout.showToolTipForView(mToolTip, mTitleText);
        }
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#save()
     */
    @Override
    public void save() throws InvalidDatatypeException {
        for (int i = 0; i < mClusterWidgets.size(); i++) {
            mClusterWidgets.get(i).save();
        }
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
     */
    @Override
    public void reset() {
        for (int i = 0; i < mClusterWidgets.size(); i++) {
            mClusterWidgets.get(i).reset();
        }
    }
}
