/*!
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 *
 * Copyright 2014-15, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr.exceptions;

import it.crs4.most.report.ehr.datatypes.EhrDatatype;


/**
 * This exception is called when a user attempts to set an invalid value to a {@link EhrDatatype} item.
 */
public class InvalidDatatypeException extends Exception {

	/**
	 * Instantiates a new invalid datatype exception.
	 *
	 * @param validityMessage the validity message
	 */
	public InvalidDatatypeException(String validityMessage) {
		super(validityMessage);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2326437407791681700L;
	
}
