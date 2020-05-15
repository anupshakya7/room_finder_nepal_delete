package com.example.roomfindernepalasn;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;



import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        BottomNavigationView bottomNav = findViewById(R.id.bottom_Navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,new Rent_Fragment()).commit();

    }

    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             Fragment selectedFragment=null;

            switch (item.getItemId()){
                case R.id.nav_rent:
                    selectedFragment= new Rent_Fragment();
                    break;
                case R.id.nav_sale:
                    selectedFragment= new Sale_Fragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment= new Profile_Fragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,selectedFragment).commit();
            return true;
        }
    };
}
