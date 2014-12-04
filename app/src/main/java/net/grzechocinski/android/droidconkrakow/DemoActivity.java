package net.grzechocinski.android.droidconkrakow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import net.grzechocinski.android.droidconkrakow.demo1.ActivityWithMultipleAsyncTasks;
import net.grzechocinski.android.droidconkrakow.demo2.ActivityWithLoaderNoForceLoad;
import net.grzechocinski.android.droidconkrakow.demo3.ActivityWithLoaderWithForceLoad;
import net.grzechocinski.android.droidconkrakow.demo4.ActivityWithLoaderWhichSupportsScreenRotation;
import net.grzechocinski.android.droidconkrakow.demo5.ActivityWithLoaderWhichSupportsOnStopAndRedelivery;
import net.grzechocinski.android.droidconkrakow.demo6.ActivityWithLoaderWhichSupportsRefreshWithoutCaching;
import net.grzechocinski.android.droidconkrakow.demo7.ActivityWithRobospice;
import net.grzechocinski.android.droidconkrakow.demo8.ActivityWithRobospiceWithOrientationSupport;
import net.grzechocinski.android.droidconkrakow.demo9.ActivityWithRxAndroid;


public class DemoActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_run_demo1:
                startActivity(new Intent(this, ActivityWithMultipleAsyncTasks.class));
                break;
            case R.id.btn_run_demo2:
                startActivity(new Intent(this, ActivityWithLoaderNoForceLoad.class));
                break;
            case R.id.btn_run_demo3:
                startActivity(new Intent(this, ActivityWithLoaderWithForceLoad.class));
                break;
            case R.id.btn_run_demo4:
                startActivity(new Intent(this, ActivityWithLoaderWhichSupportsScreenRotation.class));
                break;
            case R.id.btn_run_demo5:
                startActivity(new Intent(this, ActivityWithLoaderWhichSupportsOnStopAndRedelivery.class));
                break;
            case R.id.btn_run_demo6:
                startActivity(new Intent(this, ActivityWithLoaderWhichSupportsRefreshWithoutCaching.class));
                break;
            case R.id.btn_run_demo7:
                startActivity(new Intent(this, ActivityWithRobospice.class));
                break;
            case R.id.btn_run_demo8:
                startActivity(new Intent(this, ActivityWithRobospiceWithOrientationSupport.class));
                break;
            case R.id.btn_run_demo9:
                startActivity(new Intent(this, ActivityWithRxAndroid.class));
                break;
            case R.id.btn_run_gc:
                System.gc();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
