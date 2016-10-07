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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

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
public class InnerArchetypeWidget extends DatatypeWidget<InnerArchetype> {

    private static final String TAG = "InnerArchetypeWidget";

    private boolean mWidgetsVisible = false;
    private boolean mCreated = false;
    private String mLanguage = "en";
    private List<DatatypeWidget<EhrDatatype>> mArchetypeWidgets;
    private TextView mTitleText;
    private ToolTipRelativeLayout mToolTipLayout;
    private ToolTipView mToolTipView;
    private FrameLayout mArchetypeLayout;
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
        mWidgetProvider = provider;
        mName = name;
        mContext = provider.getContext();
        mOntology = provider.getOntology();
        mParentIndex = parentIndex;
        mDatatype = new InnerArchetype(provider, path, attributes);
        mDatatype.setDatatypeChangeListener(this);

        setupDisplaytitle(true);
        setupDescription(true);

        super.setupTooltip();

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.inner_archetype, null);
        mArchetypeLayout = (FrameLayout) mRootView.findViewById(R.id.archetype_container);
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

        mTitleText = (TextView) mRootView.findViewById(R.id.txt_archetype_title);
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

        updateLabelsContent();
        mToolTipLayout = (ToolTipRelativeLayout) mRootView.findViewById(R.id.activity_main_tooltipRelativeLayout);
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

    private void setupDescription() {
        setupDescription(false);
    }

    /**
     * Setup description.
     */
    private void setupDescription(boolean firstRequest) {
        try {
            if (firstRequest)
                mDescription = mOntology.getJSONObject(mWidgetProvider.getDatatypesSchema()
                    .getString("title")).getString("description");
            else
                mDescription = mOntology.getJSONObject(mName).getString("description");

        }
        catch (JSONException e) {
            Log.e(TAG, "Problems during DESCRIPTION SETUP:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupDisplaytitle() {
        setupDisplaytitle(false);
    }

    /**
     * Setup displaytitle.
     */
    private void setupDisplaytitle(boolean firstRequest) {
        try {
            if (firstRequest) {
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
        mArchetypeWidgets = mFormContainer.getWidgets();
        mWidgetProvider.updateOntologyLanguage(mLanguage);
    }

    private void addWidgets() {
        if (mFormContainer == null) {
            buildNewArchetypeContainer();
            mCreated = true;
        }
        mArchetypeLayout.addView(mFormContainer.getLayout());
        mWidgetsVisible = true;
        mTitleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_less_white, 0);
    }

    private void removeWidgets() {
        mArchetypeLayout.removeAllViews();
        mWidgetsVisible = false;
        mTitleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_white, 0);
    }

    /**
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatypeChangeListener#onEhrDatatypeChanged(it.crs4.most.report.ehr.datatypes.EhrDatatype)
     */
    @Override
    public void onEhrDatatypeChanged(InnerArchetype datatype) {

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
        if (mArchetypeWidgets != null) {
            for (int i = 0; i < mArchetypeWidgets.size(); i++) {
                mArchetypeWidgets.get(i).save();
            }
        }
    }

    /**
     * @see it.crs4.most.report.ehr.widgets.DatatypeWidget#reset()
     */
    @Override
    public void reset() {
        if (mArchetypeWidgets != null) {
            for (int i = 0; i < mArchetypeWidgets.size(); i++) {
                mArchetypeWidgets.get(i).reset();
            }
        }
    }
}
