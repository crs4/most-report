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

.. java:import:: it.crs4.most.ehrlib.datatypes DvText

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

DvTextWidget
============

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class DvTextWidget extends DatatypeWidget<DvText> implements ToolTipView.OnToolTipViewClickedListener

   The Class DvTextWidget.

Constructors
------------
DvTextWidget
^^^^^^^^^^^^

.. java:constructor:: public DvTextWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex)
   :outertype: DvTextWidget

   Instantiates a new dv text widget.

   :param context: the context
   :param name: the name
   :param path: the path
   :param attributes: the attributes
   :param ontology: the ontology
   :param parentIndex: the parent index

Methods
-------
onEhrDatatypeChanged
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onEhrDatatypeChanged(DvText datatype)
   :outertype: DvTextWidget

onToolTipViewClicked
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onToolTipViewClicked(ToolTipView arg0)
   :outertype: DvTextWidget

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: DvTextWidget

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: DvTextWidget

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: DvTextWidget

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: DvTextWidget

