package com.example.hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.opentok.android.OpentokError;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opentok.android.Publisher;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class VideoCallActivity extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener {

    private static  String Api_key = "46760242";
    private static  String session_Id = "2_MX40Njc2MDI0Mn5-MTU5MDM1MDY1OTAzMX5BWXJsZlJya2gwWXdMSFZqZ2ptWjdCK1l-fg";
    private static  String token = "T1==cGFydG5lcl9pZD00Njc2MDI0MiZzaWc9YmY1ZDQ2NzUzMDNjNmY0ODczZGIwOTdhMGY3NzBhNmNhZWI4ZTVlNDpzZXNzaW9uX2lkPTJfTVg0ME5qYzJNREkwTW41LU1UVTVNRE0xTURZMU9UQXpNWDVCV1hKc1psSnlhMmd3V1hkTVNGWnFaMnB0V2pkQ0sxbC1mZyZjcmVhdGVfdGltZT0xNTkwNDA4NjY3Jm5vbmNlPTAuNDM0ODM0NjgxMDUwNDQ1NzYmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTU5MDQzMDI2NiZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static  final String log_tag = VideoCallActivity.class.getSimpleName();
    private static  final int RC_Video_Permission  = 124;

    private FrameLayout subscriber,publisher;
    private Session msession;
    private Publisher mpublisher;
    private Subscriber msubscriber;
    DatabaseReference userRef;

    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        cancel = findViewById(R.id.cancel_button);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        requestPermission();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mpublisher!=null)
                {
                    mpublisher.destroy();
                    Intent intent = new Intent(VideoCallActivity.this,Dashboard.class);
                    startActivity(intent);
                    finish();
                }


            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,VideoCallActivity.this);



    }

    @AfterPermissionGranted(RC_Video_Permission)
    private void requestPermission()
    {
        String[] perms = {Manifest.permission.INTERNET,Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};

        if(EasyPermissions.hasPermissions(this,perms))
        {

            subscriber = findViewById(R.id.subscriber_container);
            publisher = findViewById(R.id.publisher_container);



            msession = new Session.Builder(this,Api_key,session_Id).build();

            msession.setSessionListener(VideoCallActivity.this);
            msession.connect(token);

        }

        else
        {
            EasyPermissions.requestPermissions(this,"This app needs the permission to perform",RC_Video_Permission,perms);
        }
    }




    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

        Intent intent = new Intent(VideoCallActivity.this,Dashboard.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }




    @Override
    public void onConnected(Session session) {

        Log.i(log_tag,"Session connected");

        mpublisher = new Publisher.Builder(this).build();
        mpublisher.setPublisherListener(VideoCallActivity.this);

        publisher.addView(mpublisher.getView());

        if(mpublisher.getView() instanceof GLSurfaceView)
        {
            ((GLSurfaceView)mpublisher.getView()).setZOrderOnTop(true);



        }

        msession.publish(mpublisher);

    }

    @Override
    public void onDisconnected(Session session) {

    }





    @Override
    public void onStreamReceived(Session session, Stream stream) {

        Log.i(log_tag,"Session Received");

     //   if(msubscriber==null) // subscriber isnt talking to any one
       // {
            msubscriber = new Subscriber.Builder(this,stream).build();
            msession.subscribe(msubscriber);

            subscriber.addView(msubscriber.getView());


        //}
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {

        if(msubscriber!=null)
        {
            msubscriber.destroy();
        }

    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
