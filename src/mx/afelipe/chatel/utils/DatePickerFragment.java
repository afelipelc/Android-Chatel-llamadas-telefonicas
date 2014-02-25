package mx.afelipe.chatel.utils;

import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

@SuppressLint({ "NewApi", "ValidFragment" })
public class DatePickerFragment extends DialogFragment {
	
	int year;
	int month;
	int day;
	
	public DatePickerFragment()
	{
		//nothing
	}
	
	public DatePickerFragment(Date fecha)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		
		year =  cal.get(Calendar.YEAR);
		month =  cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
		if(year == 0 || month ==0 || day==0)
		{
	        final Calendar c = Calendar.getInstance();
	        year = c.get(Calendar.YEAR);
	        month = c.get(Calendar.MONTH);
	        day = c.get(Calendar.DAY_OF_MONTH);
		}
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (OnDateSetListener)getActivity(), year, month, day);
    }
/*
    @SuppressLint("SimpleDateFormat")
	public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
    	DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
    	

			try {
				fechaSeleccionada = format.parse(day+"/"+month+"/"+year);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
*/
    	
    
}
