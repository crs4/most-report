/**
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.ehrlib;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArchetypeFragment extends Fragment {
	
	private WidgetProvider wp;
	private FormContainer formContainer;
	
	public ArchetypeFragment(WidgetProvider wp) {
		this.wp = wp;
	}
	
	public WidgetProvider getwidgetProvider() {
		return this.wp;
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
		this.formContainer = this.wp.buildFormView(0);
		ViewGroup rootView = formContainer.getLayout();
		
		rootView.setBackgroundColor(Color.BLACK);
		return rootView;
	}
	
	public FormContainer getFormContainer()
	{
		return this.formContainer;
	}

}
