package com.test.catalog.sales.db.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CatalogUtilities {

    private static final Logger logger = LoggerFactory.getLogger(CatalogUtilities.class);

    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static Integer YEAR_9999 = 9999;

    private CatalogUtilities() {
        throw new IllegalStateException("Utility class");
    }

    public static String getDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZZZZ");
        return dateFormat.format(date).replace("+0000", "-0000");
    }

    public static Timestamp getTimestampDate(String strDate) throws ParseException {
        Date releaseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZZZZ").parse(strDate);
        return new Timestamp(releaseDate.getTime());
    }
    public static Timestamp getTimestampDate(String strDate,String format) throws ParseException {
        Date releaseDate = new SimpleDateFormat(format).parse(strDate);
        return new Timestamp(releaseDate.getTime());
    }

    public static String getDateStr(Timestamp timestamp) {
        Date date=new Date(timestamp.getTime());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static Timestamp getTimestampDate(Date date) {
        return new Timestamp(date.getTime());
    }

}
