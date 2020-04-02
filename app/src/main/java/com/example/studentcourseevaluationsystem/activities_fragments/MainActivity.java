package com.example.studentcourseevaluationsystem.activities_fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentcourseevaluationsystem.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView navName = header.findViewById(R.id.navName);
        TextView navEmail = header.findViewById(R.id.navEmail);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        String firstname = pref.getString("first_name", "null");
        String lastname = pref.getString("last_name", "null");
        int student_id = pref.getInt("student_id", 0);
        navName.setText(firstname + " " + lastname);
        navEmail.setText("" + student_id + "@pnu.edu.sa");

        fragment = new courseFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContent, fragment);
        ft.commit();
    }//end onCreate();


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }//end onNavigationItemSelected

    private void displaySelectedScreen(int itemId) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        fragment = null;
        if (itemId == R.id.nav_profile) {
            fragment = new profileFragment();
        } else if (itemId == R.id.nav_courses) {
            fragment = new courseFragment();
        } else if (itemId == R.id.nav_about) {
            aboutMyApp();
        } else if (itemId == R.id.nav_logout) {
            logout();
        }//end multi-if
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();
        }//end if
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }//end displaySelectedScreen

    private void aboutMyApp() {
        // Alert Dialog with custom view

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("حول التطبيق");
        builder.setView(R.layout.about_dialog);
        builder.setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss dialog
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    private void logout() {
        //alert message!
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //adb.setView(alertDialogView);
        adb.setTitle("تسجيل الخروج");
        adb.setMessage("هل أنتِ متأكدة من رغبتك بتسجيل الخروج؟");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton("تجاهل", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }//end cancel onClick
        });//end set positive buttons
        adb.setNegativeButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //1- clear shared preferences
                SharedPreferences preferences = getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                //2- close app
                Intent i = new Intent(MainActivity.this, SplashScreen.class);
                startActivity(i);
                finish();
            }////end ok onClick
        });//end set negative buttons
        adb.show();
    }//end logout()
}//end fragment
