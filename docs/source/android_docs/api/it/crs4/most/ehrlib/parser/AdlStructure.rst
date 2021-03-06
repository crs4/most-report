.. java:import:: org.json JSONArray

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

AdlStructure
============

.. java:package:: it.crs4.most.ehrlib.parser
   :noindex:

.. java:type:: public class AdlStructure

   The Class AdlStructure contains a JSON structure representing a whole or a part of an OpenEHR Archetype.

Constructors
------------
AdlStructure
^^^^^^^^^^^^

.. java:constructor:: public AdlStructure(Object item)
   :outertype: AdlStructure

   Instantiates a new adl structure.

   :param item: the structure. It can be a JSONObject (a single structure or datatype) or a JSONArray (a list of structures and/or datatypes).

Methods
-------
count
^^^^^

.. java:method:: public int count()
   :outertype: AdlStructure

   Get the amount of instances of this structure.

   :return: the int

getCardinality
^^^^^^^^^^^^^^

.. java:method:: public StructureCardinality getCardinality()
   :outertype: AdlStructure

   Gets the cardinality of this ADL structure

   :return: the cardinality

getOriginalObject
^^^^^^^^^^^^^^^^^

.. java:method:: public Object getOriginalObject()
   :outertype: AdlStructure

   Gets the original item provided for building this ADL structure.

   :return: the original object

   **See also:** :java:ref:`.AdlStructure(Object)`

getStructure
^^^^^^^^^^^^

.. java:method:: public JSONObject getStructure(int index) throws JSONException
   :outertype: AdlStructure

   Get the index-th occurrence of this ADL structure.

   :param index: the index
   :throws JSONException: the JSON exception
   :return: the adl structure

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: AdlStructure

