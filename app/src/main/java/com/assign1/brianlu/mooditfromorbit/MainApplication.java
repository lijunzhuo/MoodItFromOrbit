/*
 * Copyright (c) $2017 CMPUT 301 University of Alberta. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.assign1.brianlu.mooditfromorbit;

import android.app.Application;

/**
 * this is where we store all of our local data
 *
 * Created by Gregory on 2017-03-06.
 *
 *
 */


public class MainApplication extends Application {

    transient private static MainModel model = null;

    public static MainModel getMainModel(){
        if(model == null){
            model = new MainModel();
        }
        return model;
    }

    transient private static MainController controller = null;

    public static MainController getMainController(){
        if(controller == null){
            controller = new MainController(getMainModel());
        }
        return controller;
    }
}
