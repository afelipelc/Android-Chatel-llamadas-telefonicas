package mx.afelipe.chatel.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.afelipe.chatel.R;
import mx.afelipe.chatel.R.layout;
import mx.afelipe.chatel.R.menu;
import mx.afelipe.chatel.adapters.AdaptadorLlamadas;
import mx.afelipe.chatel.data.ChatelDataSource;
import mx.afelipe.chatel.model.RegistroLlamada;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrosHoyActivity extends Activity {

	private ChatelDataSource datosdb;
	ListView RegistrosListView;
	TextView fechatextview;
	TextView totallamadasxtview;
	TextView ingresodiatextview;
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registroshoy);
		
		this.datosdb = new ChatelDataSource(this);
		
		this.RegistrosListView = (ListView) findViewById(R.id.registrosList);
		
		this.fechatextview = (TextView) findViewById(R.id.FechaRegistrosTextView);
		this.totallamadasxtview = (TextView) findViewById(R.id.TotalLlamadasTextView);
		this.ingresodiatextview = (TextView) findViewById(R.id.IngresoTotalTextView);
		
		this.fechatextview.setText(format.format(new Date()));
		
		//al cargar el activity mandar a mostra una coleccion de objetos RegistroLlamada
		//con un for crear 10 objetos RegistroLlamada
		
		//Calendar cal = Calendar.getInstance();
		//cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
		
		
		
		datosdb.Open();
		//List<RegistroLlamada> listaregistros = datosdb.HistorialLlamadas(new Date(), new Date());
		List<RegistroLlamada> listaregistros = datosdb.HistorialLlamadas(new Date(), new Date());
		datosdb.Close();
		
		/*
		for(int i=0; i<=10; i++)
		{
			RegistroLlamada registro = new RegistroLlamada();
			registro.setDuracion(i + 1 * 3);
			registro.setFecha(new Date());
			registro.setImporte(i + 1 * 3 * 3);
			registro.setNumero("234 34 2 35 4" + i);
			registro.setTarifa("Nombre tarifa");
			
			listaregistros.add(registro);
		}
		*/
		
		
		if(listaregistros!=null)
		{
			//asignar el adaptador al listview
			this.RegistrosListView.setAdapter(new AdaptadorLlamadas(this, listaregistros));
			
			this.totallamadasxtview.setText(listaregistros.size() + " llamadas");
			
			float ingresototal = 0;
			for(RegistroLlamada item : listaregistros)
				ingresototal += item.getImporte();
			
			this.ingresodiatextview.setText("$" + ingresototal);
			
			
		}else
			Toast.makeText(getApplicationContext(), "No existen registros de llamadas", Toast.LENGTH_SHORT).show();
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reportes, menu);
		return true;
	}

}
