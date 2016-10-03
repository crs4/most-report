/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.datatypes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;


/**
 * This class represents a DV_QUANTITY item, according to the definition provided by the OpenEHR Data Type Information Model
 */
public class DvQuantity extends EhrDatatype {

    /**
     * The Constant TAG.
     */
    private static final String TAG = "DvQuantity";

    /**
     * The magnitude.
     */
    private Double mMagnitude;

    /**
     * The units.
     */
    private String mUnits = "????";

    /**
     * The precision.
     */
    private int mPrecision = 0;

    /**
     * The min.
     */
    private Integer mMin = null;

    /**
     * The max.
     */
    private Integer mMax = null;


    /**
     * Instantiates a new DV_QUANTITY item.
     *
     * @param path       the path
     * @param attributes the attributes
     */
    public DvQuantity(String path, JSONObject attributes) {
        setPath(path);
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
        Log.d(TAG, String.format("Setting datatype attributes: %s", attributes));
        setUnits(attributes.getString("unit_of_measure"));
        if (attributes.has("precision")) {
            mPrecision = attributes.getInt("precision");
        }
        if (attributes.has("range")) {
            if (attributes.getJSONObject("range").has("min")) {
                setMin(attributes.getJSONObject("range").getInt("min"));
            }
            if (attributes.getJSONObject("range").has("max")) {
                setMax(attributes.getJSONObject("range").getInt("max"));
            }
        }
    }

    /**
     * Checks if the value provided as argument is valid for this DV_QUANTITY item or not.
     *
     * @param value the value to be checked
     * @return {@code True}, if ithe calue is valid, {@code False} otherwise
     */
    public boolean isValid(double value) {
        return ((mMin == null || value >= mMin) && (mMax == null || value <= mMax)); // && getPrecision(value)<=precision);
    }

    /**
     * Gets the amount of decimal digits of the value provided as argument
     *
     * @param value the value
     * @return the amount of decimal digits of the value provided as argument
     */
    private int getPrecision(double value) {
        String sval = String.valueOf(value);

        int decimalIndexOf = sval.indexOf(".");
        if (decimalIndexOf < 0 || (value - (int) value == 0)) {
            return 0;
        }
        else {
            return sval.substring(decimalIndexOf).length() - 1;
        }
    }

    /**
     * Gets the validity message.
     *
     * @param value the value
     * @return the validity message
     */
    public String getValidityMessage(double value) {
        if (mMin != null && value < mMin) {
            return String.format("Value too low: %s The MIN value is %s", value, mMin);
        }
        else if (mMax != null && value > mMax) {
            return String.format("Value too high: %s The MAX value is %s", value, mMax);
        }
        else if (getPrecision(value) > mPrecision) {
            return String.format("Too many decimal digits for %s:%s (max:%s)", value, getPrecision(value), mPrecision);
        }
        else {
            return String.format("Ok %s", getConstraintsInfo());
        }
    }

    /**
     * Gets the constraints info.
     *
     * @return the constraints info
     */
    public String getConstraintsInfo() {
        if (mMin != null && mMax != null) {
            return String.format("(Valid Range: [%s, %s] - Max Precision: %s)", mMin, mMax, mPrecision);
        }
        else if (mMin == null && mMax == null) {
            return String.format("Max Precision: %s", mPrecision);
        }
        else if (mMin != null) {
            return String.format("(Min Value: %s - Max Precision: %s)", mMin, mPrecision);
        }
        else {
            return String.format("(Max Value: %s - Max Precision: %s)", mMax, mPrecision);
        }
    }

    /**
     * Gets the current magnitude value of this datatype.
     *
     * @return the magnitude
     */
    public Double getMagnitude() {
        return mMagnitude;
    }

    /**
     * Sets the magnitude value.
     *
     * @param magnitude the new magnitude value
     * @throws InvalidDatatypeException if a not valid magnitude value is specified
     */
    public void setMagnitude(double magnitude) throws InvalidDatatypeException {
        if (isValid(magnitude)) {
            Log.d(TAG, "Magnitude to save:" + magnitude);
            double factor = Math.pow(10, mPrecision);
            mMagnitude = Math.round(magnitude * factor) / factor;
            Log.d(TAG, "Magnitude to save after:" + mMagnitude + " [" + this + "]");
            //
            if (datatypeChangeListener != null)
                datatypeChangeListener.onEhrDatatypeChanged(this);
        }
        else {
            throw new InvalidDatatypeException(getValidityMessage(magnitude));
        }
    }

    /**
     * Gets the unit of measure adopted by this DV_QUANTITY item
     *
     * @return the current unit of measure
     */
    public String getUnits() {
        Log.d(TAG, String.format("Calling get unity() from %s return: %s", this, mUnits));
        return mUnits;
    }

    /**
     * Sets the unit of measure adopted by this DV_QUANTITY item
     *
     * @param units the new unit of measure
     */
    public void setUnits(String units) {
        mUnits = units;
    }

    /**
     * Gets the maximum precision (i.e the maximum number of decimal digits admitted for this DV_QUANTITY item)
     *
     * @return the maximum precision
     */
    public int getMaxPrecision() {
        return mPrecision;
    }

    /**
     * Sets the maximum precision  (i.e the maximum number of decimal digits admitted for this DV_QUANTITY item)
     *
     * @param precision the highest precision
     */
    public void setMaxtPrecision(int precision) {
        mPrecision = precision;
    }

    /**
     * Gets the minimum value admitted for this DV_QUANTITY item
     *
     * @return the minimum value admitted for this DV_QUANTITY item
     */
    public int getMin() {
        return mMin;
    }

    /**
     * Sets the minimum value admitted for this DV_QUANTITY item
     *
     * @param min the minimum value admitted for this DV_QUANTITY item
     */
    public void setMin(int min) {
        mMin = min;
    }

    /**
     * Gets the maximum value admitted for this DV_QUANTITY item
     *
     * @return the maximum value admitted for this DV_QUANTITY item
     */
    public int getMax() {
        return mMax;
    }

    /**
     * Sets the maximum value admitted for this DV_QUANTITY item
     *
     * @param max the maximum value admitted for this DV_QUANTITY item
     */
    public void setMax(int max) {
        mMax = max;
    }

    @Override
    public void fromJSON(JSONObject content) throws JSONException, InvalidDatatypeException {
        if (!content.isNull("magnitude")) {
            setMagnitude(content.getDouble("magnitude"));
        }

        if (!content.isNull("units")) {
            setUnits(content.getString("units"));
        }

        if (datatypeChangeListener != null) {
            datatypeChangeListener.onEhrDatatypeChanged(this);
        }
    }

    @Override
    public JSONObject toJSON() {
        try {
            String value = String.format("{ \"value\": { \"magnitude\" : %s , \"units\":\"%s\"}}", mMagnitude, mUnits);
            return new JSONObject(value);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
