package ClientCalls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

public class TemplateClient implements ClientCRUD {
    private final WebTarget templateTarget;
    private final WebTarget singleTemplate;
    private final GenericType<List<HashMap<String, Object>>> type = new GenericType<>(){};

    public TemplateClient(WebTarget target) {
        templateTarget = target.path("templates");
        singleTemplate = templateTarget.path("{templateId}");
    }

    @Override
    public String POST(String entity) {
        return templateTarget.request().post(Entity.json(entity)).readEntity(String.class);
    }

    @Override
    public List<String> GET(Map<String, String> parameters) {
        Response response = Utils.prepareTarget(templateTarget, parameters).request().get();

        return response.readEntity(type)
                .stream()
                .map(entry -> new Gson().toJson(entry))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> PUT(Map<String, String> parameters, String json) {
        Response response = Utils.prepareTarget(templateTarget, parameters).request().put(Entity.json(json));
        return response.readEntity(type).stream().map(entry -> new Gson().toJson(entry)).collect(Collectors.toList());
    }

    @Override
    public String DELETE(String Id) {
        Response response = singleTemplate.resolveTemplate("templateId", Id).request().delete();
        return response.readEntity(String.class);
    }
}
