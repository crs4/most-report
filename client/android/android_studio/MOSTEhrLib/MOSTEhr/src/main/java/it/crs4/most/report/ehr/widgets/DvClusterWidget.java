/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.widgets;

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
public class DvClusterWidget extends ComplexDatatypeWidget<DvCluster> {

    private static final String TAG = "DvClusterWidget";
    private List<DatatypeWidget<EhrDatatype>> mWidgets;

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
        String sectionRef = mDatatype.getSectionName();
        mWidgets = mWidgetProvider.getClusterWidgets(sectionRef, 0);
    }

    @Override
    protected void addWidgets() {
        for(DatatypeWidget widget: mWidgets) {
            mWidgetsContainer.addView(widget.getView());
        }
        super.addWidgets();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dv_cluster;
    }

    @Override
    protected void updateLabelsContent() {
        mTitleText.setText(getDisplayTitle());
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#save()
     */
    @Override
    public void save() throws InvalidDatatypeException {
        for (int i = 0; i < mWidgets.size(); i++) {
            mWidgets.get(i).save();
        }
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
     */
    @Override
    public void reset() {
        for (int i = 0; i < mWidgets.size(); i++) {
            mWidgets.get(i).reset();
        }
    }
}
