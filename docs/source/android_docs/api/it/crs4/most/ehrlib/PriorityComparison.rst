.. java:import:: it.crs4.most.ehrlib.datatypes EhrDatatype

.. java:import:: it.crs4.most.ehrlib.widgets DatatypeWidget

.. java:import:: java.util Comparator

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

PriorityComparison
==================

.. java:package:: it.crs4.most.ehrlib
   :noindex:

.. java:type:: public class PriorityComparison implements Comparator<DatatypeWidget<EhrDatatype>>

   Helper class for sorting widgets by priority.

Constructors
------------
PriorityComparison
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public PriorityComparison(JSONObject layoutSchema)
   :outertype: PriorityComparison

   Instantiates a new priority comparison.

   :param layoutSchema: the layout schema

Methods
-------
compare
^^^^^^^

.. java:method:: public int compare(DatatypeWidget<EhrDatatype> item1, DatatypeWidget<EhrDatatype> item2)
   :outertype: PriorityComparison

