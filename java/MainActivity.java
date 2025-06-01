package com.enthe1m.myapplication;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleMonthlyNotification();
    }

    private void scheduleMonthlyNotification() {
        Calendar calendar = Calendar.getInstance();

        // Set the calendar to the first of the next month at midnight
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long initialDelay = calendar.getTimeInMillis() - System.currentTimeMillis();

        //PeriodicWorkRequest notificationWork = new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.MONTHS)
          //      .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            //    .setConstraints(new Constraints.Builder().setRequiresBatteryNotLow(true).build())
              //  .build();

//        WorkManager.getInstance(this).enqueue(notificationWork);
    }
}
