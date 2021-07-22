package ClientCalls;

import java.util.Map;
import jakarta.ws.rs.client.WebTarget;


public class Utils {
    public static WebTarget prepareTarget(WebTarget target, Map<String, String> parameters){
        WebTarget requestedTarget = target;

        for (Map.Entry<String, String> entry : parameters.entrySet())
            requestedTarget = requestedTarget.queryParam(entry.getKey(), entry.getValue());

        return requestedTarget;
    }
}
