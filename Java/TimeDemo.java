import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeDemo {
    public static void main(String[] args) throws ParseException {
        String timeZone = "GMT+5:30";//Set needed timezone
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone(timeZone);
        String dd = Integer.toString(Calendar.getInstance(tz).get(Calendar.DATE));
        String M = Integer.toString(Calendar.getInstance(tz).get(Calendar.MONTH));
        String yyyy = Integer.toString(Calendar.getInstance(tz).get(Calendar.YEAR));
        String dateString = dd + "-" + M + "-" + yyyy; /* Doing this because current
        date at a given timezone can be different from your date*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String lowerLimit = dateString + " 00:00:00";//Given Upper and lower limits
        String upperLimit = dateString + " 05:45:00";

        //required upper and lower limits and current time in milliseconds
        long timeLower = simpleDateFormat.parse(lowerLimit).getTime();
        long timeUpper = simpleDateFormat.parse(upperLimit).getTime();
        long currentTimeInMillis = Calendar.getInstance(tz).getTimeInMillis();

        if ((timeLower < currentTimeInMillis) && (currentTimeInMillis < timeUpper)) {
            System.out.println("execute true statements here");
        } else {
            System.out.println("execute false statements here");
        }
    }
}