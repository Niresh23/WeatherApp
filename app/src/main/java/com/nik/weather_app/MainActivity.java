package com.nik.weather_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.InputType;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.nik.weather_app.dataBase.DataBaseHelper;
import com.nik.weather_app.dataBase.WeatherTable;
import com.nik.weather_app.rest.OpenWeatherRepo;
import com.nik.weather_app.rest.entities.WeatherRequestRestModel;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentSetting.OnCitySelectedListener,
FragmentMain.GetDataListener{

    private Map<String, Fragment.SavedState> fragmentSavedStates = new HashMap<>();

    //Переделать
    private String currentCity;
    private MenuListAdapter adapter = null;
    private String MSG_NO_DATA = "Нет данных";

    //Работа с фрагментами
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentMain fragmentMain = new FragmentMain();
    private FragmentSetting fragmentSetting = new FragmentSetting();
    //

    //База данных
    SQLiteDatabase database;
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
        initDatabase();

        if(savedInstanceState != null){
            fragmentMain = (FragmentMain) getSupportFragmentManager()
                            .getFragment(savedInstanceState, "fragmentMain");
            openFragment(fragmentMain);
        } else {
            Toast.makeText(getApplicationContext(),"savedInstanceState is null!!", Toast.LENGTH_SHORT)
                    .show();
            openFragment(fragmentMain);
        }


        //Работа с геоданными
        mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location loc = getLastKnownLocation();
        if(loc != null) {
            currentCity = getCityByLoc(loc);
            Toast.makeText(getApplicationContext(), currentCity, Toast.LENGTH_SHORT)
                    .show();
            updateWeatherData(currentCity);
        }
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
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
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

    private void initDatabase() {
        database = new DataBaseHelper(getApplicationContext()).getWritableDatabase();
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
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initFloatingAction() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                adapter.setThreeDays();
                break;
            }

            case R.id.action_5_days: {
                adapter.setFiveDays();
                break;
            }
            case R.id.action_settings: {

                break;
            }
            case R.id.action_help: {

                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), "Prekrasoe daleko", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, getResources().getString(R.string.menu_home), Toast.LENGTH_SHORT).show();
            openFragment(fragmentMain);
        } else if (id == R.id.nav_gallery) {
            showInputDialog();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {
            Toast.makeText(getApplicationContext(), getString(R.string.menu_tools), Toast.LENGTH_SHORT)
                    .show();
            fragmentSavedStates.put("fragmentMain",fragmentManager.saveFragmentInstanceState(fragmentMain));
            fragmentSetting.getCities(WeatherTable.getCities(database));
            openFragment(fragmentSetting);
            //openFragment(fragmentSetting);
        } else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(), getString(R.string.nav_header_title), Toast.LENGTH_SHORT)
                    .show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(getApplicationContext(), getString(R.string.menu_tools), Toast.LENGTH_SHORT)
                    .show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Ввод нового города
    private void showInputDialog() {
        if(!fragmentMain.isVisible()) openFragment(fragmentMain);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_city);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentCity = input.getText().toString().toUpperCase();
                if(WeatherTable.containsCity(database, currentCity)) {
                    Toast.makeText(getApplicationContext(), currentCity + " is in the data base", Toast.LENGTH_SHORT)
                            .show();
                    HashMap<String, String> information = WeatherTable.getInformationByCity(database, currentCity);
                    renderWeather(information);
                } else {
                    Toast.makeText(getApplicationContext(), currentCity + " is not in the data base", Toast.LENGTH_SHORT)
                            .show();
                    updateWeatherData(currentCity);
                }
            }
        });
        builder.show();
    }
    //

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
        if(WeatherTable.containsCity(database, currentCity)) {
            Toast.makeText(getApplicationContext(), currentCity + " was update in the database", Toast.LENGTH_SHORT)
                    .show();
            WeatherTable.update(database, model.name, model.sys.country, model.weather[0].description,
                    model.main.humidity, model.main.pressure, model.main.temp, model.dt, model.weather[0].id,
                    model.sys.sunrise, model.sys.sunset);
        } else {
            Toast.makeText(getApplicationContext(), currentCity + " was add in the database", Toast.LENGTH_SHORT)
                    .show();
            WeatherTable.addRec(database, model.name.toUpperCase(), model.sys.country, model.weather[0].description,
                    model.main.humidity, model.main.pressure, model.main.temp, model.dt, model.weather[0].id,
                    model.sys.sunrise, model.sys.sunset);
        }
        fragmentMain.setPlaceName(model.name, model.sys.country);
        fragmentMain.setDetails(model.weather[0].description, model.main.humidity, model.main.pressure);
        fragmentMain.setCurrentTemp(model.main.temp);
        fragmentMain.setUpdateText(model.dt);
        fragmentMain.setWeatherIcon(model.weather[0].id, model.sys.sunrise * 1000,
                model.sys.sunset * 1000);
    }

    private void renderWeather(HashMap<String, String> information) {
        fragmentMain.setPlaceName(information.get("city"), information.get("country"));
        fragmentMain.setDetails(information.get("description"), Float.parseFloat(information.get("humidity")), Float.parseFloat(information.get("pressure")));
        fragmentMain.setCurrentTemp(Float.parseFloat(information.get("temperature")));
        fragmentMain.setUpdateText(Long.parseLong(information.get("update")));
        fragmentMain.setWeatherIcon(Integer.parseInt(information.get("icon")), Long.parseLong(information.get("sunrise")) * 1000,
                Long.parseLong(information.get("sunset")) * 1000);
    }

    @Override
    public void citySelected(int cityID) {
        if(cityID == 1) showInputDialog();
        else if (cityID > 1){
            currentCity = WeatherTable.getCityById(database, cityID - 1);
            updateWeatherData(currentCity);
        }
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
