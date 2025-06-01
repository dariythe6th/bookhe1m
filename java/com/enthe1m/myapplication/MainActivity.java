package com.enthe1m.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.enthe1m.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NavigationManager {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize CartManager
        CartManager.getInstance().initialize(this);

        // Setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_catalog) {
                selectedFragment = new CatalogFragment();
            } else if (id == R.id.nav_statistics) {
                selectedFragment = new StatisticsFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }else if (id == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            }

            if (selectedFragment != null) {
                navigateToFragment(selectedFragment, false);
            }
            return true;
        });

        // Open catalog by default if no saved state
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_catalog);
        }
    }

    @Override
    public void navigateToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
