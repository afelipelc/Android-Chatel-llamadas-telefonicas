package mx.afelipe.chatel.activities;

import java.util.List;

import mx.afelipe.chatel.R;
import mx.afelipe.chatel.R.layout;
import mx.afelipe.chatel.R.menu;
import mx.afelipe.chatel.data.ChatelDataSource;
import mx.afelipe.chatel.model.Tarifa;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TarifasActivity extends Activity {

	Button guardarBtn;
	EditText tarifaLocalText;
	EditText tarifaNacionalText;
	EditText tarifaInternacionalText;
	EditText tarifaCelLocalText;
	EditText tarifaCelNacionalText;
	EditText codigoAreaLocalText;
	
	List<Tarifa> listatarifas;
	private ChatelDataSource datosdb;
	private SharedPreferences chatelpreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarifas);
		
		this.guardarBtn = (Button) findViewById(R.id.guardarTarifasButton);
		this.tarifaLocalText = (EditText) findViewById(R.id.tarifaLocalText);
		this.tarifaNacionalText = (EditText) findViewById(R.id.tarifaNacionalText);
		this.tarifaInternacionalText = (EditText) findViewById(R.id.tarifaInternacionalText);
		this.tarifaCelLocalText = (EditText) findViewById(R.id.tarficaCelLocalText);
		this.tarifaCelNacionalText = (EditText) findViewById(R.id.tarifaCelNacionalText);
		this.codigoAreaLocalText = (EditText) findViewById(R.id.codigoAreaLocalText);
		
		//obtener los extras enviados por el activity que invoco
		/*
		if(getIntent().getExtras() != null)
		{
			//aqui procesar los extras
			Bundle extras = getIntent().getExtras();
			//obtener el saludo
			String saludo = extras.getString("saludo");
			Toast.makeText(getApplicationContext(), saludo, Toast.LENGTH_SHORT).show();
		}
		*/
		
		//obtener la lista de tarifas
		//inicializar la bd
		datosdb = new ChatelDataSource(this);
		datosdb.Open();
		
		//buscar tarifa por nombre
		//Tarifa tarifa = datosdb.BuscarTarifa("Local");
		//aqui lo que se quiera hacer con la tarifa
		
		listatarifas = datosdb.ObtenerTarifas();
		
		//Toast.makeText(getApplicationContext(), "se obtivieron " + listatarifas.size() + " tarifas", Toast.LENGTH_SHORT).show();
		
		if(listatarifas.size() == 0)
			Toast.makeText(getApplicationContext(), "No se pudo cargar la lista de tarifas", Toast.LENGTH_SHORT).show();
		else
		{
			//aqui procesar la lista de las tarifas
			for (Tarifa tarifa : listatarifas) {
				if(tarifa.getNombre().equals("Local"))
				{
					tarifaLocalText.setText(String.valueOf(tarifa.getCosto()));
				}else if(tarifa.getNombre().equals("Nacional"))
				{
					tarifaNacionalText.setText(String.valueOf(tarifa.getCosto()));
				}else if(tarifa.getNombre().equals("Internacional"))
				{
					tarifaInternacionalText.setText(String.valueOf(tarifa.getCosto()));
				}else if(tarifa.getNombre().equals("Celular local"))
				{
					tarifaCelLocalText.setText(String.valueOf(tarifa.getCosto()));
				}else if(tarifa.getNombre().equals("Celular Nacional"))
				{
					tarifaCelNacionalText.setText(String.valueOf(tarifa.getCosto()));
				}
				
			}
		}
		datosdb.Close();
		//fin obtener lista de tarifas
		
		//obtener el area local
		CargarPreferencias();
		
		this.guardarBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//procesar la lista de tarifas nuevamente
				if(listatarifas != null && listatarifas.size()>0)
				{
					for (Tarifa tarifa : listatarifas) {
						if(tarifa.getNombre().equals("Local"))
						{
							tarifa.setCosto(Float.valueOf(tarifaLocalText.getText().toString()));
						}else if(tarifa.getNombre().equals("Nacional"))
						{
							tarifa.setCosto(Float.valueOf(tarifaNacionalText.getText().toString()));
						}else if(tarifa.getNombre().equals("Internacional"))
						{
							tarifa.setCosto(Float.valueOf(tarifaInternacionalText.getText().toString()));
						}else if(tarifa.getNombre().equals("Celular local"))
						{
							tarifa.setCosto(Float.valueOf(tarifaCelLocalText.getText().toString()));
						}else if(tarifa.getNombre().equals("Celular Nacional"))
						{
							tarifa.setCosto(Float.valueOf(tarifaCelNacionalText.getText().toString()));
						}
					}
				}
				
				//mandar a guardar
				datosdb.Open();
				if(datosdb.ActualizarTarifas(listatarifas))
					Toast.makeText(getApplicationContext(), "Las tarifas fueron actualizadas", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Ocurrió un error al intentar actualizar las tarifas", Toast.LENGTH_SHORT).show();
					
				datosdb.Close();
				
				//guardar el codigo de area
				SharedPreferences.Editor editor = chatelpreferences.edit();
				editor.putString("arealocal", codigoAreaLocalText.getText().toString());
				editor.commit(); //save changes
				
				finish();
			}
		});
		
		
	}
	
    private void CargarPreferencias()
    {
        //Retrive store shared preferences
    	chatelpreferences = getSharedPreferences("chatelpreferences", Context.MODE_PRIVATE);
        String area = chatelpreferences.getString("arealocal","");
        this.codigoAreaLocalText.setText(area);
    }
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarifas, menu);
		return true;
	}

}
