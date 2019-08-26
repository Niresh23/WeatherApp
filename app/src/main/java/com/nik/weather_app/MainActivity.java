package com.nik.weather_app;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.InputType;
import android.view.View;

import androidx.annotation.NonNull;
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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentSetting.OnFragmentInteractionListener {
    //Переделать
    private String currentCity; //Переделать
    //Переделать
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    FloatingActionButton fab;
    private MenuListAdapter adapter = null;

    //Работа с фрагментами
    private FragmentMain fragmentMain = new FragmentMain();
    private FragmentSetting fragmentSetting = new FragmentSetting();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    //

    //База данных
    SQLiteDatabase database;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        initDatabase();
        initDrawerLayout(toolbar);
        setSupportActionBar(toolbar);
        initFloatingAction();
        if(savedInstanceState == null) openFragment(fragmentMain);
    }

    private void initDatabase() {
        database = new DataBaseHelper(getApplicationContext()).getWritableDatabase();
    }
    //ОТкрытие фрагментов
    private void openFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .addToBackStack("").commit();
    }

    //Инициализация окружения
    private void initDrawerLayout(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initFloatingAction() {
        fab = findViewById(R.id.fab);
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
            openFragment(fragmentSetting);
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
    private void showInputDialog() {
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
                    HashMap<String, String> information = WeatherTable.getInformationByCity(database, currentCity);
                } else {
                    updateWeatherData(currentCity);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
        fragmentMain.setPlaceName(model.name, model.sys.country);
        fragmentMain.setDetails(model.weather[0].description, model.main.humidity, model.main.pressure);
        fragmentMain.setCurrentTemp(model.main.temp);
        fragmentMain.setUpdateText(model.dt);
        fragmentMain.setWeatherIcon(model.weather[0].id, model.sys.sunrise * 1000,
                model.sys.sunset * 1000);
    }
}
