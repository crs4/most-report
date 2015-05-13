.. java:import:: android.graphics Color

.. java:import:: android.text Editable

.. java:import:: android.text InputFilter

.. java:import:: android.text Spanned

.. java:import:: android.text TextWatcher

.. java:import:: android.util Log

.. java:import:: android.view KeyEvent

.. java:import:: android.view View

.. java:import:: android.view View.OnKeyListener

.. java:import:: android.widget EditText

EditTextLocker
==============

.. java:package:: it.crs4.most.ehrlib.widgets.filters
   :noindex:

.. java:type:: public class EditTextLocker

Fields
------
TAG
^^^

.. java:field:: protected static final String TAG
   :outertype: EditTextLocker

Constructors
------------
EditTextLocker
^^^^^^^^^^^^^^

.. java:constructor:: public EditTextLocker(EditText editText)
   :outertype: EditTextLocker

Methods
-------
limitCharacters
^^^^^^^^^^^^^^^

.. java:method:: public void limitCharacters(int limit)
   :outertype: EditTextLocker

limitFractionDigitsinDecimal
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void limitFractionDigitsinDecimal(int fractionLimit)
   :outertype: EditTextLocker

startStopEditing
^^^^^^^^^^^^^^^^

.. java:method:: public void startStopEditing(boolean isLock)
   :outertype: EditTextLocker

unlockEditText
^^^^^^^^^^^^^^

.. java:method:: public void unlockEditText()
   :outertype: EditTextLocker

