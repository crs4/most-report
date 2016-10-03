/**
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 * <p>
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * This class allows you to display an Archetype in a Fragment. You just have to provide the {@link WidgetProvider} handling the archetype you want to include to the constructor or, if you prefer,
 * you can use the {@link #setWidgetProvider(WidgetProvider)} method (in this second case, remember to call this method before adding the fragment to the container).
 */
public class ArchetypeFragment extends Fragment {

    private WidgetProvider mWidgetProvider;
    private FormContainer formContainer;

    public ArchetypeFragment() {
    }


    /**
     * Create an Archetype fragment by providing the WidgetProvider
     *
     * @param widgetProvider
     */
    public ArchetypeFragment(WidgetProvider widgetProvider) {
        mWidgetProvider = widgetProvider;
    }

    /**
     * Set the widget provider for this fragment. This method must called BEFORE adding the fragment to itws container.
     *
     * @param wp
     */
    public void setWidgetProvider(WidgetProvider wp) {
        this.mWidgetProvider = wp;
    }

    /**
     * @return the widget provider of this Archetype Fragment
     */
    public WidgetProvider getwidgetProvider() {
        return mWidgetProvider;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        formContainer = mWidgetProvider.buildFormView(0);
        ViewGroup rootView = formContainer.getLayout();

        rootView.setBackgroundColor(Color.BLACK);
        return rootView;
    }

    /**
     * Get the form container of this fragment
     *
     * @return the form container of this archetype fragment
     */
    public FormContainer getFormContainer() {
        return formContainer;
    }

}
