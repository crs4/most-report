.. java:import:: org.json JSONObject

.. java:import:: com.nhaarman.supertooltips ToolTip

.. java:import:: com.nhaarman.supertooltips ToolTipRelativeLayout

.. java:import:: com.nhaarman.supertooltips ToolTipView

.. java:import:: it.crs4.most.ehrlib R

.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.datatypes DvCodedText

.. java:import:: it.crs4.most.ehrlib.datatypes DvQuantity

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: it.crs4.most.ehrlib.widgets.filters DvQuantityFilter

.. java:import:: android.app Activity

.. java:import:: android.content Context

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view View.OnClickListener

.. java:import:: android.widget EditText

.. java:import:: android.widget ImageView

.. java:import:: android.widget TextView

DvQuantityWidget
================

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class DvQuantityWidget extends DatatypeWidget<DvQuantity>

   This class represents a visual widget mapped on a \ :java:ref:`DvQuantity`\  datatype.

Fields
------
_input
^^^^^^

.. java:field:: protected EditText _input
   :outertype: DvQuantityWidget

   The _input.

_labUnity
^^^^^^^^^

.. java:field:: protected TextView _labUnity
   :outertype: DvQuantityWidget

   The _lab unity.

_title
^^^^^^

.. java:field:: protected TextView _title
   :outertype: DvQuantityWidget

   The _title.

_txtvalidity
^^^^^^^^^^^^

.. java:field:: protected TextView _txtvalidity
   :outertype: DvQuantityWidget

   The _txtvalidity.

Constructors
------------
DvQuantityWidget
^^^^^^^^^^^^^^^^

.. java:constructor:: public DvQuantityWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex)
   :outertype: DvQuantityWidget

   Instantiates a new \ :java:ref:`DvQuantityWidget`\

   :param provider: the widget provider
   :param name: the name of this widget
   :param path: the path of the \ :java:ref:`DvQuantity`\  mapped on this widget
   :param attributes: the attributes of the \ :java:ref:`DvQuantity`\  mapped on this widget
   :param parentIndex: the parent index

Methods
-------
onEhrDatatypeChanged
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onEhrDatatypeChanged(DvQuantity datatype)
   :outertype: DvQuantityWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener.onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)`

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: DvQuantityWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.replaceTooltip(com.nhaarman.supertooltips.ToolTip)`

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: DvQuantityWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.reset()`

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: DvQuantityWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.save()`

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: DvQuantityWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.updateLabelsContent()`

