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

.. java:import:: android.widget EditText

.. java:import:: android.widget ImageView

.. java:import:: android.widget TextView

.. java:import:: it.crs4.most.ehrlib R

.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.datatypes DvQuantity

.. java:import:: it.crs4.most.ehrlib.datatypes DvText

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

DvTextWidget
============

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class DvTextWidget extends DatatypeWidget<DvText> implements ToolTipView.OnToolTipViewClickedListener

   This class represents a visual widget mapped on a \ :java:ref:`DvText`\  datatype.

Constructors
------------
DvTextWidget
^^^^^^^^^^^^

.. java:constructor:: public DvTextWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex)
   :outertype: DvTextWidget

   Instantiates a new \ :java:ref:`DvTextWidget`\

   :param provider: the widget provider
   :param name: the name of this widget
   :param path: the path of the \ :java:ref:`DvText`\  mapped on this widget
   :param attributes: the attributes of the \ :java:ref:`DvText`\  mapped on this widget
   :param parentIndex: the parent index

Methods
-------
onEhrDatatypeChanged
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onEhrDatatypeChanged(DvText datatype)
   :outertype: DvTextWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener.onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)`

onToolTipViewClicked
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onToolTipViewClicked(ToolTipView arg0)
   :outertype: DvTextWidget

   **See also:** :java:ref:`com.nhaarman.supertooltips.ToolTipView.OnToolTipViewClickedListener.onToolTipViewClicked(com.nhaarman.supertooltips.ToolTipView)`

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: DvTextWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.replaceTooltip(com.nhaarman.supertooltips.ToolTip)`

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: DvTextWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.reset()`

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: DvTextWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.save()`

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: DvTextWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.updateLabelsContent()`

