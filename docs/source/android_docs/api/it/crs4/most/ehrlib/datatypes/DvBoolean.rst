.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.util Log

DvBoolean
=========

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public class DvBoolean extends EhrDatatype

   This class represents a DV_BOOLEAN item, according to the definition provided by the OpenEHR Data Type Information Model

Constructors
------------
DvBoolean
^^^^^^^^^

.. java:constructor:: public DvBoolean(String path, JSONObject attributes)
   :outertype: DvBoolean

   Instantiates a new DV_BOOLEAN item.

   :param path: the path
   :param attributes: the attributes

Methods
-------
fromJSON
^^^^^^^^

.. java:method:: @Override public void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException
   :outertype: DvBoolean

getValue
^^^^^^^^

.. java:method:: public boolean getValue()
   :outertype: DvBoolean

   Gets the current value of this DV_BOOLEAN item

   :return: the current text of this DV_TEXT item

setAttributes
^^^^^^^^^^^^^

.. java:method:: @Override protected void setAttributes(JSONObject attributes) throws JSONException
   :outertype: DvBoolean

setValue
^^^^^^^^

.. java:method:: public void setValue(boolean value)
   :outertype: DvBoolean

   Sets the text of this DV_BOOLEAN item

   :param text: the new text

toJSON
^^^^^^

.. java:method:: @Override public JSONObject toJSON()
   :outertype: DvBoolean

