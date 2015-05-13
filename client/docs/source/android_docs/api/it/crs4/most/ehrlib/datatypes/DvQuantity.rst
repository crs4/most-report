.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.util Log

DvQuantity
==========

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public class DvQuantity extends EhrDatatype

   The Class DvQuantity.

Constructors
------------
DvQuantity
^^^^^^^^^^

.. java:constructor:: public DvQuantity(String path, JSONObject attributes)
   :outertype: DvQuantity

   Instantiates a new dv quantity.

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

   Gets the magnitude.

   :return: the magnitude

getMax
^^^^^^

.. java:method:: public int getMax()
   :outertype: DvQuantity

   Gets the max.

   :return: the max

getMaxPrecision
^^^^^^^^^^^^^^^

.. java:method:: public int getMaxPrecision()
   :outertype: DvQuantity

   Gets the max precision.

   :return: the max precision

getMin
^^^^^^

.. java:method:: public int getMin()
   :outertype: DvQuantity

   Gets the min.

   :return: the min

getUnits
^^^^^^^^

.. java:method:: public String getUnits()
   :outertype: DvQuantity

   Gets the units.

   :return: the units

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

   Checks if is valid.

   :param value: the value
   :return: true, if is valid

setAttributes
^^^^^^^^^^^^^

.. java:method:: @Override protected void setAttributes(JSONObject attributes) throws JSONException
   :outertype: DvQuantity

setMagnitude
^^^^^^^^^^^^

.. java:method:: public void setMagnitude(double magnitude) throws InvalidDatatypeException
   :outertype: DvQuantity

   Sets the magnitude.

   :param magnitude: the new magnitude
   :throws InvalidDatatypeException: the invalid datatype exception

setMax
^^^^^^

.. java:method:: public void setMax(int max)
   :outertype: DvQuantity

   Sets the max.

   :param max: the new max

setMaxPrecision
^^^^^^^^^^^^^^^

.. java:method:: public void setMaxPrecision(int precision)
   :outertype: DvQuantity

   Sets the max precision.

   :param precision: the new max precision

setMin
^^^^^^

.. java:method:: public void setMin(int min)
   :outertype: DvQuantity

   Sets the min.

   :param min: the new min

setUnits
^^^^^^^^

.. java:method:: public void setUnits(String units)
   :outertype: DvQuantity

   Sets the units.

   :param units: the new units

toJSON
^^^^^^

.. java:method:: @Override public JSONObject toJSON()
   :outertype: DvQuantity

