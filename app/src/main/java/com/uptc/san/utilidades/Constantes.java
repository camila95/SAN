package com.uptc.san.utilidades;

public class Constantes {
   // crear tabla empleados
    public static final String CREAR_TABLA_EMPLEADOS =
            "CREATE TABLE "+ Constantes.TABLA_EMPLEADOS + " ( "
                    +Constantes.CAMPO_IDENTIFICACION +" INTEGER NOT NULL, "
                    +Constantes.CAMPO_NOMBRES + "  TEXT, "
                    +Constantes.CAMPO_APELLIDOS + "  TEXT, "
                    +Constantes.CAMPO_NUMERO_TELEFONO +"  TEXT )";
    //constantes tabla_
    public static final String TABLA_EMPLEADOS = "EMPLEADOS";
    public static final String CAMPO_IDENTIFICACION = "IDENTIFICACION";
    public static final String CAMPO_NOMBRES = "NOMBRES";
    public static final String CAMPO_APELLIDOS = "APELLIDOS";
    public static final String CAMPO_NUMERO_TELEFONO = "NUMERO_TELEFONO";


}
