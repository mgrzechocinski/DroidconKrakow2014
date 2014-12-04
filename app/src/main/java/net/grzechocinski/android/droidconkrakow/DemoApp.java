package net.grzechocinski.android.droidconkrakow;

import android.app.Application;
import android.os.StrictMode;

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
    }
}
