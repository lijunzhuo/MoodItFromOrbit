package com.assign1.brianlu.mooditfromorbit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import org.apache.commons.lang3.ObjectUtils;

import static com.assign1.brianlu.mooditfromorbit.AddMood.REQUEST_CODE;

/**
 * Edits a mood that is clicked on
 * Created by FENGYI on 2017-03-12.
 */

public class EditMood extends AppCompatActivity implements MView<MainModel> {
    ImageView IMG;
    Bitmap imageBitmap;
    Mood mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);

        final MainController mc = MainApplication.getMainController();

        int moodId = getIntent().getExtras().getInt("moodId");
        mood = mc.getMe().getMoods().getMood(moodId);


        String moodEmotion = mood.getEmotion().getEmotion();
        String moodGS = mood.getSocialSituation();

        Spinner s_emotions = (Spinner) findViewById(R.id.Eemotions);
        s_emotions.setSelection(getSelectedItem(s_emotions,moodEmotion));


        Spinner g_status = (Spinner) findViewById(R.id.Egroupstatus);
        g_status.setSelection(getSelectedItem(g_status,moodGS));

        IMG = (ImageView) findViewById(R.id.Epic);
        if (mood.getImage() != null) {
            imageBitmap = mood.getImage();
            IMG.setImageBitmap(imageBitmap);
        }


        EditText get_comment = (EditText) findViewById(R.id.Ecomment);
        if (mood.getMessage()!= null){get_comment.setText(mood.getMessage());}


        Switch locationswitch = (Switch) findViewById(R.id.Elocations);
        if(mood.getLongitude() != null && mood.getLatitude() != null){
            locationswitch.setChecked(true);
        }

        mc.startLocationListen(this);

        //camera implementation
        Button cam = (Button) findViewById(R.id.Ecamera);
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
        Button updateButton = (Button) findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO get info from input
                //this creates a test mood and adds it

                // emotions, groupstatus, and comment from spinner and edittext
                Spinner s_emotions = (Spinner) findViewById(R.id.Eemotions);
                String t_emotions = s_emotions.getSelectedItem().toString();

                Spinner g_status = (Spinner) findViewById(R.id.Egroupstatus);
                String groupstring = g_status.getSelectedItem().toString();

                EditText get_comment = (EditText) findViewById(R.id.Ecomment);
                String commentstring = get_comment.getText().toString();

                MainController mc = MainApplication.getMainController();


                mood.setEmotion(mc.getEmotion(t_emotions));
                mood.setSocialSituation(groupstring);
                mood.setMessage(commentstring);
                mood.setImage(imageBitmap);





                // Remove the listener you previously added
                mc.stopLocationListener();

                Location moodLocation = mc.getLocation();

                //only if share location is toggled
                //  mood.setLocation(moodLocation);




                Switch locationswitch = (Switch) findViewById(R.id.Elocations);
                locationswitch.setTextOn("On"); // displayed text of the Switch whenever it is in checked or on state
                locationswitch.setTextOff("Off"); // displayed text of the Switch whenever it is in unchecked i.e. off state
                // if true or false
                Boolean switchState = locationswitch.isChecked();
                if (switchState){
                    // if on locations enabled
                    mood.setLocation(moodLocation);

                } else {
                    // if off locations disabled
                    mood.setLatitude(null);
                    mood.setLongitude(null);
                }
                Log.d("location", moodLocation.toString());
                mc.updateMoodList();

                finish();
            }
        });


        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mc.getMe().getMoods().delete(mood);
                mc.updateMoodList();
                finish();
            }

        });


        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);
    }

    private int getSelectedItem(Spinner s,String checkS){
        int index = 0;

        for (int i=0;i<s.getCount();i++){
            if (s.getItemAtPosition(i).equals(checkS)){
                index = i;
            }
        }
        return index;
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


