package Entities.CRUD;

import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import Database.CRUD.Collection;
import Database.CRUD.ICRUD;
import Database.CRUD.ICollection;
import Database.Connectivity.IDatabase;
import Entities.Template;

public class TemplateCRUD implements ICRUD<Template> {
    private final ICollection<Template> templateCollection;

    public TemplateCRUD(IDatabase database) {
        templateCollection = new Collection<>(database, "Templates", Template.class);
    }

    @Override
    public Template create(Template object) {
        return templateCollection.create(object);
    }

    @Override
    public boolean delete(ObjectId objectId) {
        return templateCollection.delete(objectId);
    }

    @Override
    public List<Template> update(Map<String, String> filterQuery, Map<String, String> updateQuery) {
        return templateCollection.update(filterQuery, updateQuery);
    }

    @Override
    public List<Template> read(Map<String, String> parameters) {
        return templateCollection.read(parameters);
    }

    @Override
    public boolean exists(ObjectId objectId) {
        return templateCollection.exists(objectId);
    }
}
