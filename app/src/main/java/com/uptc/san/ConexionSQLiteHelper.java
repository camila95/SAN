package com.uptc.san;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.uptc.san.utilidades.Constantes;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Metodo encargado de crear las Tablas de bd
     * @param db
     */

     @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(Constantes.CREAR_TABLA_EMPLEADOS);
    }

    /**
     * actualiza la base de datos, verifica si existe la version antigua
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS EMPLEADOS");
        onCreate(db);

    }
}
