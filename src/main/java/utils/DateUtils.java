package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    
    public static String getFormattedDateYYYYMMDD() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }
    
    public static String getFormattedDateHHMMSS() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        return formatter.format(date);
    }
}
