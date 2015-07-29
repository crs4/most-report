.. java:import:: java.util ArrayList

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: com.nhaarman.supertooltips ToolTip

.. java:import:: com.nhaarman.supertooltips ToolTipRelativeLayout

.. java:import:: com.nhaarman.supertooltips ToolTipView

.. java:import:: android.content Context

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view View.OnClickListener

.. java:import:: android.widget AdapterView

.. java:import:: android.widget AdapterView.OnItemSelectedListener

.. java:import:: android.widget ArrayAdapter

.. java:import:: android.widget ImageView

.. java:import:: android.widget Spinner

.. java:import:: android.widget TextView

.. java:import:: it.crs4.most.ehrlib R

.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.datatypes DvCodedText

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

DvCodedTextWidget
=================

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class DvCodedTextWidget extends DatatypeWidget<DvCodedText>

   This class represents a visual widget mapped on a \ :java:ref:`DvCodedText`\  datatype. It renders all the options of the \ :java:ref:`DvCodedText`\  datatype in a Combobox.

Constructors
------------
DvCodedTextWidget
^^^^^^^^^^^^^^^^^

.. java:constructor:: public DvCodedTextWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex)
   :outertype: DvCodedTextWidget

   Instantiates a new \ :java:ref:`DvCodedTextWidget`\

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
   :outertype: DvCodedTextWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener.onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)`

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: DvCodedTextWidget

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: DvCodedTextWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.reset()`

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: DvCodedTextWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.save()`

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: DvCodedTextWidget

