//package com.enthe1m.myapplication;
//
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//import androidx.room.Room;
//import androidx.work.Worker;
//import androidx.work.WorkerParameters;
//
//import java.util.Calendar;
//import java.util.List;
//
//public class NotificationWorker extends Worker {
//    private static final String CHANNEL_ID = "books_notification_channel";
//
//    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
//        super(context, workerParams);
//    }
//
//    @NonNull
//    @Override
//    public Result doWork() {
//        UserDatabase db = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "user-database")
//                .allowMainThreadQueries()
//                .build();
//
//        // Calculate the number of books added since January 1st
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, Calendar.JANUARY);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        long startOfYear = calendar.getTimeInMillis();
//
//        List<Book> books = db.bookDao().getAllBooksAddedSince(startOfYear);
//
//        int bookCount = books.size();
//
//        // Create the notification
//        createNotificationChannel();
//
//        Intent intent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_book)
//                .setContentTitle("Books Added Since Jan 1")
//                .setContentText("You have added " + bookCount + " books since January 1st.")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//        notificationManager.notify(1, builder.build());
//
//        return Result.success();
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Books Notification Channel";
//            String description = "Channel for books notification";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//}
//
