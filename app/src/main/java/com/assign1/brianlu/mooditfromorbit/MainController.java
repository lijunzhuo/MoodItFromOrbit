/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

/**
 * this is our controller, it interacts with the model
 * Created by Gregory on 2017-03-06.
 *
 *
 */

public class MainController implements MController {
    MainModel mm = null;

    public MainController(MainModel mm){
        this.mm = mm;
    }

    public UserList getUsers() {
        return mm.getUsers();
    }

    /**
     * calls addUser() and setMe()
     * @param user
     */
    public void addUser(User user){
        mm.addUser(user);
        mm.setMe(user);
    }
    public void addFollower(User user){
        mm.addFollower(user);
    }

    public void addFollowing(User user){
        mm.addFollowing(user);
    }

    public void updateMoodList(){
        mm.updateMoodList();
    }
    public boolean checkSignIn(String userName){
        User me = mm.getUserByName(userName);

        if(me != null){
            mm.setMe(me);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkForUser(User user){
        return mm.checkForUser(user);
    }

    public User getMe(){
        return mm.getMe();
    }

    public Emotion getEmotion(String emotionName){
        return mm.getEmotion(emotionName);
    }

    /**
     * generate following moods and return them
     * @return following moods
     */
    public MoodList getFollowingMoods() {
        mm.generateFollowingMoods();
        return mm.getFollowingMoods();
    }

    public void generateFollowingMoods(){
        mm.generateFollowingMoods();
    }

    public void addNewMood(Mood mood){
        mm.addNewMood(mood);
    }

    public void startLocationListen(Context context){
        mm.startLocationListen(context);
    }

    public void stopLocationListener(){
        mm.stopLocationListener();
    }

    public Location getLocation(){
        return mm.getLocation();
    }

}
