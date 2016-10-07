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
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.nhaarman.supertooltips.ToolTip;

import org.json.JSONException;
import org.json.JSONObject;

import it.crs4.most.report.ehr.R;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.datatypes.EhrDatatype;
import it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

/**
 * This the base class for all DatatypeWidgets. A {@link DatatypeWidget}  is a visual and interactive widget mapped on a specific {@link EhrDatatype}.
 * A user can instantiate a {@link DatatypeWidget}  for reading, editing and saving the content of the {@link EhrDatatype} handled by it.
 *
 * @param <T> the generic {@link EhrDatatype}
 */
public abstract class DatatypeWidget<T extends EhrDatatype> implements EhrDatatypeChangeListener<T> {

    private String TAG = "DatatypeWidget";
    protected String mName;
    protected int mPriority;
    protected int mParentIndex;
    protected View mRootView;
    protected Context mContext;
    protected T mDatatype;
    protected JSONObject mOntology = null;
    protected String mDescription = "N.A";
    protected String mDisplayTitle;
    protected ToolTip mToolTip;
    protected WidgetProvider mWidgetProvider;

    // Nedeed for InnerArchetypeWidget that does not call the super constructor.
    protected DatatypeWidget() {
    }

    /**
     * Instantiates a new {@link DatatypeWidget} widget.
     *
     * @param name         the name of this widget
     * @param datatype     the {@link EhrDatatype} to be handled by this widget
     * @param parentIndex the parent_index
     */
    public DatatypeWidget(WidgetProvider provider, String name, T datatype, int parentIndex) {
        mWidgetProvider = provider;
        mName = name;
        mContext = provider.getContext();
        mOntology = provider.getOntology();
        mParentIndex = parentIndex;
        mDatatype = datatype;
        mDatatype.setDatatypeChangeListener(this);

        setupDisplaytitle();
        setupDescription();
        setupTooltip();
    }

    /**
     * Update the content of the labels of the widget, according to the current ontology language.
     */
    protected abstract void updateLabelsContent();

    /**
     * Replace tooltip.
     *
     * @param tooltip the tooltip
     */
    protected abstract void replaceTooltip(ToolTip tooltip);

    public WidgetProvider getWidgetProvider() {
        return mWidgetProvider;
    }

    /**
     * Setup tooltip.
     */
    protected void setupTooltip() {
        mToolTip = new ToolTip()
            .withText(getDescription())
            .withColor(mContext.getResources().getColor(R.color.colorTooltipBackground))
            .withShadow()
            .withTextColor(mContext.getResources().getColor(R.color.colorTooltipText))
            .withAnimationType(ToolTip.AnimationType.FROM_TOP);
        replaceTooltip(mToolTip);
    }

    /**
     * Gets the tool tip.
     *
     * @return the tool tip
     */
    public ToolTip getToolTip() {
        return mToolTip;
    }

    /**
     * Sets the ontology.
     *
     * @param ontology the new ontology
     * @param lang     the language of the new loaded ontology
     */
    public void setOntology(JSONObject ontology, String lang) {
        mOntology = ontology;
        setupDescription();
        setupDisplaytitle();
        setupTooltip();

        updateLabelsContent();
    }

    /**
     * Setup displaytitle.
     */
    private void setupDisplaytitle() {
        try {
            mDisplayTitle = mOntology.getJSONObject(mName).getString("text");
        }
        catch (JSONException e) {
            Log.e(TAG, String.format("Key %s not found for datatype with ontology: %s", mName, mOntology));
            mDisplayTitle = mName;
        }
    }

    /**
     * Gets the display title.
     *
     * @return the display title
     */
    public String getDisplayTitle() {
        return mDisplayTitle;
    }

    /**
     * Setup description.
     */
    private void setupDescription() {
        try {
            mDescription = mOntology.getJSONObject(mName).getString("description");
        }
        catch (JSONException e) {
            Log.e(TAG, "Problems during DESCRIPTION SETUP:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return mDescription;
    }


    /**
     * Gets the {@link EhrDatatype} handled by this widget
     *
     * @return the {@link EhrDatatype}
     */
    public T getDatatype() {
        return mDatatype;
    }


    /**
     * Update the value of the underlying datatype according to the current value of the fields of this widget.
     *
     * @throws InvalidDatatypeException if the current value of the fields cannot be converted to a datatype value
     */
    public abstract void save() throws InvalidDatatypeException;


    /**
     * Reset all fields of this widget according to the current underlying datatype value.
     */
    public abstract void reset();

    /**
     * get the Root View containing this widget's view elements.
     *
     * @return the view
     */
    public View getView() {
        return mRootView;
    }


    /**
     * Gets the parent index.
     *
     * @return the parent index
     */
    public int getParentIndex() {
        return mParentIndex;
    }

    /**
     * set the visibility of this widget.
     *
     * @param value the new visibility
     */
    public void setVisibility(int value) {
        mRootView.setVisibility(value);
    }


    // -----------------------------------------------
    //
    // set / get priority
    //
    // -----------------------------------------------

    /**
     * sets the visual priority of this widget
     * essentially this means it's physical location in the form.
     *
     * @param value the new priority
     */
    public void setPriority(int value) {
        mPriority = value;
    }

    /**
     * returns the visual priority of this widget (essentially this means it's physical location in the form).
     *
     * @return the priority
     */
    public int getPriority() {
        return mPriority;
    }


    /**
     * returns the name of this widget
     *
     * @return the name
     */
    public String getName() {
        return mName;
    }
}
