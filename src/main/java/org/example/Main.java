package org.example;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Scanner;


public class Main {

    private static Conexion conexion = new Conexion();
    public static void main(String[] args) {
        //Conectamos con la base de datos.
        conexion.conectar();
        menu();

    }

    /***
     * Menú con una entrada para elegir las diferentes operaciones que se pueden realizar
     */
    private static void menu(){

        Scanner sc = new Scanner(System.in);
        String nombre,nuevoNombre, apellidos, asignatura;

        while(true){
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("1. Listar alumnos");
            System.out.println("2. Listar asignaturas");
            System.out.println("3. Insertar alumno");
            System.out.println("4. Insertar asignatura");
            System.out.println("5. Asignar asignatura");
            System.out.println("6. Eliminar alumno");
            System.out.println("7. Modificar alumno");
            System.out.println("8. Salir de la aplicación");
            System.out.println("--------------------------------------------------------------------------");

            int op=sc.nextInt();
            sc.nextLine();
            switch(op){
                case 1:
                    conexion.listaAlumnos();
                    break;
                case 2:
                    conexion.listaAsignaturas();
                    break;
                case 3:
                    System.out.println("Nombre del alumno a insertar:");
                    nombre = sc.nextLine();
                    System.out.println("Apellidos del alumno a insertar:");
                    apellidos = sc.nextLine();
                    conexion.insertarAlumno(nombre, apellidos);
                    break;
                case 4:
                    System.out.println("Nombre de la asignatura a insertar:");
                    asignatura = sc.nextLine();
                    conexion.insertarAsignatura(asignatura);
                    break;
                case 5:
                    System.out.println("Escriba el nombre del alumno:");
                    nombre = sc.nextLine();
                    System.out.println("Escriba la asignatura a asignar:");
                    asignatura = sc.nextLine();
                    conexion.asignarAsignatura(nombre,asignatura);
                    break;
                case 6:
                    System.out.println("Introduce el nombre del alumno a eliminar:");
                    nombre = sc.nextLine();
                    conexion.eliminarAlumno(nombre);
                    break;
                case 7:
                    System.out.println("Introduce el nombre del alumno que desea modificar");
                    nombre = sc.nextLine();
                    System.out.println("Escribe un nuevo nombre para el alumno:");
                    nuevoNombre = sc.nextLine();
                    conexion.modificarAlumno(nombre, nuevoNombre);
                    break;
                case 8:
                    System.exit(0);
                default:
            }
        }
    }

}

