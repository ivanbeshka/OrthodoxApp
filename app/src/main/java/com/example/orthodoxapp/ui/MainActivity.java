package com.example.orthodoxapp.ui;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.orthodoxapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {

  private AppBarConfiguration mAppBarConfiguration;
  private BottomNavigationView bottomNavigationView;
  private NavController navController;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    toolbar = findViewById(R.id.toolbar_main);
    setSupportActionBar(toolbar);
    getSupportActionBar().hide();
    bottomNavigationView = findViewById(R.id.bottom_navv);

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.nav_tools, R.id.nav_dialog, R.id.nav_map, R.id.nav_msgs)
        .build();
    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(bottomNavigationView, navController);

    navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
      if (destination.getId() == R.id.nav_dialog) {
        hideBotNav();
        getSupportActionBar().show();
      } else {
        getSupportActionBar().hide();
        showBotNav();
      }
    });
  }

  public void setToolbarTitle(String title){
    toolbar.setTitle(title);
  }

  public void showBotNav() {
    bottomNavigationView.setVisibility(View.VISIBLE);
  }

  public void hideBotNav() {
    bottomNavigationView.setVisibility(View.GONE);
  }

}
