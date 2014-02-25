package mx.afelipe.chatel.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatelDBHelper extends SQLiteOpenHelper{

    public static  final String NombreBD="chatel.db";
    private static final int VersionBD = 1;
    
    private static final String crear_tarifas_table = "create table tarifas("
            + "id integer primary key autoincrement, "
            + "nombre text not null, "
            + "costo real not null);";
    
    private static final String crear_registros_table = "create table registrosllamadas("
            + "id integer primary key autoincrement, "
            + "numero text not null, "
            + "linea integer not null, "
            + "duracion intenger not null,"
            + "tarifa text not null, "
            + "importe real not null,"
            + "fecha TIMESTAMP not null  DEFAULT current_timestamp"
            + ");";
    
    			//yyyy-MM-dd HH:mm:ss
    //+ "fecha integer not null"
    //+ "fecha TIMESTAMP not null  DEFAULT current_timestamp"
    
//    private static final String insertar_tarifas = "insert into tarifas"
//            + "(nombre, costo) values('Local',0);"
//    		+" insert into tarifas(nombre, costo) values('Nacional',0);"
//    		+" insert into tarifas(nombre, costo) values('Internacional',0);"
//    		+" insert into tarifas(nombre, costo) values('Celular local',0);"
//    		+" insert into tarifas(nombre, costo) values('Celular Nacional',0);";

    
	public ChatelDBHelper(Context context) {
		super(context, NombreBD, null, VersionBD);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		//crear las tablas
		database.execSQL(crear_tarifas_table);
		database.execSQL(crear_registros_table);
		
		//insertar las tarifas en valor inicial
		String sql = "insert into tarifas(nombre, costo) values('Local',0);";
		database.execSQL(sql);
		sql = "insert into tarifas(nombre, costo) values('Nacional',0)";
		database.execSQL(sql);
		sql = "insert into tarifas(nombre, costo) values('Internacional',0);";
		database.execSQL(sql);
		sql = "insert into tarifas(nombre, costo) values('Celular local',0);";
		database.execSQL(sql);
		sql = "insert into tarifas(nombre, costo) values('Celular Nacional',0);";
		database.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//eliminar las tablas existentes, esto desechara toda la informacion previamente registrada
		database.execSQL("DROP TABLE IF EXISTS tarifas");
		database.execSQL("DROP TABLE IF EXISTS registrosllamadas");
		
		//volver a crear la base de datos
        onCreate(database);
	}

}
