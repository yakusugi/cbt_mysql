package com.myproject.offlinebudgettrackerappproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * Converts a String to a Date using the specified format.
     *
     * @param dateString The string to be converted to a Date.
     * @param format     The format of the dateString.
     * @return The Date object corresponding to the provided string and format.
     * @throws ParseException If the string cannot be parsed into a date.
     */
    public static Date stringToDate(String dateString, String format) throws ParseException {
        // Create a SimpleDateFormat object with the specified format
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        // Parse the string into a Date
        return dateFormat.parse(dateString);
    }

    /**
     * Converts a Date to a String using the specified format.
     *
     * @param date   The Date object to be converted to a String.
     * @param format The format for the resulting string.
     * @return The string representation of the Date object.
     */
    public static String dateToString(Date date, String format) {
        // Create a SimpleDateFormat object with the specified format
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        // Format the Date into a String
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        // Example usage
        String dateString = "2024-08-22 13:45:30";
        String format = "yyyy-MM-dd HH:mm:ss";

        try {
            // Convert the string to a Date
            Date date = DateUtil.stringToDate(dateString, format);

            // Print the Date object
            System.out.println("Parsed Date: " + date);

            // Convert the Date back to a String
            String formattedDate = DateUtil.dateToString(date, format);

            // Print the formatted date string
            System.out.println("Formatted Date String: " + formattedDate);
        } catch (ParseException e) {
            // Handle the exception if the string cannot be parsed
            System.err.println("Error parsing date: " + e.getMessage());
        }
    }
}
