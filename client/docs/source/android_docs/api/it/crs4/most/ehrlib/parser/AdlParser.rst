.. java:import:: java.util ArrayList

.. java:import:: java.util Arrays

.. java:import:: java.util Iterator

.. java:import:: org.json JSONArray

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: android.util Log

AdlParser
=========

.. java:package:: it.crs4.most.ehrlib.parser
   :noindex:

.. java:type:: public class AdlParser

   This class provides methods for exploring, retrieving and updating the contents of a JSON structure representing an OpenEHR Archetype

Constructors
------------
AdlParser
^^^^^^^^^

.. java:constructor:: public AdlParser(JSONObject jsonData)
   :outertype: AdlParser

   Instantiates a new adl parser.

   :param jsonData: the json data

Methods
-------
getItemsContainer
^^^^^^^^^^^^^^^^^

.. java:method:: public AdlStructure getItemsContainer(String path)
   :outertype: AdlParser

   Get an ADL structure containing all the items included in an archetype path. The path must be the absolute parh of any of its items. For instance, providing the path data[at0001]/events[at0006]/data[at0003]/items[at0004] , this method returns the ADL structure included in data[at0001]/events[at0006]/data[at0003]

   :param path: the path of an item
   :return: the AdlStructure

getStructureByPath
^^^^^^^^^^^^^^^^^^

.. java:method:: public AdlStructure getStructureByPath(String path)
   :outertype: AdlParser

   Get the structure corresponding to the specified path.

   :param path: the path of an item
   :return: the AdlStructure

replaceContent
^^^^^^^^^^^^^^

.. java:method:: public void replaceContent(String path, int index, JSONObject newContent) throws JSONException
   :outertype: AdlParser

   Replace the content of a json structure.

   :param path: the path of the json substructure to be replaced
   :param index: the index of the json instance
   :param newContent: the json structure containing the new json content
   :throws JSONException: the JSON exception

