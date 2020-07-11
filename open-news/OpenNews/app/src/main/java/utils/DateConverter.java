package utils;

import android.text.format.DateUtils;
import java.time.Instant;
import java.util.Date;

public class DateConverter {
    public static String utcToLocal(String s){
        Instant instant = Instant.parse(s);
        Date date = Date.from(instant);
        return DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}
