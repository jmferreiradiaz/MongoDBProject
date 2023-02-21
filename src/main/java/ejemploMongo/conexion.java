package ejemploMongo;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.*;

import static com.mongodb.client.model.Indexes.descending;

public class conexion {
    MongoDatabase database;
    public void conectar(String mongoURL){
            ConnectionString connectionString = new ConnectionString(mongoURL);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("Prueba");
    }
    public void insertar(){
        database.getCollection("Alumnos").insertOne(new Document()
                .append("nombre","Pepe")
                .append("apellidos","Pérez")
                .append("fecha",new Date(2022,10,10))
        );
    }
    public void setAsignatura(String nombre,String asignatura){
        MongoCollection<Document> lista = database.getCollection("Alumnos");
        MongoCollection<Document> asig = database.getCollection("Asignaturas");
        Document doc=new Document("nombre",nombre); // Criterio de búsqueda
        Document a = new Document("nombre",asignatura); // Criterio de búsqueda
        Document asigno = new Document("asignatura", asig.find(a).first().getObjectId("_id"));
        Document update = new Document("$set",asigno );
        lista.findOneAndUpdate(doc, update);
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
    public void modificar(String antes, String despues){
        Document query = new Document().append("nombre",  antes);
        Bson updates = Updates.combine(
                Updates.set("nombre", despues),
                Updates.currentTimestamp("lastUpdated"));
        UpdateOptions options = new UpdateOptions().upsert(true);
        try {
            UpdateResult result = database.getCollection("Alumnos").updateMany(query, updates, options);
            System.out.println("Modified document count: " + result.getModifiedCount());
            System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is performed
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }
    }

    public void eliminarAlumno() {
        System.out.println("Introduce el dni del alumno:");
        Scanner sc=new Scanner(System.in);
        String dni=sc.nextLine();

        MongoCollection<Document> alumnos = database.getCollection("Alumnos");
        Document doc=new Document("dni",dni); // Criterio de búsqueda
        try{
            DeleteResult res=alumnos.deleteMany(doc);
            if(res.getDeletedCount()>0)
                System.out.println("Alumno " + dni + " ha sido eliminado correctamente: " + res.getDeletedCount());
            else
                System.out.println();

        }catch (Exception e){
            System.out.println("No he podido eliminar al alumno "+dni);
        }
    }


    public void agregados() {
        // Maximo
        MongoCollection<Document> alumnos = database.getCollection("Alumnos");
        String ultimoId = alumnos
                .find()
                .sort(descending("dni"))
                .first()
                .getString("dni");
        System.out.println(ultimoId);
    }
}
