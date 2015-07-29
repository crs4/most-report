.. java:import:: java.util ArrayList

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONArray

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.util Log

DvCodedText
===========

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public class DvCodedText extends EhrDatatype

   This class represents a DV_CODED_TEXT item, according to the definition provided by the OpenEHR Data Type Information Model

Fields
------
terminology
^^^^^^^^^^^

.. java:field::  String terminology
   :outertype: DvCodedText

   The terminology.

Constructors
------------
DvCodedText
^^^^^^^^^^^

.. java:constructor:: public DvCodedText(String path, JSONObject attributes)
   :outertype: DvCodedText

   Instantiates a new DV_CODED_TEXT item.

   :param path: the path
   :param attributes: the attributes

Methods
-------
fromJSON
^^^^^^^^

.. java:method:: @Override public void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException
   :outertype: DvCodedText

getOptions
^^^^^^^^^^

.. java:method:: public String[] getOptions()
   :outertype: DvCodedText

   Gets the options of this DV_CODED_TEXT

   :return: the options

getSelectedOption
^^^^^^^^^^^^^^^^^

.. java:method:: public String getSelectedOption()
   :outertype: DvCodedText

   Gets the selected option.

   :return: the selected option

getSelectedOptionIndex
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public int getSelectedOptionIndex()
   :outertype: DvCodedText

   Gets the selected option index.

   :return: the selected option index

setAttributes
^^^^^^^^^^^^^

.. java:method:: @Override protected void setAttributes(JSONObject attributes) throws JSONException
   :outertype: DvCodedText

setSelectedOptionIndex
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setSelectedOptionIndex(int index)
   :outertype: DvCodedText

   Sets the selected option index.

   :param index: the new selected option index

toJSON
^^^^^^

.. java:method:: @Override public JSONObject toJSON()
   :outertype: DvCodedText

