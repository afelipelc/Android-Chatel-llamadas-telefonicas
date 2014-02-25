package mx.afelipe.chatel.activities;

import mx.afelipe.chatel.R;
import mx.afelipe.chatel.R.layout;
import mx.afelipe.chatel.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button lineasButton;
	Button configTarifasButton;
	Button registrosButton;
	Button reportesButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.lineasButton = (Button) findViewById(R.id.btn_lineas);
		this.configTarifasButton = (Button) findViewById(R.id.btn_configuracion);
		this.registrosButton = (Button) findViewById(R.id.btn_registros);
		this.reportesButton =(Button) findViewById(R.id.btn_reportes);
		
		this.lineasButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent iraLineas = new Intent(MainActivity.this, LineasActivity.class);
				startActivity(iraLineas);
			}
		});
		
		this.configTarifasButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent iraTarifas = new Intent(MainActivity.this, TarifasActivity.class);
				//agregar extras
				//iraTarifas.putExtra("saludo", "Hola como estas...");
				startActivity(iraTarifas);
			}
		});
		
		
		this.registrosButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent iraRegistrosHoy = new Intent(MainActivity.this, RegistrosHoyActivity.class);
				startActivity(iraRegistrosHoy);
			}
		});
		
		this.reportesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent iraReportes = new Intent(MainActivity.this, ReportesActivity.class);
				startActivity(iraReportes);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
