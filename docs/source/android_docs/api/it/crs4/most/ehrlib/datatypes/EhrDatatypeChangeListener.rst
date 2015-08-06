EhrDatatypeChangeListener
=========================

.. java:package:: it.crs4.most.ehrlib.datatypes
   :noindex:

.. java:type:: public interface EhrDatatypeChangeListener<T extends EhrDatatype>

   The listener interface for receiving ehrDatatypeChange events. The class that is interested in processing a ehrDatatypeChange event implements this interface, and the object created with that class is registered with a component using the component's \ :java:ref:`EhrDatatype.setDatatypeChangeListener(EhrDatatypeChangeListener)`\  method. When the ehrDatatypeChange event occurs, that object's appropriate method is invoked.

   :param <T>: the generic datatype extending the \ ``EhrDatatype``\

Methods
-------
onEhrDatatypeChanged
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void onEhrDatatypeChanged(T datatype)
   :outertype: EhrDatatypeChangeListener

   Called when a datatype changed its content.

   :param datatype: the datatype with the updated value

