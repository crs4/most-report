.. java:import:: java.util ArrayList

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: com.nhaarman.supertooltips ToolTip

.. java:import:: com.nhaarman.supertooltips ToolTipRelativeLayout

.. java:import:: com.nhaarman.supertooltips ToolTipView

.. java:import:: android.content Context

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view View.OnClickListener

.. java:import:: android.widget AdapterView

.. java:import:: android.widget AdapterView.OnItemClickListener

.. java:import:: android.widget ArrayAdapter

.. java:import:: android.widget ImageView

.. java:import:: android.widget ListView

.. java:import:: android.widget TextView

.. java:import:: it.crs4.most.ehrlib R

.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.datatypes DvCluster

.. java:import:: it.crs4.most.ehrlib.datatypes DvCodedText

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

DvCodedTextAsListWidget
=======================

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class DvCodedTextAsListWidget extends DatatypeWidget<DvCodedText>

   This class represents a visual widget mapped on a \ :java:ref:`DvCodedText`\  datatype. It renders all the options of the \ :java:ref:`DvCodedText`\  datatype in a ListView.

Constructors
------------
DvCodedTextAsListWidget
^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public DvCodedTextAsListWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex)
   :outertype: DvCodedTextAsListWidget

   Instantiates a new \ :java:ref:`DvCodedTextAsListWidget`\

   :param provider: the widget provider
   :param name: the name of this widget
   :param path: the path of the \ :java:ref:`DvCodedText`\  mapped on this widget
   :param attributes: the attributes of the \ :java:ref:`DvCodedText`\  mapped on this widget
   :param parentIndex: the parent index

Methods
-------
onEhrDatatypeChanged
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onEhrDatatypeChanged(DvCodedText datatype)
   :outertype: DvCodedTextAsListWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener.onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)`

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: DvCodedTextAsListWidget

   **See also:** :java:ref:`{@linkit.crs4.most.ehrlib.widgets.DatatypeWidget.replaceTooltip(com.nhaarman.supertooltips.ToolTip)}`

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: DvCodedTextAsListWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.reset()`

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: DvCodedTextAsListWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.save()`

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: DvCodedTextAsListWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.updateLabelsContent()`

