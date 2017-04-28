/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.widgets;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import it.crs4.most.report.ehr.FormContainer;
import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.EhrDatatype;
import it.crs4.most.report.ehr.datatypes.InnerArchetype;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

/**
 * This class represents a visual widget mapped on a  {@link InnerArchetype} datatype.
 */
public class InnerArchetypeWidget extends ComplexDatatypeWidget<InnerArchetype> {

    private static final String TAG = "InnerArchetypeWidget";

    private boolean mTitleInitialized;
    private boolean mDescriptionInitilized;
    private boolean mCreated = false;
    private String mLanguage = "en";
    private List<DatatypeWidget<EhrDatatype>> mWidgets;
    private FormContainer mFormContainer;

    /**
     * Instantiate a new InnerArchetypeWidget
     *
     * @param provider    the widget provider
     * @param name        the name of this widget
     * @param path        the path of the {@link InnerArchetype} mapped on this widget
     * @param attributes  the attributes of the {@link InnerArchetype} mapped on this widget
     * @param parentIndex the parent index of this widget
     */
    public InnerArchetypeWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex) {
        super(provider, name, new InnerArchetype(provider, path, attributes), parentIndex);
    }

    /**
     * Sets the ontology.
     *
     * @param ontology the new ontology
     */
    @Override
    public void setOntology(JSONObject ontology, String language) {
        mLanguage = language;
        mOntology = ontology;
        setupDescription();
        setupDisplaytitle();
        setupTooltip();
        updateLabelsContent();

        if (mCreated) {
            mWidgetProvider.updateOntologyLanguage(language);
        }
    }

    /**
     * Setup description.
     */
    @Override
    protected void setupDescription() {
        try {
            if (!mDescriptionInitilized) {
                mDescriptionInitilized = true;
                mDescription = mOntology.getJSONObject(mWidgetProvider.getDatatypesSchema()
                    .getString("title")).getString("description");
            }
            else {
                mDescription = mOntology.getJSONObject(mName).getString("description");
            }
        }
        catch (JSONException e) {
            Log.d(TAG, "IS FIRST TIME: " + mDescriptionInitilized);
            Log.e(TAG, "Problems during DESCRIPTION SETUP:" + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Setup displaytitle.
     */
    @Override
    protected void setupDisplaytitle() {
        try {
            if (!mTitleInitialized) {
                mTitleInitialized = true;
                mDisplayTitle = mOntology.getJSONObject(mWidgetProvider.getDatatypesSchema()
                    .getString("title")).getString("text");
            }
            else {
                mDisplayTitle = mOntology.getJSONObject(mName).getString("text");
            }
        }
        catch (JSONException e) {
            Log.e(TAG, "Error retrieving inner archetype title:" + e.getMessage());
            Log.d(TAG, "Current ontology:" + mOntology.toString());
            mDisplayTitle = mName;
        }
    }

    private void buildNewArchetypeContainer() {
        mFormContainer = mWidgetProvider.buildFormView(0);
        mWidgets = mFormContainer.getWidgets();
        mWidgetProvider.updateOntologyLanguage(mLanguage);
    }

    @Override
    protected void addWidgets() {
        if (mFormContainer == null) {
            buildNewArchetypeContainer();
            mCreated = true;
        }
        mWidgetsContainer.addView(mFormContainer.getLayout());
        super.addWidgets();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.inner_archetype;
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
        if (mWidgets != null) {
            for (int i = 0; i < mWidgets.size(); i++) {
                mWidgets.get(i).save();
            }
        }
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
     */
    @Override
    public void reset() {
        if (mWidgets != null) {
            for (int i = 0; i < mWidgets.size(); i++) {
                mWidgets.get(i).reset();
            }
        }
    }
}
