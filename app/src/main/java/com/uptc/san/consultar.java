package com.uptc.san;

import android.content.ContentValues;
import android.content.Intent;
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
        comboEmpleados.setAdapter(adaptador);

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

                    //Se obtienen los valores (Cargo y Salario) a mostrarse en la pantalla
                    SQLiteDatabase db = conn.getWritableDatabase();
                    Cursor cursor = db.rawQuery(Constantes.GET_DATOS_TRABAJO_BY_ID, new String[] {idCargoSeleccionado});

                    String nombreTrabajo="";
                    Integer salarioTrabajo = 0;
                    while (cursor.moveToNext()) {
                        nombreTrabajo = (cursor.getString(0));
                        salarioTrabajo = (cursor.getInt(1));
                    }
                    cursor.close();

                    //Se llama al metodo que obtiene la posicion en la lista del nombre del trabajo
                    int posicion = getPositionListaCargo(nombreTrabajo);
                    cargo.setSelection(posicion);
                    salario.setText(salarioTrabajo.toString());

                }else{
                    limpiarCampos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Método que limpia campos
     */
    public void limpiarCampos(){
        identificacion.setText("");
        nombres.setText("");
        apellidos.setText("");
        telefono.setText("");
        cargo.setSelection(0);
        salario.setText("");
    }

    /**
     * Método que consulta todos los empleados creados, para mostrarlos en el spinner
     */
    public void consultarListaPersonas(){
        SQLiteDatabase db = conn.getWritableDatabase();
        Empleados empleados = null;
        empleadosLista = new ArrayList<>();

        Cursor cursor = db.rawQuery(Constantes.GET_ALL_EMPLEADOS, null);

        while(cursor.moveToNext()){
            empleados = new Empleados();
            empleados.setIdentificacion(cursor.getInt(0));
            empleados.setNombres(cursor.getString(1));
            empleados.setApellidos(cursor.getString(2));
            empleados.setNumeroTelefono(cursor.getString(3));
            empleados.setIdTrabajo(cursor.getInt(4));
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
            listaEmpleados.add(empleadosLista.get(i).getNombres() + " " +empleadosLista.get(i).getApellidos());
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
            trabajos.setSalario(cursor.getInt(2));
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

    /**
     *
     * @param view
     */
    public void Actualizar(View view) {
        actualizarUsuario();
    }

    /**
     *
     * @return
     */
    public boolean validarDatosActualizar(){
        if(identificacion.getText().equals("") && identificacion.getText() == null){
            return false;
        }
        if(nombres.getText().equals("") && nombres.getText() == null){
            return false;
        }
        if(apellidos.getText().equals("") && apellidos.getText() == null){
            return false;
        }
        if(telefono.getText().equals("") && telefono.getText() == null){
            return false;
        }
        if(salario.getText().equals("") && salario.getText() == null){
            return false;
        }
        if(cargo.getSelectedItem().equals("Seleccione")){
            return false;
        }
        return true;
    }


    /**
     * Metodo actualizar
     */
    private void actualizarUsuario(){

        if(validarDatosActualizar()){

            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constantes.CAMPO_IDENTIFICACION, identificacion.getText().toString());
            values.put(Constantes.CAMPO_NOMBRES, nombres.getText().toString());
            values.put(Constantes.CAMPO_APELLIDOS, apellidos.getText().toString());
            values.put(Constantes.CAMPO_NUMERO_TELEFONO, telefono.getText().toString());

            //Se obtiene el valor seleccionado por el spinner
            String cargoActualizado = cargo.getSelectedItem().toString();
            //Se realiza la consulta para obtener el id del trabajo
            Cursor cursor = db.rawQuery(Constantes.GET_ID_TRABAJO_BY_NOMBRE, new String[] {cargoActualizado});
            String idCargo = "";
            while (cursor.moveToNext()) {
                idCargo = (cursor.getString(0));
            }
            values.put(Constantes.CAMPO_TRABAJO_ID, idCargo);
            String[] parametros = {identificacion.getText().toString()};
            db.update(Constantes.TABLA_EMPLEADOS, values, Constantes.CAMPO_IDENTIFICACION + " =? " , parametros);
            db.close();
            Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            redireccionar();

        }else{
            Toast.makeText(getApplicationContext(), "Faltan campos", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Redirecciona al ingresar empleados
     */
    public void redireccionar(){
        Intent pantallaRegistrarEmpleados = new Intent(consultar.this, ClienteActivity.class);
        startActivity(pantallaRegistrarEmpleados);
    }

    /**
     *
     * @param view
     */
    public void eliminar(View view){
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {identificacion.getText().toString()};
        db.delete(Constantes.TABLA_EMPLEADOS, Constantes.CAMPO_IDENTIFICACION + " =? ", parametros);
        db.close();
        Toast.makeText(getApplicationContext(),"Eliminado correctamente",Toast.LENGTH_LONG).show();
        limpiarCampos();
        redireccionar();
    }

}
