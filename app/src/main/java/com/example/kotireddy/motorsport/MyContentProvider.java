package com.example.kotireddy.motorsport;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    private static final UriMatcher myuri = matchUri();
    DBhelper favouriteDBHelper = new DBhelper(getContext());
    SQLiteDatabase sqLiteDatabase;

    private static UriMatcher matchUri() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.AUTH, Contract.PATH, Contract.ROW_ID);
        uriMatcher.addURI(Contract.AUTH, Contract.PATH + "/#", Contract.TASK_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        favouriteDBHelper = new DBhelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        sqLiteDatabase = favouriteDBHelper.getReadableDatabase();
        int match = myuri.match(uri);
        Cursor result = null;
        if (match == Contract.ROW_ID) {
            result = sqLiteDatabase.query(Contract.FavouriteContractEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (match == Contract.TASK_ID) {
            String id = uri.getPathSegments().get(1);
            String mSelection = "id=?";
            String[] mSelectionArgs = new String[]{"" + id};
            result = sqLiteDatabase.query(Contract.FavouriteContractEntry.TABLE_NAME, projection, mSelection, mSelectionArgs, null, null, sortOrder);
        }
        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri returnUri = null;
        int match = myuri.match(uri);
        sqLiteDatabase = favouriteDBHelper.getWritableDatabase();
        if (match == Contract.ROW_ID) {
            long id = sqLiteDatabase.insert(Contract.FavouriteContractEntry.TABLE_NAME, null, values);
        } else {
            throw new UnsupportedOperationException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = myuri.match(uri);
        long result;
        if (match == Contract.ROW_ID) {
            SQLiteDatabase sqLiteDatabase = favouriteDBHelper.getWritableDatabase();
            result = sqLiteDatabase.delete(Contract.FavouriteContractEntry.TABLE_NAME, Contract.FavouriteContractEntry.COLUMN_ID + "=" + Integer.parseInt(selection), null);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        return (int) result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

