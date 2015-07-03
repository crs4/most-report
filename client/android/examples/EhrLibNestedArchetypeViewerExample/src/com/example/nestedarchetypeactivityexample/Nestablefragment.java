package com.example.nestedarchetypeactivityexample;



import android.app.Fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class Nestablefragment extends Fragment {
	
	
	private static final String TAG = "NestableFragment";
	LinearLayout  fragmentContainer = null;
	private String title;
	private int id;
	private String childTitle;
	private int childId = -1;

	static Nestablefragment newInstance(String title, int id, String childTitle, int childId) {
		Nestablefragment fragmentDemo = new  Nestablefragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("id", id);
        args.putString("childTitle" , childTitle);
        args.putInt("childId", childId);
        
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
	
	@Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       this.title = getArguments().getString("title", "Unknown");	
	       this.id = getArguments().getInt("id");
	       this.childId = getArguments().getInt("childId");
	       this.childTitle = getArguments().getString("childTitle");
	   }
	
           
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
		LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		fragmentContainer = new LinearLayout( getActivity() );
		fragmentContainer.setOrientation( LinearLayout.VERTICAL );
		fragmentContainer.setLayoutParams( defaultLayoutParams );
        
		//this.fragmentContainer = (LinearLayout) inflater.inflate(R.layout.fragment_layout, container, false);
		Log.d(TAG, "CONTAINER ID:" + this.id);
		fragmentContainer.setId(this.id);
		
		//TextView titleView =  (TextView) fragmentContainer.findViewById(R.id.fragment_title);         
        //titleView.setText(this.title);
        
        TextView titleView2 = new TextView(getActivity());
        titleView2.setText(this.title);
        
        fragmentContainer.addView(titleView2);
        
        if (childId>0)
        {
        	Log.d(TAG, "ADDING CHILD....");
        	Nestablefragment nf = Nestablefragment.newInstance(this.childTitle,this.childId, "NIPOTE", -1);
        	insertFragment(nf);
        }
        
		return fragmentContainer;
	}
	
	
	private void insertFragment(final Nestablefragment nf)
	{    Log.d(TAG, "Fragment " + title + ": Container:" + fragmentContainer);
		  getChildFragmentManager().beginTransaction().add(fragmentContainer.getId(), nf).commit();
	}
}
