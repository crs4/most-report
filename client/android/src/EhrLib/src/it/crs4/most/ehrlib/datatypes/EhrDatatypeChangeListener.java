package it.crs4.most.ehrlib.datatypes;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving ehrDatatypeChange events.
 * The class that is interested in processing a ehrDatatypeChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEhrDatatypeChangeListener<code> method. When
 * the ehrDatatypeChange event occurs, that object's appropriate
 * method is invoked.
 *
 * @param <T> the generic type
 * @see EhrDatatypeChangeEvent
 */
public interface EhrDatatypeChangeListener<T extends EhrDatatype> {
	
	/**
	 * Called when a datatype changed its content.
	 *
	 * @param datatype the datatype with the updated value
	 */
	public void onEhrDatatypeChanged(T datatype);

}
