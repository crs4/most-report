.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: com.nhaarman.supertooltips ToolTip

.. java:import:: it.crs4.most.ehrlib WidgetProvider

.. java:import:: it.crs4.most.ehrlib.datatypes EhrDatatype

.. java:import:: it.crs4.most.ehrlib.datatypes EhrDatatypeChangeListener

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: android.content Context

.. java:import:: android.graphics Color

.. java:import:: android.util Log

.. java:import:: android.view View

DatatypeWidget
==============

.. java:package:: it.crs4.most.ehrlib.widgets
   :noindex:

.. java:type:: public abstract class DatatypeWidget<T extends EhrDatatype> implements EhrDatatypeChangeListener<T>

   This the base class for all DatatypeWidgets. A \ :java:ref:`DatatypeWidget`\  is a visual and interactive widget mapped on a specific \ :java:ref:`EhrDatatype`\ . A user can instantiate a \ :java:ref:`DatatypeWidget`\  for reading, editing and saving the content of the \ :java:ref:`EhrDatatype`\  handled by it.

   :param <T>: the generic \ :java:ref:`EhrDatatype`\

Fields
------
_context
^^^^^^^^

.. java:field:: protected Context _context
   :outertype: DatatypeWidget

   The _context.

_name
^^^^^

.. java:field:: protected String _name
   :outertype: DatatypeWidget

   The _name.

_ontology
^^^^^^^^^

.. java:field:: protected JSONObject _ontology
   :outertype: DatatypeWidget

   The _ontology.

_parent_index
^^^^^^^^^^^^^

.. java:field:: protected int _parent_index
   :outertype: DatatypeWidget

   The _parent_index.

_priority
^^^^^^^^^

.. java:field:: protected int _priority
   :outertype: DatatypeWidget

   The _priority.

_root_view
^^^^^^^^^^

.. java:field:: protected View _root_view
   :outertype: DatatypeWidget

   The _root_view.

_view
^^^^^

.. java:field:: protected View _view
   :outertype: DatatypeWidget

   The _view.

_widget_provider
^^^^^^^^^^^^^^^^

.. java:field:: protected WidgetProvider _widget_provider
   :outertype: DatatypeWidget

datatype
^^^^^^^^

.. java:field:: protected T datatype
   :outertype: DatatypeWidget

   The datatype.

description
^^^^^^^^^^^

.. java:field:: protected String description
   :outertype: DatatypeWidget

   The description.

displayTitle
^^^^^^^^^^^^

.. java:field:: protected String displayTitle
   :outertype: DatatypeWidget

   The display title.

toolTip
^^^^^^^

.. java:field:: protected ToolTip toolTip
   :outertype: DatatypeWidget

   The tool tip.

Constructors
------------
DatatypeWidget
^^^^^^^^^^^^^^

.. java:constructor:: protected DatatypeWidget()
   :outertype: DatatypeWidget

DatatypeWidget
^^^^^^^^^^^^^^

.. java:constructor:: public DatatypeWidget(WidgetProvider provider, String name, T datatype, int parent_index)
   :outertype: DatatypeWidget

   Instantiates a new \ :java:ref:`DatatypeWidget`\  widget.

   :param context: the context
   :param name: the name of this widget
   :param datatype: the \ :java:ref:`EhrDatatype`\  to be handled by this widget
   :param ontology: the ontology used
   :param parent_index: the parent_index

Methods
-------
getDatatype
^^^^^^^^^^^

.. java:method:: public T getDatatype()
   :outertype: DatatypeWidget

   Gets the \ :java:ref:`EhrDatatype`\  handled by this widget

   :return: the \ :java:ref:`EhrDatatype`\

getDescription
^^^^^^^^^^^^^^

.. java:method:: public String getDescription()
   :outertype: DatatypeWidget

   Gets the description.

   :return: the description

getDisplayTitle
^^^^^^^^^^^^^^^

.. java:method:: public String getDisplayTitle()
   :outertype: DatatypeWidget

   Gets the display title.

   :return: the display title

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: DatatypeWidget

   returns the name of this widget

   :return: the name

getParentIndex
^^^^^^^^^^^^^^

.. java:method:: public int getParentIndex()
   :outertype: DatatypeWidget

   Gets the parent index.

   :return: the parent index

getPriority
^^^^^^^^^^^

.. java:method:: public int getPriority()
   :outertype: DatatypeWidget

   returns the visual priority of this widget (essentially this means it's physical location in the form).

   :return: the priority

getToolTip
^^^^^^^^^^

.. java:method:: public ToolTip getToolTip()
   :outertype: DatatypeWidget

   Gets the tool tip.

   :return: the tool tip

getView
^^^^^^^

.. java:method:: public View getView()
   :outertype: DatatypeWidget

   get the Root View containing this widget's view elements.

   :return: the view

getWidgetProvider
^^^^^^^^^^^^^^^^^

.. java:method:: public WidgetProvider getWidgetProvider()
   :outertype: DatatypeWidget

replaceTooltip
^^^^^^^^^^^^^^

.. java:method:: protected abstract void replaceTooltip(ToolTip tooltip)
   :outertype: DatatypeWidget

   Replace tooltip.

   :param tooltip: the tooltip

reset
^^^^^

.. java:method:: public abstract void reset()
   :outertype: DatatypeWidget

   Reset all fields of this widget according to the current underlying datatype value.

save
^^^^

.. java:method:: public abstract void save() throws InvalidDatatypeException
   :outertype: DatatypeWidget

   Update the value of the underlying datatype according to the current value of the fields of this widget.

   :throws InvalidDatatypeException: if the current value of the fields cannot be converted to a datatype value

setOntology
^^^^^^^^^^^

.. java:method:: public void setOntology(JSONObject ontology, String lang)
   :outertype: DatatypeWidget

   Sets the ontology.

   :param ontology: the new ontology
   :param lang: the language of the new loaded ontology

setPriority
^^^^^^^^^^^

.. java:method:: public void setPriority(int value)
   :outertype: DatatypeWidget

   sets the visual priority of this widget essentially this means it's physical location in the form.

   :param value: the new priority

setVisibility
^^^^^^^^^^^^^

.. java:method:: public void setVisibility(int value)
   :outertype: DatatypeWidget

   set the visibility of this widget.

   :param value: the new visibility

setupTooltip
^^^^^^^^^^^^

.. java:method:: protected void setupTooltip()
   :outertype: DatatypeWidget

   Setup tooltip.

updateLabelsContent
^^^^^^^^^^^^^^^^^^^

.. java:method:: protected abstract void updateLabelsContent()
   :outertype: DatatypeWidget

   Update the content of the labels of the widget, according to the current ontology language.

