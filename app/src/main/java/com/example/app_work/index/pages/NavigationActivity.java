package com.example.app_work.index.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.app_work.R;
import com.example.app_work.index.pages.fragment_page.MySpaceFragment;
import com.example.app_work.index.pages.fragment_page.SchoolFragment;
import com.example.app_work.index.pages.fragment_page.SquareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SchoolFragment())
                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.index) {
                selectedFragment = new SchoolFragment();
            } else if (item.getItemId() == R.id.myspace) {
                selectedFragment = new MySpaceFragment();
            } else if (item.getItemId() == R.id.square) {
                selectedFragment = new SquareFragment();
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}
