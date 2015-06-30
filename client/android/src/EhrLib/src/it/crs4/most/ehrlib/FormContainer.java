package it.crs4.most.ehrlib;

import java.util.List;

import android.util.Log;
import android.view.ViewGroup;

import it.crs4.most.ehrlib.datatypes.EhrDatatype;
import it.crs4.most.ehrlib.exceptions.InvalidDatatypeException;
import it.crs4.most.ehrlib.widgets.DatatypeWidget;


/**
 * A Form Container contains the list of {@link DatatypeWidget} widgets included in a Form along with the visual layout containing them.
 */
public class FormContainer {
	
	/** The layout. */
	ViewGroup layout = null;
	
	/** The widgets. */
	List<DatatypeWidget<EhrDatatype>> widgets = null;
    
    /** The index. */
    int index = -1;
	
	/** The tag. */
	private String TAG="FormContainer";
    
    /**
     * Creates a new Form Container
     * 
     * @param layout the layout the layout containing all the {@link DatatypeWidget}
     * @param widgets the list of the {@link DatatypeWidget}
     * @param index the index of this form
     */
	public FormContainer(ViewGroup layout, List<DatatypeWidget<EhrDatatype>> widgets, int index)
	{
		this.layout = layout;
		this.widgets = widgets;
		this.index = index;
	}

	/**
	 * Reset the content of the selected widget according to the current value of the underlying data type.
	 *
	 * @param index the index
	 */
	public void resetWidget(int index)
	{
		this.widgets.get(index).reset();
	}
	
	/**
	 * Reset the content of all widgets of this form according to the current value of their underlying data types.
	 */
	public void resetAllWidgets()
	{
		for (int i=0;i<this.widgets.size();i++)
			this.resetWidget(i);
	}
	
	/**
	 * Update the value of the underlying data type according to the current content of the widget.
	 *
	 * @param index the widget index
	 * @throws InvalidDatatypeException if the content cannot be converted to the datatype.
	 */
    public void submitWidget(int index) throws InvalidDatatypeException
    {
    	Log.d(TAG,"Saving widget for path:" + this.widgets.get(index).getDatatype().getPath());
    	this.widgets.get(index).save();
    }
    
    /**
     * Submit all widgets.
     *
     * @throws InvalidDatatypeException if any of the widgets contains invalid data
     */
    public void submitAllWidgets() throws InvalidDatatypeException
	{
		for (int i=0;i<this.widgets.size();i++)
			this.submitWidget(i);
	}
    
	/**
	 * Gets the layout of this form
	 *
	 * @return the layout
	 */
	public ViewGroup getLayout() {
		return this.layout;
	}



	/**
	 * Get the widgets of this form container.
	 *
	 * @return the widgets
	 */
	public List<DatatypeWidget<EhrDatatype>> getWidgets() {
		return widgets;
	}


	/**
	 * Get the index of this form container
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
}
