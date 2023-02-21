package org.example;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class Conexion {
    static MongoDatabase database;
    public static void main(String[] args) {

    }


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

    public static void modificar(String antes, String despues){
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

    public void deleteAll(){

    }
}
