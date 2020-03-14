package com.example.chooseyourmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    private Fragment mHomeFragment;
    private Fragment mListFragment;
    private Fragment mFavFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNav = findViewById(R.id.bottom_navigation_view);
        mBottomNav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        mHomeFragment = new HomeFragment();
        mListFragment = new ListFragment();
        mFavFragment = new FavFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mHomeFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.bottom_nav_home:
                            selectedFragment = mHomeFragment;
                            break;
                        case R.id.bottom_nav_fav:
                            selectedFragment = mFavFragment;
                            break;
                        case R.id.bottom_nav_list:
                            selectedFragment = mListFragment;
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}
