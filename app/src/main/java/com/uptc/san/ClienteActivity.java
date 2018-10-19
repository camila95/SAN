package com.uptc.san;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Clase que permite Registrar a un empleado
 */
public class ClienteActivity extends AppCompatActivity {

    public EditText campoId, campoNombre, campoApellidos, campoTelefono;
    public Spinner campoCargo;
    ArrayList<String> listaTrabajos;
    ArrayList<Trabajos> trabajosLista;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        campoId = (EditText)findViewById(R.id.identificacion);
        campoNombre = (EditText)findViewById(R.id.campoNombre);
        campoApellidos = (EditText)findViewById(R.id.campoApellidos);
        campoTelefono = (EditText)findViewById(R.id.campoTelefono);
        campoCargo = (Spinner)findViewById(R.id.campoCargo);

        //Se llena la lista de trabajos en el spinner
        consultarTrabajos();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTrabajos);
        campoCargo.setAdapter(adaptador);
    }

    /**
     * Método que permite realizar la consulta de los trabajos ingresados, obtenerlos y add a la lista
     */
    public void consultarTrabajos(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"db_empleados",null,1);
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
        obtenerLista();
    }

    /**
     * Método que add el valor "Seleccione" al inicio de la lista de trabajos
     */
    public void obtenerLista(){
        listaTrabajos = new ArrayList<>();
        listaTrabajos.add("Seleccione");
        for (int i = 0; i < trabajosLista.size(); i++){
            listaTrabajos.add(trabajosLista.get(i).getNombreTrabajo());
        }
    }


    /**
     * Metodo que crea un empleado
     * @param view
     */
    public void crear(View view) {
        registrarEmpleados();
    }

    /**
     * Metodo que inserta un empleado en la base de datos
     */
    private void registrarEmpleados(){
        if(validarDatosConsultar()) {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "db_empleados", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constantes.CAMPO_IDENTIFICACION, campoId.getText().toString());
            values.put(Constantes.CAMPO_NOMBRES, campoNombre.getText().toString());
            values.put(Constantes.CAMPO_APELLIDOS, campoApellidos.getText().toString());
            values.put(Constantes.CAMPO_NUMERO_TELEFONO, campoTelefono.getText().toString());

            //Se obtiene el valor seleccionado por el spinner
            String cargo = campoCargo.getSelectedItem().toString();
            //Se realiza la consulta para obtener el id del trabajo
            Cursor cursor = db.rawQuery(Constantes.GET_ID_TRABAJO_BY_NOMBRE, new String[] {cargo});
            String idCargo = "";
            while (cursor.moveToNext()) {
                idCargo = (cursor.getString(0));
            }

            values.put(Constantes.CAMPO_TRABAJO_ID, idCargo);

            //Constantes.CAMPO_IDENTIFICACION = nombre del campo a devolver
            db.insert(Constantes.TABLA_EMPLEADOS, Constantes.CAMPO_IDENTIFICACION, values);

            Toast.makeText(getApplicationContext(), "Creado satisfactoriamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            db.close();
        }else{
            Toast.makeText(getApplicationContext(), "Faltan campos", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Limpiar campos despues de creado el empleado
     */
    public void limpiarCampos(){
        campoNombre.setText("");
        campoApellidos.setText("");
        campoId.setText("");
        campoTelefono.setText("");
        campoCargo.setSelection(0);
    }

    /**
     *Método que valida los campos
     * @return
     */
    public boolean validarDatosConsultar(){
        if(campoId.getText().equals("") && campoId.getText() == null){
            return false;
        }
        if(campoNombre.getText().equals("") && campoNombre.getText() == null){
            return false;
        }
        if(campoApellidos.getText().equals("") && campoApellidos.getText() == null){
            return false;
        }
        if(campoTelefono.getText().equals("") && campoTelefono.getText() == null){
            return false;
        }
        if(campoCargo.getSelectedItem().equals("Seleccione")){
            return false;
        }
        return true;
    }

    /**
     *Método que redirige a consultar un empleado
     * @param view
     */
    public void consultar(View view){
        Intent pantallaConsultar = new Intent(ClienteActivity.this, consultar.class);
        startActivity(pantallaConsultar);
    }
}
