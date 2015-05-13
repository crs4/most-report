package it.crs4.most.ehrlib.widgets.filters;

import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

public class EditTextLocker {

	protected static final String TAG = "EditTextLocker";

	private EditText editText;

	private int charactersLimit;

	private int fractionLimit;

	public EditTextLocker(EditText editText) {

		this.editText = editText;

		editText.setOnKeyListener(editTextOnKeyListener);
	}

	private OnKeyListener editTextOnKeyListener = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {

 			if (keyCode == KeyEvent.KEYCODE_DEL) {
 				Log.d(TAG, "Pressed DEL KEY");
 			startStopEditing(false);
 			}
 			//else Log.d(TAG, "Pressed KEY:" + keyCode); 

			return false;
		}
	};

	private TextWatcher editTextWatcherForCharacterLimits = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

			if (!editText.getText().toString().equalsIgnoreCase("")) {

				int editTextLength = editText.getText().toString().trim().length();

				if (editTextLength >= charactersLimit) {
                 
					startStopEditing(true);

				}

			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	private TextWatcher editTextWatcherForFractionLimit = new TextWatcher() {
        
		private boolean is_locked = false;
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

			if (!editText.getText().toString().equalsIgnoreCase("")) {

				String editTextString = editText.getText().toString().trim();
				int decimalIndexOf = editTextString.indexOf(".");

				if (decimalIndexOf >= 0) {

					if (editTextString.substring(decimalIndexOf).length() > fractionLimit+1) {
						Log.d(TAG, "Numero di cifre decimali: troppe: " + editTextString.substring(decimalIndexOf).length() );
						Log.d(TAG, "Contenuto: " + s);
						startStopEditing(true);
						editText.setTextColor(Color.RED);

					}
					else 
					{   Log.d(TAG, "Numero di cifre decimali:" + editTextString.substring(decimalIndexOf).length() );
						editText.setTextColor(Color.WHITE);
						startStopEditing(false);
					}
				}

			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           Log.d(TAG, String.format("prima di cambiare: %s start:%s count: %s after:%s" , s, start,count, after));
           if (after==0)
           {
        	   Log.d(TAG, "Premuto back slash. Riabilito editing");
        	   startStopEditing(false);
        	   is_locked = false;
           }
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	public void limitCharacters(final int limit) {

		this.charactersLimit = limit;
		editText.addTextChangedListener(editTextWatcherForCharacterLimits);
	}

	public void limitFractionDigitsinDecimal(int fractionLimit) {

		this.fractionLimit = fractionLimit;
		editText.addTextChangedListener(editTextWatcherForFractionLimit);
	}

	public void unlockEditText() {

		startStopEditing(false);
	}

	public void startStopEditing(boolean isLock) {

		if (isLock) {
           Log.d(TAG, "FILTER LOCKED");
			editText.setFilters(new InputFilter[] { new InputFilter() {
				@Override
				public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
					Log.d(TAG, String.format("IN LOCKED: source:%s start:%s end:%s dest:%s, dstart:%s dend:%s" ,source, start,end, dest, dstart, dend));
					if (dstart<dend) return null;
					else
					return source.length() < 1 ? dest.subSequence(dstart, dend) : "";
				}
			} });

		} else {
			Log.d(TAG, "FILTER UNLOCKED");
			editText.setFilters(new InputFilter[] { new InputFilter() {
				@Override
				public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
					return null;
				}
			} });
		}
	}
}