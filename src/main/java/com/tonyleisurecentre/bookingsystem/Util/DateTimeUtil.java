package com.tonyleisurecentre.bookingsystem.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    public static LocalDate strToLocalDate(String str) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }

    public static String dateTimeToSectionId(LocalDateTime dateTime, String facilityId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return facilityId.concat(dateTime.format(formatter));
    }
}
