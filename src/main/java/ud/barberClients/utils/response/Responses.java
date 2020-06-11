package ud.barberClients.utils.response;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


public class Responses {

    public Response getResponse(Response.Status status, String id, String[] keys, String[] values) {
        Map<String, String> description = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            description.put(keys[i], values[i]);
        }
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode(status.getStatusCode());
        errorModel.setDescription(description);
        errorModel.setId(id);
        return Response.status(status).entity(errorModel).build();
    }
}