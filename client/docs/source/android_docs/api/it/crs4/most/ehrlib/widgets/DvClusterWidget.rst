.. java:import:: java.util List

.. java:import:: org.json JSONObject

.. java:import:: android.content Context

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view View.OnClickListener

.. java:import:: android.widget ImageView

.. java:import:: android.widget LinearLayout

.. java:import:: android.widget TextView

.. java:import:: com.nhaarman.supertooltips ToolTip

.. java:import:: com.nhaarman.supertooltips ToolTipRelativeLayout

.. java:import:: com.nhaarman.supertooltips ToolTipView

.. java:import:: it.crs4.most.ehrlib R

.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.datatypes DvCluster

.. java:import:: it.crs4.most.ehrlib.datatypes EhrDatatype

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

DvClusterWidget
===============

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class DvClusterWidget extends DatatypeWidget<DvCluster>

Fields
------
myToolTipView
^^^^^^^^^^^^^

.. java:field:: protected ToolTipView myToolTipView
   :outertype: DvClusterWidget

Constructors
------------
DvClusterWidget
^^^^^^^^^^^^^^^

.. java:constructor:: public DvClusterWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex)
   :outertype: DvClusterWidget

Methods
-------
onEhrDatatypeChanged
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onEhrDatatypeChanged(DvCluster datatype)
   :outertype: DvClusterWidget

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: DvClusterWidget

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: DvClusterWidget

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: DvClusterWidget

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: DvClusterWidget

