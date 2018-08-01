package com.example.kotireddy.motorsport;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        Cursor cursor = context.getContentResolver().query(Contract.CONTENT_URI, null, null, null, null);
        String text="";
        while (cursor.moveToNext()) {
            text = text + "\n" + cursor.getString(1);
        }        // Instruct the widget manager t// o update the widget
        cursor.close();
        if (text.equals("")) {
            views.setTextViewText(R.id.appwidget_text, context.getResources().getString(R.string.NoFavourites));
            appWidgetManager.updateAppWidget(appWidgetId, views);

        } else {
            views.setTextViewText(R.id.appwidget_text, text);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

