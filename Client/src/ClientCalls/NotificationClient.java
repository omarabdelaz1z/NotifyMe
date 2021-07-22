package ClientCalls;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;


public class NotificationClient implements ClientCRUD{
    private final WebTarget notificationTarget;
    private final GenericType<List<HashMap<String, Object>>> type = new GenericType<>(){};

    public NotificationClient(WebTarget target) {
        notificationTarget = target.path("notifications");
    }

    @Override
    public String POST(String entityAsJson) {
        return notificationTarget.request().post(Entity.json(entityAsJson)).readEntity(String.class);
    }

    @Override
    public List<String> GET(Map<String, String> parameters) {
        Response response = Utils.prepareTarget(notificationTarget, parameters).request().get();
        return response.readEntity(type).stream().map(entry -> new Gson().toJson(entry)).collect(Collectors.toList());
    }

    @Override
    public List<String> PUT(Map<String, String> parameters, String json) {
        Response response = Utils.prepareTarget(notificationTarget, parameters).request().put(Entity.json(json));
        return response.readEntity(type).stream().map(entry -> new Gson().toJson(entry)).collect(Collectors.toList());
    }

    @Override
    public String DELETE(String Id) {
        return "";
    }
}
