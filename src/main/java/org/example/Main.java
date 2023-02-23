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
        conexion.conectar();
        menu();

    }

    private static void menu(){

        Scanner sc = new Scanner(System.in);
        String nombre, apellidos, asignatura;

        while(true){
            System.out.println("1. Listar alumnos");
            System.out.println("2. Listar asignaturas");
            System.out.println("3. Insertar alumno");
            System.out.println("4. Insertar asignatura");
            System.out.println("5. Asignar asignatura");
            System.out.println("6. Eliminar alumno");
            System.out.println("6. Eliminar adignatura");

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
                    conexion.setAsignatura(nombre,asignatura);break;
                case 6:
                    //conexion.eliminarAlumno();break;

                case 7:
                    //conexion.agregados();break;
                default:
            }
        }
    }

}

