package mx.afelipe.chatel.activities;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import mx.afelipe.chatel.R;
import mx.afelipe.chatel.R.layout;
import mx.afelipe.chatel.R.menu;
import mx.afelipe.chatel.adapters.AdaptadorLlamadas;
import mx.afelipe.chatel.data.ChatelDataSource;
import mx.afelipe.chatel.model.RegistroLlamada;
import mx.afelipe.chatel.utils.DatePickerFragment;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportesActivity extends FragmentActivity implements OnDateSetListener {

	Button reportesDeButton;
	Button reportesAButton;
	Date fechaInicio;
	Date fechaFin;
	ListView listaRegistrosList;
	TextView totallamadasxtview;
	TextView ingresototaltextview;
	
	private ChatelDataSource datosdb;
	int fechaedit=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportes);
		
		this.reportesDeButton = (Button) findViewById(R.id.reportesDeButton);
		this.reportesAButton = (Button) findViewById(R.id.reportesAButton);
		this.listaRegistrosList = (ListView) findViewById(R.id.listaRegistrosList);
		
		this.totallamadasxtview = (TextView) findViewById(R.id.TotalLlamadasTextView);
		this.ingresototaltextview = (TextView) findViewById(R.id.IngresoTotalTextView);
		
		this.datosdb = new ChatelDataSource(this);
		fechaInicio = new Date();
		fechaFin = new Date();
		
		this.reportesDeButton.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fechaedit=1;
				DialogFragment newFragment = new DatePickerFragment(fechaInicio);
			    newFragment.show(getFragmentManager(), "datePicker");
			}
		});
		
		this.reportesAButton.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fechaedit=2;
				DialogFragment newFragment = new DatePickerFragment(fechaFin);
			    newFragment.show(getFragmentManager(), "datePicker");
			}
		});
	}

	DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		
		switch(fechaedit)
		{
		case 1:
			try {
				fechaInicio = format.parse(day+"/"+(month+1)+"/"+year);
				reportesDeButton.setText(format.format(fechaInicio));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				fechaFin = format.parse(day+"/"+(month+1)+"/"+year);
				reportesAButton.setText(format.format(fechaFin));
				CargarRegistros();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
		fechaedit=0;
	}
	
	private void CargarRegistros()
	{
		if(fechaInicio.getTime()>fechaFin.getTime())
		{
			Toast.makeText(getApplicationContext(), "No se puede generar un reporte cuando la fecha de inicio es mas reciente que la de fin.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		datosdb.Open();
		List<RegistroLlamada> listaregistros = datosdb.HistorialLlamadas(fechaInicio, fechaFin);
		datosdb.Close();
		
		if(listaregistros!=null)
		{
			//asignar el adaptador al listview
			listaRegistrosList.setAdapter(new AdaptadorLlamadas(this, listaregistros));
			
			this.totallamadasxtview.setText(listaregistros.size() + " llamadas");
			
			float ingresototal = 0;
			for(RegistroLlamada item : listaregistros)
				ingresototal += item.getImporte();
			
			this.ingresototaltextview.setText("$" + ingresototal);
			
			
		}else
			Toast.makeText(getApplicationContext(), "No existen registros de llamadas para este rango de fechas", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reportes, menu);
		return true;
	}



}
