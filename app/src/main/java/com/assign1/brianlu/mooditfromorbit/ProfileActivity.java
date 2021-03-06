/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.content.Intent;
import android.location.Location;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * this activity class displays the current users mood history
 */
public class ProfileActivity extends AppCompatActivity implements MView<MainModel>{

    private ListView moodListView;
    private MoodListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        moodListView = (ListView) findViewById(R.id.profileListView);

        //used https://developer.android.com/training/appbar/setting-up.html#utility
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        MainController mc = MainApplication.getMainController();


        //back button
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(mc.getMe().getUserName());




        MainModel mm = MainApplication.getMainModel();
        mm.addView(this);





        moodListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent3 = new Intent(ProfileActivity.this, EditMood.class);
                intent3.putExtra("moodId",position);
                startActivity(intent3);
            }
        });
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        MainController mc = MainApplication.getMainController();
        Log.d("hello", mc.getMe().getId());
        adapter = new MoodListAdapter(this, mc.getMe().getMoods().getMoods());
        moodListView.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainModel mm = MainApplication.getMainModel();
        mm.deleteView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_mood:
                //switch to add mood activity
                Intent intent1 = new Intent(ProfileActivity.this, AddMood.class);
                startActivity(intent1);
                return true;
            case R.id.action_map:

                Intent intent2 = new Intent(ProfileActivity.this, MapActivity.class);
                startActivity(intent2);
                return true;

            case R.id.action_follow:
                // temporary because the main following function is not implemented
                MainController mc = MainApplication.getMainController();

                for(User user : mc.getUsers().getUsers()){
                    if(mc.getMe().getId().equals(user.getId())){
                        continue;
                    }
                    else{
                        mc.addFollowing(user);
                    }

                }


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        MenuItem sortItem = menu.findItem(R.id.action_sort);
        MenuView menuView =
                (MenuView) MenuItemCompat.getActionView(sortItem);
        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }



    public void update(MainModel m ){
        //TODO code to redisplay the data
        adapter.notifyDataSetChanged();
    }
}
