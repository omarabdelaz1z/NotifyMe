package JSONMapper;

public interface Adapter <T> {
    T toEntity(String JSON);
    String toJSON(T entity);
}
