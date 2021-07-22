package JSONMapper;

import com.google.gson.Gson;
import Entities.Notification;

public class NotificationAdapter implements Adapter <Notification> {
    @Override
    public Notification toEntity(String JSON) {
        try {
            return new Gson().fromJson(JSON, Notification.class);

        }catch(Exception e){
            System.out.println("Couldn't parse Input");
            return null;
        }
    }

    @Override
    public String toJSON(Notification entity) {
        try{
            return new Gson().toJson(entity);
        }
        catch(Exception e){
            System.out.println("Couldn't parse Input");
            return null;
        }
    }
}