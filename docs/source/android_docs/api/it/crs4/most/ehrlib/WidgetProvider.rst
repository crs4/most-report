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

   A WidgetProvider lets you build a set of visual and interactive widgets corresponding to a specific OpenEHR Archetype. The Archetype description is specified by a set of json structures (to be provided to the class constructor).

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

clusterWidgetsMap
^^^^^^^^^^^^^^^^^

.. java:field:: protected Map<String, List<DatatypeWidget<EhrDatatype>>> clusterWidgetsMap
   :outertype: WidgetProvider

   The cluster widgets map.

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

.. java:constructor:: public WidgetProvider(Context context, ArchetypeSchemaProvider asp, String archetypeClassName, String language, String jsonExclude) throws JSONException, InvalidDatatypeException
   :outertype: WidgetProvider

   Setup a Widget provider representing a specific archetype, according to the specified Archetype Schema Provider and archetype class name

   :param context: get application context
   :param asp: the Archetype Schema Provider
   :param archetypeClassName: the name of the archetype class to be built (e.g: openEHR-EHR-OBSERVATION.blood_pressure.v1)
   :param language: the default ontology language
   :param jsonExclude: the json array containing a list of item ids to be excluded from the archetype
   :throws InvalidDatatypeException:
   :throws JSONException:

WidgetProvider
^^^^^^^^^^^^^^

.. java:constructor:: public WidgetProvider(Context context, String jsonDatatypes, String jsonOntology, String jsonAdlStructure, String jsonLayoutSchema, String language) throws JSONException, InvalidDatatypeException
   :outertype: WidgetProvider

   Setup a Widget provider representing a specific archetype, according to the specified json datatypes schema , json archetype structure and json ontology.

   :param context: the application context
   :param jsonDatatypes: - the json description of all datatypes used by this archetype, subdivided in sections
   :param jsonOntology: - the json ontology (it includes a textual description of each item of the archetype)
   :param jsonAdlStructure: - the initial json structure of the archetype (optionally including initial values)
   :param jsonLayoutSchema: (optional, it can be null) the layout schema containing informations about visual rendering (sections, custom widgets, priorities..)
   :param language: - the default language code (any language code included in the ontology json schema)
   :throws InvalidDatatypeException:
   :throws JSONException: the JSON exception

WidgetProvider
^^^^^^^^^^^^^^

.. java:constructor:: public WidgetProvider(Context context, String jsonDatatypes, String jsonOntology, String jsonAdlStructure, String jsonLayoutSchema, String language, String jsonExclude) throws JSONException, InvalidDatatypeException
   :outertype: WidgetProvider

   Setup a Widget provider representing a specific archetype, according to the specified json datatypes schema , json archetype structure and json ontology.

   :param context: the application context
   :param jsonDatatypes: - the json description of all datatypes used by this archetype, subdivided in sections
   :param jsonOntology: - the json ontology (it includes a textual description of each item of the archetype)
   :param jsonAdlStructure: - the initial json structure of the archetype (optionally including initial values)
   :param jsonLayoutSchema: (optional, it can be null) the layout schema containing informations about visual rendering (sections, custom widgets, priorities..)
   :param jsonExclude: (optional, it can be null) the list of archetype items (i.e their id , like "at0004") to exclude from the viewer
   :param language: - the default language code (any language code included in the ontology json schema)
   :throws InvalidDatatypeException:
   :throws JSONException: - if an error occurred during the parsing of the json schemas

Methods
-------
buildFormView
^^^^^^^^^^^^^

.. java:method:: public FormContainer buildFormView(int index)
   :outertype: WidgetProvider

   build a view containing all widgets according to the json archetype structure, layout and ontology, All widgets are rendered in a vertical layout, optionally ordered by section and/or item priority (if specified in the layout json schema)

   :param index: the index of this Form Container
   :return: the FormContainer containing all widgets, ordered by section and item priority in a vertical layout

getClusterWidgets
^^^^^^^^^^^^^^^^^

.. java:method:: public List<DatatypeWidget<EhrDatatype>> getClusterWidgets(String cluster, int itemIndex)
   :outertype: WidgetProvider

getContext
^^^^^^^^^^

.. java:method:: public Context getContext()
   :outertype: WidgetProvider

   Get the application context

   :return: the application context

getDatatypesSchema
^^^^^^^^^^^^^^^^^^

.. java:method:: public JSONObject getDatatypesSchema()
   :outertype: WidgetProvider

getOntology
^^^^^^^^^^^

.. java:method:: public JSONObject getOntology()
   :outertype: WidgetProvider

   Get the json schema containing the ontology of this archetype

   :return: the ontology json schema

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

   Update the ontology of all \ :java:ref:`DatatypeWidget`\  widgets.

   :param lang: the language code (ISO 639-1:2002)

updateSectionsJsonContent
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void updateSectionsJsonContent(int index) throws JSONException
   :outertype: WidgetProvider

   Update the json structure according to the current value of the datatype widgets belonging to this form.

   :param index: the form index
   :throws JSONException: the JSON exception
   :return: the updated json structure

