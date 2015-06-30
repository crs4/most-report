package com.example.ehrtemplateviewerexample;

import it.crs4.most.ehrlib.FormContainer;
import it.crs4.most.ehrlib.WidgetProvider;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArchetypeFragment extends Fragment {
	
	private WidgetProvider wp;
	
	public ArchetypeFragment(WidgetProvider wp) {
		this.wp = wp;
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
		FormContainer  formContainer = this.wp.buildFormView(0);
		ViewGroup rootView = formContainer.getLayout();
		rootView.setBackgroundColor(Color.BLACK);
		return rootView;
	}

}
