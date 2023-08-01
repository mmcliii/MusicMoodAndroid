package com.example.musicmoods;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
//import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;
//import androidx.core.app.JobIntentService;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoodLog //extends Service
{

    private static final String FILE_NAME = "MoodLog.txt";
//    private static final String TAG = "MoodLog";
    private static final String CHANNEL_ID = "ServiceNotification";
//    private PowerManager.WakeLock wl;


//    @Override
//    public  void onCreate(){
//        super.onCreate();
//        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
//        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                "Wake:Lock");
//        wl.acquire(600000);
//
//    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel(
//                   CHANNEL_ID,
//                   CHANNEL_ID,
//                    NotificationManager.IMPORTANCE_LOW
//            );
//
//            Notification notification = new Notification.Builder(this, CHANNEL_ID)
//                    .setContentTitle("Mood Log")
//                    .setContentText("Generating...")
//                    .setSmallIcon(R.drawable.ic_launcher_foreground)
//                    .build();
//            startForeground(1001, notification);
//        }
//        onHandleIntent(intent);
//        return super.onStartCommand(intent, flags, startId);
//    }
//    protected void onHandleIntent(@Nullable Intent intent) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss z", Locale.CHINA);
//        String currentDateTime = sdf.format(new Date());
//
//        String track_id = intent.getStringExtra("trackId");
//
//        if(!Python.isStarted())
//            Python.start(new AndroidPlatform(this));
//
//        Python py = Python.getInstance();
//        final PyObject pyObj = py.getModule("TrackInfo");
//        PyObject moodInt = pyObj.callAttr("predict", track_id);
//        String entryText = currentDateTime + moodInt.toString();
//
//        //Write to file
//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput(FILE_NAME, MODE_APPEND);
//            fos.write(entryText.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(fos != null){
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
}
