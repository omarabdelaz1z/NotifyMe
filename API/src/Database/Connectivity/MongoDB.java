package Database.Connectivity;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB implements IDatabase {
    private MongoClient mongoClient;
    private final MongoClientSettings clientSettings;

    public MongoDB(String mongoURI){
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        ConnectionString connectionString = new ConnectionString(mongoURI);
        clientSettings = prepareConnectionSettings(connectionString);
    }

    @Override
    public void openConnection() {
        mongoClient = MongoClients.create(clientSettings);

    }

    @Override
    public Object getClientConnection() {
        return mongoClient;
    }

    private MongoClientSettings prepareConnectionSettings(ConnectionString connectionString){
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(setMappingCodecs())
                .build();
    }

    private CodecRegistry setMappingCodecs(){
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        return fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
    }
}