package ud.barberClients.utils.dates;

import java.util.Calendar;
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
}
