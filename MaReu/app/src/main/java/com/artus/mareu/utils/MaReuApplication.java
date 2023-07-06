package com.artus.mareu.utils;


import android.app.Application;

import com.artus.mareu.di.MareuInjection;
import com.artus.mareu.repository.MareuRepository;

public class MaReuApplication extends Application {

    private static MareuRepository mareuRepository;


    public static MareuRepository getMareuRepository() {
        if(mareuRepository == null) mareuRepository = MareuInjection.createMareuRepository();
        return mareuRepository;
    }

    public void resetMareuRepository(){ mareuRepository = null;}
}
