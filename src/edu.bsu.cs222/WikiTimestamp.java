package edu.bsu.cs222;

import java.sql.Timestamp;

public class WikiTimestamp {
    private String readable;
    private Timestamp timestamp;

    public WikiTimestamp(String unformattedTS) {
        this.timestamp = createTS(unformattedTS);
        this.readable = createReadableTS();
    }

    public String getReadable(){
        return readable;
    }

    //The Wikipedia timestamp format includes 2 undesired characters when we need them to be spaces instead
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
        ts += monthDayYeat() + " at " + hour() + ":" + minutes() + timeOfDay();
        return ts;
    }

    private String timeOfDay() {
        if(isAfterNoon(timestamp.getHours())) { return "p.m.";}
        else { return "a.m."; }
    }

    private String monthDayYeat() { return (timestamp.getMonth()+1) + "/" + timestamp.getDate() + "/" + (timestamp.getYear()+1900) ; }

    private String hour() {
        int hour = timestamp.getHours();
        if (isAfterNoon(hour)) { hour = hour - 12; }
        if (hourHandIsOnTwelve(hour)) { hour = 12; }
        return hour + "";
    }

    private String minutes() {
        String minuteString = "";
        int minutes = timestamp.getMinutes();
        if ( notTenPastYet(minutes) ){
            minuteString += timestamp.getMinutes();
            minuteString += "0";
        }
        return minuteString;
    }

    private boolean isAfterNoon(int hour) { return(hour >= 12); }

    private boolean hourHandIsOnTwelve(int hour) { return(hour == 0); }

    private boolean notTenPastYet(int minutes) { return(minutes < 10); }
}
