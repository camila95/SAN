package com.uptc.san;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.uptc.san.entidades.Empleados;
import com.uptc.san.utilidades.Constantes;

import java.util.ArrayList;

public class consultar extends AppCompatActivity {
    Spinner comboEmpleados;
    EditText nombres, apellidos, cargo , salario;
    ArrayList<String> listaEmpleados ;
    ArrayList<Empleados> empleadosLista;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_empleados", null, 1);

        comboEmpleados = (Spinner)findViewById(R.id.comboEmpleados);
        nombres = (EditText)findViewById(R.id.nombres);
        apellidos = (EditText)findViewById(R.id.apellidos);
        salario = (EditText)findViewById(R.id.salario);
        cargo = (EditText)findViewById(R.id.cargo);

        consultarListaPersonas();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaEmpleados);
        comboEmpleados.setAdapter(adaptador );

        //Se muestran los datos del empleados, segun la consulta a realizarse
        comboEmpleados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    nombres.setText(empleadosLista.get(position-1).getNombres().toString());
                    apellidos.setText(empleadosLista.get(position-1).getApellidos().toString());
                }else{
                    nombres.setText("");
                    apellidos.setText("");
                    Toast.makeText(getApplicationContext(),"Seleccione un empleado",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void consultarListaPersonas(){
        SQLiteDatabase db = conn.getWritableDatabase();
        Empleados empleados = null;
        empleadosLista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+Constantes.TABLA_EMPLEADOS, null);

        while(cursor.moveToNext()){

            empleados = new Empleados();

            empleados.setIdentificacion(cursor.getInt(0));
            empleados.setNombres(cursor.getString(1));
            empleados.setApellidos(cursor.getString(2));
            empleados.setNumeroTelefono(cursor.getString(3));

            empleadosLista.add(empleados);
        }

        obtenerLista();
    }

    public void obtenerLista(){
        listaEmpleados = new ArrayList<>();
        listaEmpleados.add("Seleccione");

        for (int i = 0; i < empleadosLista.size(); i++){
            listaEmpleados.add(empleadosLista.get(i).getIdentificacion() + " " +empleadosLista.get(i).getNombres());
        }
    }
    //Metodo actualizar Usuario
    private void actualizarUsuario(){
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros = {nombres.getText().toString()};
        ContentValues values= new ContentValues();
        values.put(Constantes.CAMPO_NOMBRES,nombres.getText().toString());
        values.put(Constantes.CAMPO_APELLIDOS,apellidos.getText().toString());

        db.update(Constantes.TABLA_EMPLEADOS,values,Constantes.CAMPO_NOMBRES+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Actualizado",Toast.LENGTH_LONG).show();
    }

    public void Actualizar(View view) {
        actualizarUsuario();
    }
}
