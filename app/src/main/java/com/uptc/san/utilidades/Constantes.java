package com.uptc.san.utilidades;

public class Constantes {

   // crear tabla empleados
    public static final String CREAR_TABLA_EMPLEADOS =
            "CREATE TABLE "+ Constantes.TABLA_EMPLEADOS + " ( "
                    +Constantes.CAMPO_IDENTIFICACION +" INTEGER PRIMARY KEY, "
                    +Constantes.CAMPO_NOMBRES + "  TEXT NOT NULL, "
                    +Constantes.CAMPO_APELLIDOS + "  TEXT NOT NULL, "
                    +Constantes.CAMPO_NUMERO_TELEFONO +"  TEXT,"
                    +Constantes.CAMPO_TRABAJO_ID +"  INTEGER NOT NULL ) ";

    //constantes tabla_
    public static final String TABLA_EMPLEADOS = "EMPLEADOS";
    public static final String CAMPO_IDENTIFICACION = "IDENTIFICACION";
    public static final String CAMPO_NOMBRES = "NOMBRES";
    public static final String CAMPO_APELLIDOS = "APELLIDOS";
    public static final String CAMPO_NUMERO_TELEFONO = "NUMERO_TELEFONO";
    public static final String CAMPO_TRABAJO_ID = "ID_TRABAJO";

    //Consulta de todos los empleados
    public static final String GET_ALL_EMPLEADOS = "SELECT * FROM "+Constantes.TABLA_EMPLEADOS + " ORDER BY " + Constantes.CAMPO_NOMBRES;

    //Tabla Trabajos
    public static final String CREAR_TABLA_TRABAJOS =
            "CREATE TABLE " + Constantes.TABLA_TRABAJOS +" ( "
                    +Constantes.CAMPO_ID_TRABAJO +" INTEGER PRIMARY KEY, "
                    +Constantes.CAMPO_NOMBRE_TRABAJO + " TEXT NOT NULL, "
                    +Constantes.CAMPO_SALARIO + " INTEGER NOT NULL ) ";

    //constantes tabla TRABAJOS
    public static final String TABLA_TRABAJOS = "TRABAJOS";
    public static final String CAMPO_ID_TRABAJO = "ID_TRABAJO";
    public static final String CAMPO_NOMBRE_TRABAJO = "NOMBRE_TRABAJO";
    public static final String CAMPO_SALARIO = "SALARIO";

    public static final String INSERT_1 = "INSERT INTO TRABAJOS (ID_TRABAJO, NOMBRE_TRABAJO, SALARIO) VALUES (100,'Programador Advanced', 1500000)";
    public static final String INSERT_2 = "INSERT INTO TRABAJOS (ID_TRABAJO, NOMBRE_TRABAJO, SALARIO) VALUES (200,'Ing. Desarrollo Junior', 1800000)";
    public static final String INSERT_3 = "INSERT INTO TRABAJOS (ID_TRABAJO, NOMBRE_TRABAJO, SALARIO) VALUES (300,'Ing. Desarrollo Senior', 2100000)";
    public static final String INSERT_4 = "INSERT INTO TRABAJOS (ID_TRABAJO, NOMBRE_TRABAJO, SALARIO) VALUES (400,'Arquitecto', 2500000)";
    public static final String INSERT_5 = "INSERT INTO TRABAJOS (ID_TRABAJO, NOMBRE_TRABAJO, SALARIO) VALUES (500,'Gerente', 3000000)";

    //consultas
    public static final String GET_ID_TRABAJO_BY_NOMBRE = "SELECT " + Constantes.CAMPO_ID_TRABAJO + " FROM " + Constantes.TABLA_TRABAJOS +" WHERE " + Constantes.CAMPO_NOMBRE_TRABAJO + " = ? ";
    public static final String GET_DATOS_TRABAJO_BY_ID = "SELECT " + Constantes.CAMPO_NOMBRE_TRABAJO +", "+ Constantes.CAMPO_SALARIO +" FROM " + Constantes.TABLA_TRABAJOS + " WHERE " + Constantes.CAMPO_ID_TRABAJO + " = ? ";

}
