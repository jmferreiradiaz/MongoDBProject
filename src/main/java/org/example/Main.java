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

    }

    private static void menu(){
        System.out.println("1. lista de alumnos");
        System.out.println("2. insertar alumno");
        System.out.println("3. insertar asignatura");
        System.out.println("4. asignar asignatura");
        System.out.println("5. eliminar alumno");
        Scanner sc = new Scanner(System.in);
        int op=sc.nextInt();
        switch(op){
            case 1:
                //conexion.listaAlumnos();break;
            case 2:
                System.out.println("Nombre del alumno a insertar");
                String nombre = sc.nextLine();
                System.out.println("Apellidos del alumno a insertar");
                String apellidos = sc.nextLine();
                conexion.insertarAlumno(nombre, apellidos);
                break;
            case 3:
                System.out.println("Nombre de la asignatura a insertar");
                String asignatura = sc.nextLine();
                conexion.insertarAsignatura(asignatura);break;
            case 4:
                //conexion.setAsignatura("pepe","Lengua");break;
            case 5:
                //conexion.eliminarAlumno();break;

            case 6:
                //conexion.agregados();break;
            default:
        }
    }

}

