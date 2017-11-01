package com.karrar.prog.askandpush;

import android.app.Notification;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView My_recive;
    EditText my_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        My_recive = (TextView) findViewById(R.id.my_recive);
        my_add = (EditText) findViewById(R.id.my_add);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                String s = my_add.getText().toString();
                myRef.setValue(s);
                my_add.setText("");
            }
        });

        //


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference root_ref = firebaseDatabase.getReference();

        root_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String message = dataSnapshot.child("message").getValue().toString();
                String username = dataSnapshot.child("username").getValue().toString();
                String s = My_recive.getText().toString();
                String m = username + " : " + message + System.lineSeparator() + s;

                My_recive.setText(m);
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
             toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Read from the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        DatabaseReference myRef2 = database.getReference("username");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("م", "Value is: " + value);
//                String s = My_recive.getText().toString();
//                String m = value + System.lineSeparator() + s;
//
//                My_recive.setText(m);
////                try {
////                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
////                    r.play();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
//                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("م", "Failed to read value.", error.toException());
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
