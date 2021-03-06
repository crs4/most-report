/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.datatypes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;


/**
 * This class represents a DV_CODED_TEXT item, according to the definition provided by the OpenEHR Data Type Information Model
 */
public class DvCodedText extends EhrDatatype {

    /**
     * The options.
     */
    private ArrayList<String> options = new ArrayList<String>();

    /**
     * The default value.
     */
    private String defaultValue = null;

    /**
     * The terminology.
     */
    String terminology = null;

    /**
     * The selected option index.
     */
    private int selectedOptionIndex = 0;


    /**
     * Instantiates a new DV_CODED_TEXT item.
     *
     * @param path       the path
     * @param attributes the attributes
     */
    public DvCodedText(String path, JSONObject attributes) {

        this.setPath(path);

        try {
            setAttributes(attributes);
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    protected void setAttributes(JSONObject attributes) throws JSONException {
        /*
		 *  "attributes" : {
	                  			"terminology": "local",
	   				  			"options" : ["atXXXX", "atXXXY" , ...],
	   			       			"default" : null
	                  			}
		 */
        this.defaultValue = attributes.optString("default");
        JSONArray jsonOptions = attributes.getJSONArray("options");

        for (int i = 0; i < jsonOptions.length(); i++) {
            String option = jsonOptions.getString(i);
            if (option.equals(this.defaultValue)) {
                this.options.add(0, option);
            }
            else
                this.options.add(option);
        }
    }

    /**
     * Gets the selected option.
     *
     * @return the selected option
     */
    public String getSelectedOption() {
        return this.options.get(selectedOptionIndex);
    }

    /**
     * Sets the selected option index.
     *
     * @param index the new selected option index
     */
    public void setSelectedOptionIndex(int index) {
        this.selectedOptionIndex = index;
    }

    /**
     * Gets the selected option index.
     *
     * @return the selected option index
     */
    public int getSelectedOptionIndex() {
        return this.selectedOptionIndex;
    }

    /* (non-Javadoc)
     * @see it.crs4.most.report.ehr.datatypes.EhrDatatype#fromJSON(org.json.JSONObject)
     */
    @Override
    public void fromJSON(JSONObject data) throws JSONException,
        InvalidDatatypeException {
    }

    /**
     * Gets the options of this DV_CODED_TEXT
     *
     * @return the options
     */
    public String[] getOptions() {
        return this.options.toArray(new String[0]);
    }


    @Override
    public JSONObject toJSON() {
        JSONObject jsonText = new JSONObject();
        try {
            String value = String.format("{\"value\": { \"position\" : \"%s\"}}", this.options.get(this.selectedOptionIndex));

            jsonText = new JSONObject(value);

            return jsonText;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
