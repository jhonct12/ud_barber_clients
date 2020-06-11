package ud.barberClients.utils.exceptions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForbiddenException extends Exception {
    private Map<String, String> reasons;
    public ForbiddenException(String message) {
        super(message);
        this.reasons = new HashMap<>();
    }

    public ForbiddenException(String message, Map<String, String> cause) {
        super(message);
        this.reasons = cause;
    }

    public Map<String, String> getReasons() {
        return reasons;
    }

    public void setReasons(Map<String, String> reasons) {
        this.reasons = reasons;
    }

    public List<String[]> getErrorsResponse() {
        List<String[]> errorList = new ArrayList<>();
        String[] title = new String[reasons.keySet().size()];
        String[] message = new String[reasons.keySet().size()];
        Integer counter = 0;
        for (Map.Entry<String, String> entry: reasons.entrySet()) {
            title[counter] = entry.getKey();
            message[counter] = entry.getValue();
            counter++;
        }
        errorList.add(title);
        errorList.add(message);
        return errorList;
    }

}
