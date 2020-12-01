package com.androidtutorialpoint.sendsms;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class WlaczAlarm extends AppWidgetProvider {

    static String CLICK_ACTION = "Klikniety";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context,WlaczAlarm.class);
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

        CharSequence widgetText = context.getString(R.string.appwidget_textwl);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wlacz_alarm);
        views.setTextViewText(R.id.appwidget_textwl, widgetText);
        views.setOnClickPendingIntent(R.id.wlacz_alarm,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
        if(intent.getAction().equals(CLICK_ACTION)){
            sendMySMSWL(context);
        }

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

    public void sendMySMSWL(Context context) {

        String phone = "48530515282";
        String message = "Zdom";

            SmsManager sms = SmsManager.getDefault();
            // if message length is too long messages are divided
            List<String> messages = sms.divideMessage(message);
            for (String msg : messages) {

                PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
                PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED"), 0);
                sms.sendTextMessage(phone, null, msg, sentIntent, deliveredIntent);

            }
        Toast.makeText(context, "Włączam alarm", Toast.LENGTH_SHORT).show();
    }
}

