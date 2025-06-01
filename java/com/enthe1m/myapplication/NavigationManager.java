package com.enthe1m.myapplication;

import androidx.fragment.app.Fragment;

public interface NavigationManager {
    void navigateToFragment(Fragment fragment, boolean addToBackStack);
}