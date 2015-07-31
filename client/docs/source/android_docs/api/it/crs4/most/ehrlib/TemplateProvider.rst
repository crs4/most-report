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

Constructors
------------
TemplateProvider
^^^^^^^^^^^^^^^^

.. java:constructor:: public TemplateProvider(Context ctx, String templateSchema, ArchetypeSchemaProvider archetypeSchemaProvider, String language) throws JSONException
   :outertype: TemplateProvider

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

