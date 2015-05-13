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

.. java:import:: it.crs4.most.ehrlib.datatypes DvCodedText

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

DvCodedTextAsListWidget
=======================

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class DvCodedTextAsListWidget extends DatatypeWidget<DvCodedText>

   The Class DvCodedTextAsListWidget.

Constructors
------------
DvCodedTextAsListWidget
^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public DvCodedTextAsListWidget(WidgetProvider provider, String name, String path, JSONObject attributes, JSONObject ontology, int parentIndex)
   :outertype: DvCodedTextAsListWidget

   Instantiates a new dv coded text as list widget.

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

.. java:method:: @Override public void onEhrDatatypeChanged(DvCodedText datatype)
   :outertype: DvCodedTextAsListWidget

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: DvCodedTextAsListWidget

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: DvCodedTextAsListWidget

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: DvCodedTextAsListWidget

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: DvCodedTextAsListWidget

