package com.example.kotireddy.motorsport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favouriteDatabase.db";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void drop(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SportTeamDetails");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE "
                + Contract.FavouriteContractEntry.TABLE_NAME + " ( "
                + Contract.FavouriteContractEntry.COLUMN_POSTER_PATH + " TEXT , "
                + Contract.FavouriteContractEntry.COLUMN_TITLE + " TEXT  , "
                + Contract.FavouriteContractEntry.COLUMN_ALSO_KNOWN_AS + " TEXT  , "
                + Contract.FavouriteContractEntry.COLUMN_MANAGER + " TEXT  , "
                + Contract.FavouriteContractEntry.COLUMN_COUNTRY + " TEXT  , "
                + Contract.FavouriteContractEntry.COLUMN_WEBSITE + " TEXT  , "
                + Contract.FavouriteContractEntry.COLUMN_YEAR + " TEXT  , "
                + Contract.FavouriteContractEntry.COLUMN_DESCRIPTION + " TEXT, "
                + Contract.FavouriteContractEntry.COLUMN_ID + " TEXT PRIMARY KEY" + ");";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.FavouriteContractEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}

