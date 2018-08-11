package thebrightcompany.com.garage.fcm.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.logout.LogoutCallAPI;
import thebrightcompany.com.garage.fcm.app.Config;
import thebrightcompany.com.garage.fcm.utils.NotificationUtils;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.view.MainActivity;
import thebrightcompany.com.garage.view.SplashActivity;

/**
 * 0: Mới
 * 1:Đang sửa
 * 2: Đã sửa xong
 * 3: Đã bàn giao cho khách hàng
 * -1: Đã hủy
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private NotificationManager mNotificationManager;
    private String mTittle, mBody;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "fcm_From: " + remoteMessage.getFrom());
        Log.d(TAG, "fcm_Data Payload: " + remoteMessage.getData().toString());
        Log.d(TAG, "fcm_Notification Body: " + remoteMessage.getNotification().getBody());

        if (remoteMessage == null)
            return;

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "fcm_Data Payload: " + remoteMessage.getData().toString());

          /*  try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.d(TAG, "fcm_Exception: " + e.getMessage());
            }*/
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "fcm_Notification Body: " + remoteMessage.getNotification().getBody());
            mTittle =  remoteMessage.getNotification().getTitle();
            mBody = remoteMessage.getNotification().getBody();

            //handleNotification(remoteMessage.getNotification().getBody());

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constant.ACTION_RECEIVER_STATE);
                pushNotification.putExtra("message", remoteMessage.getNotification().getBody());
                pushNotification.putExtra("tittle", mTittle);
                pushNotification.putExtra("body", mBody);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }else{

                //sendNotification(mTittle, mBody);
            }
        }
    }

    private void handleNotification(String message) {
        Log.d(TAG, "fcm_message: " + message);
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Constant.ACTION_RECEIVER_STATE);
            pushNotification.putExtra("message", message);
            pushNotification.putExtra("tittle", mTittle);
            pushNotification.putExtra("body", mBody);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
            Log.d(TAG, "fcm_isAppIsInBackground");
               /* Intent resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
                resultIntent.putExtra("data", message);

                // check for image attachment
                startActivity(resultIntent);
                //showNotificationMessage(getApplicationContext(), type, message, "27/07/2018", resultIntent);*/
            //sendNotification(234, message);
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.d(TAG, "fcm_push json: " + json.toString());

        try {
            String type = json.getString("type");
            Log.d(TAG, "fcm_type: " + type);
            int order_id = json.getInt("order_id");
            Log.d(TAG, "fcm_order_id: " + order_id);
            String message = type + " - " + order_id;
            Log.d(TAG, "fcm_message: " + message);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Log.d(TAG, "fcm_isNotAppIsInBackground");
                Intent pushNotification = new Intent(Constant.ACTION_RECEIVER_STATE);
                pushNotification.putExtra("data", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Log.d(TAG, "fcm_isAppIsInBackground");
               /* Intent resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
                resultIntent.putExtra("data", message);

                // check for image attachment
                startActivity(resultIntent);
                //showNotificationMessage(getApplicationContext(), type, message, "27/07/2018", resultIntent);*/
                //sendNotification(order_id, message);
            }
        } catch (JSONException e) {
            Log.d(TAG, "fcm_Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "fcm_Exception: " + e.getMessage());
        }
    }
    /**
     * Create and show a simple notification containing the received GCM message.
     */
    private void sendNotification(String tittle, String body) {

        Log.e(TAG,"fcm_messageBody = " + body);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = {
                500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
                500, 500, 500, 500, 500, 500
        };
       mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i = new Intent(this, SplashActivity.class);
        i.putExtra("body", body);
        i.putExtra("tittle", tittle);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();

        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_small_logo)
                .setContentTitle(tittle).setContentText(body).setVibrate(pattern)
                .setSound(alarmSound).setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
