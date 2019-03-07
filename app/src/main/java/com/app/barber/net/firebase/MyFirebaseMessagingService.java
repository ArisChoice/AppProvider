/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.barber.net.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.barber.R;
import com.app.barber.core.BarberApplication;
import com.app.barber.models.ChatPushModel;
import com.app.barber.ui.postauth.activities.HomeActivity;
import com.app.barber.ui.postauth.activities.settings.NotificationActivity;
import com.app.barber.ui.postauth.activities.socket_work.chat.ChatActivity;
import com.app.barber.util.CommonUtils;
import com.app.barber.util.GlobalValues;
import com.app.barber.util.PreferenceManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import javax.inject.Inject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Inject
    PreferenceManager mPref;
    private static final String TAG = "MyFirebaseMsgService";
    private static int NOTIFICATION_ID;
    private String notificationMsgType, messageText;
    private PushNotificationModel senderData;
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BarberApplication) getApplication()).getMyComponent(this).inject(this);
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "  , From ::::::: P U S H     Notification" + remoteMessage);

        if (mPref.getPrefrencesBoolean(GlobalValues.KEYS.isLogedIn)) {
            try {
                Log.d(TAG, " Barber  , From ::::::: P U S H     M E S S A G E:: 1 " + new Gson().toJson(remoteMessage.getData()));
                PushNotificationModel notifiData = new Gson().fromJson(new Gson().toJson(remoteMessage.getData()), PushNotificationModel.class);
//                Log.d(TAG, " Barber  , From ::::::: P U S H     M E S S A G E:: 1 " + new Gson().toJson(notifiData));
                if (notifiData.getNotificationType() != null) {
                    Intent intent;
                    if (notifiData.getNotificationType().equals("" + GlobalValues.NotificationType.CHAT_MESSAGE)) {
                        if (!new CommonUtils().getIsChatActive()) {
                            intent = new Intent(this, HomeActivity.class);
                            intent.setAction(ChatActivity.class.getName());
                            intent.putExtra(GlobalValues.KEYS.EXTRA_DIALOG_ID, notifiData.getQbdialog());
                            generateNotification(notifiData.getAlert(), notifiData.getName(), intent);
                        }
                    } else {
                        if (notifiData.getNotificationType().equals("" + GlobalValues.NotificationType.AppointmentConfirmation) ||
                                notifiData.getNotificationType().equals("" + GlobalValues.NotificationType.CallOutConfirmation)) {
                            if (!mPref.getPrefrencesBoolean(GlobalValues.KEYS.BLOCK_NOTIFICATION_APPOINTMENTS_CALLOUOTS)) {
                                intent = new Intent(this, HomeActivity.class);
                                intent.setAction(NotificationActivity.class.getName());
                                generateNotification(notifiData.getMessage(), notifiData.getMessage(), intent);
                            }
                        } else {
                            intent = new Intent(this, HomeActivity.class);
                            intent.setAction(NotificationActivity.class.getName());
                            generateNotification(notifiData.getMessage(), notifiData.getMessage(), intent);
                        }

                    }
                } else {
                   /* ChatPushModel pushData = new Gson().fromJson(remoteMessage.getData().get("message"), ChatPushModel.class);
                    Log.d(TAG, " Barber  , else  P U S H     ----" + remoteMessage.getData().get("message"));

                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.setAction(NotificationActivity.class.getName());
                    intent.putExtra(GlobalValues.KEYS.EXTRA_DIALOG_ID, pushData.getSenderName());
                    generateNotification(pushData.getMessage(), pushData.getSenderName(), intent);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
        }
    }

    private void generateNotification(String message, String title, Intent intent) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 128, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NOTIFICATION_ID++;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "Barber_channel_01";
            CharSequence name = "Barber_channel";
            String Description = "This is Barber channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            mNotificationManager.createNotificationChannel(mChannel);
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(contentIntent);
        } else {
            mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message))
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentText(message);
            mBuilder.setContentIntent(contentIntent);
        }
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
