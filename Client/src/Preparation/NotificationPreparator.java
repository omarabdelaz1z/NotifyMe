package Preparation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.text.StringSubstitutor;
import ClientCalls.ClientCRUD;
import Entities.Notification;
import Entities.Template;
import Entities.PreparedNotification;
import JSONMapper.Adapter;
import JSONMapper.NotificationAdapter;
import JSONMapper.TemplateAdapter;

public class NotificationPreparator {
    private final ClientCRUD templateClient;
    private final List<Notification> pendingNotifications;

    public NotificationPreparator(ClientCRUD templateClient, ClientCRUD notificationClient) {
        this.templateClient = templateClient;
        Map<String, String> filters = new HashMap<>(){{put("state", "PENDING");}};
        Adapter<Notification> adapter = new NotificationAdapter();
        pendingNotifications = notificationClient.GET(filters).stream().map(adapter::toEntity).collect(Collectors.toList());
    }

    private String replacePlaceholders(Notification notification, Template template){
        return new StringSubstitutor(notification.getParameters()).replace(template.getContent());
    }

    private String fetchTemplate(String ID) {
        return templateClient.GET(new HashMap<>(){{ put("id", ID); }}).get(0);
    }

    public List<PreparedNotification> prepare(){
        List<PreparedNotification> preparedNotifications = new ArrayList<>();
        Adapter<Template> adapter = new TemplateAdapter();

        for(Notification pendingNotification: pendingNotifications){
            String response = fetchTemplate(pendingNotification.getTemplateId());
            Template template = adapter.toEntity(response);
            String content = replacePlaceholders(pendingNotification, template);

            PreparedNotification preparedNotification = new
                    PreparedNotification(content, pendingNotification.getTarget());

            preparedNotifications.add(preparedNotification);
        }

        return preparedNotifications;
    }
}
