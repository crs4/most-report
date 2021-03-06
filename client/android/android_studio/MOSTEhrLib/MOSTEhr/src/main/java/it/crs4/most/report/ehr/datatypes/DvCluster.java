/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.datatypes;

import org.json.JSONException;
import org.json.JSONObject;

import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

/**
 * It is a particular datatype that is a container for other datatypes.
 */
public class DvCluster extends EhrDatatype {


    private String clusterSectionName = null;

    /**
     * Instantiates a new DvCluster datatype
     *
     * @param path       the path of this datatype
     * @param attributes the attributes of this datatype
     */
    public DvCluster(String path, JSONObject attributes) {

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
    public boolean isCluster() {
        return true;
    }

    @Override
    protected void setAttributes(JSONObject attributes) throws JSONException {
        this.clusterSectionName = attributes.getString("datatype_ref");
    }

    /**
     * Get the name of the datatype section containing all the datatypes of this cluster
     *
     * @return the name of the section
     */
    public String getSectionName() {
        return this.clusterSectionName;
    }

    @Override
    public void fromJSON(JSONObject data) throws JSONException,
        InvalidDatatypeException {
        // TODO Auto-generated method stub

    }

    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }

}
