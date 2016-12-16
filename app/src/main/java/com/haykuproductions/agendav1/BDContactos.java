package com.haykuproductions.agendav1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hayku on 15/12/2016.
 */

public class BDContactos extends SQLiteOpenHelper {
    private static final int VERSION_BASEDATOS = 2;
    private static final String NOMBRE_BASEDATOS = "BD_Agenda.db";

    private static final String contactos = "CREATE TABLE contactos (idContacto INTEGER PRIMARY KEY, nombre VARCHAR(50), direccion VARCHAR(50),email varchar(50),webblog Varchar(100))";
    private static final String telefonos = "CREATE TABLE  telefonos(idTelefonos INTEGER PRIMARY KEY AUTOINCREMENT, telefono VARCHAR(45), idContacto INTEGER)";
    private static final String fotos = "CREATE TABLE fotos (idFoto INTEGER PRIMARY KEY AUTOINCREMENT, nomFichero VARCHAR(50), idContacto INTEGER)";


    public BDContactos(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(contactos);
        sqLiteDatabase.execSQL(telefonos);
        sqLiteDatabase.execSQL(fotos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contactos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS telefonos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS fotos");
        onCreate(sqLiteDatabase);
    }

}
