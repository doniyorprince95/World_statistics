package com.ikuchko.world_population;

import android.app.Application;

import com.firebase.client.Firebase;
import com.ikuchko.world_population.services.WorldBankService;

/**
 * Created by iliak on 4/5/16.
 */
public class WorldPopulationApplication extends Application {
    private static WorldPopulationApplication app;
    private Firebase firebaseRef;

    public static WorldPopulationApplication getAppInstance() {
        return app;
    }

    public Firebase getFirebaseRef() {
        return firebaseRef;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(this.getString(R.string.firebase_url));
    }
}
