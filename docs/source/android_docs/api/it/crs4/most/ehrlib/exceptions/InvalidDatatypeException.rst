.. java:import:: it.crs4.most.ehrlib.datatypes EhrDatatype

InvalidDatatypeException
========================

.. java:package:: it.crs4.most.ehrlib.exceptions
   :noindex:

.. java:type:: public class InvalidDatatypeException extends Exception

   This exception is called when a user attempts to set an invalid value to a \ :java:ref:`EhrDatatype`\  item.

Constructors
------------
InvalidDatatypeException
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public InvalidDatatypeException(String validityMessage)
   :outertype: InvalidDatatypeException

   Instantiates a new invalid datatype exception.

   :param validityMessage: the validity message

