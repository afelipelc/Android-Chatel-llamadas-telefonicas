package mx.afelipe.chatel.activities;

import java.util.Date;

import mx.afelipe.chatel.R;
import mx.afelipe.chatel.R.menu;
import mx.afelipe.chatel.data.ChatelDataSource;
import mx.afelipe.chatel.model.RegistroLlamada;
import mx.afelipe.chatel.model.Tarifa;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.Editable.Factory;
import android.util.Log;
import android.view.ActionProvider.VisibilityListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContadorLineaActivity extends Activity {

	TextView numLineaTextView;
	TextView importePagarTextView;
	Chronometer cronometroLinea;
	EditText numeroTelefonoEditText;
	Button iniciarContadorBtn;
	Button detenerContadorBtn;
	Button reiniciarContadorBtn;
	
	String numeroLinea="0";
	private ChatelDataSource datosdb;
	RegistroLlamada registroLlamada;
	Tarifa tarifa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contador_linea);
		
		this.numLineaTextView = (TextView) findViewById(R.id.NumeroLineaTextView);
		this.cronometroLinea = (Chronometer) findViewById(R.id.ContadorTiempoChron);
		this.numeroTelefonoEditText = (EditText) findViewById(R.id.numeroTelefonoEditText);
		this.importePagarTextView = (TextView) findViewById(R.id.importePagarTextView);
		this.iniciarContadorBtn = (Button) findViewById(R.id.iniciarContadorBtn);
		this.detenerContadorBtn = (Button) findViewById(R.id.detenerContadorBtn);
		this.reiniciarContadorBtn = (Button) findViewById(R.id.reiniciarBtn);
				
		datosdb = new ChatelDataSource(this);
		
		//si trae el numero de linea
		if(getIntent().getExtras() != null)
		{
			Bundle extras = getIntent().getExtras();
			numeroLinea = extras.getString("num");
			 
			 if(!numeroLinea.equals(""))
			 {
				 numLineaTextView.setText("Línea número " + numeroLinea);
			 } 
		}
		
		/// Control de eventos en botones
		this.iniciarContadorBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// comprobar que se ingreso el numero de telefono
				
				if(!numeroTelefonoEditText.getText().toString().equals(""))
				{
					//Log.d("Contador", "Iniciado");
					
					//aqui dar forma al registro de llamada
					registroLlamada = new RegistroLlamada();
					registroLlamada.setFecha(new Date());
					registroLlamada.setNumero(numeroTelefonoEditText.getText().toString());
					registroLlamada.setLinea(Integer.valueOf(numeroLinea));
					
					datosdb.Open();
					tarifa = datosdb.BuscarTarifa("Local");
					if(tarifa!=null)
					{
						registroLlamada.setTarifa("Local");
					}else
					{
						Toast.makeText(getApplicationContext(), "No se pudo encontrar la tarifa que aplica a esta llamada, no se puede continuar.", Toast.LENGTH_SHORT).show();
						datosdb.Close();
						return;
					}
					
					datosdb.Close();
					
					cronometroLinea.setBase(SystemClock.elapsedRealtime());
					cronometroLinea.start();
					Log.d("Contador", "Iniciado y contando para el numero " + registroLlamada.getNumero() + " con tarifa Local de " + tarifa.getCosto() + " x min");
					numeroTelefonoEditText.setEnabled(false);
					detenerContadorBtn.setVisibility(View.VISIBLE);
					reiniciarContadorBtn.setVisibility(View.GONE);
					//Log.d("Contador", "antes de iniciar");
					
					iniciarContadorBtn.setVisibility(View.GONE);
					
				}else
				{
					Toast.makeText(getApplicationContext(), "Ingrese el número de teléfono a registrar.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		this.detenerContadorBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//if(cronometroLinea.)
				//{
					cronometroLinea.stop();
					
					//obtener los datos del chronometro y registrarlos
					long tiempotranscurrido = SystemClock.elapsedRealtime() - cronometroLinea.getBase();
					//registrar la llamada
					//long duracion = (tiempotranscurrido/1000);
					registroLlamada.setDuracion((int)(tiempotranscurrido/1000));
					registroLlamada.setImporte(12);
					
					datosdb.Open();
					registroLlamada = datosdb.RegistrarLlamada(registroLlamada);
					datosdb.Close();
					
					if(registroLlamada!=null && registroLlamada.getId()>0)
					{
						Log.d("Contador", "Llamada registrada con id" + registroLlamada.getId());
						Toast.makeText(getApplicationContext(), "La llamada ha sido registrada.", Toast.LENGTH_SHORT).show();
					}else
						Toast.makeText(getApplicationContext(), "Ocurrio un error al intentar registrar la llamada.", Toast.LENGTH_SHORT).show();
					
					numeroTelefonoEditText.setEnabled(true);
					//iniciarContadorBtn.setVisibility(1);
					iniciarContadorBtn.setVisibility(View.VISIBLE);
					reiniciarContadorBtn.setVisibility(View.VISIBLE);
					detenerContadorBtn.setVisibility(View.GONE);
					iniciarContadorBtn.setVisibility(View.VISIBLE);
					
				//}
			}
		});
	
		/*
		//Numero telefono Edit Text
		this.numeroTelefonoEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "al ser ingresado el numero", Toast.LENGTH_SHORT).show();
			}
		});
		*/
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contador_linea, menu);
		return true;
	}

}
