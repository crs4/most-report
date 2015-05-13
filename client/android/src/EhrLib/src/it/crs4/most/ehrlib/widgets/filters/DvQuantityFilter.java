package it.crs4.most.ehrlib.widgets.filters;

import it.crs4.most.ehrlib.datatypes.DvQuantity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class DvQuantityFilter.
 */
public class DvQuantityFilter {
	
	/** The Constant TAG. */
	protected static final String TAG = "DvQuantityFilter";
	
	/** The edit text. */
	private EditText editText;
	
	/** The validity text view. */
	TextView validityTextView;
	
	/** The dv quantity. */
	private DvQuantity dvQuantity;
    
	/**
	 * Instantiates a new dv quantity filter.
	 *
	 * @param editText the edit text
	 * @param validityTextView the validity text view
	 * @param dvQuantity the dv quantity
	 */
	public DvQuantityFilter(EditText editText, TextView validityTextView, DvQuantity dvQuantity) {
		
		this.editText = editText;
		this.validityTextView = validityTextView;
		
		this.dvQuantity = dvQuantity;
		this.editText.addTextChangedListener(editTextWatcherForDvQuantity);
		this.checkValidity();
	}
	
	/**
	 * Check validity.
	 */
	private void checkValidity()
	{   
		double value = 0;
		try{
		value =  Double.parseDouble(editText.getText().toString());
		} catch (NumberFormatException e)
		{
			Log.e(TAG, "Invalid double:" + e);
			setValid(false, "No Valid Number:" + dvQuantity.getConstraintsInfo());
			return;
		}
		
		boolean isValid = this.dvQuantity.isValid(value);
		
		setValid(isValid, this.dvQuantity.getValidityMessage(value));
		
	 
	}

	/**
	 * Sets the valid.
	 *
	 * @param isValid the is valid
	 * @param validityMessage the validity message
	 */
	private void setValid(boolean isValid, String validityMessage) {
		
		if (isValid)
		{
			editText.setTextColor(Color.WHITE);
			validityTextView.setTextColor(Color.GREEN);
			
		}
		else
		{
			editText.setTextColor(Color.MAGENTA);
			validityTextView.setTextColor(Color.MAGENTA);
		}
		
		validityTextView.setText(validityMessage);
	}

	/** The edit text watcher for dv quantity. */
	private TextWatcher editTextWatcherForDvQuantity = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
			checkValidity();
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
		
		
	};
	
}
