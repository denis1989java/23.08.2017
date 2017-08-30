package ru.mail.denis.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 29.08.2017.
 */
public class DateConverter {

    public static String converter (){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date=new Date();
        return dateFormat.format(date);
    }
}
