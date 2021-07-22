package Endpoints;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import Database.CRUD.ICRUD;
import Database.Connectivity.IDatabase;
import Database.Connectivity.MongoDB;
import Entities.CRUD.TemplateCRUD;
import Entities.Template;
import Utilities.Utils;
import Validation.Global.BaseValidator;
import Validation.Global.EntityValidator;

@Path("/templates")
@Singleton
public class TemplateResourceService extends Utils {
    static final IDatabase database = new MongoDB(
            "mongodb+srv://notifyme:Sc1yTvMC8mR3d4tX@notifyme.a9swz.mongodb.net/NotificationModule?retryWrites=true&w=majority");

    static {
        database.openConnection();
    }

    ICRUD<Template> templateCRUD = new TemplateCRUD(database);
    EntityValidator<Template> validator = new BaseValidator<>();

    @GET
    public Response GET(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        List<Template> templateList = templateCRUD.read(prepareQueryParameters(queryParameters));

        return Response.status(Response.Status.CREATED)
                .type(MediaType.APPLICATION_JSON)
                .entity(templateList)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response POST(Template template) {
        String validationError = validator.containsViolations(validator.validate(template));

        if(validationError != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity(validationError)
                .build();
        }

        Template response = templateCRUD.create(template);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PUT(@Context UriInfo uriInfo, Template template) {
        String templateID = uriInfo.getQueryParameters().get("id").get(0);
        String validationError = validator.containsViolations(validator.validate(template));

        if(validationError != null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(validationError)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }

        ObjectId objectId = new ObjectId(templateID);

        if(!templateCRUD.exists(objectId)) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Template with Id: " + templateID + " not Found.")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }

        template.setId(objectId);
        Map<String, String> filter = prepareQueryParameters(uriInfo.getQueryParameters());
        Map<String, String> update = new ObjectMapper().convertValue(template, Map.class);
        List<Template> templates = templateCRUD.update(filter, update);

        return Response.status(Response.Status.ACCEPTED)
                .entity(templates)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Path("/{templateId}")
    public Response DELETE(@PathParam("templateId") String templateID) {
        if(!templateCRUD.delete((new ObjectId(templateID)))) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Template with Id: " + templateID + " not found.")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }
        return Response
                .ok("Template with Id: " + templateID + " is deleted.", MediaType.TEXT_PLAIN_TYPE)
                .build();
    }
}