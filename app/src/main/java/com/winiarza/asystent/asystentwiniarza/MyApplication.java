package com.winiarza.asystent.asystentwiniarza;

import android.app.Application;

import com.winiarza.asystent.asystentwiniarza.db.DataManager;

public class MyApplication extends Application {

    private DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        dataManager = new DataManager(this.getApplicationContext());
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
