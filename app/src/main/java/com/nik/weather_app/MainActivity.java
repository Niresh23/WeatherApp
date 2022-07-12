package com.nik.weather_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nik.weather_app.ui.main.FragmentMain;
import com.nik.weather_app.ui.setting.SettingFragment;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private final String[] LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private MainViewModel mainViewModel;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initFloatingAction();
        String string = getString(R.string.weather_cloudy);
        requestPermissions(LOCATION_PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);
        openFragment(FragmentMain.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            requestWeatherByLocation();
        }
    }

    private void requestWeatherByLocation() {
        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locManager != null) {
            Location location = getLocation(locManager);
            if (location != null) getCityByLocation(location);
        }
    }

    private void getCityByLocation(Location location) {

        final Geocoder geo = new Geocoder(this);
        // Try to get addresses list
        List<Address> list;
        try {
            list = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            // If list is empty, return "No data" string
            if (list.isEmpty()) return;
            // Get first element from List
            Address address = list.get(0);
            if (address.hasLatitude() && address.hasLongitude()) {
                String language = address.getLocale().getISO3Language();
            }

            // Get a Postal Code
            final int index = address.getMaxAddressLineIndex();
            if (index >= 0) {
                String city = address.getLocality() + "," + address.getCountryCode();
                if (address.getLocality() != null) {
                    mainViewModel.getWeather(city, "metric");
                    mainViewModel.getForecast(city, "metric");
                } else {
                    mainViewModel.getWeather(address.getLatitude(), address.getLongitude(), "metric");
                    mainViewModel.getForecast(address.getLatitude(), address.getLongitude(), "metric");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private Location getLocation(LocationManager locationManager) {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers) {
            Location loc = locationManager.getLastKnownLocation(provider);
            if (loc == null) continue;
            if (bestLocation == null || loc.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = loc;
            }
        }

        return bestLocation;
    }

    private void openFragment(Class<? extends Fragment> fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .replace(R.id.fragment_layout, fragment, null, null)
                .addToBackStack(null)
                .commit();
    }

    private void initFloatingAction() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            openFragment(SettingFragment.class);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
                break;
            }

            case R.id.action_change_ciy: {
                showInputDialog();
                break;
            }

            case R.id.action_help:
                break;

            default: {
                openFragment(FragmentMain.class);
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //Ввод нового города
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.action_change_city);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {

            });
        builder.show();
    }
}
