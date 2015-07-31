.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

InnerArchetype
==============

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public class InnerArchetype extends EhrDatatype

   This class represents an Archetype item included in another one.

Constructors
------------
InnerArchetype
^^^^^^^^^^^^^^

.. java:constructor:: public InnerArchetype(WidgetProvider wp, String path, JSONObject attributes)
   :outertype: InnerArchetype

   Instantiates a new Archetype item.

   :param wp: the Widget Provider of the archetype
   :param path: the absolute path of the archetype inside the json structure
   :param attributes:

Methods
-------
fromJSON
^^^^^^^^

.. java:method:: @Override public void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException
   :outertype: InnerArchetype

getArchetypeClass
^^^^^^^^^^^^^^^^^

.. java:method:: public String getArchetypeClass()
   :outertype: InnerArchetype

   :return: the name of the archetype class

getWidgetProvider
^^^^^^^^^^^^^^^^^

.. java:method:: public WidgetProvider getWidgetProvider()
   :outertype: InnerArchetype

isInnerArchetype
^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean isInnerArchetype()
   :outertype: InnerArchetype

   :return: \ ``True``\  if this datatype is an archetype itself \ ``False``\  otherwise

setAttributes
^^^^^^^^^^^^^

.. java:method:: @Override protected void setAttributes(JSONObject attributes) throws JSONException
   :outertype: InnerArchetype

toJSON
^^^^^^

.. java:method:: @Override public JSONObject toJSON()
   :outertype: InnerArchetype

