package Entities.CRUD;



import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import Database.CRUD.Collection;
import Database.CRUD.ICRUD;
import Database.CRUD.ICollection;
import Database.Connectivity.IDatabase;
import Entities.Notification;

public class NotificationCRUD implements ICRUD<Notification> {
    private final ICollection<Notification> notificationCollection;

    public NotificationCRUD(IDatabase database) {
        notificationCollection = new Collection<>(database, "Notifications", Notification.class);
    }

    @Override
    public Notification create(Notification object) { return notificationCollection.create(object); }

    @Override
    public List<Notification> read(Map<String, String> parameters) { return notificationCollection.read(parameters); }

    @Override
    public boolean delete(ObjectId objectId) { return notificationCollection.delete(objectId); }

    @Override
    public List<Notification> update(Map<String, String> filterQuery, Map<String, String> updateQuery) { return notificationCollection.update(filterQuery, updateQuery); }

    @Override
    public boolean exists(ObjectId objectId) { return notificationCollection.exists(objectId); }
}
