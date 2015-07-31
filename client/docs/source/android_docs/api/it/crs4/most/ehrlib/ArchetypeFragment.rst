.. java:import:: android.graphics Color

.. java:import:: android.os Bundle

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

ArchetypeFragment
=================

.. java:package:: it.crs4.most.ehrlib
   :noindex:

.. java:type:: public class ArchetypeFragment extends Fragment

   This class allows you to display an Archetype in a Fragment. You just have to provide the \ :java:ref:`WidgetProvider`\  handling the archetype you want to include to the constructor or, if you prefer, you can use the \ :java:ref:`setWidgetProvider(WidgetProvider)`\  method (in this second case, remember to call this method before adding the fragment to the container).

Constructors
------------
ArchetypeFragment
^^^^^^^^^^^^^^^^^

.. java:constructor:: public ArchetypeFragment()
   :outertype: ArchetypeFragment

ArchetypeFragment
^^^^^^^^^^^^^^^^^

.. java:constructor:: public ArchetypeFragment(WidgetProvider wp)
   :outertype: ArchetypeFragment

   Create an Archetype fragment by providing the WidgetProvider

   :param wp:

Methods
-------
getFormContainer
^^^^^^^^^^^^^^^^

.. java:method:: public FormContainer getFormContainer()
   :outertype: ArchetypeFragment

   Get the form container of this fragment

   :return: the form container of this archetype fragment

getwidgetProvider
^^^^^^^^^^^^^^^^^

.. java:method:: public WidgetProvider getwidgetProvider()
   :outertype: ArchetypeFragment

   :return: the widget provider of this Archetype Fragment

onCreateView
^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: ArchetypeFragment

setWidgetProvider
^^^^^^^^^^^^^^^^^

.. java:method:: public void setWidgetProvider(WidgetProvider wp)
   :outertype: ArchetypeFragment

   Set the widget provider for this fragment. This method must called BEFORE adding the fragment to itws container.

   :param wp:

