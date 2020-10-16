package stop.one.sellerapp.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import stop.one.sellerapp.R;

public class FirebaseMessangingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title =remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();
        String Order_id=remoteMessage.getData().get("Order_id");
        String Product_id=remoteMessage.getData().get("Product_id");
        String Address_id=remoteMessage.getData().get("Address_id");
        String User_id=remoteMessage.getData().get("User_id");
        String click_action=remoteMessage.getNotification().getClickAction();


        Log.d("detail",Order_id+" "+Product_id+" "+Address_id+" "+User_id);


        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),getString(R.string.default_notification_channel_id))
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);

        Intent intent = new Intent(click_action);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Order_id",Order_id);
        intent.putExtra("Product_id",Product_id);
        intent.putExtra("Address_id",Address_id);
        intent.putExtra("User_id",User_id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);

        int notificationId=(int)System.currentTimeMillis();
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId,builder.build());



    }
}
