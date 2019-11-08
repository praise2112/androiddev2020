package vn.edu.usth.weather;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("onCreate()", "onCreate() method is active");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart()", "onStart() method is active");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume()", "onResume() method is active");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause()", "onPause() method is active");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop()", "onStop() method is active");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy()", "onDestroy() method is active");
    }
}

/** App Start:
 * onCreate()
 * onStart()
 * onResume()
 * */

/**
 *  Back button:
 *  onPause()
 *  onStop()
 *  onDestroy()
 **/

/**
 * Home button:
 * onPause()
 * onStop()
 **/

/** Close:
 *  onDestroy()
 **/

/** Left button:
 *  onPause()
 *  onStop()
 *
 *  Click app again:
 *  onStart()
 *  onResume
 * */

