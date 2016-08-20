package noti.example.com.creating_notif;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.widget.Toast;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
//// rethink imports
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlError;
import com.rethinkdb.gen.exc.ReqlQueryLogicError;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
//import com.squareup.okhttp.Call;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.Headers;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

//import org.json.simple.JSONObject;

import java.io.IOException;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTextView;


    // declaring button
    Button clickme;

    //declaring rethink db object
//    public static final RethinkDB r = RethinkDB.r;

    //ok http try
//    OkHttpClient client = new OkHttpClient();

    public static final RethinkDB r = RethinkDB.r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });


        // bindings of clickme part
        clickme = (Button) findViewById(R.id.clickme);
        clickme.setOnClickListener(this);


//        Request request = new Request.Builder()
//                .url("http://glados.local:8008")
//                .build();
//        Response response = client.execute(request);


        // instantiate rethink connections on activity inflate
//        Connection conn = r.connection().hostname("glados.local").port(8008).connect();
//        r.table('users').changes().run(conn, function(err, cursor) {
//            cursor.each(console.log);
//        });

//        for (int i = 0 ; i < 10000 ; i++) {
//            if(i%100 == 0) {
//                int notificationId = i;
//
//                //Building notification layout
//                NotificationCompat.Builder notificationBuilder =
//                        new NotificationCompat.Builder(this)
//                                .setSmallIcon(R.drawable.ic_launcher)
//                                .setContentTitle("Falling Incident")
//                                .setContentText("The person you subscribed to might have fallen down");
////                                .setContentIntent(viewPendingIntent);
////                        .addAction(R.drawable.ic_launcher, "Open Google", mapPendingIntent)
////                        .setStyle(bigStyle);
//
//
//
//                // instance of the NotificationManager service
//                NotificationManagerCompat notificationManager =
//                        NotificationManagerCompat.from(this);
//
//                // Build the notification and notify it using notification manager.
//                notificationManager.notify(notificationId, notificationBuilder.build());
//
//            }




        Connection conn = r.connection().hostname("192.168.6.26").port(28015).connect();


        conn.use("hackiiitd");
        r.table("fall").run(conn); //, ...) // refers to r.db('marvel').table('heroes')

        Cursor changeCursor = r.table("users").changes().run(conn);
        for (Object change : changeCursor) {
            System.out.println(change);
        }
    }






    //putting out requests
//    OkHttpClient client = new OkHttpClient();
//
//    String run(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url("http://glados.local:8008")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }


    // using json parsers
//    String fall_res = response.body().string().toString();
//    JSONObject json = new JSONObject(fall_res);


    // implemented onClick function
    public void onClick(View v){

        int notificationId = 101;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, MainActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);


        //Building notification layout
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Falling Incident")
                        .setContentText("The person you subscribed to might have fallen down")
                        .setContentIntent(viewPendingIntent);
//                        .addAction(R.drawable.ic_launcher, "Open Google", mapPendingIntent)
//                        .setStyle(bigStyle);



        // instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and notify it using notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());


    }
}

