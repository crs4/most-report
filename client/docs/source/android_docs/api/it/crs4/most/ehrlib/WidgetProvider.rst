.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.lang.reflect Constructor

.. java:import:: java.lang.reflect InvocationTargetException

.. java:import:: java.util ArrayList

.. java:import:: java.util Collections

.. java:import:: java.util HashMap

.. java:import:: java.util List

.. java:import:: java.util Map

.. java:import:: org.json JSONArray

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.content Context

.. java:import:: android.graphics Color

.. java:import:: android.graphics Typeface

.. java:import:: android.util Log

.. java:import:: android.view Gravity

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget LinearLayout

.. java:import:: android.widget ScrollView

.. java:import:: android.widget LinearLayout.LayoutParams

.. java:import:: android.widget TextView

.. java:import:: it.crs4.most.ehrlib.datatypes EhrDatatype

.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: it.crs4.most.ehrlib.parser AdlParser

.. java:import:: it.crs4.most.ehrlib.parser AdlStructure

WidgetProvider
==============

.. java:package:: it.crs4.most.ehrlib
   :noindex:

.. java:type:: public class WidgetProvider

   The Class WidgetProvider.

   :author: smonni A WidgetProvider is a utility class that builds a set of visual and iteractive widgets corresponding to a specific Archetype. The Archetype description is specified by a set of json structures, to be provided to the class constructor.

Fields
------
_container
^^^^^^^^^^

.. java:field:: protected LinearLayout _container
   :outertype: WidgetProvider

   The _container.

_layout
^^^^^^^

.. java:field:: protected LinearLayout _layout
   :outertype: WidgetProvider

   The _layout.

_viewport
^^^^^^^^^

.. java:field:: protected ScrollView _viewport
   :outertype: WidgetProvider

   The _viewport.

defaultLayoutParams
^^^^^^^^^^^^^^^^^^^

.. java:field:: public static final LayoutParams defaultLayoutParams
   :outertype: WidgetProvider

   The Constant defaultLayoutParams.

sectionWidgetsMap
^^^^^^^^^^^^^^^^^

.. java:field:: protected Map<String, List<DatatypeWidget<EhrDatatype>>> sectionWidgetsMap
   :outertype: WidgetProvider

   The section widgets map.

Constructors
------------
WidgetProvider
^^^^^^^^^^^^^^

.. java:constructor:: public WidgetProvider(Context context, String jsonDatatypes, String jsonOntology, String jsonInstances, String jsonLayoutSchema, String language) throws JSONException, InvalidDatatypeException
   :outertype: WidgetProvider

   Setup a Widget provider representing a specific archetype, according to the specified json archetype datatypes, structure and ontology.

   :param context: the application context
   :param jsonDatatypes: - the json description of all datatypes used by this archetype, subdivided in sections
   :param jsonOntology: - the json ontology (it includes a textual description of each item of the archetype)
   :param jsonInstances: - the initial json structure of the archetype (optionally including initial values)
   :param jsonLayoutSchema: (optional, it can be null) the layout schema containing informations about visual rendering (sections, custom widgets, priorities..)
   :param language: - the ontology language
   :throws InvalidDatatypeException:
   :throws JSONException: the JSON exception

Methods
-------
buildFormView
^^^^^^^^^^^^^

.. java:method:: public FormContainer buildFormView(int index) throws InvalidDatatypeException
   :outertype: WidgetProvider

   build a view containing all widgets according to the json archetype structure, layout and ontology, All widgets are rendered in a vertical layout, optionally ordered by section and/or item priority (if specified in the layout json schema) (This method first calls the \ :java:ref:`buildSectionWidgetsMap(String[])`\  methods on all archetype sections (or on the sections provided by the layout schema , if provided).

   :param index: the index
   :throws InvalidDatatypeException: the invalid datatype exception
   :return: the FormContainer containing all widgets, ordered by section and item priority in a vertical layout

getContext
^^^^^^^^^^

.. java:method:: public Context getContext()
   :outertype: WidgetProvider

getOntology
^^^^^^^^^^^

.. java:method:: public JSONObject getOntology()
   :outertype: WidgetProvider

getOntology
^^^^^^^^^^^

.. java:method:: public static JSONObject getOntology(String data, String language)
   :outertype: WidgetProvider

   Get the ontology .

   :param data: the ontology schema (including all available languages)
   :param language: the selected language
   :return: a json object containing the ontology of the specified language

getSectionWidgets
^^^^^^^^^^^^^^^^^

.. java:method:: public List<DatatypeWidget<EhrDatatype>> getSectionWidgets(String section, int itemIndex)
   :outertype: WidgetProvider

getSections
^^^^^^^^^^^

.. java:method:: public String[] getSections()
   :outertype: WidgetProvider

   Get the sections of this archetype structure.

   :return: the sections

parseFileToString
^^^^^^^^^^^^^^^^^

.. java:method:: public static String parseFileToString(Context context, String filename)
   :outertype: WidgetProvider

   Parses the file to string.

   :param context: the context
   :param filename: the filename
   :return: the string

toJson
^^^^^^

.. java:method:: public JSONObject toJson()
   :outertype: WidgetProvider

   get a Json representation of the current state of this archetype.

   :return: the JSON object

updateOntologyLanguage
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void updateOntologyLanguage(String lang)
   :outertype: WidgetProvider

   Update the ontology on all datatype widgets.

   :param lang: the language code

updateSectionsJsonContent
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void updateSectionsJsonContent(int index) throws JSONException
   :outertype: WidgetProvider

   Update the json structure according to the current value of the datatype widgets belonging to this form.

   :param index: the form index
   :throws JSONException: the JSON exception
   :return: the updated json structure

