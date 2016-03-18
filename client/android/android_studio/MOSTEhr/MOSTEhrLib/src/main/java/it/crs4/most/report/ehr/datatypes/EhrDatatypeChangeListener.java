/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.datatypes;


/**
 * The listener interface for receiving ehrDatatypeChange events.
 * The class that is interested in processing a ehrDatatypeChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's {@link EhrDatatype#setDatatypeChangeListener(EhrDatatypeChangeListener)} method. When
 * the ehrDatatypeChange event occurs, that object's appropriate
 * method is invoked.
 *
 * @param <T> the generic datatype extending the {@code EhrDatatype}
 */
public interface EhrDatatypeChangeListener<T extends EhrDatatype> {
	
	/**
	 * Called when a datatype changed its content.
	 *
	 * @param datatype the datatype with the updated value
	 */
	public void onEhrDatatypeChanged(T datatype);

}
