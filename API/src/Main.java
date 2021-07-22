import java.util.HashMap;

import Database.Connectivity.IDatabase;
import Database.Connectivity.MongoDB;
import Entities.CRUD.TemplateCRUD;

public class Main {
    public static void main(String[] args) {
        IDatabase database = new MongoDB(
                "mongodb+srv://notifyme:Sc1yTvMC8mR3d4tX@notifyme.a9swz.mongodb.net/NotificationModule?retryWrites=true&w=majority");

        database.openConnection();

        TemplateCRUD templateCRUD = new TemplateCRUD(database);
        System.out.println(templateCRUD.read(new HashMap<>()));
    }
}
