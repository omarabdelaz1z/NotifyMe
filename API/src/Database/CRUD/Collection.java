package Database.CRUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.types.ObjectId;
import Database.Connectivity.IDatabase;

import static com.mongodb.client.model.Filters.eq;

public class Collection <T> implements ICollection<T> {
    private final MongoCollection<T> mongoCollection;

    public Collection(IDatabase client , String collectionName, Class <T> type) {
        MongoClient mongoClient = (MongoClient) client.getClientConnection();
        this.mongoCollection = mongoClient.getDatabase("NotificationModule").getCollection(collectionName, type);
    }

    @Override
    public T create(T object) {
        ObjectId objectId = mongoCollection.insertOne(object).getInsertedId().asObjectId().getValue();
        HashMap<String,  String> parameters = new HashMap<>();
        parameters.put("id", objectId.toString());
        try {
            return read(parameters).get(0);
        }catch(Exception err){
            return null;
        }
    }

    @Override
    public List<T> read(Map<String, String> parameters) {
        BasicDBObject query = new BasicDBObject(parameters);

        if(query.containsKey("id")) {
            String id = parameters.get("id");
            query.remove("id");
            query.put("_id",  new ObjectId(id));
        }

        if(query.isEmpty())
            return mongoCollection.find().into(new ArrayList<>());

        return mongoCollection.find(query).into(new ArrayList<>());
    }

    @Override
    public boolean delete(ObjectId objectId) {
        return mongoCollection.deleteOne(eq("_id", objectId)).getDeletedCount() > 0;
    }

    @Override
    public List<T> update(Map<String, String> filterQuery, Map<String, String> updateQuery) {
        BasicDBObject filter = new BasicDBObject(filterQuery);
        BasicDBObject update = new BasicDBObject(updateQuery);
        BasicDBObject updateOperation = new BasicDBObject("$set", update);

        if(filter.containsKey("id")) {
            String id = filterQuery.get("id");
            filter.remove("id");
            filter.put("_id",  new ObjectId(id));
        }

        mongoCollection.updateMany(filter, updateOperation);

        FindIterable<T> iterable = mongoCollection.find(filter);
        MongoCursor<T> cursor = iterable.iterator();
        List<T> documents = new ArrayList<>();

        while(cursor.hasNext())
            documents.add(cursor.next());

        return documents;
    }

    @Override
    public boolean exists(ObjectId objectId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", objectId);
        return mongoCollection.countDocuments(query) == 1;
    }
}