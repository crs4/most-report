.. java:import:: org.json JSONObject

.. java:import:: com.nhaarman.supertooltips ToolTip

.. java:import:: com.nhaarman.supertooltips ToolTipRelativeLayout

.. java:import:: com.nhaarman.supertooltips ToolTipView

.. java:import:: android.app Activity

.. java:import:: android.content Context

.. java:import:: android.text.method ScrollingMovementMethod

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view View.OnClickListener

.. java:import:: android.widget CheckBox

.. java:import:: android.widget EditText

.. java:import:: android.widget ImageView

.. java:import:: android.widget TextView

.. java:import:: it.crs4.most.ehrlib R

.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.datatypes DvBoolean

.. java:import:: it.crs4.most.ehrlib.datatypes DvQuantity

.. java:import:: it.crs4.most.ehrlib.datatypes DvText

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

DvBooleanWidget
===============

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class DvBooleanWidget extends DatatypeWidget<DvBoolean> implements ToolTipView.OnToolTipViewClickedListener

   This class represents a visual widget mapped on a \ :java:ref:`DvBoolean`\  datatype.

Constructors
------------
DvBooleanWidget
^^^^^^^^^^^^^^^

.. java:constructor:: public DvBooleanWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex)
   :outertype: DvBooleanWidget

   Instantiates a new \ :java:ref:`DvBooleanWidget`\

   :param provider: the widget provider
   :param name: the name of this widget
   :param path: the path of the \ :java:ref:`DvBoolean`\  mapped on this widget
   :param attributes: the attributes of the \ :java:ref:`DvBoolean`\  mapped on this widget
   :param parentIndex: the parent index

Methods
-------
onEhrDatatypeChanged
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onEhrDatatypeChanged(DvBoolean datatype)
   :outertype: DvBooleanWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener.onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)`

onToolTipViewClicked
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onToolTipViewClicked(ToolTipView arg0)
   :outertype: DvBooleanWidget

   **See also:** :java:ref:`com.nhaarman.supertooltips.ToolTipView.OnToolTipViewClickedListener.onToolTipViewClicked(com.nhaarman.supertooltips.ToolTipView)`

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: DvBooleanWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.replaceTooltip(com.nhaarman.supertooltips.ToolTip)`

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: DvBooleanWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.reset()`

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: DvBooleanWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.save()`

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: DvBooleanWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.updateLabelsContent()`

