package com.uptc.san;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"bd_empleados",null,1);

    }

    public void ingresar(View view) {
        login();
    }

    public void login(){
        Intent pantallaRegistrarEmpleados = new Intent(MainActivity.this, ClienteActivity.class);
        startActivity(pantallaRegistrarEmpleados);
    }
}

