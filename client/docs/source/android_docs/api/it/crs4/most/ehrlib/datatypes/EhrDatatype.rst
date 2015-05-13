.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

EhrDatatype
===========

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public abstract class EhrDatatype

   Defines a generic openEHR datatype.

   :author: smonni

Fields
------
datatypeChangeListener
^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: protected EhrDatatypeChangeListener datatypeChangeListener
   :outertype: EhrDatatype

   The datatype change listener.

path
^^^^

.. java:field:: protected String path
   :outertype: EhrDatatype

   The path.

Methods
-------
fromJSON
^^^^^^^^

.. java:method:: public abstract void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException
   :outertype: EhrDatatype

   From json.

   :param data: the data
   :throws InvalidDatatypeException: the invalid datatype exception
   :throws JSONException: the JSON exception

getPath
^^^^^^^

.. java:method:: public String getPath()
   :outertype: EhrDatatype

   Gets the path.

   :return: the path

isCluster
^^^^^^^^^

.. java:method:: public boolean isCluster()
   :outertype: EhrDatatype

setAttributes
^^^^^^^^^^^^^

.. java:method:: protected abstract void setAttributes(JSONObject attributes) throws JSONException
   :outertype: EhrDatatype

   Sets the attributes.

   :param attributes: the new attributes
   :throws JSONException: the JSON exception

setDatatypeChangeListener
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setDatatypeChangeListener(EhrDatatypeChangeListener datatypeChangeListener)
   :outertype: EhrDatatype

   Sets the datatype change listener.

   :param datatypeChangeListener: the new datatype change listener

setPath
^^^^^^^

.. java:method:: protected void setPath(String path)
   :outertype: EhrDatatype

   Sets the path.

   :param path: the new path

toJSON
^^^^^^

.. java:method:: public abstract JSONObject toJSON()
   :outertype: EhrDatatype

   To json.

   :return: the JSON object

