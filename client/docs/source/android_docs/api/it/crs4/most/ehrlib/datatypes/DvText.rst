.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.util Log

DvText
======

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public class DvText extends EhrDatatype

   This class represents a DV_CODED_TEXT item, according to the definition provided by the OpenEHR Data Type Information Model

Constructors
------------
DvText
^^^^^^

.. java:constructor:: public DvText(String path, JSONObject attributes)
   :outertype: DvText

   Instantiates a new DV_TEXT item.

   :param path: the path
   :param attributes: the attributes

Methods
-------
fromJSON
^^^^^^^^

.. java:method:: @Override public void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException
   :outertype: DvText

getText
^^^^^^^

.. java:method:: public String getText()
   :outertype: DvText

   Gets the current text of this DV_TEXT item

setAttributes
^^^^^^^^^^^^^

.. java:method:: @Override protected void setAttributes(JSONObject attributes) throws JSONException
   :outertype: DvText

setText
^^^^^^^

.. java:method:: public void setText(String text)
   :outertype: DvText

   Sets the text of this DV_TEXT item

   :param text: the new text

toJSON
^^^^^^

.. java:method:: @Override public JSONObject toJSON()
   :outertype: DvText

