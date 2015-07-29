.. java:import:: java.util List

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.content Context

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view View.OnClickListener

.. java:import:: android.widget ImageView

.. java:import:: android.widget LinearLayout

.. java:import:: android.widget TextView

.. java:import:: com.nhaarman.supertooltips ToolTip

.. java:import:: com.nhaarman.supertooltips ToolTipRelativeLayout

.. java:import:: com.nhaarman.supertooltips ToolTipView

.. java:import:: it.crs4.most.ehrlib FormContainer

.. java:import:: it.crs4.most.ehrlib R

.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.datatypes DvCluster

.. java:import:: it.crs4.most.ehrlib.datatypes EhrDatatype

.. java:import:: it.crs4.most.ehrlib.datatypes InnerArchetype

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

InnerArchetypeWidget
====================

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public class InnerArchetypeWidget extends DatatypeWidget<InnerArchetype>

   This class represents a visual widget mapped on a \ :java:ref:`InnerArchetype`\  datatype.

Fields
------
myToolTipView
^^^^^^^^^^^^^

.. java:field:: protected ToolTipView myToolTipView
   :outertype: InnerArchetypeWidget

Constructors
------------
InnerArchetypeWidget
^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public InnerArchetypeWidget(WidgetProvider provider, String name, String path, JSONObject attributes, int parentIndex)
   :outertype: InnerArchetypeWidget

   Instantiate a new InnerArchetypeWidget

   :param provider: the widget provider
   :param name: the name of this widget
   :param path: the path of the \ :java:ref:`InnerArchetype`\  mapped on this widget
   :param attributes: the attributes of the \ :java:ref:`InnerArchetype`\  mapped on this widget
   :param parentIndex: the parent index of this widget

Methods
-------
onEhrDatatypeChanged
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onEhrDatatypeChanged(InnerArchetype datatype)
   :outertype: InnerArchetypeWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.datatypes.EhrDatatypeChangeListener.onEhrDatatypeChanged(it.crs4.most.ehrlib.datatypes.EhrDatatype)`

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: @Override protected void replaceTooltip(ToolTip tooltip)
   :outertype: InnerArchetypeWidget

reset
^^^^^

.. java:method:: @Override public void reset()
   :outertype: InnerArchetypeWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.reset()`

save
^^^^

.. java:method:: @Override public void save() throws InvalidDatatypeException
   :outertype: InnerArchetypeWidget

   **See also:** :java:ref:`it.crs4.most.ehrlib.widgets.DatatypeWidget.save()`

setOntology
^^^^^^^^^^^

.. java:method:: @Override public void setOntology(JSONObject ontology, String language)
   :outertype: InnerArchetypeWidget

   Sets the ontology.

   :param ontology: the new ontology

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void updateLabelsContent()
   :outertype: InnerArchetypeWidget

