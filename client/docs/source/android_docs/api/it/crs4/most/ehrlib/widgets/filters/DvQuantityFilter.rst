.. java:import:: it.crs4.most.ehrlib.datatypes DvQuantity

.. java:import:: android.graphics Color

.. java:import:: android.text Editable

.. java:import:: android.text TextWatcher

.. java:import:: android.util Log

.. java:import:: android.widget EditText

.. java:import:: android.widget TextView

DvQuantityFilter
================

.. java:package:: it.crs4.most.ehrlib.widgets.filters
   :noindex:

.. java:type:: public class DvQuantityFilter

   The Class DvQuantityFilter.

Fields
------
TAG
^^^

.. java:field:: protected static final String TAG
   :outertype: DvQuantityFilter

   The Constant TAG.

validityTextView
^^^^^^^^^^^^^^^^

.. java:field::  TextView validityTextView
   :outertype: DvQuantityFilter

   The validity text view.

Constructors
------------
DvQuantityFilter
^^^^^^^^^^^^^^^^

.. java:constructor:: public DvQuantityFilter(EditText editText, TextView validityTextView, DvQuantity dvQuantity)
   :outertype: DvQuantityFilter

   Instantiates a new dv quantity filter.

   :param editText: the edit text
   :param validityTextView: the validity text view
   :param dvQuantity: the dv quantity

