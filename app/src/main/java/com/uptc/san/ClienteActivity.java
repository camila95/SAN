package com.uptc.san;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.uptc.san.utilidades.Constantes;


public class ClienteActivity extends AppCompatActivity {

    public EditText campoId, campoNombre, campoApellidos, campoTelefono, campoSalario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        campoId = (EditText)findViewById(R.id.identificacion);
        campoNombre = (EditText)findViewById(R.id.campoNombre);
        campoApellidos = (EditText)findViewById(R.id.campoApellidos);
        campoTelefono = (EditText)findViewById(R.id.campoTelefono);
        campoSalario = (EditText)findViewById(R.id.campoSalario);
    }

    public void crear(View view) {
        registrarEmpleados();
    }


    private void registrarEmpleados(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"db_empleados",null,1);
        SQLiteDatabase db= conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constantes.CAMPO_IDENTIFICACION,campoId.getText().toString());
        values.put(Constantes.CAMPO_NOMBRES, campoNombre.getText().toString());
        values.put(Constantes.CAMPO_APELLIDOS, campoApellidos.getText().toString());
        values.put(Constantes.CAMPO_NUMERO_TELEFONO, campoTelefono.getText().toString());

        //long id = consulta: select id from trabajo where nombre = "escogido"
        //falta id cargo
        //values.put(Constantes.CAMPO_ID, id.toString());

        //Constantes.CAMPO_IDENTIFICACION = nombre del campo a devolver
        db.insert(Constantes.TABLA_EMPLEADOS,Constantes.CAMPO_IDENTIFICACION,values);

        Toast.makeText(getApplicationContext(),"Creado satisfactoriamente",Toast.LENGTH_SHORT).show();
        //limpiar campos despues de creado el empleado
        campoNombre.setText("");
        campoApellidos.setText("");
        campoId.setText("");
        campoTelefono.setText("");
        campoSalario.setText("");

        db.close();
    }

    public void consultar(View view){
        Intent pantallaConsultar = new Intent(ClienteActivity.this, consultar.class);
        startActivity(pantallaConsultar);
    }
}
