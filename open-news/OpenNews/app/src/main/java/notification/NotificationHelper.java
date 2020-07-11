package notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.example.opennews.R;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationHelper {

    private static AlarmManager alarmManagerRTC;
    private static PendingIntent alarmIntentRTC;
    public static int ALARM_TYPE_RTC = 100;

    public static void scheduleNotification(Context context, String hours, String minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,
                Integer.getInteger(hours, 8),
                Integer.getInteger(minutes, 0));
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntentRTC = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManagerRTC = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        alarmManagerRTC.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentRTC);
    }

    public static void cancelNotifications(){
        if(alarmManagerRTC != null)
            alarmManagerRTC.cancel(alarmIntentRTC);
    }

    public static NotificationManager getNotificationManager(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "openNewsChannel";
            CharSequence name = "simpleChannel";
            String description = "This is news notification channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(R.color.colorPrimaryDark);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
        return notificationManager;
    }

}
