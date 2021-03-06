/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
/**
 * creates mood from user input and adds it to the user
 * Created by cqtran on 2017-03-07.
 */

public class AddMood extends AppCompatActivity implements MView<MainModel> {
    ImageView IMG;
    Bitmap imageBitmap;

    public static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);

        IMG = (ImageView) findViewById(R.id.pic);

        MainController mc = MainApplication.getMainController();

        mc.startLocationListen(this);


        //camera implementation
        Button cam = (Button) findViewById(R.id.camera);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager())!= null)
                {
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        });








        // when done button is pressed
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO get info from input
                //this creates a test mood and adds it

                // emotions, groupstatus, and comment from spinner and edittext
                Spinner s_emotions = (Spinner) findViewById(R.id.emotions);
                String t_emotions = s_emotions.getSelectedItem().toString();

                Spinner g_status = (Spinner) findViewById(R.id.groupstatus);
                String groupstring = g_status.getSelectedItem().toString();

                EditText get_comment = (EditText) findViewById(R.id.comment);
                String commentstring = get_comment.getText().toString();

                MainController mc = MainApplication.getMainController();
                Mood mood = new Mood(mc.getEmotion(t_emotions), mc.getMe());
                mood.setSocialSituation(groupstring);
                mood.setMessage(commentstring);
                mood.setImage(imageBitmap);





                // Remove the listener you previously added
                mc.stopLocationListener();

                Location moodLocation = mc.getLocation();

                //only if share location is toggled
              //  mood.setLocation(moodLocation);




                Switch locationswitch = (Switch) findViewById(R.id.locations);
                locationswitch.setTextOn("On"); // displayed text of the Switch whenever it is in checked or on state
                locationswitch.setTextOff("Off"); // displayed text of the Switch whenever it is in unchecked i.e. off state
                // if true or false
                Boolean switchState = locationswitch.isChecked();
                if (switchState){
                    // if on locations enabled
                    mood.setLocation(moodLocation);

                } else {
                    // if off locations disabled
                }
                Log.d("location", moodLocation.toString());
                mc.addNewMood(mood);

                finish();
            }
        });







        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }



// setting photo to imageview
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                IMG.setImageBitmap(imageBitmap);

            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }
    public void update(MainModel mc){}


}
