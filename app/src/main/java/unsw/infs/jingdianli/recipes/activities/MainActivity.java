package unsw.infs.jingdianli.recipes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import unsw.infs.jingdianli.recipes.R;
import unsw.infs.jingdianli.recipes.fragments.HomeFragment;
import unsw.infs.jingdianli.recipes.fragments.ProfileFragment;
import unsw.infs.jingdianli.recipes.fragments.SearchFragment;
import unsw.infs.jingdianli.recipes.quiz.QuizSelectionFragement;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new HomeFragment();
        swapFragment(fragment);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_item_home){
                    Fragment fragment = new HomeFragment();
                    swapFragment(fragment);
                }else if (menuItem.getItemId() == R.id.menu_item_search){
                    Fragment fragment = new SearchFragment();
                    swapFragment(fragment);
                }else if (menuItem.getItemId() == R.id.menu_item_profile){
                    Fragment fragment = new ProfileFragment();
                    swapFragment(fragment);
                }
                else if (menuItem.getItemId() == R.id.menu_item_quiz){
                    Fragment fragment = new QuizSelectionFragement ();
                    swapFragment(fragment);
                }
                return true;
            }
        });
    }

    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}
