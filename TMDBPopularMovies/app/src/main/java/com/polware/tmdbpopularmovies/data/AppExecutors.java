package com.polware.tmdbpopularmovies.data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

// Executors run tasks in background
public class AppExecutors {
    private static AppExecutors instance;

    // Singleton Pattern
    public static AppExecutors getInstance(){
        if (instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    // Run the Retrofit calls in background (threads)
    private final ScheduledExecutorService myNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return myNetworkIO;
    }

}
