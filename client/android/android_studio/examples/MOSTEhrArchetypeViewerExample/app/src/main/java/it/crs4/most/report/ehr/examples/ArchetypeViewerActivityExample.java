/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */


package it.crs4.most.report.ehr.examples;

import org.json.JSONException;

import it.crs4.most.report.ehr.FormContainer;
import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.method.ScrollingMovementMethod;
import android.app.Dialog;
import android.content.Context;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This example allow you how to load an Archetype
 */
public class ArchetypeViewerActivityExample extends ActionBarActivity {

    /**
     * The Constant LANGUAGE.
     */
    public static final String LANGUAGE = "es-ar"; // "es-ar"; //

    /**
     * The Constant TAG.
     */
    public static final String TAG = "ArchetypeViewerActivityExample";

    /**
     * The json datatypes schema.
     */
    private static String JSON_SCHEMA_DATATYPES = "ecg/schema_datatypes_ECG_recording_v1.json";
    private static String JSON_SCHEMA_DATATYPES2 = "blood_pressure/schema_datatypes_blood_pressure_v1.json";

    /**
     * The json layout schema.
     */
    private static String JSON_SCHEMA_LAYOUT = "ecg/schema_layout_ECG_recording_v1.json";
    private static String JSON_SCHEMA_LAYOUT2 = "blood_pressure/schema_layout_blood_pressure_v1.json";

    /**
     * The json ontology.
     */
    private static String JSON_SCHEMA_ONTOLOGY = "ecg/schema_ontology_ECG_recording_v1.json";
    private static String JSON_SCHEMA_ONTOLOGY2 = "blood_pressure/schema_ontology_blood_pressure_v1.json";

    /**
     * The json instances.
     */
    private static String JSON_SCHEMA_INSTANCE = "ecg/schema_adl_void_instance_ECG_recording_v1.json";
    private static String JSON_SCHEMA_INSTANCE2 = "blood_pressure/schema_adl_void_instance_blood_pressure_v1.json";

    private ArchetypeViewerFragment archetypeFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blood_pressure);
        archetypeFragment = new ArchetypeViewerFragment(getApplicationContext());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, archetypeFragment)
                    .commit();
        }

    }

    public void loadArchetypeFragment(String datatypes, String ontology, String instances, String schema, String language) {

        Log.d(TAG, "Replacing the fragment....");
        FragmentManager fm = getSupportFragmentManager();

        ArchetypeViewerFragment f = new ArchetypeViewerFragment(getApplicationContext(), datatypes, ontology, instances, schema, language);
        fm.beginTransaction().replace(R.id.container, f).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blood_pressure, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_quit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A fragment containing a form for The Archetype (e.g Blood Pressure) handling.
     */
    public static class ArchetypeViewerFragment extends Fragment {


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.action_fragment, menu);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_spain:
                    widgetProvider.updateOntologyLanguage("es-ar");
                    return true;
                case R.id.menu_english:

                    widgetProvider.updateOntologyLanguage("en");
                    return true;


            }
            return super.onOptionsItemSelected(item);
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        /**
         * The form container.
         */
        private FormContainer formContainer = null;

        /**
         * The widget provider.
         */
        private WidgetProvider widgetProvider;


        private String datatypes = null;
        private String ontology = null;
        private String instances = null;
        private String schema = null;
        private String language = null;


        /**
         * Instantiates a new Fragment for rendering the Archetype
         */
        public ArchetypeViewerFragment(Context ctx) {
            this(ctx, null, null, null, null, null);
        }

        public ArchetypeViewerFragment(Context ctx, String datatypes, String ontology, String instances, String schema, String language) {

            this.datatypes = (datatypes == null ? WidgetProvider.parseFileToString(ctx, JSON_SCHEMA_DATATYPES2) : datatypes);
            this.ontology = (ontology == null ? WidgetProvider.parseFileToString(ctx, JSON_SCHEMA_ONTOLOGY2) : ontology);
            this.instances = (instances == null ? WidgetProvider.parseFileToString(ctx, JSON_SCHEMA_INSTANCE2) : instances);
            this.schema = (schema == null ? WidgetProvider.parseFileToString(ctx, JSON_SCHEMA_LAYOUT2) : schema);
            this.instances = (instances == null ? WidgetProvider.parseFileToString(ctx, JSON_SCHEMA_INSTANCE2) : instances);
            this.language = (language == null ? LANGUAGE : language);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            try {
                Log.d(TAG, String.format("--->>>> Activity:%s DT:%s,ON:%s, IN:%s. SC: %s, LA:%s", getActivity(), datatypes, ontology, instances, schema, language));
                widgetProvider = new WidgetProvider(getActivity(),
                        this.datatypes,
                        this.ontology,
                        this.instances,
                        this.schema,
                        this.language);

                this.formContainer = widgetProvider.buildFormView(0);


            } catch (JSONException e1) {
                Log.e(TAG, "Error reading json data: " + e1);
                e1.printStackTrace();
            } catch (InvalidDatatypeException e) {
                Log.e(TAG, "Error building widgets: " + e);
                e.printStackTrace();
            }

            // Buttons Panel
            View buttonsPanel = inflater.inflate(R.layout.datatype_form_buttons, null);
            ViewGroup rootView = formContainer.getLayout();

            rootView.addView(buttonsPanel);

            rootView.setBackgroundColor(Color.BLACK);
            setupButtonsListeners(buttonsPanel);
            return rootView;
        }


        /**
         * Show info dialog.
         *
         * @param content the content
         */
        private void showInfoDialog(String content) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.setTitle("Json Adl Structure");
            dialog.setContentView(R.layout.custom_dialog);
            TextView dialogText = (TextView) dialog.findViewById(R.id.dialogText);
            dialogText.setMovementMethod(new ScrollingMovementMethod());
            dialogText.setText(content);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            dialogButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            dialog.show();
        }

        /**
         * Sets the up buttons listeners.
         *
         * @param buttonsPanel the new up buttons listeners
         */
        private void setupButtonsListeners(View buttonsPanel) {
            Button butJson = (Button) buttonsPanel.findViewById(R.id.butJson);
            butJson.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String content;
                    try {
                        content = widgetProvider.toJson().toString(2);
                        showInfoDialog(content);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        showInfoDialog(String.format("Error Parsing Json Content: \n\n %s", e.getMessage()));
                    }

                }
            });


            Button butLoad = (Button) buttonsPanel.findViewById(R.id.butLoad);
            //butLoad.setOnClickListener(new OnClickListener() {});


            Button butReset = (Button) buttonsPanel.findViewById(R.id.butReset);
            butReset.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    formContainer.resetAllWidgets();

                }
            });

            Button butSave = (Button) buttonsPanel.findViewById(R.id.butSave);
            butSave.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {

                        formContainer.submitAllWidgets();
                        widgetProvider.updateSectionsJsonContent(0);

                    } catch (InvalidDatatypeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.e(TAG, "Invalid datatype:" + e.getMessage());
                        Toast.makeText(getActivity(), "Invalid Input value:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.e(TAG, "Invalid JSON PARSING:" + e.getMessage());
                    }
                }
            });
        }
    }
}
