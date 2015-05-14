.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

EhrDatatype
===========

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public abstract class EhrDatatype

   This is the base class for all data types included in a generic Archetype, as defined by the OpenEHR ADL structure

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

   The path of the datatype

Methods
-------
fromJSON
^^^^^^^^

.. java:method:: public abstract void fromJSON(JSONObject data) throws JSONException, InvalidDatatypeException
   :outertype: EhrDatatype

   Load the new content of this datatype from a json schema.

   :param data: the json structure representing this datatype
   :throws InvalidDatatypeException: the invalid datatype exception
   :throws JSONException: if a malformed json structure was provided

getPath
^^^^^^^

.. java:method:: public String getPath()
   :outertype: EhrDatatype

   Gets the path of this datatype

   :return: the path

isCluster
^^^^^^^^^

.. java:method:: public boolean isCluster()
   :outertype: EhrDatatype

   :return: \ ``True``\  if this datatype is a container for other datatypes, \ ``False``\  otherwise

setAttributes
^^^^^^^^^^^^^

.. java:method:: protected abstract void setAttributes(JSONObject attributes) throws JSONException
   :outertype: EhrDatatype

   Sets the attributes for this datatype. Generally, different datatypes have different attributes.

   :param attributes: the json structure containing all the attributes of this datatype.
   :throws JSONException: if a malformed json structure was provided

setDatatypeChangeListener
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setDatatypeChangeListener(EhrDatatypeChangeListener datatypeChangeListener)
   :outertype: EhrDatatype

   Sets the Event listener interface for 'change' events.

   :param datatypeChangeListener: the Listener where to notify any content modification of this datatype

setPath
^^^^^^^

.. java:method:: protected void setPath(String path)
   :outertype: EhrDatatype

   Sets the path of this datatype.

   :param path: the new path

toJSON
^^^^^^

.. java:method:: public abstract JSONObject toJSON()
   :outertype: EhrDatatype

   Get the json structure representing the current state of this datatype.

   :return: the JSON structure representing the current state of this datatype

