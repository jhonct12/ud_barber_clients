package ud.barberClients.utils.dates;

import ud.barberClients.utils.exceptions.ConflictException;
import ud.barberClients.utils.nameReserved.NameReserved;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Dates {

    public static Calendar initializeCalendar() {
        Calendar calendar;
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        return calendar;
    }

    public static Long currentDateInEpoch() {
        Calendar calendar = initializeCalendar();
        return calendar.getTimeInMillis();
    }

    public static Boolean verifyCrossDates(Long initialDate, Long finalDate) throws ConflictException {
        if (finalDate > initialDate){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
            reason.put(NameReserved.FINAL_DATE, finalDate.toString());
            throw new ConflictException(NameReserved.CROSS_DATES, reason);
        }
    }
}
