package Entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This class stores time information
 */
public class Date {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private static final Pattern datePattern
            = Pattern.compile("\\b(\\d{1,2})\\/(\\d{1,2})\\/(\\d{1,4})\\b");

    private static final Pattern dateTimePattern
            = Pattern.compile("\\b(\\d{1,2})\\/(\\d{1,2})\\/(\\d{1,4})\\((\\d{1,2}):(\\d{1,2})\\)");

    public Date(int y, int mon, int d, int h, int min) {
        year = y;
        month = mon;
        day = d;
        hour = h;
        minute = min;
    }

    public Date(String dateStr) throws IllegalArgumentException {
        setDateTime(dateStr);
    }

    public String toDateString() {
        return String.format("%d/%d/%d", month, day, year);
    }

    public String toTimeString() {
        return String.format("%2d:%2d", hour, minute);
    }

    public void setDateTime(String dateStr) throws IllegalArgumentException {
        Matcher dateTimeMatcher = dateTimePattern.matcher(dateStr);
        if(dateTimeMatcher.matches()) {
            month = Integer.parseInt(dateTimeMatcher.group(1));
            day = Integer.parseInt(dateTimeMatcher.group(2));
            year = Integer.parseInt(dateTimeMatcher.group(3));
            hour = Integer.parseInt(dateTimeMatcher.group(4));
            minute = Integer.parseInt(dateTimeMatcher.group(5));
            valiDate();
            return;
        }
        Matcher dateMatcher = datePattern.matcher(dateStr);
        if(dateMatcher.matches()) {
            month = Integer.parseInt(dateMatcher.group(1));
            day = Integer.parseInt(dateMatcher.group(2));
            year = Integer.parseInt(dateMatcher.group(3));
            hour = 0;
            minute = 0;
            valiDate();
            return;
        }
        throw new IllegalArgumentException(
                "Date must be in format MM/DD/YYYY or MM/DD/YYYY(hh:mm)");
    }

    //Getters
    public int getYear() {return year;}
    public int getMonth() {return month;}
    public int getDay() {return day;}
    public int getHour() {return hour;}
    public int getMinute() {return minute;}
    public String getString() {return "String";}

    private void valiDate() throws IllegalArgumentException {
        if(month < 0 || month > 12) {
            throw new IllegalArgumentException("Invalid month.");
        }
        if(day < 0 || day > 31) {
            throw new IllegalArgumentException("Invalid day.");
        }
        if(year < 0 || year > 9999) {
            throw new IllegalArgumentException("Invalid year.");
        }
        if(hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Invalid hour.");
        }
        if(minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid minute.");
        }
    }

    public static void main(String[] args) {
        Date d = new Date("01/01/2002");
    }
}
