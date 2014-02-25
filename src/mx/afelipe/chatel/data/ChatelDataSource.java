package mx.afelipe.chatel.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.afelipe.chatel.model.RegistroLlamada;
import mx.afelipe.chatel.model.Tarifa;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ChatelDataSource {
	//campos para la conexion a la BD
	 private SQLiteDatabase database;
	 private ChatelDBHelper chateldb;
	 
	 //constructor
	 public ChatelDataSource(Context context)
	 {
		 chateldb = new ChatelDBHelper(context);
	 }
	 
	 public void Open() throws SQLException 
	 {
	    database = chateldb.getWritableDatabase();
	 }
	 
	 public void Close(){
		 database.close();
		 chateldb.close();
	 }

	 public List<Tarifa> ObtenerTarifas()
	 {
		 //obtener los registros
		 //Cursor es para un solo registro
		 
		 Cursor cursor = database.rawQuery("select * from tarifas", null);
		 
		 //recorrer la lista de registros y convertirlos a objetos Tarifa para devolverlos
		 List<Tarifa> listatarifas = new ArrayList<Tarifa>();
		 //Log.d("total de tarifas", cursor.getCount() + " ");
		 
		 while(cursor.moveToNext())
		 {
			 Log.d("leyendo tarifas", "Se ha leido una tarifa");
			 Tarifa tarifa = new Tarifa();
			 tarifa.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 tarifa.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
			 tarifa.setCosto(cursor.getFloat(cursor.getColumnIndex("costo")));
			 
			 listatarifas.add(tarifa);
		 }
		 cursor.close();
		return listatarifas;
		 
	 }
	 
	 //localizar tarifa por nombre
	 public Tarifa BuscarTarifa(String Nombre)
	 {
		 Cursor cursor = database.rawQuery("select * from tarifas where nombre = ?", new String[]{ Nombre });
		 //procesar el resultado y convertirlo a objeto Tarifa
		 if(cursor.moveToFirst())
		 {
			 Tarifa tarifa = new Tarifa();
			 tarifa.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 tarifa.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
			 tarifa.setCosto(cursor.getFloat(cursor.getColumnIndex("costo")));
			 
			 return tarifa;
		 }else
			 return null;
	 }
	 
	 //actualizar una sola tarifa
	 public boolean ActualizarTarifa(Tarifa tarifa)
	 {
		 try
		 {
			 ContentValues valores = new ContentValues();
			 valores.put("costo" , tarifa.getCosto());
			 
			 //actualizar
			 database.update("tarifas",valores,"id = ?", new String[]{ String.valueOf(tarifa.getId())});
		 return true;
		 }catch(SQLException ex)
		 {
			 return false;
		 }
	 }
	 
	 public boolean ActualizarTarifas(List<Tarifa> Tarifas)
	 {
		 try
		 {
			 for (Tarifa tarifa : Tarifas) {
				 
				 ContentValues valores = new ContentValues();
				 valores.put("costo" , tarifa.getCosto());
				 //actualizar
				 database.update("tarifas",valores,"id = ?", new String[]{ String.valueOf(tarifa.getId())});
			 }
		 
			 return true;
		 }catch(SQLException ex)
		 {
			 return false;
		 }
	 }
	 

	 @SuppressLint("SimpleDateFormat")
	public RegistroLlamada RegistrarLlamada(RegistroLlamada registro)
	 {
		 try
		 {
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 
			 ContentValues valores = new ContentValues();
			 valores.put("numero", registro.getNumero());
			 valores.put("linea", registro.getLinea());
			 valores.put("duracion", registro.getDuracion());
			 valores.put("tarifa", registro.getTarifa());
			 valores.put("importe", registro.getImporte());
			 //conversion de la fecha
			 valores.put("fecha", format.format(registro.getFecha()));
			 //valores.put("fecha", registro.getFecha().getTime());
			 
			 //Log.d("Registro de llamada", "Fecha de llamada" + format.format(registro.getFecha()) + "importe " + registro.getImporte());
			 
			 database.insert("registrosllamadas", null, valores);
			 Log.d("Registro insertado", "Si se registro la llamada");
			 //recuperar el registro que coincida con el recientemente insertado
			 Cursor cursor = database.rawQuery("select * from registrosllamadas where numero = ? and linea = ? and duracion = ? and importe = ? and fecha = ?", new String[]{ registro.getNumero(), String.valueOf(registro.getLinea()), String.valueOf(registro.getDuracion()), String.valueOf(registro.getImporte()), format.format(registro.getFecha()) });
			 //Cursor cursor = database.rawQuery("select * from registrosllamadas where numero = ? and linea = ? and duracion = ? and importe = ? and fecha = ?", new String[]{ registro.getNumero(), String.valueOf(registro.getLinea()), String.valueOf(registro.getDuracion()), String.valueOf(registro.getImporte()), String.valueOf(registro.getFecha().getTime()) });
			 //procesar el resultado y convertirlo a objeto Tarifa
			 if(cursor.moveToFirst())
			 {
				 registro.setId(cursor.getInt(cursor.getColumnIndex("id")));
				 
				 return registro;
			 }else
				 return null;
			 
		 }catch(SQLException ex)
		 {
			 return null;
		 }
	 }
	 
	 @SuppressLint("SimpleDateFormat")
	 public List<RegistroLlamada> HistorialLlamadas(Date FechaInicio, Date FechaFin)
	 {

		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 //Log.d("Obteniendo registros...", "Antes de hacer la consulta>>>" +format.format(FechaInicio)+">>" + FechaInicio.getTime() +"   -- " + format.format(FechaFin));
		 
		 Cursor cursor = database.rawQuery("select id, numero, linea, duracion, tarifa, importe, fecha, (strftime('%s', fecha , 'start of day') * 1000) AS fechams from registrosllamadas where fechams >= (strftime('%s', ?, 'start of day') * 1000) and fechams <= (strftime('%s', ?, 'start of day') * 1000)", new String[]{ format.format(FechaInicio), format.format(FechaFin) });
		 //Cursor cursor = database.rawQuery("select * from registrosllamadas where fecha >= ? and fecha <= ? ", new String[]{ String.valueOf(FechaInicio.getTime()), String.valueOf(FechaFin.getTime()) });
		 //Cursor cursor = database.rawQuery("select id, numero, linea, duracion, tarifa, importe, fecha from registrosllamadas", null);
		 List<RegistroLlamada> historialList = new ArrayList<RegistroLlamada>();
		 
		 while(cursor.moveToNext())
		 {
			 RegistroLlamada registro = new RegistroLlamada();
			 registro.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 registro.setDuracion(cursor.getInt(cursor.getColumnIndex("duracion")));
			 registro.setImporte(cursor.getFloat(cursor.getColumnIndex("importe")));
			 registro.setLinea(cursor.getInt(cursor.getColumnIndex("linea")));
			 registro.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
			 registro.setTarifa(cursor.getString(cursor.getColumnIndex("tarifa")));
			 try {
				registro.setFecha(format.parse(cursor.getString(cursor.getColumnIndex("fecha"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				registro.setFecha(new Date());
			}
			 //registro.setFecha(new Date(cursor.getLong(cursor.getColumnIndex("fecha"))));
			 
			 Log.d("Registro " + cursor.getInt(cursor.getColumnIndex("id")), "Fecha>> " + cursor.getLong(cursor.getColumnIndex("fecha")) );
			 historialList.add(registro);
		 }
		 
		 return historialList;
	 }
	 
	 

}
