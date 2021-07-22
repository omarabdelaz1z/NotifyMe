package Database.CRUD;

import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;

public interface ICollection<T> {
    T create(T object);
    List<T> read(Map<String, String> parameters);
    boolean delete(ObjectId objectId);
    List<T> update(Map<String, String> filterQuery, Map<String, String> updateQuery);
    boolean exists(ObjectId objectId);
}