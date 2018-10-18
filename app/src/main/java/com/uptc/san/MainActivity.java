package com.uptc.san;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.uptc.san.utilidades.Constantes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se crea la base de datos
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"bd_empleados",null,1);
        //Se ingresan los datos de trabajos en la db creada
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(Constantes.INSERT_1);
        db.execSQL(Constantes.INSERT_2);
        db.execSQL(Constantes.INSERT_3);
        db.execSQL(Constantes.INSERT_4);
        db.execSQL(Constantes.INSERT_5);
    }

    /**
     * Botón de ingresar
     * @param view
     */
    public void ingresar(View view) {
        login();
    }

    /**
     * Método que lleva al Registrar de la app
     */
    public void login(){
        Intent pantallaRegistrarEmpleados = new Intent(MainActivity.this, ClienteActivity.class);
        startActivity(pantallaRegistrarEmpleados);
    }
}

