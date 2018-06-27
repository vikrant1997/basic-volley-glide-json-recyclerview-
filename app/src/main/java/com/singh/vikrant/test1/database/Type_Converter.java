package com.singh.vikrant.test1.database;

import android.arch.persistence.room.TypeConverter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class Type_Converter {
    @TypeConverter
    public static URI uriToString(String timestamp) throws URISyntaxException {

        return timestamp == null ? null : new URI(timestamp);
    }

    @TypeConverter
    public static String stringToUri(URI date) {
        return date == null ? null : date.toString();
    }
}
