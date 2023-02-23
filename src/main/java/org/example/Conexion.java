package org.example;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class Conexion {
    static MongoDatabase database;

    public static void conectar(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://jmferreira:jmferdia623d@cluster0.bpjzscd.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("Prueba");
    }

    public static void insertarAlumno(String nombre, String apellidos){
        database.getCollection("Alumnos").insertOne(new Document()
                .append("nombre", nombre)
                .append("apellidos", apellidos));
    }

    public static void insertarAsignatura(String asignatura){
        database.getCollection("Asignaturas").insertOne(new Document()
                .append("nombre", asignatura));

    }

    public static void modificarAlumno(String antes, String despues){
        Document query = new Document().append("nombre", antes);
        Bson updates = Updates.combine(
                Updates.set("nombre", despues),
                Updates.currentTimestamp("lastUpdated"));
        UpdateOptions options = new UpdateOptions().upsert(true);
        try{
            UpdateResult result = database.getCollection("Alumnos").updateMany(query, updates, options);
            System.out.println("Modified document count: "+result.getModifiedCount());
            System.out.println("Upserted id: "+result.getUpsertedId()); //only contains a value when an upsert

        } catch (MongoException me){
         System.err.println("Unable to update due to an error: " + me);
        }
    }

    public void listaAlumnos(){
        MongoCollection<Document> lista = database.getCollection("Alumnos");
        MongoCursor<Document> res=lista.find().iterator();
        MongoCollection<Document> asig = database.getCollection("Asignaturas");
        Document a,next;
        String nombreAsignatura;
        while(res.hasNext()){
            next = res.next();
            System.out.print(next.getString("nombre")+" ");
            ObjectId buscaAsig = next.getObjectId("asignatura");
            a = new Document("_id",buscaAsig); // Criterio de búsqueda
            Document matriculada = asig.find(a).first();
            if(matriculada!=null) {
                nombreAsignatura = matriculada.getString("nombre");
                System.out.print(nombreAsignatura);
            }else{
                System.out.print("No tiene asignatura matriculada");
            }
            System.out.println("");
        }
    }

    public void listaAsignaturas(){
        MongoCollection<Document> asignaturas = database.getCollection("Asignaturas");
        MongoCursor<Document> res = asignaturas.find().iterator();
        Document asig;
        while(res.hasNext()){
            asig = res.next();
            System.out.print(asig.getString("nombre")+" ");
            System.out.println("");
        }
    }

    public void borrar(){

    }
}
