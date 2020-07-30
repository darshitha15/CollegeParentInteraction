package darshita.com.parentteacherinteraction;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import darshita.com.parentteacherinteraction.aws.AmazonClientManager;


public class MessageReceivingService extends IntentService {
    public static SharedPreferences savedValues;
    static String details;
    static String currentUserUniqueId;
    static Bitmap bitmap;
    static PendingIntent pendingIntent = null;
    static SharedPreferences prefs;
    static DynamoDBMapper mapper;
    static AmazonClientManager clientManager;
    static String operation;
    static String facultyName, time, uniqueId;
    static Intent intent;
    static Notification notification;
    private GoogleCloudMessaging gcm;

    public MessageReceivingService() {
        super("MessageReceivingService");
    }

    public static void sendToApp(Bundle extras, final Context context) {

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        details = extras.getString("default");
//        String string=extras.getString("subject");
//        String body=extras.getString("title");
//       Log.d("dsa",string  + "        "+ body+  "           "+ extras.toString());

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            clientManager = new AmazonClientManager(context);
            AmazonDynamoDBClient ddb = clientManager.ddb();
            mapper = new DynamoDBMapper(ddb);
           // db = DatabaseHandler.getInstance(context);
        } catch (Exception e) {

            Log.d("notification", "jdfs");
        }


       // currentUserUniqueId = db.getUserData().getPhoneNumber();
        final List<String> list = Arrays.asList(details.split(","));
        operation = list.get(0);
        facultyName = list.get(1);
        uniqueId = list.get(2);
        time = list.get(3);

    }

    public void onCreate() {
        super.onCreate();
        final String preferences = "noti";
        savedValues = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        if (VERSION.SDK_INT > 9) {
            savedValues = getSharedPreferences(preferences, Context.MODE_MULTI_PROCESS);
        }
        gcm = GoogleCloudMessaging.getInstance(getBaseContext());
        SharedPreferences savedValues = PreferenceManager.getDefaultSharedPreferences(this);
        if (savedValues.getBoolean(getString(R.string.first_launch), true)) {
            register();
            SharedPreferences.Editor editor = savedValues.edit();
            editor.putBoolean(getString(R.string.first_launch), false);
            editor.commit();
        }


    }

    private void register() {
        new AsyncTask() {
            protected Object doInBackground(final Object... params) {
                String token;
                try {
                    token = gcm.register("parentteacherinteraction-63328");
                    Log.i("registrationId", token);
                } catch (IOException e) {
                    Log.i("Registration Error", e.getMessage());
                }
                return true;
            }
        }.execute(null, null, null);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            sendToApp(intent.getExtras(), this);
        } catch (Exception e) {
          e.printStackTrace();
        }
        stopSelf();

    }
}