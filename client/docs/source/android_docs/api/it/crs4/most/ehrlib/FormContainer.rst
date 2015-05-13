.. java:import:: java.util List

.. java:import:: android.util Log

.. java:import:: android.view ViewGroup

.. java:import:: it.crs4.most.ehrlib.datatypes EhrDatatype

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: it.crs4.most.ehrlib.widgets DatatypeWidget

FormContainer
=============

.. java:package:: it.crs4.most.ehrlib
   :noindex:

.. java:type:: public class FormContainer

   The Class FormContainer.

Fields
------
index
^^^^^

.. java:field::  int index
   :outertype: FormContainer

   The index.

layout
^^^^^^

.. java:field::  ViewGroup layout
   :outertype: FormContainer

   The layout.

widgets
^^^^^^^

.. java:field::  List<DatatypeWidget<EhrDatatype>> widgets
   :outertype: FormContainer

   The widgets.

Constructors
------------
FormContainer
^^^^^^^^^^^^^

.. java:constructor:: public FormContainer(ViewGroup layout, List<DatatypeWidget<EhrDatatype>> widgets, int index)
   :outertype: FormContainer

   This class represents a Form Container. It contains the list of \ :java:ref:`DatatypeWidget`\  widgets included in a Form along with the layout that you can use for rendering them.

   :param layout: the layout
   :param widgets: the widgets
   :param index: the index

Methods
-------
getIndex
^^^^^^^^

.. java:method:: public int getIndex()
   :outertype: FormContainer

   Gets the index.

   :return: the index

getLayout
^^^^^^^^^

.. java:method:: public ViewGroup getLayout()
   :outertype: FormContainer

   Gets the layout.

   :return: the layout

getWidgets
^^^^^^^^^^

.. java:method:: public List<DatatypeWidget<EhrDatatype>> getWidgets()
   :outertype: FormContainer

   Gets the widgets.

   :return: the widgets

resetAllWidgets
^^^^^^^^^^^^^^^

.. java:method:: public void resetAllWidgets()
   :outertype: FormContainer

   Reset the content of all widgets of this form according to the current value of their underlying data types.

resetWidget
^^^^^^^^^^^

.. java:method:: public void resetWidget(int index)
   :outertype: FormContainer

   Reset the content of the selected widget according to the current value of the underlying data type.

   :param index: the index

submitAllWidgets
^^^^^^^^^^^^^^^^

.. java:method:: public void submitAllWidgets() throws InvalidDatatypeException
   :outertype: FormContainer

   Submit all widgets.

   :throws InvalidDatatypeException: the invalid datatype exception

submitWidget
^^^^^^^^^^^^

.. java:method:: public void submitWidget(int index) throws InvalidDatatypeException
   :outertype: FormContainer

   Update the value of the underlying data type according to the current content of the widget.

   :param index: the widget index
   :throws InvalidDatatypeException: if the content cannot be converted to the datatype.

