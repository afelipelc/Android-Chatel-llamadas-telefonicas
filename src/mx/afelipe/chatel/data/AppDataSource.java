package mx.afelipe.chatel.data;

import java.util.ArrayList;
import java.util.List;

import mx.afelipe.chatel.model.Tarifa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AppDataSource {
	//esta clase, contiene los metodos para abrir y cerrar la base de datos
	//metodos para obtener la lista de tarifas, actualizar tarifas
	//crear registros de llamadas
	//quiza hasta actualizar registros de llamadas
	//obtener la lista de llamadas registrara para mostrarlas en un informe
	//y todo lo que quieran que haga con la base de datos
	
	//necesitamos los objetos de conexion a la base de datos
	 private SQLiteDatabase database;
	 private ChatelDB chateldb;
	 
	 //constructor
	 public AppDataSource(Context context)
	 {
		 chateldb = new ChatelDB(context);
	 }
	 
	 //metodo para abrir la base de datos
	 public void Open() throws SQLException
	 {
		 //abrir la base de datos para escritura
		 database = chateldb.getWritableDatabase();
	 }
	 
	 public void Close()
	 {
		 //cerrar la base de datos
		 database.close();
		 chateldb.close();
	 }
	 
	 //implementar todos los metodos que permitan manipular la fuente de datos
	 //aqui necesitamos usar objetos de la clase Tarifa y la clase RegistroLlamada
	 
	 //metodo que devuelva la lista de Tarifa
	 public List<Tarifa> ListaTarifas()
	 {
		 //aqui realizar la consulta a la bd para obtener la lista de tarifas
		 Cursor cursor = database.rawQuery("select * from tarifas", null);
		 
		 //crear la coleccion de objetos Tarifa
		 List<Tarifa> tarifas = new ArrayList<Tarifa>();
		 
		 //procesar el objeto cursor >>> contiene los resultados
		 while(cursor.moveToNext())
		 {
			 //tomar los datos del resultado y ponerlos en un objeto Tarifa
			 Tarifa tarifa = new Tarifa();
			 
			 tarifa.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 tarifa.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
			 tarifa.setCosto(cursor.getFloat(cursor.getColumnIndex("costo")));
			 
			 //el objeto tarifa agregarlo a la coleccion
			 tarifas.add(tarifa);
		 }
		  
		 return tarifas;
	  }
	 
	 //implementar metodo que devuelva un objeto de la clase tarifa
	 public Tarifa BuscarTarifa(String nombreTarifa)
	 {
		 //instrucciones que devuelvan el objeto tarifa
		 Cursor cursor = database.rawQuery("select * from tarifas where nombre = ?", new String[] { nombreTarifa});
		 
		 //si se tiene un resultado, procesarlo, sino entonces devover NULL
		 if(cursor.moveToFirst())
		 {
			 Tarifa tarifa = new Tarifa();
			 tarifa.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 tarifa.setCosto(cursor.getFloat(cursor.getColumnIndex("costo")));
			 tarifa.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
			 
			 return tarifa;
		 }else
			 return null;
	 }
	 
	 //metodo que permita actualizar una tarifa
	 public boolean ActualizarTarifa(Tarifa tarifa)
	 {
		 try
		 {
			 //instruccion que mande a actualizar el registro en la BD
			 ContentValues valor = new ContentValues();
			 valor.put("costo" , tarifa.getCosto());
			 //actualizar
			 database.update("tarifas",valor,"id = ?", new String[]{ String.valueOf(tarifa.getId()) });
			 //new String[] { "1" }
			 return true;
			 
		 }catch(SQLException ex)
		 {
			 return false;
		 }
	 }
	 
	 //agregar los metodos que permitan registrar llamadas
	 //obtener la lista de llamadas registras por fecha
	 //o por rango de fechas
	 
}
