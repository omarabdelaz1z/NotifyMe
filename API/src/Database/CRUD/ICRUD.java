package Database.CRUD;

import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;

public interface ICRUD <T> {
    T create(T object);
    List<T> read(Map<String, String> parameters);
    List<T> update(Map<String, String> filterQuery, Map<String, String> updateQuery);
    boolean delete(ObjectId objectId);
    boolean exists(ObjectId objectId);
}