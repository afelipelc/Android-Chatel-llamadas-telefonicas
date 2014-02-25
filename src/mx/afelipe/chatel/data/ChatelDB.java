package mx.afelipe.chatel.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatelDB extends SQLiteOpenHelper {

	public ChatelDB(Context context) {
		super(context, "chateldatabase.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//crear la base de datos
		//crear las tablas
		//podemos hasta agregar los registros iniciales (si fuera necesario)
		
		
		//AQUI CREAR LAS TABLAS
		String tablaTarifas = "create table tarifas("
	            + "id integer primary key autoincrement, "
	            + "nombre text not null, "
	            + "costo real not null);";
		db.execSQL(tablaTarifas);
		
		String tablaRegistrosLlamadas = "create table registrosllamadas("
	            + "id integer primary key autoincrement, "
	            + "numero text not null, "
	            + "linea integer not null, "
	            + "duracion real not null,"
	            + "tarifa text not null, "
	            + "importe real not null,"
	            + "fecha datetime not null"
	            + ");";
		db.execSQL(tablaRegistrosLlamadas);
		
		//insertar las tarifas en valor inicial
		String sql = "insert into tarifas(nombre, costo) values('Local',0);";
		db.execSQL(sql);
		sql = "insert into tarifas(nombre, costo) values('Nacional',0)";
		db.execSQL(sql);
		sql = "insert into tarifas(nombre, costo) values('Internacional',0);";
		db.execSQL(sql);
		sql = "insert into tarifas(nombre, costo) values('Celular local',0);";
		db.execSQL(sql);
		sql = "insert into tarifas(nombre, costo) values('Celular Nacional',0);";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// actualizar la estructura de la base de datos
		
		//primero eliminar las tablas existentes (los datos se pierden)
		db.execSQL("DROP TABLE IF EXISTS tarifas");
		db.execSQL("DROP TABLE IF EXISTS registrosllamadas");
		
		//volver a crear la base de datos
        onCreate(db);
	}

}
