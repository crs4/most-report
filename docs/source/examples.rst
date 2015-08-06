
Examples
========


All the following examples are located in the folder *client/android/examples/* of the Most-Report repository. 

 * **EhrLibArchetypeViewerExample** This example shows you:
  - how to load an Archetype into an Activity, according to the provided json schema files
  - how to read, edit and save the content of the Archetype
  - how to get textual informations about any item of the archetype
  - how to read the json file containing the current content of the archetype
   
 * **EhrTemplateViewerExample** This example explains you:
  - how to load a template of archetypes into an Activity, according to the provided json schema files
  - how to read, edit and save the content of the Archetypes of the template
  - how to get textual informations about any item of each archetype of the template
  
   
 * **NestedArchetypeActivityExample** This example contains all the features of the *EhrTemplateViewerExample* example, and in addition,  shows you: 
  - how to read the json file containing the current content of each  archetype of the template
  - how the library supports archetypes including other archetypes inside it
 
  
For running the Android examples, open your preferred IDE (e.g Eclipse) and do the following:
   - Import the EhrLib project library (located in the folder client/android/src/EhrLib of the Most-Report Repository)
   - Import the `SuperTooltips Lib <https://github.com/nhaarman/supertooltips/>`_library  and add it as a dependency of the EhrLib project
   - Import your preferred Android project example located in the *android/examples* folder and add the *EhrLib* project as Library reference
   - Build the projects
   - Deploy the application on an android device or emulator 
