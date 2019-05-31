package com.example.acer.capstonestagetwo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.widget.RemoteViews;

import com.example.acer.capstonestagetwo.UI.PosterActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */

public class NewAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", MODE_PRIVATE);

        String date = sharedPreferences.getString("redate", "");
        String title = sharedPreferences.getString("title", "");


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.reviews, date);
        views.setTextViewText(R.id.name, title);


        Intent intent = new Intent(context, PosterActivity.class);
        sharedPreferences.edit().clear();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.reviews, pendingIntent);
        views.setOnClickPendingIntent(R.id.name, pendingIntent);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);


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

