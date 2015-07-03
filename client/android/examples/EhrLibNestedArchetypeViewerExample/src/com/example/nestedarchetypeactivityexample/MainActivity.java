package com.example.nestedarchetypeactivityexample;

import android.support.v7.app.ActionBarActivity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
	private Nestablefragment baseFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        baseFragment =  Nestablefragment.newInstance("PARENT FRAGMENT", 12345, "CHILD", 889012);
       
            getFragmentManager().beginTransaction()
                    .add(R.id.container,baseFragment)
                    .commit();
            
            
           
            
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
              @Override
              public void run() {
            	  
              }
            }, 0);
         
            Button b = (Button) findViewById(R.id.but_add_fragment);
            b.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				/*
    				Nestablefragment child = Nestablefragment.newInstance("CHILD FRAGMENT");
    	            FragmentTransaction transaction = baseFragment.getChildFragmentManager().beginTransaction();
    	            transaction.add(R.id.fragment_container, child, "child").commit();
    	           
    	           final Nestablefragment child = Nestablefragment.newInstance("CHILD FRAGMENT", 5678, "CHILD_OF_CHILD", 9012);
    			  FragmentTransaction transaction = child.getChildFragmentManager().beginTransaction();
  	              transaction.add(R.id.fragment_container, child, "child").commit();
    	            */
    				
    			  
  	            
    			}
    		});

    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
