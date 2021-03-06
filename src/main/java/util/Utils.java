package util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by abora on 4/4/15.
 */
public abstract class Utils {

    public static String getRenewdTimestamp(Timestamp end_date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(end_date);
        cal.add(Calendar.DAY_OF_WEEK, 150);
        cal.set(Calendar.MILLISECOND, 0);
        end_date.setTime(cal.getTime().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(end_date);
    }

    public static Long getCurrentTS(){
        return  Calendar.getInstance().getTimeInMillis();
    }

    public static void main(String[] args) {
        System.out.println(getCurrentTS());
        System.out.println(getDate("10-15-1987"));
    }

    public static java.util.Date getDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
        java.util.Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
}
