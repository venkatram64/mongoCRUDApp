package com.venkat.mongo.emboddedMongo.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.venkat.mongo.emboddedMongo.model.Employee;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private MongoClient client;
    public MongoClient getClient(){
        if(client == null){
            client = new MongoClient("localhost", 27017);
        }
        return client;
    }
    public List<Employee> getAll() {
        MongoClient mc = getClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("employee");
        List<Employee> employees = new ArrayList<>();
        for(Document e: collection.find()){
            Employee emp = new Employee(e.get("_id").toString(), e.getString("firstName"),
                    e.getString("lastName"), e.getString("address"));
            employees.add(emp);
        }
        return employees;
    }

    public String save(Employee emp) {
        MongoClient mc = getClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("employee");
        Document emp1 = new Document();
        emp1.append("firstName", emp.getFirstName());
        emp1.append("lastName", emp.getLastName());
        emp1.append("address", emp.getAddress());
        String response;
        try{
            collection.insertOne(emp1);
            response = "Successfully Added";
        }catch (Exception e){
            response = "Something went wrong";
        }
        return response;
    }

    public String update(Employee emp) {
        MongoClient mc = getClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("employee");
        Document emp1 = new Document();
        emp1.append("firstName", emp.getFirstName());
        emp1.append("lastName", emp.getLastName());
        emp1.append("address", emp.getAddress());
        String response;
        try{
            BasicDBObject filter = new BasicDBObject("_id", new ObjectId(emp.getId()));
            collection.updateOne(filter, new BasicDBObject("$set",emp1));
            response = "Successfully Updated";
        }catch (Exception e){
            response = "Something went wrong";
        }
        return response;
    }

    public String delete(String id) {
        MongoClient mc = getClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("employee");

        String response;
        try{
            BasicDBObject filter = new BasicDBObject("_id", new ObjectId(id));
            collection.deleteOne(filter);
            response = "Successfully Deleted";
        }catch (Exception e){
            response = "Something went wrong";
        }
        return response;
    }
}
