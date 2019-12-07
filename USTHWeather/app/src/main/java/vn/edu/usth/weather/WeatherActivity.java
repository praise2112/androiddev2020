package vn.edu.usth.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Adapter adapter;
    private TabLayout tabLayout;
    private Toolbar myToolbar;

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setAppLocale("vi"); // change app language
        setContentView(R.layout.weather_activity);
        Log.i("onCreate()", "onCreate() method is active");


        adapter = new Adapter(getSupportFragmentManager(), getResources());
        viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Music
//        askPermissionAndWriteFile();    // save music to external storage from res
//        askPermissionAndReadFile();     // play music from external storage

//        new GetRequest().execute("http://ict.usth.edu.vn/wp-content/uploads/usth/usthlogo.png");

//        WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
//        weatherHttpClient.getWeatherData("London", "5");

    }



    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {
            this.writeFile();
        }
    }

    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (canRead) {
            this.readFile();
        }
    }

    private void readFile() {       // play music
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/music.mp3";
        MediaPlayer player = new MediaPlayer();

        try {
            player.setDataSource(path);
            player.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception of type : " + e.toString());
            e.printStackTrace();
        }
        player.start();
        Toast.makeText(getApplicationContext(), "Playing music", Toast.LENGTH_LONG).show();


    }

    private void writeFile() {  // save music
        File extStore = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        // ==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath() + "/music.mp3";
        Log.i("ExternalStorageDemo", "Save to: " + path);
        InputStream file = getApplicationContext().getResources().openRawResource(R.raw.music);

        try {
            File myFile = new File(path);
            FileOutputStream fOut = new FileOutputStream(myFile);

            byte[] buffer = new byte[file.available()];
            file.read(buffer);

            File targetFile = new File(path);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            fOut.close();

            Toast.makeText(getApplicationContext(), "music saved", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {   //request permission
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                Toast.makeText(getApplicationContext(), "Refreshing...", Toast.LENGTH_LONG).show();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

                return true;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "Starting a PrefActivity", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
//
//    public class WeatherHttpClient {
//        //        539487bbed5ea48fa30f9ae17bbbd4e0
////        http://api.weatherstack.com/forecast?access_key=539487bbed5ea48fa30f9ae17bbbd4e0&query=NewYork
////
//        private String BASE_URL = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=";
//        //        private String IMG_URL = "http://openweathermap.org/img/w/";
//        private String apiKey = "06d6bbbc9161428f80b40023190612";
////        "http://api.openweathermap.org/data/2.5/weather?q=London&cnt=5&aapid=592bfdab1607f4bec1bd11ac83400f2d"
////        http://api.worldweatheronline.com/premium/v1/weather.ashx?key=06d6bbbc9161428f80b40023190612&q=48.85,2.35&num_of_days=2&tp=3&format=json
//
//        public StringRequest getWeatherData(String location, String no_of_days) {
//            HttpURLConnection con = null;
//            InputStream is = null;
//            String url = BASE_URL + apiKey + "&q=" + location + "&num_of_days=" + no_of_days + "&tp=24" + "&format=json";
//
//            Log.i("URL", "getWeatherData: "+ url);
//            StringRequest request = new StringRequest(url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
////                            res = response;
//                            Log.i("USTHWeather", "Json response " + response);
////                            return response;
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.i("GetWeatherData", "onErrorResponse: "+ error);
//                        }
//                    });
//
//
//
//            return request;
//        }
//    }


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

