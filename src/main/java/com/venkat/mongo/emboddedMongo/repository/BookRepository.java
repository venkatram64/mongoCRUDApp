package com.venkat.mongo.emboddedMongo.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {

    private static Logger logger = LogManager.getLogger(BookRepository.class.getName());

    private MongoClient mongoClient;

    private MongoClient getMongoClient(){
        if(mongoClient == null){
            mongoClient = new MongoClient("localhost", 27017);
        }
        return mongoClient;
    }
    public List<Object> getAll() {
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("books");

        FindIterable<Document> findIterable = collection.find();
        List<Object> booksResponse = new ArrayList<>();

        for(Document document: findIterable){
            booksResponse.add(document);
        }
        return booksResponse;
    }

    public String create(Map<String, Object> book) {
        Document doc = new Document(book);
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("books");

        try{
            collection.insertOne(doc);
            return "Successfully Create Book";
        }catch(Exception e){
            return "Failed to Create Book";
        }
    }

    public String update(Map<String, Object> book) {

        Document doc = new Document(book);
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("books");
        String id = doc.getString("id");
        doc.remove("id");
        BasicDBObject filter = new BasicDBObject("_id", id);
        BasicDBObject update = new BasicDBObject("$set", doc);
        try{
            collection.updateOne(filter, update);
            return "Successfully Updated Book";
        }catch(Exception e){
            return "Failed to Update Book";
        }
    }

    public String delete(String id) {
        MongoClient mc = getMongoClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("books");

        String response;
        try{
            BasicDBObject filter = new BasicDBObject("_id", new ObjectId(id));
            collection.deleteOne(filter);
            response = "Successfully Deleted Book";
        }catch (Exception e){
            response = "Something went wrong";
        }
        return response;
    }

    public List<Object> getAllByPage(int pageNo, int pageSize, String[] fields, String sortBy) {
        MongoClient mc = getMongoClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("books");
        //BasicDBObject projection = new BasicDBObject("_id", 0); //id is not selected
        BasicDBObject projection = new BasicDBObject();
        for(String field : fields){
            projection.append(field,1);
        }
        BasicDBObject sort = new BasicDBObject(sortBy, 1); //ascending

        FindIterable<Document> findIterable = collection.find()
                                            .projection(projection)
                                            .sort(sort)
                                            .skip(pageNo * pageSize)
                                            .limit(pageSize);

        List<Object> booksResponse = new ArrayList<>();

        for(Document document: findIterable){
            booksResponse.add(document);
        }
        return booksResponse;

    }

    public long countDocuments() {
        MongoClient mc = getMongoClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("books");
        return collection.countDocuments();
    }

    public String countPages() {
        MongoClient mc = getMongoClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("books");
        /**
         * {$group:{
         *   _id: null,
         *   count: {
         *     $sum: "$pageCount"
         *   }
         * }}
         */
        BasicDBObject sum = new BasicDBObject("$sum", "$pageCount");
        BasicDBObject grp = new BasicDBObject();
        grp.append("_id", null);
        grp.append("count", sum);
        BasicDBObject group = new BasicDBObject("$group", grp);
        logger.info("Group query {}",group.toJson());
        List<BasicDBObject> pipeline = new ArrayList<>();
        pipeline.add(group);
        AggregateIterable<Document> aggregateIterable = collection.aggregate(pipeline);
        return aggregateIterable.first().get("count").toString();
    }

    public List<Object> getByCategories(String[] categories) {
        MongoClient mc = getMongoClient();
        MongoDatabase database = mc.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("books");
        //{"categories":{$elemMatch: {$in:["Mobile","Java"]}}}
        BasicDBObject in = new BasicDBObject("$in", categories);
        BasicDBObject eleMatch = new BasicDBObject("$elemMatch", in);
        BasicDBObject catFilter = new BasicDBObject("categories", eleMatch);
        BasicDBObject projection = new BasicDBObject();
        projection.append("title", 1);
        projection.append("isbn", 1);
        FindIterable<Document> findIterable = collection.find(catFilter).projection(projection);
        List<Object> books = new ArrayList<>();
        for(Document doc: findIterable){
            books.add(doc);
        }
        return books;
    }
}
