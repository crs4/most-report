package it.crs4.most.report.ehr.datatypes;

import org.json.JSONException;
import org.json.JSONObject;

import it.crs4.most.report.ehr.WidgetProvider;
import it.crs4.most.report.ehr.exceptions.InvalidDatatypeException;

/**
 * This class represents an Archetype item included in another one.
 */
public class InnerArchetype extends EhrDatatype {

    private String mArchetypeClass;
    private WidgetProvider mWidgetProvider;

    /**
     * Instantiates a new Archetype item.
     *
     * @param widgetProvider         the Widget Provider of the archetype
     * @param path       the absolute path of the archetype inside the json structure
     * @param attributes
     */
    public InnerArchetype(WidgetProvider widgetProvider, String path, JSONObject attributes) {
        mWidgetProvider = widgetProvider;
        setPath(path);

        try {
            setAttributes(attributes);
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public WidgetProvider getWidgetProvider() {
        return mWidgetProvider;
    }

    @Override
    protected void setAttributes(JSONObject attributes) throws JSONException {
        mArchetypeClass = attributes.getString("archetype_class");
    }

    /**
     * @return the name of the archetype class
     */
    public String getArchetypeClass() {
        return mArchetypeClass;
    }

    @Override
    public void fromJSON(JSONObject data) throws JSONException,
        InvalidDatatypeException {
        // TODO Auto-generated method stub

    }

    @Override
    public JSONObject toJSON() {

        return mWidgetProvider.toJson();
    }

    /**
     * @return {@code True} if this datatype is an archetype itself  {@code False} otherwise
     */
    @Override
    public boolean isInnerArchetype() {
        return true;
    }

}
