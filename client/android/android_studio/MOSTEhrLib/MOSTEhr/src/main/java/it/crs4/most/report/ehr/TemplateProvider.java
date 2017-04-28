/**
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 * <p>
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

/**
 * This class represents a visual Archetypes Template, according to the OpenEHR specifications.
 * A template is an ordered list of {@link WidgetProvider} , each of them contains the layout of a specific archetype of the template-
 */
public class TemplateProvider {

    private Context mContext;
    private JSONObject mTemplateSchema;
    private ArchetypeSchemaProvider mArchetypeSchemaProvider;
    private List<WidgetProvider> mWidgetProviders;
    private String mLanguage;
    private static final String TAG = "TemplateProvider";
    private String mName;
    private String mId;


    /**
     * Creates the template, building all the archetypes specified in the provided json schemas.
     *
     * @param context                 the application Context
     * @param templateSchema          the json schema of the template
     * @param archetypeSchemaProvider the archetype schema provider
     * @param language                the default ontology language
     * @throws JSONException if an error occurred during the parsing of the json schemas
     */
    public TemplateProvider(Context context, String templateSchema, ArchetypeSchemaProvider archetypeSchemaProvider, String language) throws JSONException {
        this.mContext = context;
        this.mTemplateSchema = new JSONObject(templateSchema);

        this.mArchetypeSchemaProvider = archetypeSchemaProvider;
        this.mLanguage = language;

        this.buildtemplate();
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    /**
     * Get the list of the widget providers of this template, one for each archetype
     *
     * @return
     */
    public List<WidgetProvider> getWidgetProviders() {
        return mWidgetProviders;
    }

    private void buildtemplate() {
        try {

            mId = mTemplateSchema.getString("id");
            mName = mTemplateSchema.getString("name");

            JSONArray archetype_classes = mTemplateSchema.getJSONArray("definition");

            mWidgetProviders = new ArrayList<>();

            for (int i = 0; i < archetype_classes.length(); i++) {
                String archetypeClass = archetype_classes.getJSONObject(i).getString("archetype_class");
                String jsonExclude = archetype_classes.getJSONObject(i).getJSONArray("exclude").toString();
                try {
                    WidgetProvider wp = new WidgetProvider(mContext, mArchetypeSchemaProvider, archetypeClass, mLanguage, jsonExclude);

                    mWidgetProviders.add(wp);

                }
                catch (InvalidDatatypeException e) {
                    Log.e(TAG, "InvalidDatatypeException:" + e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
        }
        catch (JSONException e) {
            Log.e(TAG, "JSON Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
