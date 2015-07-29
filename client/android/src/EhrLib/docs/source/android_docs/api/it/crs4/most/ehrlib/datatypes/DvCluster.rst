.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

DvCluster
=========

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public class DvCluster extends EhrDatatype

   It is a particular datatype that is a container for other datatypes.

Constructors
------------
DvCluster
^^^^^^^^^

.. java:constructor:: public DvCluster(String path, JSONObject attributes)
   :outertype: DvCluster

   Instantiates a new DvCluster datatype

   :param path: the path of this datatype
   :param attributes: the attributes of this datatype

Methods
-------
fromJSON
^^^^^^^^

.. java:method:: @Override public void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException
   :outertype: DvCluster

getSectionName
^^^^^^^^^^^^^^

.. java:method:: public String getSectionName()
   :outertype: DvCluster

   Get the name of the datatype section containing all the datatypes of this cluster

   :return: the name of the section

isCluster
^^^^^^^^^

.. java:method:: @Override public boolean isCluster()
   :outertype: DvCluster

setAttributes
^^^^^^^^^^^^^

.. java:method:: @Override protected void setAttributes(JSONObject attributes) throws JSONException
   :outertype: DvCluster

toJSON
^^^^^^

.. java:method:: @Override public JSONObject toJSON()
   :outertype: DvCluster

