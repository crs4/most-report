.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.util Log

DvQuantity
==========

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public class DvQuantity extends EhrDatatype

   This class represents a DV_QUANTITY item, according to the definition provided by the OpenEHR Data Type Information Model

Constructors
------------
DvQuantity
^^^^^^^^^^

.. java:constructor:: public DvQuantity(String path, JSONObject attributes)
   :outertype: DvQuantity

   Instantiates a new DV_QUANTITY item.

   :param path: the path
   :param attributes: the attributes

Methods
-------
fromJSON
^^^^^^^^

.. java:method:: @Override public void fromJSON(JSONObject content) throws JSONException, InvalidDatatypeException
   :outertype: DvQuantity

getConstraintsInfo
^^^^^^^^^^^^^^^^^^

.. java:method:: public String getConstraintsInfo()
   :outertype: DvQuantity

   Gets the constraints info.

   :return: the constraints info

getMagnitude
^^^^^^^^^^^^

.. java:method:: public double getMagnitude()
   :outertype: DvQuantity

   Gets the current magnitude value of this datatype.

   :return: the magnitude

getMax
^^^^^^

.. java:method:: public int getMax()
   :outertype: DvQuantity

   Gets the maximum value admitted for this DV_QUANTITY item

   :return: the maximum value admitted for this DV_QUANTITY item

getMaxPrecision
^^^^^^^^^^^^^^^

.. java:method:: public int getMaxPrecision()
   :outertype: DvQuantity

   Gets the maximum precision (i.e the maximum number of decimal digits admitted for this DV_QUANTITY item)

   :return: the maximum precision

getMin
^^^^^^

.. java:method:: public int getMin()
   :outertype: DvQuantity

   Gets the minimum value admitted for this DV_QUANTITY item

   :return: the minimum value admitted for this DV_QUANTITY item

getUnits
^^^^^^^^

.. java:method:: public String getUnits()
   :outertype: DvQuantity

   Gets the unit of measure adopted by this DV_QUANTITY item

   :return: the current unit of measure

getValidityMessage
^^^^^^^^^^^^^^^^^^

.. java:method:: public String getValidityMessage(double value)
   :outertype: DvQuantity

   Gets the validity message.

   :param value: the value
   :return: the validity message

isValid
^^^^^^^

.. java:method:: public boolean isValid(double value)
   :outertype: DvQuantity

   Checks if the value provided as argument is valid for this DV_QUANTITY item or not.

   :param value: the value to be checked
   :return: \ ``True``\ , if ithe calue is valid, \ ``False``\  otherwise

setAttributes
^^^^^^^^^^^^^

.. java:method:: @Override protected void setAttributes(JSONObject attributes) throws JSONException
   :outertype: DvQuantity

setMagnitude
^^^^^^^^^^^^

.. java:method:: public void setMagnitude(double magnitude) throws InvalidDatatypeException
   :outertype: DvQuantity

   Sets the magnitude value.

   :param magnitude: the new magnitude value
   :throws InvalidDatatypeException: if a not valid magnitude value is specified

setMax
^^^^^^

.. java:method:: public void setMax(int max)
   :outertype: DvQuantity

   Sets the maximum value admitted for this DV_QUANTITY item

   :param max: the maximum value admitted for this DV_QUANTITY item

setMaxtPrecision
^^^^^^^^^^^^^^^^

.. java:method:: public void setMaxtPrecision(int precision)
   :outertype: DvQuantity

   Sets the maximum precision (i.e the maximum number of decimal digits admitted for this DV_QUANTITY item)

   :param precision: the highest precision

setMin
^^^^^^

.. java:method:: public void setMin(int min)
   :outertype: DvQuantity

   Sets the minimum value admitted for this DV_QUANTITY item

   :param min: the minimum value admitted for this DV_QUANTITY item

setUnits
^^^^^^^^

.. java:method:: public void setUnits(String units)
   :outertype: DvQuantity

   Sets the unit of measure adopted by this DV_QUANTITY item

   :param units: the new unit of measure

toJSON
^^^^^^

.. java:method:: @Override public JSONObject toJSON()
   :outertype: DvQuantity

