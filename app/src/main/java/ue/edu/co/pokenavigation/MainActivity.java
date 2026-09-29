package ue.edu.co.pokenavigation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ue.edu.co.pokenavigation.ui.FavoritesFragment;
import ue.edu.co.pokenavigation.ui.HomeFragment;
import ue.edu.co.pokenavigation.ui.InfoFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initObjects();
        configurarBottomNavigation();
        cargarFragment(new HomeFragment());
    }
    private Fragment obtenerFragment(int itemId) {
        if (itemId == R.id.navigation_home) {
            return new HomeFragment();
        }
        if (itemId == R.id.navigation_favorites) {
            return new FavoritesFragment();
        }
        if (itemId == R.id.navigation_info) {
            return new InfoFragment();
        }
        return null;
    }
    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
    private void configurarBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = obtenerFragment(item.getItemId());
            if (fragment == null) {
                return false;
            }
            cargarFragment(fragment);
            return true;
        });
    }
    private void initObjects() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }
}