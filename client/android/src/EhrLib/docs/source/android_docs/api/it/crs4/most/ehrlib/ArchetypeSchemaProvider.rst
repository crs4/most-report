.. java:import:: java.io IOException

.. java:import:: java.io InputStream

.. java:import:: java.util Properties

.. java:import:: android.content Context

.. java:import:: android.content.res AssetManager

.. java:import:: android.util Log

ArchetypeSchemaProvider
=======================

.. java:package:: it.crs4.most.ehrlib
   :noindex:

.. java:type:: public class ArchetypeSchemaProvider

   Utility class that provides a convenient way for getting the json schemas needed for loading an archetype on the EhrLibViewer.

Constructors
------------
ArchetypeSchemaProvider
^^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public ArchetypeSchemaProvider(Context context, String archetypesPropertyFile, String schemasRootDir)
   :outertype: ArchetypeSchemaProvider

   Provides default datatypes, layouts and ontology schema for specific archetypes. All available archetype schema must be specified in a property file provided as input argument

   :param context: the application context
   :param archetypesPropertyFile: the path of the Archetype Property file, containing a list of key-values like =
   :param schemasRootDir: the root dir (under assets folder) containing all archetypes schemaa (note that each subfolder is a folder for a specific archetype. e.g blood_pressure)

Methods
-------
getAdlStructureSchema
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getAdlStructureSchema(String archetypeClass)
   :outertype: ArchetypeSchemaProvider

   Get the adl schema for the specified archetype class, or null if not available

   :param archetypeClass: the archetype class, (e.g openEHR-EHR-OBSERVATION.blood_pressure.v1)
   :return: the json schema representing the internal structure of this specific archetype, or null if not available

getDatatypesSchema
^^^^^^^^^^^^^^^^^^

.. java:method:: public String getDatatypesSchema(String archetypeClass)
   :outertype: ArchetypeSchemaProvider

   Get the datatypes schema for the specified archetype class, or null if not available

   :param archetypeClass: the archetype class, (e.g openEHR-EHR-OBSERVATION.blood_pressure.v1)
   :return: the json schema containing the internal structure of datatypes used for visually representing this specific archetype, or null if not available

getLayoutSchema
^^^^^^^^^^^^^^^

.. java:method:: public String getLayoutSchema(String archetypeClass)
   :outertype: ArchetypeSchemaProvider

   Get the Layout schema for the specified archetype class, or null if not available

   :param archetypeClass: the archetype class, (e.g openEHR-EHR-OBSERVATION.blood_pressure.v1)
   :return: the json schema of the default layout, or null if not available

getOntologySchema
^^^^^^^^^^^^^^^^^

.. java:method:: public String getOntologySchema(String archetypeClass)
   :outertype: ArchetypeSchemaProvider

   Get the ontology schema for the specified archetype class, or null if not available

   :param archetypeClass: the archetype class, (e.g openEHR-EHR-OBSERVATION.blood_pressure.v1)
   :return: the json schema of the default ontology, or null if not available

