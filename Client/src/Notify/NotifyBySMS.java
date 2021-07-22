package Notify;

import java.util.List;
import ClientCalls.ClientCRUD;
import Entities.PreparedNotification;

public class NotifyBySMS implements NotifyStrategy {
    private final ClientCRUD notificationClient;
    private final List<PreparedNotification> preparedNotifications;

    public NotifyBySMS(ClientCRUD notificationClient, List<PreparedNotification> preparedNotifications){
        this.notificationClient = notificationClient;
        this.preparedNotifications = preparedNotifications;
    }

    @Override
    public void Notify() {
        for(PreparedNotification preparedNotification: preparedNotifications)
        {
            /*
            String status = ((int)(Math.random() * ((2 - 1) + 1)) + 1) == 1 ? "SUCCESS" : "FAILURE";
            String update = new Gson().toJson(new HashMap<String, String>(){{ put("state", status); }},vy
                    new TypeToken<HashMap>(){}.getType() );

            notificationClient.update(filter, update);
            filter.remove("id");

            */
        }
    }
}
