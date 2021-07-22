package ClientCalls;

import java.util.Map;
import java.util.List;

public interface ClientCRUD {
    String POST(String entity);
    List<String> GET(Map<String, String> parameters);
    List<String> PUT(Map<String, String> parameters, String json);
    String DELETE(String Id);
}
