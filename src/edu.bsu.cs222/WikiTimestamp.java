package edu.bsu.cs222;

import java.sql.Timestamp;

/**
 * Created by brian on 9/20/2016.
 */
public class WikiTimestamp {

    private String unformatted, readable;
    private Timestamp timestamp;

    public WikiTimestamp(String unformattedTS) {
        this.timestamp = createTS(unformattedTS);
        this.readable = createReadableTS();
    }

    public String getReadable(){
        return readable;
    }

    private String removeExtraChars(String ts){
        char[] chars = ts.toCharArray();
        chars[10] = ' ';
        chars[19] = ' ';
        return new String(chars);
    }

    private Timestamp createTS(String unformattedTS){
        return Timestamp.valueOf(removeExtraChars(unformattedTS));
    }

    @SuppressWarnings("deprecation")
    private String createReadableTS(){
        String ts = "";
        String timeOfDay = "a.m.";
        String minuteString = "";
        int hour = timestamp.getHours();
        int minutes = timestamp.getMinutes();
        ts += (timestamp.getMonth()+1) + "/" + timestamp.getDate() + "/" + (timestamp.getYear()+1900) + " at ";
        if ( isAfterNoon(hour) ) {//hour > 12){
            hour = hour-12;
            timeOfDay="p.m.";
        }
        if ( hourHandIsOnTwelve(hour) ){
            hour = 12;
        }
        if ( notTenPastYet(minutes) ){
            minuteString += "0";
        }
        minuteString += timestamp.getMinutes();
        ts += hour + ":" + minuteString + timeOfDay;
        return ts;
    }

    private boolean isAfterNoon(int hour) { return(hour >= 12); }

    private boolean hourHandIsOnTwelve(int hour) { return(hour == 0); }

    private boolean notTenPastYet(int minutes) { return(minutes < 10); }
}
