package com.nik.weather_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.text.InputType;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.nik.weather_app.cities_db.City;
import com.nik.weather_app.data.Weather;
import com.nik.weather_app.repository.Repository;
import com.nik.weather_app.rest.OpenWeatherRepo;
import com.nik.weather_app.rest.entities.WeatherRequestRestModel;
import com.nik.weather_app.ui.main.FragmentMain;
import com.nik.weather_app.ui.setting.SettingFragment;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements SettingFragment.OnCitySelectedListener,
FragmentMain.GetDataListener{

    //Переделать
    private String currentCity;
    private MenuListAdapter adapter = null;
    private String MSG_NO_DATA = "Нет данных";
    private String[] LOCATION_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int INITIAL_REQUEST = 1337;

    //Работа с фрагментами
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentMain fragmentMain = new FragmentMain();
    private SettingFragment settingFragment = new SettingFragment();
    //

    //База данных
    Repository repository;
    Weather weather = new Weather();
    //

    //Геоданные
    private String TAG = "LOCATION";
    private LocationManager mLocManager = null;
    private LocListener mLocListener = null;
    //

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initFloatingAction();
        initDrawerLayout(toolbar);

        repository = new Repository(getApplication());

        if(!canAccessLocation()) {
            requestPermissions(LOCATION_PERMISSION, INITIAL_REQUEST);
        }

        openFragment(fragmentMain);

        //Работа с геоданными
        if(canAccessLocation()) {
            mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location loc = getLastKnownLocation();
            if (loc != null) {
                currentCity = getCityByLoc(loc);
                Toast.makeText(getApplicationContext(), currentCity, Toast.LENGTH_SHORT)
                        .show();
                updateWeatherData(currentCity);
            }
        }
    }
    private boolean canAccessLocation() {
        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentManager.putFragment(outState, "fragmentMain", fragmentMain);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        if(mLocListener == null) mLocListener = new LocListener();
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000L, 50000.0F, mLocListener);
    }

    @Override
    protected void onPause() {
        if(mLocListener != null) mLocManager.removeUpdates(mLocListener);
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Location getLastKnownLocation() {
        mLocManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocManager.getProviders(true);
        Location bestLocation = null;
        for(String provider : providers) {
            Location loc = null;
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            } else {
                loc = mLocManager.getLastKnownLocation(provider);
            }
            if(loc == null) continue;
            if(bestLocation == null || loc.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = loc;
            }
        }
        return bestLocation;
    }
    private String getCityByLoc(Location loc) {

        final Geocoder geo = new Geocoder(this);

        // Try to get addresses list
        List<Address> list;
        try {
            list = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }

        // If list is empty, return "No data" string
        if (list.isEmpty()) return MSG_NO_DATA;

        // Get first element from List
        Address a = list.get(0);

        // Get a Postal Code
        final int index = a.getMaxAddressLineIndex();
        String city = null;
        if (index >= 0) {
            city = a.getLocality();
        }

        return city;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                                @NonNull int[] grantResults) {
        if(requestCode == 100) {
            boolean permissionsGranted = (grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
            if(permissionsGranted) recreate();
        }
    }

    //Открытие фрагментов
    private void openFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .addToBackStack(null).commit();
    }

    //Инициализация окружения
    private void initDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFloatingAction() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                openFragment(settingFragment)

        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh: {
                updateWeatherData(currentCity);
                break;
            }

            case R.id.action_3_days: {
                showInputDialog();
                break;
            }

            case R.id.action_5_days: {
                adapter.setFiveDays();
                break;
            }
            case R.id.action_settings: {
                openFragment(settingFragment);
                break;
            }
            case R.id.action_help:
                break;

            default: {
                Toast.makeText(getApplicationContext(), "Prekrasoe daleko", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //Ввод нового города
    private void showInputDialog() {
        if(!fragmentMain.isVisible()) openFragment(fragmentMain);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_city);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            currentCity = input.getText().toString().toUpperCase();
            updateWeatherData(currentCity);
            City city = new City();
            city.setCity(currentCity);
            });
        builder.show();
    }

    private void updateWeatherData(final String city) {
        OpenWeatherRepo.getSingletone().getAPI().loadWeather(city + ",ru",
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(Call<WeatherRequestRestModel> call, Response<WeatherRequestRestModel> response) {
                        if(response.body() != null && response.isSuccessful()) {
                            renderWeather(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        Toast.makeText(getBaseContext(), getString(R.string.network_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void renderWeather(WeatherRequestRestModel model) {
        weather.setCity(model.name);
        weather.setCountry(model.sys.country);
        weather.setDescription(model.weather[0].description);
        weather.setHumidity(model.main.humidity);
        weather.setPressure(model.main.pressure);
        weather.setTemperature(model.main.temp);
        weather.setUpdated(model.dt);
        weather.setIcon(getWeatherIcon(model.weather[0].id, model.sys.sunrise, model.sys.sunset));
    }

    public String getWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800) {
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime < sunset) {
                icon = getString(R.string.weather_sunny);
            } else {
                icon = getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = getString(R.string.weather_thunder);
                    break;
                }
                case 3: {
                    icon = getString(R.string.weather_drizzle);
                    break;
                }
                case 5: {
                    icon = getString(R.string.weather_rainy);
                    break;
                }
                case 6: {
                    icon = getString(R.string.weather_snowy);
                    break;
                }
                case 7: {
                    icon = getString(R.string.weather_foggy);
                    break;
                }
                case 8: {
                    icon = getString(R.string.weather_cloudy);
                    break;
                }
            }
        }
        return icon;
    }

    @Override
    public void citySelected(int cityID) {
        if(cityID == 1) showInputDialog();
    }

    @Override
    public void getData() {
        updateWeatherData(currentCity);
    }

    private class LocListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged" + location.toString());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {/*Empty*/}

        @Override
        public void onProviderEnabled(String s) {/*Empty*/}

        @Override
        public void onProviderDisabled(String s) {/*Empty*/}
    }
}
