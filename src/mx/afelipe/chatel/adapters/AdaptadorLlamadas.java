package mx.afelipe.chatel.adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import mx.afelipe.chatel.R;
import mx.afelipe.chatel.model.RegistroLlamada;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorLlamadas extends ArrayAdapter<RegistroLlamada> {
	
	//campos
	Context context;
	List<RegistroLlamada> registros;
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public AdaptadorLlamadas(Context context, List<RegistroLlamada> registros) {
		super(context, R.layout.llamada_list_item, registros);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.registros = registros;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	     
		 //objeto que contendra todos los datos de la llamada
		 View item = inflater.inflate(R.layout.llamada_list_item, null);
	     
	     //poner los valores en cada elemento del layout
	     
		 //inicializar los elementos del layout
		 TextView NumeroTelefono = (TextView) item.findViewById(R.id.NumeroTelefonoTextView);
		 TextView TipoLlamada = (TextView) item.findViewById(R.id.TipoLlamadaTextView);	     
		 TextView Importe = (TextView) item.findViewById(R.id.ImporteTextView);
		 TextView Duracion = (TextView) item.findViewById(R.id.DuracionTextView);
		 TextView Fecha = (TextView) item.findViewById(R.id.FechaTextView);
		 TextView NumLinea = (TextView) item.findViewById(R.id.NumLineaTextView);

		 //localizar el objeto en la lista
		 /*
		 float segs = (registros.get(position).getDuracion()/60) - ((int)registros.get(position).getDuracion()/60);
		 String strsegs = String.valueOf(segs);
		 strsegs = strsegs.substring(2);
		 strsegs = strsegs.substring(0, 2);
		 Log.d("segundos extraidos", strsegs);
		 String tiempo = String.valueOf((int)registros.get(position).getDuracion()/60);
		 */
		 
		
		 int mins=0;
		 String segs;
		 
		 /*
		 while (duraciontotal>0)
		 {
			 if(duraciontotal>=60)
			 {
				 mins++;
				 duraciontotal = duraciontotal - 60;
			 }
			 else if(duraciontotal<60)
			 {
				 segs = duraciontotal;
				 duraciontotal = 0;
			 }
		 }
		 */
		 mins = (int)registros.get(position).getDuracion()/60;
		 segs =String.valueOf(registros.get(position).getDuracion() - (mins * 60));
		 if(segs.length() == 1)
			 segs = "0" + segs;
		 
		 NumeroTelefono.setText(registros.get(position).getNumero());
		 TipoLlamada.setText(registros.get(position).getTarifa());
		 Importe.setText("$" + String.valueOf(registros.get(position).getImporte()));
		 Duracion.setText(mins + ":" + segs);
		 Fecha.setText(format.format(registros.get(position).getFecha()));
		 NumLinea.setText(String.valueOf(registros.get(position).getLinea()));
	     
		 return item;
	}
	
	
}
