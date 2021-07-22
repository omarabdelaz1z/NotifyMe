package Endpoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import Database.CRUD.ICRUD;
import Database.Connectivity.IDatabase;
import Database.Connectivity.MongoDB;
import Entities.CRUD.NotificationCRUD;
import Entities.CRUD.TemplateCRUD;
import Entities.Notification;
import Entities.Template;
import Utilities.Utils;
import Validation.Global.BaseValidator;
import Validation.Global.EntityValidator;



@Path("/notifications")
@Singleton
public class NotificationResourceService extends Utils {
    static final IDatabase database = new MongoDB(
            "mongodb+srv://notifyme:Sc1yTvMC8mR3d4tX@notifyme.a9swz.mongodb.net/NotificationModule?retryWrites=true&w=majority");

    static {
        database.openConnection();
    }

    ICRUD<Notification> notificationCRUD = new NotificationCRUD(database);
    ICRUD<Template> templateCRUD = new TemplateCRUD(database);
    EntityValidator<Notification> validator = new BaseValidator<>();


    @GET
    public Response GET(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

        List<Notification> notificationList = notificationCRUD.read(prepareQueryParameters(queryParameters));

        return Response.status(Response.Status.CREATED)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity(notificationList)
                    .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response POST(Notification notification) {
        String validationError = validator.containsViolations(validator.validate(notification));

        if(validationError != null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity(validationError)
                    .build();
        }

        ObjectId templateId = new ObjectId(notification.getTemplateId());

        if(!templateCRUD.exists(templateId)){
            String errorMessage = "ERROR 404: Template with Id: " + notification.getTemplateId() + " not found." +
                    " Notification cannot be created.";

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessage)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", templateId.toString());

        Template template = templateCRUD.read(parameters).get(0);

        ArrayList<String> templateParameters = template.fetchParameters();
        boolean matched = templateParameters.containsAll(notification.getParameters().keySet());

        if(!matched){
            String errorMessage = "Notification target parameters and template - with Id: "
                     + notification.getTemplateId() + " don't match";

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessage)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }

        Notification response = notificationCRUD.create(notification);

        return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PUT(@Context UriInfo uriInfo, String json) {
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

        Map<String, String> filterQuery = prepareQueryParameters(queryParameters);
        Map<String, String> updateQuery = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());

        List<Notification> dequeuedNotifications = notificationCRUD.update(filterQuery, updateQuery);

        return Response.status(Response.Status.CREATED)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(dequeuedNotifications)
                .build();
    }
}