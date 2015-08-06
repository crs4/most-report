.. java:import:: it.crs4.most.ehrlib.exceptions InvalidDatatypeException

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: org.json JSONArray

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.content Context

.. java:import:: android.util Log

TemplateProvider
================

.. java:package:: it.crs4.most.ehrlib
   :noindex:

.. java:type:: public class TemplateProvider

   This class represents a visual Archetypes Template, according to the OpenEHR specifications. A template is an ordered list of \ :java:ref:`WidgetProvider`\  , each of them contains the layout of a specific archetype of the template-

Constructors
------------
TemplateProvider
^^^^^^^^^^^^^^^^

.. java:constructor:: public TemplateProvider(Context ctx, String templateSchema, ArchetypeSchemaProvider archetypeSchemaProvider, String language) throws JSONException
   :outertype: TemplateProvider

   Creates the template, building all the archetypes specified in the provided json schemas.

   :param ctx: the application Context
   :param templateSchema: the json schema of the template
   :param archetypeSchemaProvider: the archetype schema provider
   :param language: the default ontology language
   :throws JSONException: if an error occurred during the parsing of the json schemas

Methods
-------
getId
^^^^^

.. java:method:: public String getId()
   :outertype: TemplateProvider

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: TemplateProvider

getWidgetProviders
^^^^^^^^^^^^^^^^^^

.. java:method:: public List<WidgetProvider> getWidgetProviders()
   :outertype: TemplateProvider

   Get the list of the widget providers of this template, one for each archetype

