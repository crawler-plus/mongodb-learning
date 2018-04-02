package site.it4u;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBJDBC {

    public static void main(String[] args) {
        MongoCollection<Document> collection = getCollection("test", "yb");
        System.out.println(collection.count()); // 打印文档数量
//        insertDocument(collection);
//        findDocument(collection);
//        updateDocument(collection);
//        deleteDocument(collection);
    }

    // 获取mongoClient
    private static MongoClient getMongoClient() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        return mongoClient;
    }

    // 获取mongoDatabase
    private static MongoDatabase getMongoDatabase(String databaseName) {
        return getMongoClient().getDatabase(databaseName);
    }

    // 获取collection
    private static MongoCollection<Document> getCollection(String databaseName, String collectionName) {
        MongoCollection<Document> collection = getMongoDatabase(databaseName).getCollection(collectionName);
        return collection;
    }

    // 插入文档
    private static void insertDocument(MongoCollection collection) {
        Document document = new Document("title", "MongoDB").
                append("description", "database").
                append("likes", 10000).
                append("by", "yb");
        List<Document> documents = new ArrayList<>();
        // 插入文档
        documents.add(document);
        collection.insertMany(documents);
    }

    // 检索文档
    private static void findDocument(MongoCollection<Document> collection) {
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> iterator = findIterable.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            System.out.println(next);
        }
    }

    // 更新文档
    private static void updateDocument(MongoCollection<Document> collection) {
        //  将文档中likes=100的文档修改为likes=200
        collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> iterator = findIterable.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            System.out.println(next);
        }
    }

    // 删除文档
    private static void deleteDocument(MongoCollection<Document> collection) {
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("likes", 200));
        //删除所有符合条件的文档
        collection.deleteMany (Filters.eq("likes", 200));
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

}
