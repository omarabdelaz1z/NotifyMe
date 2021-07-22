package Utilities;

import java.util.HashMap;
import java.util.Map;
import jakarta.ws.rs.core.MultivaluedMap;

public class Utils {
    public static Map<String, String> prepareQueryParameters(MultivaluedMap<String, String> queryParameters){
        Map<String,String> parameters = new HashMap<>();

        for(String str : queryParameters.keySet())
            parameters.put(str, queryParameters.getFirst(str));

        return parameters;
    }
}
