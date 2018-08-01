package com.example.kotireddy.motorsport;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {
    public static final String AUTH = "com.example.kotireddy.motorsport";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTH);
    public static final String PATH = "ParticipantDetails";
    public static final int ROW_ID = 50;
    public static final int TASK_ID = 100;
    public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

    public class FavouriteContractEntry implements BaseColumns {
        public static final String TABLE_NAME = "ParticipantDetails";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_ALSO_KNOWN_AS = "alsoKnownas";
        public static final String COLUMN_POSTER_PATH = "Poster_path";
        public static final String COLUMN_COUNTRY = "Country";
        public static final String COLUMN_MANAGER = "manager";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_WEBSITE = "website";
        public static final String COLUMN_DESCRIPTION = "news";
    }
}
