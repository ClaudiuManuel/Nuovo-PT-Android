package com.example.nuovo_pt;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.nuovo_pt.api.ExerciseRepository;
import com.example.nuovo_pt.api.OnGetAPIResponseCallBack;
import com.example.nuovo_pt.api.Result;
import com.example.nuovo_pt.ui.AddClientFragment;
import com.example.nuovo_pt.ui.ClientWorkoutsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClientsAdditionListener,NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    ClientsHolder clientsHolder;
    NavigationView navigationView;
    Menu navMenu;
    NavController navController;
    DrawerLayout drawer;
    List<Result> exercises;
    ExerciseRepository exerciseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navMenu = navigationView.getMenu();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_clients, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_add_client)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        clientsHolder = ClientsHolder.getInstance();

        exerciseRepository = ExerciseRepository.getInstance();
        exercises = new ArrayList<>();
        //showExercises();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void addClient(Client client) {
        clientsHolder.addNewClient(client);
        navMenu.add(client.getName());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id != R.id.nav_add_client) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new ClientWorkoutsFragment(new Client((String) item.getTitle(),true)))
                    .commit();

        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new AddClientFragment())
                    .commit();
        }

        getSupportActionBar().setTitle(item.getTitle());
        drawer.close();
        return true;
    }

//    public void showExercises() {
//        exerciseRepository.getExercises(new OnGetAPIResponseCallBack() {
//            @Override
//            public void onSuccess(List<Result> exerciseListFromAPI) {
//                exercises = exerciseListFromAPI;
//                StringBuilder stringBuilder = new StringBuilder();
//                for (Result exercise : exerciseListFromAPI) {
//                    stringBuilder.append(exercise.getName() + " /// ");
//                }
//                Toast.makeText(MainActivity.this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError() {
//                Log.d("exerciseERROR","error in main activity");
//                Toast.makeText(MainActivity.this, "API error",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}