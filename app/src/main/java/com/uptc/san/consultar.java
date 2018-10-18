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
import com.uptc.san.entidades.Trabajos;
import com.uptc.san.utilidades.Constantes;

import java.util.ArrayList;

/**
 * Clase que permite Consultar a un empleado
 */
public class consultar extends AppCompatActivity {
    Spinner comboEmpleados, cargo;
    EditText nombres, apellidos, salario, identificacion, telefono;
    ArrayList<String> listaEmpleados ;
    ArrayList<Empleados> empleadosLista;
    ArrayList<String> listaTrabajos;
    ArrayList<Trabajos> trabajosLista;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_empleados", null, 1);

        //spinner que selecciona el empleado a consultarse
        comboEmpleados = (Spinner)findViewById(R.id.comboEmpleados);

        identificacion = (EditText)findViewById(R.id.identificacion);
        nombres = (EditText)findViewById(R.id.nombres);
        apellidos = (EditText)findViewById(R.id.apellidos);
        telefono = (EditText)findViewById(R.id.telefono);
        cargo = (Spinner)findViewById(R.id.cargo);
        salario = (EditText)findViewById(R.id.salario);

        //Se llena la lista de trabajos en el spinner
        consultarTrabajos();
        ArrayAdapter<CharSequence> adaptadorTrabajo = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTrabajos);
        cargo.setAdapter(adaptadorTrabajo);


        //Se add al spinner los datos de los empleados, para mostrarlos en lista
        consultarListaPersonas();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaEmpleados);
        comboEmpleados.setAdapter(adaptador );

        //Se muestran los datos del empleados, segun la consulta a realizarse
        comboEmpleados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    identificacion.setText(empleadosLista.get(position-1).getIdentificacion().toString());
                    nombres.setText(empleadosLista.get(position-1).getNombres().toString());
                    apellidos.setText(empleadosLista.get(position-1).getApellidos().toString());
                    telefono.setText(empleadosLista.get(position-1).getNumeroTelefono().toString());

                    String idCargoSeleccionado = empleadosLista.get(position-1).getIdTrabajo().toString();

                    SQLiteDatabase db = conn.getWritableDatabase();
                    Cursor cursor = db.rawQuery(Constantes.GET_DATOS_TRABAJO_BY_ID + idCargoSeleccionado, null);
                    String nombreTrabajo = cursor.getString(0);
                    Double salarioTrabajo = cursor.getDouble(1);

                    //Se llama al metodo que obtiene la posicion en la lista del nombre del trabajo
                    int posicion = getPositionListaCargo(nombreTrabajo);
                    cargo.setSelection(posicion);
                    salario.setText(salarioTrabajo.toString());

                }else{
                    identificacion.setText("");
                    nombres.setText("");
                    apellidos.setText("");
                    telefono.setText("");
                    cargo.setSelection(0);
                    salario.setText("");
                    Toast.makeText(getApplicationContext(),"Seleccione un empleado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Método que consulta todos los empleados creados, para mostrarlos en el spinner
     */
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

    /**
     * Método que add al inicio el campo "Seleccione" a la lista de los empleados
     */
    public void obtenerLista(){
        listaEmpleados = new ArrayList<>();
        listaEmpleados.add("Seleccione");

        for (int i = 0; i < empleadosLista.size(); i++){
            listaEmpleados.add(empleadosLista.get(i).getIdentificacion() + " " +empleadosLista.get(i).getNombres());
        }
    }

    /**
     * Método que permite realizar la consulta de los trabajos ingresados, obtenerlos y add a la lista
     */
    public void consultarTrabajos(){
        SQLiteDatabase db = conn.getWritableDatabase();
        Trabajos trabajos = null;
        trabajosLista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+Constantes.TABLA_TRABAJOS, null);

        while(cursor.moveToNext()){

            trabajos = new Trabajos();
            trabajos.setIdTrabajo(cursor.getInt(0));
            trabajos.setNombreTrabajo(cursor.getString(1));
            trabajos.setSalario(cursor.getDouble(2));
            trabajosLista.add(trabajos);
        }
        obtenerListaTrabajos();
    }

    /**
     * Método que add el valor "Seleccione" al inicio de la lista de trabajos
     */
    public void obtenerListaTrabajos(){
        listaTrabajos = new ArrayList<>();
        listaTrabajos.add("Seleccione");
        for (int i = 0; i < trabajosLista.size(); i++){
            listaTrabajos.add(trabajosLista.get(i).getNombreTrabajo());
        }
    }

    /**
     * Método que obtiene la posicion en la lista de los trabajos
     * @param nombreCargo
     * @return
     */
    public int getPositionListaCargo(String nombreCargo){
        int result = 0;
        if(this.listaTrabajos.size() > 0 && this.trabajosLista.size() > 0){
            for (int i = 0; i < trabajosLista.size(); i++){
                if(trabajosLista.get(i).getNombreTrabajo().equals(nombreCargo)){
                    result = i+1;
                }
            }
        }
        return result;
    }


    //Metodo actualizar Usuario FALTA TERMINAR
    private void actualizarUsuario(){
        //Escribir en la db
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros = {nombres.getText().toString()};
        ContentValues values= new ContentValues();
        values.put(Constantes.CAMPO_IDENTIFICACION,apellidos.getText().toString());
        values.put(Constantes.CAMPO_NOMBRES,nombres.getText().toString());
        values.put(Constantes.CAMPO_APELLIDOS,apellidos.getText().toString());
        values.put(Constantes.CAMPO_TRABAJO_ID,apellidos.getText().toString());
        values.put(Constantes.CAMPO_NUMERO_TELEFONO,apellidos.getText().toString());

        db.update(Constantes.TABLA_EMPLEADOS,values,Constantes.CAMPO_NOMBRES+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Actualizado",Toast.LENGTH_LONG).show();
    }

    public void Actualizar(View view) {
        actualizarUsuario();
    }
}
