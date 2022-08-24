package com.arbib.admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arbib.admin_panel.fragments.AdminsFragment;
import com.arbib.admin_panel.fragments.ExperiencesFragment;
import com.arbib.admin_panel.fragments.HomeFragment;
import com.arbib.admin_panel.fragments.OperationsFragment;
import com.arbib.admin_panel.fragments.PositionsFragment;
import com.arbib.admin_panel.fragments.RolesFragment;
import com.arbib.admin_panel.fragments.SkillsFragment;
import com.arbib.admin_panel.fragments.UsersFragment;
import com.arbib.admin_panel.interfaces.ResponseListener;
import com.arbib.admin_panel.interfaces.operations.OperationsReceiverListener;
import com.arbib.admin_panel.objects.Operation;
import com.arbib.admin_panel.objects.Role;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MyDatabase database;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflate();
        database = new MyDatabase(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        replaceFragments(new HomeFragment());

        TextView name = headerView.findViewById(R.id.admin_name);

        name.setText("adil arbib");


































    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else super.onBackPressed();

    }

    private void inflate(){
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
    }

    private void replaceFragments(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                replaceFragments(new HomeFragment());
                break;
            case R.id.nav_admins:
                replaceFragments(new AdminsFragment());
                break;
            case R.id.nav_experiences:
                replaceFragments(new ExperiencesFragment());
                break;
            case R.id.nav_operations:
                replaceFragments(new OperationsFragment());
                break;
            case R.id.nav_positions:
                replaceFragments(new PositionsFragment());
                break;
            case R.id.nav_roles:
                replaceFragments(new RolesFragment());
                break;
            case R.id.nav_skills:
                replaceFragments(new SkillsFragment());
                break;
            case R.id.nav_users:
                replaceFragments(new UsersFragment());
                break;
            case R.id.nav_logout:
                Toast.makeText(getBaseContext(), "logout", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}