package com.venkat.mongo.emboddedMongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmboddedMongoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EmboddedMongoApplication.class, args);
	}


	public MongoClient getClient(){
		return new MongoClient("localhost", 27017);
	}

	@Override
	public void run(String... args) throws Exception {
		/*MongoClient mongoClient = getClient();
		MongoDatabase database = mongoClient.getDatabase("test");
		MongoCollection<Document> empCollection = database.getCollection("employee");

		Document emp1 = new Document();
		emp1.append("firstName", "Venkatram");
		emp1.append("lastName", "Veerareddy");
		emp1.append("address", "Narayana gude, hyd");
		empCollection.insertOne(emp1);*/
	}
}
