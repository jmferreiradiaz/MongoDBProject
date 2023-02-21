package ejemploMongo;

import java.util.Scanner;

public class Main {
    private static conexion con;
    public static void main(String[] args) {

        con=new conexion();
        con.conectar("mongodb+srv://jmferreira:jmferdia623d@cluster0.bpjzscd.mongodb.net/?retryWrites=true&w=majority");
        //con.insertar();
        //con.modificar("Antonio","Manolo");
       // con.setAsignatura("Raul","Lengua");
        //con.listaAlumnos();
        menu();
    }
private static void menu(){
        System.out.println("1. lista de alumnos");
        System.out.println("2. matricular alumno");
        System.out.println("3. asignar asignatura");
        System.out.println("4. eliminar alumno");
    Scanner sc = new Scanner(System.in);
    int op=sc.nextInt();
    switch(op){
        case 1:
            con.listaAlumnos();break;
        case 2:
            con.insertar();break;
        case 3:
            con.setAsignatura("pepe","Lengua");break;
        case 4:
            con.eliminarAlumno();break;
        case 5:
            con.agregados();break;
        default:
    }
}
}